/*
 *  Filename:    PostingCategoryList
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
import com.me.eng.finances.domain.PostingCategory;

/**
 *
 * @author Artur Tomasi
 */
public class PostingCategoryList 
    extends 
        AbstractList<PostingCategory>
{

    /**
     * doContent
     * 
     * @param source PostingCategory
     * @return String
     */
    @Override
    public String doContent( PostingCategory source ) 
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( "<html>" )
          .append(  "<table>" )
          .append(      "<tr>" )
          .append(          "<td rowspan=\"3\">" )
          .append(              "<img style=\"width: 75px; padding: 0px 25px;\" src=\"" ).append( ResourceLocator.getFullImageResource( source.getType().icon() ) ).append( "\">" )
          .append(          "</td>" )
          .append(      "</tr>" )
          .append(      "<tr>" )
          .append(          "<td style=\"vertical-align: top; padding-top: 10px\">" )
          .append(              "<span style=\"font-family: Arial,Sans-serif; font-size: 20px;font-weight: bold;\">" ).append( source.getName() ).append( "</span>" )
          .append(          "</td>" )
          .append(      "</tr>" )
          .append(      "<tr>" )
          .append(          "<td style=\"padding-top: 10px\">" )
          .append(              "<span style=\"font-family: Arial,Sans-serif; font-size: 12px;font-weight: normal;\">" ).append( source.getInfo() ).append( "</span>" )
          .append(          "</td>" )
          .append(      "</tr>" )
          .append(  "</table>" )
          .append( "</html>" );
        
        return sb.toString();
    }
}
