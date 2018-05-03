/* 
 *  Filename:    DefaulInputWindow 
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
import com.me.eng.core.ui.editors.EditorCaption;
import com.me.eng.core.ui.editors.Errors;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.North;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

/**
 *
 * @author Matheus
 */
public abstract class DefaulInputWindow<T extends DefaultInputPane, V>
    extends 
        Window
{
    protected static <E extends DefaulInputWindow> E createInputWindow( Component owner, 
                                                               Class<E> runtime,
                                                               DefaultInputPane defaultPane, 
                                                               Callback callback )
    {
        try
        {
            DefaulInputWindow window = runtime.newInstance();

            window.setTitle( defaultPane.getTitle() );
            window.setWidth( "600px" );
            window.setHeight( "85%" );
            window.setClosable( true );
            window.callback = callback;

            window.setDefaultPane( defaultPane );

            window.setParent( owner );
            window.onModal();

            return (E) window;
        }
        
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }
    
    protected Callback<V> callback;
    protected T defaultPane;
    
    protected DefaulInputWindow()
    {
        setSclass( "default-editor" );
        
        initComponents();
    }
    
    private void setDefaultPane( T editorPanel )
    {
        this.defaultPane = editorPanel;
        
        editorPanel.setVflex( "true" );
        
        vlayout.appendChild( editorPanel );
        vlayout.appendChild( buttonsPane );
        
        borderlayout.getNorth().appendChild( new EditorCaption( editorPanel.getIcon(),
                                                                editorPanel.getCaption(),
                                                                editorPanel.getInfo() ) );
        
        setInput( callback.getSource() );
    }

    protected void setInput( V source )
    {
    }
    
    protected void getInput( V source )
    {
    }

    private void validateInput( Errors e )
    {
        defaultPane.validateInput( e );
    }
    
    private void acceptInput()
    {
        Errors e = new Errors();
        
        validateInput( e );
        
        if ( e.validate( this ) )
        {
            try
            {
                getInput( callback.getSource() );
                
                callback.acceptInput();
                
                detach();
            }
            
            catch ( Exception ex )
            {
                ApplicationContext.getInstance().handleException( ex );
            }
        }
    }
    
    private void cancelInput()
    {
        try
        {
            callback.cancelInput();
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().handleException( e );
        }

        finally
        {
            detach();
        }
    }
    
    private void initComponents()
    {
        okButton.setLabel( "OK" );
        cancelButton.setLabel( "Cancelar" );
        
        vlayout.setHflex( "true" );
        vlayout.setVflex( "true" );
        vlayout.setSpacing( "45px" );

        okButton.setWidth( "80px" );
        cancelButton.setWidth( "80px" );
        
        buttonsPane.setWidth( "170px" );
        buttonsPane.setHeight( "30px" );
        buttonsPane.setSpacing( "5px" );
        buttonsPane.setStyle( "position: absolute; right: 10px; bottom: 5px" );
        buttonsPane.appendChild( okButton );
        buttonsPane.appendChild( cancelButton );
        
        borderlayout.appendChild( new North() );
        borderlayout.appendChild( new Center() );
        borderlayout.appendChild( new West() );
        
        borderlayout.getNorth().setHeight( "50px" );
        borderlayout.getNorth().setBorder( "none" );
        borderlayout.getNorth().setStyle( "border-bottom: 1px solid lightgray" );
        
        borderlayout.getWest().setWidth( "20px" );
        borderlayout.getWest().setBorder( "none" );
        borderlayout.getWest().setStyle( "background-color: rgba(48, 67, 105, 0.75)" );
        
        borderlayout.getCenter().setBorder( "none" );
        borderlayout.getCenter().setStyle( "padding-left: 10px; padding-top: 10px" );
        
        borderlayout.getCenter().appendChild( vlayout );
        
        appendChild( borderlayout );
        
        okButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                acceptInput();
            }
        } );
        
        cancelButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                cancelInput();
            }
        } );
    }
    
    private Hlayout buttonsPane = new Hlayout();
    private Borderlayout borderlayout = new Borderlayout();
    private Vlayout vlayout = new Vlayout();
    private Button okButton = new Button();
    private Button cancelButton = new Button();
}
