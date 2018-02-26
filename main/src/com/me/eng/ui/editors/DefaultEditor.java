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
