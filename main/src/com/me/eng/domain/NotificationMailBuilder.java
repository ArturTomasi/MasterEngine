/* 
 *  Filename:    NotificationMailBuilder 
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
package com.me.eng.domain;

import com.me.eng.infrastructure.Mail;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class NotificationMailBuilder
{
    public static NotificationMailBuilder withSamples( List<Sample> items )
    {
        return new NotificationMailBuilder( items );
    }
    
    private List<Sample> items;

    /**
     * NotificationMailBuilder
     * 
     * @param items List&lt;Sample&gt;
     */
    NotificationMailBuilder( List<Sample> items )
    {
        this.items = items;
    }
    
    /**
     * build
     * 
     * @return Mail
     */
    public Mail build()
    {
        Mail mail = new Mail();
        mail.setSubject( "Relatório de Corpos de Prova a serem rompidos" );

        StringBuilder sb = new StringBuilder();
        sb.append( "<table border=\"1\" align=\"center\">" );
        sb.append( "<tr>" );
        sb.append( "<th>" );
        sb.append( "Cliente" );
        sb.append( "</th>" );
        sb.append( "<th>" );
        sb.append( "Corpo de prova" );
        sb.append( "</th>" );
        sb.append( "<th>" );
        sb.append( "Data prevista para Ruptura" );
        sb.append( "</th>" );
        sb.append( "</tr>" );

        SampleFormmater formmater = SampleFormmater.newInstance();
        
        for ( Sample sample : items )
        {
            sb.append( "<tr>" );
            sb.append( "<td>" );
            sb.append( sample.getClient().getName() );
            sb.append( "</td>" );
            sb.append( "<td>" );
            sb.append( formmater.formatId( sample ) );
            sb.append( "</td>" );
            sb.append( "<td>" );
            sb.append( formmater.formatDate( sample.getDateRupture() ) );
            sb.append( "</td>" );
            sb.append( "</tr>" );
        }

        sb.append( "</table>" );

        mail.setContent( sb.toString() ); 
        
        return mail;
    }
}
