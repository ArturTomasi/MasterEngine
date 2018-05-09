/* 
 *  Filename:    DefaultEditor 
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
package com.me.eng.core.ui.editors;

import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.DefaulInputWindow;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author Matheus
 */
public class DefaultEditor<V>
    extends 
        DefaulInputWindow<EditorPanel, V>
{
    /**
     * createEditor
     * 
     * @param owner Component
     * @param editorPanel EditorPanel
     * @param callback Callback
     * @return DefaultEditor
     */
    public static DefaultEditor createEditor( Component owner, EditorPanel editorPanel, Callback callback )
    {
        return createInputWindow( owner, DefaultEditor.class, editorPanel, callback );
    }

    /**
     * setInput
     * 
     * @param source V
     */
    @Override
    protected void setInput( V source )
    {
        defaultPane.setInput( source );
    }
    
    /**
     * getInput
     * 
     * @param source V
     */
    @Override
    protected void getInput( V source )
    {
        defaultPane.getInput( source );
    }
}
