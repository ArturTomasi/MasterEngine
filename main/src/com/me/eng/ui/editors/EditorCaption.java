package com.me.eng.ui.editors;

import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class EditorCaption
    extends 
        Div
{
    private String caption;
    private String icon;
    private String info;

    public EditorCaption( String icon, String caption, String info )
    {
        this.caption = caption;
        this.icon = icon;
        this.info = info;
        
        initComponents();
    }
    
    private void initComponents()
    {
        setVflex( "true" );
        setHflex( "true" );
        
        Label lbCaption = new Label( caption );
        lbCaption.setStyle( "font-weight: bold;" );
        
        Label lbInfo = new Label( info );
        lbInfo.setStyle( "color: #64728E; margin-left: 5px" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setHflex( "true" );
        vlayout.setVflex( "true" );
        vlayout.appendChild( lbCaption );
        vlayout.appendChild( lbInfo );
        vlayout.setStyle( "margin-left: 25px; margin-top: 5px" );
        
        appendChild( vlayout );
    }
}
