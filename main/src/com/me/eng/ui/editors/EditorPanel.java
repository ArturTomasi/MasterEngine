package com.me.eng.ui.editors;

import com.me.eng.ui.DefaultInputPane;

/**
 *
 * @author Matheus
 */
public abstract class EditorPanel<T>
    extends 
        DefaultInputPane
{
    public abstract void setInput( T value );
    public abstract void getInput( T value );
}
