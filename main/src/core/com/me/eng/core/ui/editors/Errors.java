/* 
 *  Filename:    Errors 
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
package com.me.eng.core.ui.editors;

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
