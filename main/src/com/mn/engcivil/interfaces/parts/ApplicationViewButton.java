package com.mn.engcivil.interfaces.parts;

import com.mn.engcivil.application.ResourceLocator;
import com.mn.engcivil.interfaces.views.ApplicationViewUI;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vbox;

/**
 *
 * @author Matheus
 */
public class ApplicationViewButton
    extends 
        Div
{
    private ApplicationViewUI viewUI;

    public ApplicationViewButton( ApplicationViewUI viewUI )
    {
        this.viewUI = viewUI;
        
        initComponents();
    }

    public ApplicationViewUI getViewUI()
    {
        return viewUI;
    }
    
    public void setSelected( boolean value )
    {
        if ( value )
        {
            setSclass( "default-app-view-button selected" );
        }
        
        else
        {
            setSclass( "default-app-view-button" );
        }
    }
    
    private void initComponents()
    {
        setSclass( "default-app-view-button" );
        
        Label lb = new Label( viewUI.getLabel() );

        Image image = new Image( ResourceLocator.getImageResource( viewUI.getIcon() ) );
        
        Vbox vbox = new Vbox();
        vbox.setHflex( "true" );
        vbox.setVflex( "true" );
        vbox.setAlign( "middle" );
        vbox.setSpacing( "2px" );
        vbox.appendChild( image );
        vbox.appendChild( lb );
        
        appendChild( vbox );
    }
}
