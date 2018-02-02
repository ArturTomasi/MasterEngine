package com.mn.engcivil.domain;

import com.mn.engcivil.infrastructure.Mail;
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

    NotificationMailBuilder( List<Sample> items )
    {
        this.items = items;
    }
    
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
