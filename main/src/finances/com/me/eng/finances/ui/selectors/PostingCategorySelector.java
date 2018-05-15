/*
 *  Filename:    PostingCategorySelector
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
package com.me.eng.finances.ui.selectors;

import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.selectors.AbstractFieldSelector;
import com.me.eng.finances.domain.PostingCategory;
import com.me.eng.finances.ui.pickers.PostingCategoryPicker;

/**
 *
 * @author Artur Tomasi
 */
public class PostingCategorySelector 
    extends 
        AbstractFieldSelector<PostingCategory>
{
    /**
     * chooseItem
     * 
     * @param callback Callback&lt;PostingCategory&gt;
     */
    @Override
    protected void chooseItem( Callback<PostingCategory> callback )
    {
        PostingCategoryPicker.pick( this, callback );
    }
}
