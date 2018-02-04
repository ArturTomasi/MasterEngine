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
        public String get( T value );
    }

    private Utils(){}
    
    public static <T> T search( String text, 
                                T selected, 
                                List<T> elements, 
                                Field<T> field )
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
