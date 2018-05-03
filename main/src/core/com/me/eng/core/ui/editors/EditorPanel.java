/* 
 *  Filename:    EditorPanel 
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

import com.me.eng.core.ui.DefaultInputPane;

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
