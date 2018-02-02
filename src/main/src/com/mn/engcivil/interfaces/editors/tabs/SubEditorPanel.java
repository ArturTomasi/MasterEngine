
package com.mn.engcivil.interfaces.editors.tabs;

import com.mn.engcivil.application.ApplicationContext;
import com.mn.engcivil.interfaces.editors.Errors;
import org.zkoss.zul.Div;

/**
 *
 * @author Matheus
 */
public abstract class SubEditorPanel<T>
    extends 
        Div
{
    public SubEditorPanel()
    {
        setHflex( "true" );
        setVflex( "true" );
        setStyle( "overflow: auto;" );
    }
    
    protected void handleException( Exception e )
    {
        ApplicationContext.getInstance().handleException( e );
    }
    
    public void validateInput( Errors e ){}
    
    public abstract void getInput( T source );
    public abstract void setInput( T source );
}
