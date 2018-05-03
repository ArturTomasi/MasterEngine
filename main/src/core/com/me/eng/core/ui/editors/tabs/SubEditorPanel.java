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
package com.me.eng.core.ui.editors.tabs;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.ui.editors.Errors;
import org.zkoss.zul.Div;

/**
 *
 * @author Matheus
 */
public abstract class SubEditorPanel<T>
    extends 
        Div
{
    /**
     * SubEditorPanel
     * 
     */
    public SubEditorPanel()
    {
        setHflex( "true" );
        setVflex( "true" );
        setStyle( "overflow: auto;" );
    }
    
    /**
     * handleException
     * 
     * @param e Exception
     */
    public void handleException( Exception e )
    {
        ApplicationContext.getInstance().handleException( e );
    }
    
    /**
     * validateInput
     * 
     * @param e Errors
     */
    public void validateInput( Errors e ){}
    
    /**
     * void
     * 
     * @param source T
     * @return abstract
     * @ignored getInput
     */
    public abstract void getInput( T source );
    
    /**
     * void
     * 
     * @param source T
     * @return abstract
     * @ignored setInput
     */
    public abstract void setInput( T source );
}
