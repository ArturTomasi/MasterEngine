package com.mn.engcivil.interfaces.tables;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Matheus
 */
public abstract class ActionColumn<T>
    implements 
        Column<T>
{
    private String label;

    public ActionColumn( String label )
    {
        this.label = label;
    }
    
    @Override
    public String getLabel()
    {
        return "#";
    }

    @Override
    public Object getValueAt( T value )
    {
        Toolbarbutton bt = new Toolbarbutton( label );

        bt.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                execute( value );
            }
        } );

        return bt;
    }
    
    protected abstract void execute( T value );
}
