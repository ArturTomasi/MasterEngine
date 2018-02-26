/* 
 *  Filename:    ReportApplicationViewUI 
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
import com.me.eng.application.ResourceLocator;
import com.me.eng.domain.Sample;
import com.me.eng.domain.SampleFilter;
import com.me.eng.domain.SampleFormmater;
import com.me.eng.domain.SampleMail;
import com.me.eng.domain.reports.SampleReport;
import com.me.eng.domain.repositories.SampleRepository;
import com.me.eng.domain.util.SampleValidator;
import com.me.eng.infrastructure.FileUtils;
import com.me.eng.infrastructure.Mail;
import com.me.eng.infrastructure.SampleMailSender;
import com.me.eng.infrastructure.SendMail;
import com.me.eng.ui.Callback;
import com.me.eng.ui.apps.Action;
import com.me.eng.ui.editors.Errors;
import com.me.eng.ui.panes.SampleFilterPane;
import com.me.eng.ui.tables.Column;
import com.me.eng.ui.tables.SampleTable;
import com.me.eng.ui.tables.sample.SampleColumns;
import com.me.eng.ui.util.Prompts;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class ReportApplicationViewUI
    extends 
        ApplicationViewUI
{
    private SampleFilter filter;
    
    private SampleFormmater formmater = SampleFormmater.newInstance();
    
    private Map<String, List<Sample>> jobs;
    
    public ReportApplicationViewUI()
    {
        setLabel( "Relatórios" );
        setIcon( "sb_report.png" );
        
        addAction( "Relatórios", generateAction, generateLetterHeadAction );
    }
    
    @Override
    public void refreshContent()
    {
        filter = ApplicationContext.getInstance().getSharedSampleFilter();
        
        sampleFilterPane.setFilter( filter );
        
        loadSamples();
    }
    
    private void loadSamples()
    {
        try
        {
            sampleFilterPane.getFilter( filter );
            
            List<Sample> samples = ApplicationServices.getCurrent()
                        .getSampleRepository() 
                        .getSamples( filter );
            
            jobs = new LinkedHashMap();

            List<Sample> result = new LinkedList();
            
            for ( Sample s : samples )
            {
                String key = key( s );

                List<Sample> items = jobs.getOrDefault( key, new LinkedList() );
                
                if ( items.isEmpty() )
                {
                    result.add( s );
                }

                items.add( s );
                
                jobs.put( key, items );
            }
            
            sampleTable.setElements( result );
        }
        
        catch ( Exception ex )
        {
            handleException( ex );
        }
    }
    
    private String key( Sample s )
    {
        return "#" + s.getJob().hashCode() + "#" + s.getDateRupture().hashCode();
    }
    
    private void generateReport()
    {
        generateReport( false );
    }
    
    private void generateReport( boolean letterHead )
    {
        Sample sample = sampleTable.getSelectedElement();
        
        if ( sample == null )
        {
            Messagebox.show( "Selecione um item!" );
            return;
        }
        
        List<Sample> items = jobs.getOrDefault( key( sample ) , new LinkedList() );
        
        String erros = SampleValidator.getInstance().dontHaveResistences( items );
        
        if ( erros != null )
        {
            Messagebox.show( erros );
            return;
        }
        
        File out = FileUtils.createTempFile( "ENSAIO LCPT " + sample.getSerial() + ".pdf" );

        try
        {
            SampleReport report = new SampleReport( sample );
            report.setLetterhead( letterHead );
            report.setItems( items );
            report.generate( out );

            Filedownload.save( out, "application/pdf" );
        }

        catch ( Exception ex )
        {
            handleException( ex );
        }
    }
    
    private void sendMails() throws Exception
    {
        Prompts.confirm( "Reenviar os e-mails que já foram entregues?", 
                         new Callback()
        {
            @Override
            public void acceptInput() throws Exception
            {
                sendMails( sampleTable.getElements() );
            }

            @Override
            public void rejectInput() throws Exception
            {
                SampleRepository repository = ApplicationServices.getCurrent()      
                                                    .getSampleRepository();
                
                List<Sample> samples = sampleTable.getElements();
                
                for ( Iterator<Sample> it = samples.iterator(); it.hasNext(); )
                {
                    if ( repository.hasSuccessSampleMail( it.next() ) )
                    {
                        it.remove();
                    }
                }
                
                sendMails( samples );
            }
        } );
    }
    
    private void sendMails( List<Sample> samples ) throws Exception
    {
        Errors errors = new Errors();

        for ( Sample sample : samples )
        {
            sendMail( sample, errors );
        }

        errors.validate( ReportApplicationViewUI.this );
    }
    
    private void sendMail( Sample value ) throws Exception
    {
        Errors e = new Errors();
        
        sendMail( value, e );
        
        e.validate( this );
    }
    
    private void sendMail( Sample value, Errors e ) throws Exception
    {
        if ( value.getContact() == null || value.getContact().getEmail() == null )
        {
            e.addError( formmater.formatId( value ) + ": Contato sem e-mail cadastrado!" );
            return;
        }
        
        if ( SendMail.getInstance().hasPendingMailBySource( value ) )
        {
            e.addError( formmater.formatId( value ) + ": Já existe um e-mail pendente para envio deste laudo!" );
            return;
        }
        
        List<Sample> items = jobs.getOrDefault( key( value ) , new LinkedList() );
        
        String erros = SampleValidator.getInstance().dontHaveResistences( items );
        
        if ( erros != null )
        {
            e.addError( erros );
            return;
        }
        
        SampleMail sampleMail = value.generateMail();

        final Image img = (Image) getFellow( "sample-mail-status-" + value.getId(), true );
        
        if ( img != null )
        {
            final Desktop desktop = getDesktop();

            desktop.enableServerPush( true );

            sampleMail.addObserver( new Observer()
            {
                @Override
                public void update( Observable o, Object arg )
                {
                    try
                    {
                        if ( Executions.getCurrent() == null )
                        {
                            Executions.activate( desktop );
                        }
                        
                        updateImageStatus( sampleMail, img );
                    }

                    catch ( Exception e )
                    {
                        ApplicationContext.getInstance().handleException( e );
                    }

                    finally
                    {
                        if ( sampleMail.isCompleted() )
                        {
                            sampleMail.deleteObserver( this );
                        }

                        Executions.deactivate( desktop );

                        if ( ! SendMail.getInstance().hasEmails() )
                        {
                            if ( sampleMail.isCompleted() )
                            {
                                desktop.enableServerPush( false );
                            }
                        }
                    }
                }
            } );
        }
        
        ApplicationServices.getCurrent()
                .getSampleRepository()
                .addSampleMail( sampleMail );

        SampleMailSender sender = new SampleMailSender( sampleMail );
        sender.setItems( jobs.getOrDefault( key( value ), new LinkedList() ) );
        sender.send();
    }
    
    private void updateImageStatus( SampleMail lastMail, Image img )
    {
        Mail.Status status = Mail.Status.IDLE;
        
        if ( lastMail != null )
        {
            status = lastMail.getStatus();

            if ( lastMail.isSending() )
            {
                if ( ! SendMail.getInstance().hasPendingMailBySource( lastMail.getSample() ) )
                {
                    status = Mail.Status.UNKNOWN;

                    img.setTooltiptext( "O email está demarcado como pendente," +
                                    "porém, não está na fila de envio.\n" +
                                    "Possivelmente ocorreu algum erro ao atualizar o status no banco de dados.\n" + 
                                    "Por gentileza, verifique sua caixa de saída ou itens enviados\n" + 
                                    "do seu e-mail para identificar se o envio ocorreu corretamente.\n" + 
                                    "Caso estiver tendo dificuldades, entre em contato com o Suporte do Sistema." ); 
                }
            }

            else if ( lastMail.isFail() )
            {
                img.setTooltiptext( "Falha no envio\n" + lastMail.getReason() );
            }
        }
        
        img.setSrc( ResourceLocator.getImageResource( status.getIcon() ) );
    }
    
    protected void initComponents()
    {
        sampleFilterPane = new SampleFilterPane();
        
        sampleTable = new SampleTable();
        sampleTable.addContextAction( generateAction );
        sampleTable.addContextAction( generateLetterHeadAction );
        
        sampleTable.removeColumn( SampleColumns.CP );
        sampleTable.removeColumn( SampleColumns.RESISTENCE );
        sampleTable.removeColumn( SampleColumns.RC );
        sampleTable.removeColumn( SampleColumns.DIM_H );
        sampleTable.removeColumn( SampleColumns.DIM_W );
        sampleTable.removeColumn( SampleColumns.FC );
        sampleTable.removeColumn( SampleColumns.NAME );
        sampleTable.removeColumn( SampleColumns.MOLD_DATE );
        sampleTable.removeColumn( SampleColumns.PROOF );
        sampleTable.removeColumn( SampleColumns.TYPE_RUPTURE );
        
        sampleTable.addColumn( 0, new Column<Sample>()
        {
            @Override
            public String getLabel()
            {
                return "Email";
            }

            @Override
            public Object getValueAt( final Sample value )
            {
                Image img = new Image();
                img.setId( "sample-mail-status-" + value.getId() );
                img.setWidth( "16px" );
                img.setHeight( "16px" );
                
                Toolbarbutton btSendMail = new Toolbarbutton();
                btSendMail.setLabel( "Enviar" );
                btSendMail.setImage( ResourceLocator.getImageResource( "ti_mail.png" ) );
                btSendMail.addEventListener( Events.ON_CLICK, new EventListener<Event>()
                {
                    @Override
                    public void onEvent( Event t ) throws Exception
                    {
                        sendMail( value );
                    }
                } );
                
                try
                {
                    SampleMail lastMail = ApplicationServices.getCurrent()
                                                .getSampleRepository()
                                                .getLastSampleMail( value );

                    updateImageStatus( lastMail, img );
                }
                
                catch ( Exception e )
                {
                    throw new RuntimeException( e );
                }
                
                Hbox hbox = new Hbox();
                hbox.setHflex( "true" );
                hbox.setAlign( "center" );
                hbox.appendChild( img );
                hbox.appendChild( btSendMail );
                
                return hbox;
            }
        } );
        
        Button btSend = new Button();
        btSend.setLabel( "Enviar e-mail para todos" );
        
        setVflex( "true" );
        setHflex( "true" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setSpacing( "10px" );
        vlayout.setHflex( "true" );
        vlayout.setVflex( "true" );
        vlayout.appendChild( sampleFilterPane );
        vlayout.appendChild( btSend );
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
        
        btSend.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                sendMails();
            }
        } );
    }
    
    private SampleFilterPane sampleFilterPane;
    private SampleTable sampleTable;
    
    private Action generateAction = new Action( "", "Gerar", "Gerar Relatório" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            generateReport();
        }
    };
    
    private Action generateLetterHeadAction = new Action( "", "Gerar para papel timbrado", "Gerar Relatório para papel timbrado" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            generateReport( true );
        }
    };
}
