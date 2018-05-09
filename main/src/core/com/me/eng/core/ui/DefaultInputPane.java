/* 
 *  Filename:    DefaultInputPane 
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
package com.me.eng.core.ui;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.ui.editors.Errors;
import org.zkoss.zul.Div;

/**
 *
 * @author Matheus
 * @param <T>
 */
public abstract class DefaultInputPane<T>
    extends 
        Div
{
    private String title, info, icon = "core/tb_default_editor.png";

    /**
     * getTitle
     * 
     * @return String
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * setTitle
     * 
     * @param title String
     */
    public void setTitle( String title )
    {
        this.title = title;
    }

    /**
     * getIcon
     * 
     * @return String
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * setIcon
     * 
     * @param icon String
     */
    public void setIcon( String icon )
    {
        this.icon = icon;
    }

    /**
     * getInfo
     * 
     * @return String
     */
    public String getInfo()
    {
        return info;
    }

    /**
     * setInfo
     * 
     * @param info String
     */
    public void setInfo( String info )
    {
        this.info = info;
    }
    
    /**
     * validateInput
     * 
     * @param e Errors
     */
    public void validateInput( Errors e ){}
    
    /**
     * handleException
     * 
     * @param e Exception
     */
    protected void handleException( Exception e )
    {
        ApplicationContext.getInstance().handleException( e );
    }
}
