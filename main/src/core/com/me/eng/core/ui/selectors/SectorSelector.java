/*
 * Filename:    SectorSelector 
 *
 * Author:      Artur Tomasi
 * EMail:       tomasi.artur@gmail.com
 * Internet:    https://www.masterengine.com.br
 *
 * Copyright © 2019 by Over Line Ltda.
 * 95900-038, LAJEADO, RS
 * BRAZIL
 *
 * The copyright to the computer program(s) herein
 * is the property of Over Line Ltda., Brazil.
 * The program(s) may be used and/or copied only with
 * the written permission of Over Line Ltda.
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */
package com.me.eng.core.ui.selectors;

import com.me.eng.core.domain.Sector;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.pickers.SectorPicker;

/**
 *
 * @author Artur Tomasi
 */
public class SectorSelector 
    extends 
        AbstractFieldSelector<Sector>
{
    /**
     * chooseItem
     * 
     * @param callback Callback&lt;Sector&gt;
     */
    @Override
    protected void chooseItem( Callback<Sector> callback )
    {
        SectorPicker.pick( this, callback );
    }
}