package com.mn.engcivil.interfaces.panes;

import com.mn.engcivil.BuildInfo;
import com.mn.engcivil.application.ResourceLocator;
import com.mn.engcivil.domain.User;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

/**
 *
 * @author Matheus
 */
public class StatusBar
    extends 
        Div
{
    public StatusBar()
    {
        initComponents();
    }
    
    public void setUser( User user )
    {
        if ( user != null )
        {
            lbUser.setValue( user.getName() );
        }
    }
    
    private void initComponents()
    {
        lbUser.setValue( "(Sem usuário autenticado)" );
        lbVersion.setValue( BuildInfo.getVersion() );
        
        setHflex( "true" );
        setStyle( "color: white; font-size: 10px; position: relative;" );

        imgUser.setSrc( ResourceLocator.getImageResource( "sb_user.png" ) );
        imgUser.setHeight( "18px" );
        imgUser.setWidth( "auto" );
        
        lbVersion.setStyle( "position: absolute; right: 10px; top: 2px" );
        
        Hbox hbox = new Hbox();
        hbox.setAlign( "middle" );
        hbox.appendChild( lbUser );
        hbox.appendChild( imgUser );
        hbox.setStyle( "position: absolute; left: 10px; top: 2px" );
        
        appendChild( hbox );
        appendChild( lbVersion );
    }
    
    private Image imgUser = new Image();
    private Label lbUser = new Label();
    private Label lbVersion = new Label();
}
