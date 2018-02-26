/* 
 *  Filename:    SampleMailSender 
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
package com.me.eng.infrastructure;

import com.me.eng.application.ApplicationContext;
import com.me.eng.services.ApplicationServices;
import com.me.eng.domain.Sample;
import com.me.eng.domain.SampleMail;
import com.me.eng.domain.reports.SampleReport;
import com.me.eng.domain.repositories.SampleRepository;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Matheus
 */
public class SampleMailSender
{
    private SampleMail sampleMail;
    private List<Sample> items = new LinkedList();

    public SampleMailSender( SampleMail sampleMail )
    {
        this.sampleMail = sampleMail;
    }

    public void setItems( List<Sample> items ) 
    {
        this.items = items;
    }
    
    public Mail send() throws Exception
    {
        final File out = FileUtils.createTempFile( "ENSAIO LCPT " + sampleMail.getSample().getSerial() + ".pdf" );
        
        SampleReport report = new SampleReport( sampleMail.getSample() );
        report.setLetterhead( false );
        report.setItems( items );
        report.generate( out );
        
        Mail mail = new Mail();
        mail.setSubject( "Laudo: LCPT " + sampleMail.getSample().getSerial() );
        mail.setContent( "Prezados,\nSegue Laudo em anexo" );
        mail.setSource( sampleMail.getSample() );
        mail.addRecipient( sampleMail.getEmail() );
        mail.addAttachment( out );
        
        final SampleRepository repository = ApplicationServices.getCurrent()
                                                    .getSampleRepository();
        
        mail.addObserver( new Observer()
        {
            @Override
            public void update( Observable o, Object arg )
            {
                try
                {
                    sampleMail.setStatus( mail.getStatus().ordinal() );
                    
                    if ( mail.getException() != null )
                    {
                        sampleMail.setReason( Objects.toString( mail.getException().getMessage(), "" ) );
                    }
                    
                     repository.updateSampleMail( sampleMail );
                }
                
                catch ( Exception e )
                {
                    ApplicationContext.getInstance().logException( e );
                }
                
                finally
                {   
                    if ( sampleMail.isCompleted() )
                    {
                        FileUtils.delete( out.getParentFile() );
                    }
                }
            }
        } );
        
        sampleMail.setStatus( Mail.Status.PENDING.ordinal() );
        
        SendMail.getInstance().addQueue( mail );
        
        return mail;
    }
}
