package com.mn.engcivil.interfaces.apps;

import com.mn.engcivil.interfaces.panes.ApplicationCaption;
import com.mn.engcivil.interfaces.panes.StatusBar;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.zkoss.zhtml.Form;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Matheus
 */
public class LoginApplication
    extends 
        Window
{
    public LoginApplication()
    {
        initComponents();
        
        Form form = new Form();
        form.setDynamicProperty( "method", "POST" );
        form.setDynamicProperty( "action", "j_security_check" );
        
        Textbox tfLogin = new Textbox();
        tfLogin.setName( "j_username" );
        
        Textbox tfPwd = new Textbox();
        tfPwd.setType( "password" );
        tfPwd.setName( "j_password" );

        Hbox line1 = new Hbox();
        line1.setAlign( "middle" );
        line1.appendChild( new Label( "Login:" ) );
        line1.appendChild( tfLogin );
        
        Hbox line2 = new Hbox();
        line2.setAlign( "middle" );
        line2.appendChild( new Label( "Senha:" ) );
        line2.appendChild( tfPwd );
        
        Input submit = new Input();
        submit.setDynamicProperty( "type", "submit" );
        submit.setDynamicProperty( "value", "Entrar" );
        submit.setDynamicProperty( "style", "margin-top: 10px; margin-bottom: 10px" );
        
        Vbox vlayout = new Vbox();
        vlayout.setAlign( "middle" );
        vlayout.setSpacing( "5px" );
        vlayout.appendChild( line1 );
        vlayout.appendChild( line2 );
        vlayout.appendChild( submit );
        
        form.appendChild( vlayout );
        
        Div div = new Div();
        div.setSclass( "login-app-container" );
        div.appendChild( form );
        
        Boolean error = (Boolean) Sessions.getCurrent().getAttribute( "auth-status" );
        
        if ( error != null && error )
        {
            Label lb = new Label( "Dados inválidos" );
            lb.setStyle( "color: red" );
            
            vlayout.appendChild( lb );
        }
        
        inner.appendChild( div );
    }
    
    private void initComponents()
    {
        setSclass( "login-app" );
        setStubonly( true );
        
        inner.setWidgetAttribute( "align", "center" );
        inner.setStyle( "display: table-cell; vertical-align: middle" );
        
        content.appendChild( inner );
        
        content.setStyle( "display: table" );
        content.setHflex( "true" );
        content.setVflex( "true" );
        
        borderlayout.appendChild( new North() );
        borderlayout.appendChild( new Center() );
        borderlayout.appendChild( new South() );
        
        borderlayout.getNorth().setHeight( "70px" );
        borderlayout.getNorth().setBorder( "none" );
//        borderlayout.getNorth().setStyle( "background: transparent" );
        borderlayout.getNorth().setStyle( "background-color: rgb(48, 67, 105);" );
        borderlayout.getNorth().appendChild( caption );
        
        borderlayout.getSouth().setHeight( "20px" );
        borderlayout.getSouth().setBorder( "none" );
        borderlayout.getSouth().setStyle( "background-color: rgb(48, 67, 105);" );
        borderlayout.getSouth().appendChild( statusBar );
        
        borderlayout.getCenter().setBorder( "none" );
        borderlayout.getCenter().setStyle( "background: transparent" );
        borderlayout.getCenter().appendChild( content );
        
        borderlayout.setStyle( "background: transparent" );
        
        appendChild( borderlayout );
        
        addEventListener( org.zkoss.zk.ui.event.Events.ON_CLIENT_INFO, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                ClientInfoEvent e = (ClientInfoEvent) t;
                
                setWidth( e.getDesktopWidth() + "px" );
                setHeight( e.getDesktopHeight() + "px" );
            }
        } );
    }
    
    private Borderlayout borderlayout = new Borderlayout();
    
    private ApplicationCaption caption = new ApplicationCaption();
    
    private Div content = new Div();
    private Div inner = new Div();
    
    private StatusBar statusBar = new StatusBar();
}
