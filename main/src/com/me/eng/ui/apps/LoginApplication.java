/* 
 *  Filename:    LoginApplication 
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
package com.me.eng.ui.apps;

import com.me.eng.application.ConfigurationManager;
import org.zkoss.zhtml.Form;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Matheus
 */
public class LoginApplication   
    extends 
        Window
{
    /**
     * LoginApplication
     * 
     */
    public LoginApplication()
    {
        initComponents();
        
        Boolean error = (Boolean) Sessions.getCurrent().getAttribute( "auth-status" );
        
        if ( error != null && error )
        {
            Clients.evalJavaScript( "onLoginError()" );
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setStubonly( true );
        setZclass( "container login-center" );
        
        Label labelApplication = new Label();
        labelApplication.setValue( ConfigurationManager.getInstance().getProperty( "application.name", "Application Name" ) );
        labelApplication.setStyle( "display: block;" );
        labelApplication.setZclass( "title-login" );
        
        Label errorLabel = new Label( "Atenção! login ou senha incorretos." );
        errorLabel.setZclass( "error-login" );
        
        Form form = new Form();
        form.setStyle( "width: 100%" );
        form.setDynamicProperty( "method", "POST" );
        form.setDynamicProperty( "action", "j_security_check" );
        
        Textbox tfLogin = new Textbox();
        tfLogin.setWidgetAttribute( "autocomplete", "off" );
        tfLogin.setPlaceholder( "Login" );
        tfLogin.setSclass( "input-login" );
        tfLogin.setStyle( "background-image: url(\"../img/tb_login.svg\")" );
        tfLogin.setWidth( "100%" );
        tfLogin.setName( "j_username" );
        
        Textbox tfPwd = new Textbox();
        tfPwd.setWidgetAttribute( "autocomplete", "off" );
        tfPwd.setPlaceholder( "Senha" );
        tfPwd.setStyle( "background-image: url(\"../img/tb_password.svg\")" );
        tfPwd.setSclass( "input-login" );
        tfPwd.setType( "password" );
        tfPwd.setWidth( "100%" );
        tfPwd.setName( "j_password" );

        Input submit = new Input();
        submit.setSclass( "btn-login" );
        submit.setDynamicProperty( "type", "submit" );
        submit.setDynamicProperty( "value", "Entrar" );
        
        Div vlayout = new Div();
        vlayout.setWidth( "500px" );
        vlayout.appendChild( tfLogin );
        vlayout.appendChild( tfPwd );
        vlayout.appendChild( submit );
        
        form.appendChild( vlayout );
        
        Div conteiner = new Div();
        conteiner.setZclass( "login-center-container" );
        
        conteiner.appendChild( labelApplication );
        conteiner.appendChild( form );
        conteiner.appendChild(  errorLabel );
        
        appendChild( conteiner );
    }
}
