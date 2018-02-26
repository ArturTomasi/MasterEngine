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
package com.me.eng.ui.util;

import com.me.eng.ui.Callback;
import org.zkoss.zk.ui.event.Event;
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
     * @param message String
     * @param callback Callback
     */
    public static void confirm( String message, final Callback callback )
    {
        Messagebox.show( message, "Confirmação", Messagebox.YES | Messagebox.NO, 
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
}
