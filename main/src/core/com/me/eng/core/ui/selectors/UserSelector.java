/*
 *  Filename:    UserSelector
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.core.ui.selectors;

import com.me.eng.core.domain.User;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.pickers.UserPicker;

/**
 *
 * @author Artur Tomasi
 */
public class UserSelector 
    extends 
        AbstractFieldSelector<User>
{
    /**
     * chooseItem
     * 
     * @param callback Callback&lt;User&gt;
     */
    @Override
    protected void chooseItem( Callback<User> callback )
    {
        UserPicker.pick( this, callback );
    }
}
