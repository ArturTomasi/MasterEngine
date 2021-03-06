/* 
 *  Filename:    JobPicker 
 *
 *  Author:      Artur Tomasi
 *  EMail:       tomasi.artur@gmail.com
 *  Internet:    www.masterengine.com.br
 *
 *  Copyright © 2018 by Over Line Ltda.
 *  95900-038, LAJEADO, RS
 *  BRAZIL
 *
 *  The copyright to the computer program(s) herein
 *  is the property of Over Line Ltda., Brazil.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda.
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.samples.ui.pickers;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.domain.Client;
import com.me.eng.samples.domain.Job;
import com.me.eng.samples.repositories.JobRepository;
import com.me.eng.core.ui.Callback;
import com.me.eng.samples.ui.editors.JobEditor;
import com.me.eng.core.ui.parts.SearchField;
import com.me.eng.core.ui.pickers.DefaultPicker;
import com.me.eng.core.ui.pickers.PickerPanel;
import com.me.eng.samples.ui.tables.JobTable;
import com.me.eng.core.ui.util.Prompts;
import com.me.eng.core.ui.util.Utils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class JobPicker
    extends 
        PickerPanel<Job>
{
    /**
     * pick
     * 
     * @param owner Component
     * @param callback Callback&lt;Job&gt;
     */
    public static void pick( Component owner, Callback<Job> callback )
    {
        pick( owner, null, callback );
    }
    
    /**
     * pick
     * 
     * @param owner Component
     * @param client Client
     * @param callback Callback&lt;Job&gt;
     */
    public static void pick( Component owner, Client client, Callback<Job> callback )
    {
        JobPicker jobPicker = new JobPicker();
        jobPicker.client = client;
        jobPicker.btAdd.setVisible( Client.isPersisted( client ) );
        
        try
        {
            jobPicker.jobTable.setElements( ApplicationServices
                                                .getCurrent()
                                                .getJobRepository() 
                                                .getJobs( client ) );
        }
        
        catch ( Exception e )
        {
            jobPicker.handleException( e );
        }
        
        DefaultPicker.createPicker( owner, jobPicker, callback );
    }

    private Client client;
    
    /**
     * JobPicker
     * 
     */
    public JobPicker()
    {
        initComponents();
    }

    /**
     * setSelectedItem
     * 
     * @param source Job
     */
    @Override
    public void setSelectedItem( Job source )
    {
        jobTable.setSelectedElement( source );
    }
    
    /**
     * getSelectedItem
     * 
     * @return Job
     */
    @Override
    public Job getSelectedItem()
    {
        return jobTable.getSelectedElement();
    }
    
    /**
     * getSelectedJob
     * 
     * @return Job
     */
    private Job getSelectedJob()
    {
        Job job = getSelectedItem();
        
        if ( job == null )
        {
            Prompts.alert( "Selecione uma obra!" );
        }
        
        return job;
    }
    
    /**
     * search
     * 
     */
    private void search()
    {
        Job found = Utils.search( searchField.getText(), 
                                  jobTable.getSelectedElement(), 
                                  jobTable.getElements(), 
                                  (Job value) -> value.getName() );

        if ( found != null )
        {
            jobTable.setSelectedElement( found );
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        btAdd.setLabel( "Novo" );
        btEdit.setLabel( "Editar" );
        btDelete.setLabel( "Excluir" );
        
        jobTable.setCheckmark( true );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" );
        
        vlayout.appendChild( btAdd );
        vlayout.appendChild( btEdit );
        vlayout.appendChild( btDelete );
        
        Hlayout hlayout = new Hlayout();
        hlayout.setHflex( "true" );
        hlayout.setVflex( "true" );
        
        hlayout.appendChild( jobTable );
        hlayout.appendChild( vlayout );
        
        Vlayout mainVlayout = new Vlayout();
        mainVlayout.setHflex( "true" );
        mainVlayout.setVflex( "true" );
        mainVlayout.setSpacing( "10px" );
        mainVlayout.appendChild( searchField );
        mainVlayout.appendChild( hlayout );
        
        appendChild( mainVlayout );
        
        btAdd.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, (Event t) ->
        {
            Job job = new Job();
            job.setClient( client ); 
            
            JobEditor.edit( JobPicker.this, new Callback<Job>( job )
            {
                @Override
                public void acceptInput()
                {
                    try
                    {
                        ApplicationServices.getCurrent()
                                .getJobRepository()
                                .add( getSource() );
                        
                        jobTable.addElement( getSource() );
                        jobTable.setSelectedElement( getSource() );
                    }
                    
                    catch ( Exception e )
                    {
                        handleException( e );
                    }
                }
            } );
        });
        
        btEdit.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, (Event t) ->
        {
            Job job = getSelectedJob();
            
            if ( job != null )
            {
                JobEditor.edit( JobPicker.this, new Callback<Job>( job )
                {
                    @Override
                    public void acceptInput()
                    {
                        try
                        {
                            ApplicationServices.getCurrent()
                                    .getJobRepository()
                                    .update( getSource() );
                            
                            jobTable.updateElement( getSource() );
                        }
                        
                        catch ( Exception e )
                        {
                            handleException( e );
                        }
                    }
                } );
            }
        });
        
        btDelete.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, (Event t) ->
        {
            Job job = getSelectedJob();
            
            if ( job != null )
            {
                Prompts.confirm( "Realmente excluir \"" + job + "\"?", new Callback<Job>( job )
                {
                    @Override
                    public void acceptInput()
                    {
                        try
                        {
                            JobRepository repository = ApplicationServices.getCurrent()
                                    .getJobRepository();
                            
                            int v = repository.countSamples( getSource() );
                            
                            if ( v > 0 )
                            {
                                Prompts.alert( "Esta obra possui " + v + " amostra(s) registradas.\n" +
                                        "Não é possível proceder com a exclusão." );
                            }
                            
                            else
                            {
                                repository.delete( getSource() );
                                jobTable.removeElement( getSource() );
                            }
                        }
                        
                        catch ( Exception e )
                        {
                            handleException( e );
                        }
                    }
                } );
            }
        });
        
        searchField.addEventListener( SearchField.Events.ON_SEARCH, (Event t) ->
        {
            search();
        });
    }
    
    private SearchField searchField = new SearchField();
    
    private JobTable jobTable = new JobTable();
    private Toolbarbutton btAdd = new Toolbarbutton();
    private Toolbarbutton btEdit = new Toolbarbutton();
    private Toolbarbutton btDelete = new Toolbarbutton();
}
