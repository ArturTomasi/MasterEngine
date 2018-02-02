package com.mn.engcivil.interfaces.editors;

import com.mn.engcivil.interfaces.DefaultInputPane;

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
