/*
 *  Filename:    AttachmentList
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
package com.me.eng.core.ui.lists;

import com.me.eng.core.application.ResourceLocator;
import com.me.eng.core.domain.Attachment;

/**
 *
 * @author Artur Tomasi
 */
public class AttachmentList 
    extends 
        AbstractList<Attachment>
{
    /**
     * AttachmentList
     * 
     */
    public AttachmentList() 
    {
        initComponents();
    }
    
    /**
     * doContent
     * 
     * @param source Attachment
     * @return String
     */
    @Override
    public String doContent( Attachment source )
    {
        return "<table>" +
                    "<tr>" +
                        "<td>" +
                            "<img style=\"width: 30px; padding: 5px;\" src=\"" + ResourceLocator.getFullImageResource( source.getType().icon() )+ "\"/>" +
                        "</td>" +
                        "<td>" +
                            "<span>" + source.getName() +"</span>" +
                        "</td>" +
                    "</tr>" +
               "</table>";
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setStyle( "border-radius: 15px; z-index: 100000;" );
        setEmptyMessage( "" );
    }
}
