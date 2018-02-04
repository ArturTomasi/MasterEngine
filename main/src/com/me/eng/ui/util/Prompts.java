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
    public static void confirm( String message, final Callback callback )
    {
        Messagebox.show( message, 
                         "Confirmação", 
                         Messagebox.YES | Messagebox.NO, 
                         Messagebox.QUESTION, 
                         (Event e) ->
        {
            if ( ( (Integer) e.getData() ) == Messagebox.YES )
            {
                callback.acceptInput();
            }
            
            else if ( ( (Integer) e.getData() ) == Messagebox.NO )
            {
                callback.rejectInput();
            }
            
            else
            {
                callback.cancelInput();
            }
        } );
    }
}
