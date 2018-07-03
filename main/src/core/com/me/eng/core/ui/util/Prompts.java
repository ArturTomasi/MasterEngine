/* 
 *  Filename:    Prompts 
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
package com.me.eng.core.ui.util;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.ui.Callback;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Matheus
 */
public class Prompts
{
    /**
     * confirm
     * 
     * @param title String
     * @param message String
     * @param callback Callback
     */
    public static void confirm( String title, String message, final Callback callback )
    {
        Messagebox.show( message, title , Messagebox.YES | Messagebox.NO, 
                         Messagebox.QUESTION,  (Event e) ->
        {
            switch ( (Integer) e.getData() )
            {
                case Messagebox.YES:
                    callback.acceptInput();
                case Messagebox.NO:
                    callback.rejectInput();
                default:
                    callback.cancelInput();
            }
        } );
    }
    
    /**
     * confirm
     * 
     * @param message String
     * @param callback Callback
     */
    public static void confirm( String message, final Callback callback )
    {   
        confirm( "Confirmação", message, callback );
    }
    
    /**
     * info
     * 
     * @param info String
     */
    public static void info( String info )
    {
        info( ApplicationContext.getInstance().getRoot(), info );
    }
    
    /**
     * info
     * 
     * @param parent Component
     * @param info String
     */
    public static void info( Component parent, String info )
    {
        Html html = new Html( "<div class=\"me-prompts-info-text\">" + info + "</div>" );

        Div div = new Div();
        div.setZclass( "me-prompts-info" );
        div.appendChild( html );
        
        div.setWidgetListener( "onBind", "shake( this );" );

        parent.appendChild( div );
    }
}
