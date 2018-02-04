
package com.me.eng.ui.editors.tabs;

import com.me.eng.application.ApplicationContext;
import com.me.eng.ui.editors.Errors;
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
