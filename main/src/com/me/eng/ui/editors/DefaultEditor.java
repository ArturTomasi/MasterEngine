package com.me.eng.ui.editors;

import com.me.eng.ui.Callback;
import com.me.eng.ui.DefaulInputWindow;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author Matheus
 */
public class DefaultEditor<V>
    extends 
        DefaulInputWindow<EditorPanel, V>
{
    public static DefaultEditor createEditor( Component owner, EditorPanel editorPanel, Callback callback )
    {
        return createInputWindow( owner, DefaultEditor.class, editorPanel, callback );
    }

    @Override
    protected void setInput( V source )
    {
        defaultPane.setInput( source );
    }
    
    @Override
    protected void getInput( V source )
    {
        defaultPane.getInput( source );
    }
}
