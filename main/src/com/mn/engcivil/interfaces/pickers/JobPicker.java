package com.mn.engcivil.interfaces.pickers;

import com.mn.engcivil.application.ApplicationServices;
import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Job;
import com.mn.engcivil.domain.repositories.JobRepository;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.editors.JobEditor;
import com.mn.engcivil.interfaces.parts.SearchField;
import com.mn.engcivil.interfaces.tables.JobTable;
import com.mn.engcivil.interfaces.util.Prompts;
import com.mn.engcivil.interfaces.util.Utils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Messagebox;
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
    public static void pick( Component owner, Callback<Job> callback )
    {
        pick( owner, null, callback );
    }
    
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
    
    public JobPicker()
    {
        initComponents();
    }

    @Override
    public void setSelectedItem( Job source )
    {
        jobTable.setSelectedElement( source );
    }
    
    @Override
    public Job getSelectedItem()
    {
        return jobTable.getSelectedElement();
    }
    
    private Job getSelectedJob()
    {
        Job job = getSelectedItem();
        
        if ( job == null )
        {
            Messagebox.show( "Selecione uma obra!" );
        }
        
        return job;
    }
    
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
                                Messagebox.show( "Esta obra possui " + v + " amostra(s) registradas.\n" +
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
