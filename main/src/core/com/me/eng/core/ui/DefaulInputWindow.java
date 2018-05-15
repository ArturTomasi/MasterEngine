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
import com.me.eng.core.application.ResourceLocator;
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
import org.zkoss.zul.South;
import org.zkoss.zul.Window;

/**
 *
 * @author Matheus
 */
public abstract class DefaulInputWindow<T extends DefaultInputPane, V>
    extends 
        Window
{
    /**
     * extends
     * 
     * @param owner Component
     * @param runtime Class&lt;E&gt;
     * @param defaultPane DefaultInputPane
     * @param callback Callback
     * @return &lt;E
     * @ignored DefaulInputWindow&gt;
     * @ignored E
     * @ignored createInputWindow
     */
    protected static <E extends DefaulInputWindow> E createInputWindow( Component owner, 
                                                               Class<E> runtime,
                                                               DefaultInputPane defaultPane, 
                                                               Callback callback )
    {
        try
        {
            DefaulInputWindow window = runtime.newInstance();

            window.setWidth( "600px" );
            window.setHeight( "85%" );
            window.setClosable( false );
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
    
    /**
     * DefaulInputWindow
     * 
     */
    protected DefaulInputWindow()
    {
        setSclass( "default-editor" );
        
        initComponents();
    }
    
    /**
     * setDefaultPane
     * 
     * @param editorPanel T
     */
    private void setDefaultPane( T editorPanel )
    {
        this.defaultPane = editorPanel;
        
        editorPanel.setVflex( "true" );
        
        borderlayout.getCenter().appendChild( editorPanel );
        
        borderlayout.getNorth().appendChild( new EditorCaption( editorPanel.getIcon(),
                                                                editorPanel.getTitle(),
                                                                editorPanel.getInfo() ) );
        
        setInput( callback.getSource() );
    }

    /**
     * setInput
     * 
     * @param source V
     */
    protected void setInput( V source )
    {
    }
    
    /**
     * getInput
     * 
     * @param source V
     */
    protected void getInput( V source )
    {
    }

    /**
     * validateInput
     * 
     * @param e Errors
     */
    private void validateInput( Errors e )
    {
        defaultPane.validateInput( e );
    }
    
    /**
     * acceptInput
     * 
     */
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
    
    /**
     * cancelInput
     * 
     */
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
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        okButton.setLabel( "OK" );
        okButton.setImage( ResourceLocator.getImageResource( "core/tb_ok.png" ) );
        
        cancelButton.setLabel( "Cancelar" );
        cancelButton.setImage( ResourceLocator.getImageResource( "core/tb_cancel.png" ) );

        okButton.setSclass( "default-editor-btn" );
        cancelButton.setSclass( "default-editor-btn" );
        
        buttonsPane.setSclass( "default-editor-btn-pane" );
        buttonsPane.setSpacing( "5px" );
        buttonsPane.appendChild( okButton );
        buttonsPane.appendChild( cancelButton );
        
        borderlayout.appendChild( new North() );
        borderlayout.appendChild( new Center() );
        borderlayout.appendChild( new South() );
        
        borderlayout.getNorth().setHeight( "65px" );
        borderlayout.getNorth().setBorder( "none" );
        borderlayout.getNorth().setStyle( "background-color: #3568d8; padding: 10px 20px; color: #fff;" );
        
        borderlayout.getCenter().setBorder( "none" );
        borderlayout.getCenter().setStyle( "padding: 10px;" );
        
        borderlayout.getSouth().setHeight( "50px" );
        borderlayout.getSouth().setStyle( "border: 0px; background-color: #c6c6c6;" );
        borderlayout.getSouth().appendChild( buttonsPane );
        
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
    private Button okButton = new Button();
    private Button cancelButton = new Button();
}
