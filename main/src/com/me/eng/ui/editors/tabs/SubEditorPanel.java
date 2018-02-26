/* 
 *  Filename:    SubEditorPanel 
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
