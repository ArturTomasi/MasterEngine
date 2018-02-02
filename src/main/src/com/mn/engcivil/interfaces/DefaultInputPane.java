
package com.mn.engcivil.interfaces;

import com.mn.engcivil.application.ApplicationContext;
import com.mn.engcivil.interfaces.editors.Errors;
import org.zkoss.zul.Div;

/**
 *
 * @author Matheus
 */
public abstract class DefaultInputPane<T>
    extends 
        Div
{
    private String title;
    private String icon;
    private String info;
    private String caption;

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon( String icon )
    {
        this.icon = icon;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo( String info )
    {
        this.info = info;
    }

    public String getCaption()
    {
        return caption;
    }

    public void setCaption( String caption )
    {
        this.caption = caption;
    }
    
    public void validateInput( Errors e )
    {
    }
    
    protected void handleException( Exception e )
    {
        ApplicationContext.getInstance().handleException( e );
    }
}
