package com.me.eng.ui.editors;

import java.util.LinkedList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Matheus
 */
public class Errors
{
    private List<String> errors = new LinkedList<String>();
    
    public void addError( String error )
    {
        errors.add( error );
    }
    
    public boolean validate( Component owner )
    {
        StringBuilder sb = new StringBuilder();

        if ( ! errors.isEmpty() )
        {
            for ( String e : errors )
            {
                sb.append( "* " );
                sb.append( e );
                sb.append( "\n" );
            }
            
            Messagebox.show( sb.toString() );
        }
        
        return errors.isEmpty();
    }
}
