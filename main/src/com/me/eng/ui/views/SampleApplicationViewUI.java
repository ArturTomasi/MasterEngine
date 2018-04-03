/* 
 *  Filename:    SampleApplicationViewUI 
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
package com.me.eng.ui.views;

import com.me.eng.application.ApplicationContext;
import com.me.eng.services.ApplicationServices;
import com.me.eng.domain.Client;
import com.me.eng.domain.Sample;
import com.me.eng.domain.SampleFilter;
import com.me.eng.domain.reports.ListSamplesReport;
import com.me.eng.infrastructure.FileUtils;
import com.me.eng.ui.Callback;
import com.me.eng.ui.apps.Action;
import com.me.eng.ui.editors.SampleEditor;
import com.me.eng.ui.panes.SampleFilterPane;
import com.me.eng.ui.tables.SampleTable;
import java.io.File;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class SampleApplicationViewUI
    extends 
        ApplicationViewUI
{
    private SampleFilter filter;
    
    /**
     * SampleApplicationViewUI
     * 
     */
    public SampleApplicationViewUI()
    {
        setLabel( "Amostras" );
        setIcon( "sb_sample.png" );
        
        addAction( "Amostras", addAction, editAction, deleteAction, proofAction, relationAction );
    }
    
    /**
     * refreshContent
     * 
     */
    @Override
    public void refreshContent()
    {
        filter = ApplicationContext.getInstance().getSharedSampleFilter();
        
        sampleFilterPane.setFilter( filter );
        
        loadSamples();
    }
    
    private void printSamples()
    {
        try
        {
            sampleFilterPane.getFilter( filter );

            filter.setOnlyRootSamples( false );
            
            File out = FileUtils.createTempFile( "Relação de corpos de provas.pdf" );

            ListSamplesReport report = new ListSamplesReport();
            
            report.setSamples( ApplicationServices.getCurrent()
                                        .getSampleRepository()
                                        .getSamples( filter ) );
            
            report.generateReport( out );
            
            Filedownload.save( out, "application/pdf" );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    /**
     * loadSamples
     * 
     */
    private void loadSamples()
    {
        try
        {
            sampleFilterPane.getFilter( filter );
            
            filter.setOnlyRootSamples( false );
            
            sampleTable.setElements( ApplicationServices.getCurrent()
                                        .getSampleRepository()
                                        .getSamples( filter ) );
        }
        
        catch ( Exception ex )
        {
            handleException( ex );
        }
    }
    
    /**
     * addSample
     * 
     */
    private void addSample()
    {
        try
        {
            Client client = sampleFilterPane.getSelectedClient();

            if ( ! Client.isPersisted( client ) )
            {
                Messagebox.show( "Selecione um cliente!" );
            }

            else
            {
                Sample sample = ApplicationServices.getCurrent()
                                            .getSampleRepository().getLastSample( client );

                
                sample.setId( null );
                sample.setParent( null );
                sample.setResistence( null );
                sample.setDateCreated( new Date( System.currentTimeMillis() ) ); 
                sample.setClient( client );
                sample.setUser( ApplicationContext.getInstance().getActiveUser() );
                
                SampleEditor.edit( this, new Callback<Sample>( sample ) 
                {
                    @Override
                    public void acceptInput() throws Exception
                    {
                        Sample sample = getSource();
                        
                        List<Sample> proofs = new LinkedList( sample.getProofs() );
                                
                        sample.getProofs().clear();
                        
                        ApplicationServices.getCurrent()
                                .getSampleRepository()
                                .add( sample );

                        sampleTable.addElement( sample );
                        
                        if ( ! proofs.isEmpty() )
                        {
                            for ( Sample proof : proofs )
                            {
                                ApplicationServices.getCurrent()
                                                   .getSampleRepository()
                                                   .add( proof );
                                
                                sampleTable.addElement( proof );
                            }
                            
                            sample.setProofs( proofs );
                            
                            ApplicationServices.getCurrent()
                                .getSampleRepository()
                                .update( sample );
                            
                            sampleTable.updateElement( sample );
                        }
                        
                    }
                } );
            }
        }
        
        catch ( Exception e )
        {
            Logger.getGlobal().log( Level.SEVERE, e.getMessage() );
        }
    }
    
    /**
     * editSample
     * 
     */
    private void editSample()
    {
        Sample sample = getSelectedSample();
        
        if ( sample != null )
        {
            SampleEditor.edit( this, new Callback<Sample>( sample ) 
            {
                @Override
                public void acceptInput() throws Exception
                {
                    ApplicationServices.getCurrent()
                            .getSampleRepository()
                            .update( getSource() );

                    sampleTable.updateElement( getSource() );

                    for ( Sample proof : getSource().getProofs() )
                    {
                        sampleTable.updateElement( proof );
                    }
                }
            } );
        }
    }
    
    /**
     * deleteSample
     * 
     */
    private void deleteSample()
    {
        Sample sample = getSelectedSample();
        
        if ( sample != null )
        {
            if ( ! sample.getProofs().isEmpty() )
            {
                Messagebox.show( "Não é possível a exclusão desta Amostra.\n" + 
                                 "Você deve excluir a(s) Contra(s) prova(s) primeiro." );
                
                return;
            }
            
            Messagebox.show( "Realmente excluir a Amostra " + sample.getId() + " ?", 
                         "Confirmação", 
                         Messagebox.YES | Messagebox.NO, 
                         Messagebox.QUESTION, 
                         new EventListener<Event>()
            {
                @Override
                public void onEvent( Event e ) throws Exception
                {
                    if ( ( (Integer) e.getData() ) == Messagebox.YES )
                    {
                        Sample parent = sample.unbound();
                        
                        ApplicationServices.getCurrent()
                                .getSampleRepository()
                                .delete( sample );

                        sampleTable.removeElement( sample );
                        
                        if ( parent != null )
                        {
                            ApplicationServices.getCurrent()
                                .getSampleRepository()
                                .update( parent );
                            
                            sampleTable.updateElement( parent );
                        }
                    }
                }
            } );
        }
    }
    
    /**
     * proofSample
     * 
     */
    private void proofSample()
    {
        final Sample sample = getSelectedSample();
        
        if ( sample != null )
        {
            try
            {
                if ( sample.getParent() != null )
                {
                    Messagebox.show( "Esta já é uma contra prova!" );
                    return;
                }
                
                Sample proof = sample.createProof();
                
                SampleEditor.edit( this, new Callback<Sample>( proof )
                {
                    @Override
                    public void acceptInput() throws Exception
                    {
                        ApplicationServices.getCurrent()
                                .getSampleRepository()
                                .add( getSource() );
                        
                        ApplicationServices.getCurrent()
                                .getSampleRepository()
                                .update( getSource().getParent() );

                        int index = sampleTable.getElements().indexOf( getSource().getParent() );
                        
                        if ( index == -1 )
                        {
                            index = sampleTable.getElements().size();
                        }
                        
                        else
                        {
                            index += getSource().getParent().getProofs().size();
                        }
                        
                        sampleTable.addElement( getSource(), index );
                        sampleTable.updateElement( getSource().getParent() );
                        sampleTable.setSelectedElement( getSource() );
                    }

                    @Override
                    public void cancelInput()
                    {
                        proof.unbound();
                    }
                } );
            }
            
            catch ( Exception e )
            {
                ApplicationContext.getInstance().handleException( e );
            }
        }
    }
    
    /**
     * getSelectedSample
     * 
     * @return Sample
     */
    private Sample getSelectedSample()
    {
        Sample sample = sampleTable.getSelectedElement();
        
        if ( sample == null )
        {
            Messagebox.show( "Selecione uma amostra!" );
        }
        
        return sample;
    }
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents()
    {
        sampleFilterPane = new SampleFilterPane();
        
//        sampleTree = new SampleTree();
        sampleTable = new SampleTable();
        sampleTable.enableInplace();
        sampleTable.addContextAction( proofAction );
        sampleTable.addContextAction( editAction );
        sampleTable.addContextAction( deleteAction );
        
        setVflex( "true" );
        setHflex( "true" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setSpacing( "10px" );
        vlayout.setHflex( "true" );
        vlayout.setVflex( "true" );
        vlayout.appendChild( sampleFilterPane );
        vlayout.appendChild( sampleTable );
        
        appendChild( vlayout );
        
        sampleFilterPane.addEventListener( org.zkoss.zk.ui.event.Events.ON_CHANGE, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                loadSamples();
            }
        } );
    }
    
    private SampleFilterPane sampleFilterPane;
    
    private SampleTable sampleTable;
    
    private Action addAction = new Action( "", "Nova", "Nova amostra" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            addSample();
        }
    };
    
    private Action editAction = new Action( "", "Editar", "Editar amostra" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            editSample();
        }
    };
    
    private Action deleteAction = new Action( "", "Excluir", "Excluir amostra" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            deleteSample();
        }
    };
    
    private Action proofAction = new Action( "", "Contra prova", "Criar contra prova" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            proofSample();
        }
    };
    
    private Action relationAction = new Action( "", "Imprimir", "Imprimir relação de amostras" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            printSamples();
        }
    };
}
