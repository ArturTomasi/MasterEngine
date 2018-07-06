/*
 *  Filename:    PostingList
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.ui.lists;

import com.me.eng.core.application.ResourceLocator;
import com.me.eng.core.ui.lists.AbstractList;
import com.me.eng.core.util.Formatter;
import com.me.eng.finances.domain.Posting;

/**
 *
 * @author Artur Tomasi
 */
public class PostingList
    extends 
        AbstractList<Posting>
{

    /**
     * PostingList
     * 
     */
    public PostingList() {}
    
    /**
     * doContent
     * 
     * @param source Posting
     * @return String
     */
    @Override
    public String doContent( Posting source )
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( "<html>" )
          .append(  "<table class=\"posting-list\">" )
          .append(      "<tr>" )
          .append(          "<td rowspan=\"3\" class=\"icon\">" ).append( "<img src=\"" ).append( ResourceLocator.getFullImageResource( source.getState().icon() ) ).append( "\"></td>" )
          .append(          "<td colspan=\"8\" class=\"title\">" ).append( source ).append( "</td>" )
          .append(      "</tr>" )
          .append(          "</td>" )
          .append(      "<tr>" )
          .append(          "<td class=\"info\" style=\"width: 60px\">" ).append( "Data: ").append( "</td>" )
          .append(          "<td class=\"value\" style=\"width: 150px\">" ).append( Formatter.getInstance().formatDate( source.getEstimateDate() ) ).append( "</td>" )
          .append(          "<td class=\"info\">" ).append( "Companhia: ").append( "</td>" )
          .append(          "<td class=\"value\" style=\"width: 300px\">" ).append( source.getCompany() ).append( "</td>" )
          .append(          "<td class=\"info\" style=\"width: auto; visibility: " ).append( source.isCompletionAuto() ? "visible" : "hidden" ).append( "\">" )
          .append(              "Finaliza automaticamente" )
          .append(          "</td>" )
          .append(      "</tr>" )
          .append(      "<tr>" )
          .append(          "<td class=\"info\" style=\"width: 60px\">" ).append( "Valor: ").append( "</td>" )
          .append(          "<td class=\"value\" style=\"width: 150px\">" ).append( Formatter.getInstance().formatCurrency( source.getEstimateValue() ) ).append( "</td>" )
          .append(          "<td class=\"info\">" ).append( "Categoria: " ).append( "</td>" )
          .append(          "<td class=\"value\" style=\"width: 300px\">" ).append( source.getCategory() ).append( "</td>" )
          .append(          "<td class=\"info\" style=\"width: auto; visibility: " ).append( source.isRepeat() ? "visible" : "hidden" ).append( "\">" )
          .append(              "Parcela: " ).append( source.getPortion() ).append( " de " ).append( source.getPortionTotal() )
          .append(          "</td>" )
          .append(      "</tr>" )
          .append(  "</table>" )
          .append( "</html>" );
        
        return sb.toString();
    }
    
}
