package com.me.eng.ui.selectors;

import com.me.eng.ui.Callback;
import java.util.Objects;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Matheus
 */
public abstract class AbstractFieldSelector<T>
    extends 
        Hbox
{
    private T source;

    public AbstractFieldSelector()
    {
        initComponents();
    }
    
    public void setEnableClearButton( boolean enabled )
    {
        clearButton.setVisible( enabled );
    }
    
    public void setSelectedItem( T source )
    {
        this.source = source;
        
        tfValue.setText( Objects.isNull( source ) ? "" : source.toString() );
    }
    
    public T getSelectedItem()
    {
        return source;
    }
    
    private void initComponents()
    {
        openButton.setLabel( "Abrir" );
        clearButton.setLabel( "Limpar" );
        
        clearButton.setVisible( false );
        
        tfValue.setHflex( "true" );
        tfValue.setReadonly( true );
        
        setAlign( "center" );
        setSpacing( "5px" );
        setHflex( "true" );
        
        appendChild( tfValue );
        appendChild( openButton );
        appendChild( clearButton );
        
        openButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                chooseItem( new Callback<T>( source )
                {
                    @Override
                    public void acceptInput()
                    {
                        setSelectedItem( getSource() );
                        Events.postEvent( Events.ON_SELECT, AbstractFieldSelector.this, null );
                    }
                } );
            }
        } );
        
        clearButton.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                setSelectedItem( null );
                Events.postEvent( Events.ON_SELECT, AbstractFieldSelector.this, null );
            }
        } ); 
    }
    
    private Textbox tfValue = new Textbox();
    private Toolbarbutton openButton = new Toolbarbutton();
    private Toolbarbutton clearButton = new Toolbarbutton();
    
    protected abstract void chooseItem( Callback callback );
}
