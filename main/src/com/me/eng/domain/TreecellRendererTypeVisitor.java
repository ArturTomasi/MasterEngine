/* 
 *  Filename:    TreecellRendererTypeVisitor 
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
package com.me.eng.domain;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Matheus
 */
public class TreecellRendererTypeVisitor
    implements 
        Configuration.TypeVisitor<Component>
{
    private Configuration configuration;

    public TreecellRendererTypeVisitor( Configuration configuration )
    {
        this.configuration = configuration;
    }

    @Override
    public Component visitString()
    {
        Textbox textbox = new Textbox();
        textbox.setWidth( "200px" );
        textbox.setValue( configuration.getDecodedValue() );
        
        EventListener e = new EventListener()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                configuration.setDecodedValue( textbox.getValue() );
            }
        };
        
        textbox.addEventListener( org.zkoss.zk.ui.event.Events.ON_OK, e );
        textbox.addEventListener( org.zkoss.zk.ui.event.Events.ON_BLUR, e );
        
        return textbox;
    }

    @Override
    public Component visitInteger()
    {
        Intbox intbox = new Intbox();
        intbox.setWidth( "200px" );
        intbox.setValue( configuration.getDecodedValue() );
        
        EventListener e = new EventListener()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                configuration.setDecodedValue( intbox.getValue() );
            }
        };
        
        intbox.addEventListener( org.zkoss.zk.ui.event.Events.ON_OK, e );
        intbox.addEventListener( org.zkoss.zk.ui.event.Events.ON_BLUR, e );
        
        return intbox;
    }

    @Override
    public Component visitFlag()
    {
        Combobox combobox = new Combobox();
        combobox.setReadonly( true );
        
        for ( Configuration.FlagOptions option : Configuration.FlagOptions.values() )
        {
            combobox.appendItem( option.toString() ).setValue( option );
        }
        
        EventListener e = new EventListener()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Configuration.FlagOptions option = Configuration.FlagOptions.FALSE;
                
                Comboitem item = combobox.getSelectedItem();
                
                if ( item != null )
                {
                    option = item.getValue();
                }
                
                configuration.setDecodedValue( option.boolenValue() );
            }
        };
        
        Boolean value = configuration.getDecodedValue();
        
        Configuration.FlagOptions option = Configuration.FlagOptions.valueOf( value );
        
        combobox.setSelectedIndex( option.ordinal() );
        
        combobox.addEventListener( org.zkoss.zk.ui.event.Events.ON_SELECT, e );
        
        return combobox;
    }

    @Override
    public Component visitEncrypt()
    {
        Textbox textbox = (Textbox) visitString();
        textbox.setType( "password" );
        
        return textbox;
    }
}
