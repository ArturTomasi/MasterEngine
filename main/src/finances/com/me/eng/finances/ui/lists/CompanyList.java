/*
 *  Filename:    CompanyList
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
import com.me.eng.finances.domain.Company;

/**
 *
 * @author Artur Tomasi
 */
public class CompanyList 
    extends 
        AbstractList<Company>
{

    /**
     * CompanyList
     * 
     */
    public CompanyList() 
    {
        setCheckmark( false );
    }

    /**
     * doContent
     * 
     * @param source Company
     * @return String
     */
    @Override
    public String doContent( Company source )
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( "<html>" )
          .append(  "<table>" )
          .append(      "<tr>" )
          .append(          "<td rowspan=\"4\">" )
          .append(              "<img style=\"width: 50px; padding: 0px 25px;\" src=\"" ).append( ResourceLocator.getFullImageResource( "finances/fi_company.png" ) ).append( "\">" )
          .append(          "</td>" )
          .append(          "<td style=\"vertical-align: top; padding-top: 10px; width: 100%;\" colspan=\"2\">" )
          .append(              "<span style=\"font-family: Arial,Sans-serif; font-size: 20px;font-weight: bold;\">" ).append( source.getName() ).append( "</span>" )
          .append(          "</td>" )
          .append(      "</tr>" )
          .append(      "<tr>" )
          .append(          "<td style=\"vertical-align: top; padding-top: 10px; width: 80px; max-width: 80px; min-width: 80px;\">" )
          .append(              "<span style=\"font-family: Arial,Sans-serif; font-size: 14px;font-weight: bold;\">Contato: </span>" )
          .append(          "</td>" )
          .append(          "<td style=\"vertical-align: top; padding-top: 10px; width: 100%;\">" )
          .append(              "<span style=\"font-family: Arial,Sans-serif; font-size: 14px;\">" ).append( source.getContact()).append( "</span>" )
          .append(          "</td>" )
          .append(      "</tr>" )
          .append(      "<tr>" )
          .append(          "<td style=\"padding-top: 10px; width: 100%;\" colspan=\"2\">" )
          .append(              "<span style=\"font-family: Arial,Sans-serif; font-size: 12px;font-weight: normal;\">" ).append( source.getInfo() != null ? source.getInfo() : "n/d" ).append( "</span>" )
          .append(          "</td>" )
          .append(      "</tr>" )
          .append(  "</table>" )
          .append( "</html>" );
        
        return sb.toString();
    }
}
