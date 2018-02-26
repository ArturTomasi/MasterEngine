/* 
 *  Filename:    Utils 
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

import java.util.List;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Matheus
 */
public class Utils
{
    public interface Field<T> 
    {
        /**
         * get
         * 
         * @param value T
         * @return String
         */
        public String get( T value );
    }

    /**
     * Utils
     * 
     */
    private Utils(){}
    
    /**
     * T
     * 
     * @param text String
     * @param selected T
     * @param elements List&lt;T&gt;
     * @param field Field&lt;T&gt;
     * @return &lt;T&gt;
     * @ignored search
     */
    public static <T> T search( String text, T selected, List<T> elements, Field<T> field )
    {
        T found = null; 

        boolean visitSelectedElement = selected == null;
        
        for ( T value : elements )
        {
            if ( field.get( value ).toLowerCase().contains( text.toLowerCase() ) )
            {
                found = value;
                
                if ( visitSelectedElement )
                {
                    break;
                }
            }
            
            if ( selected != null && selected.equals( value ) )
            {
                visitSelectedElement = true;
            }
        }
        
        if ( found != null )
        {
            return found;
        }
        
        Messagebox.show( "Nenhum item encontrado!" );
        
        return null;
    }
}
