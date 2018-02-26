/* 
 *  Filename:    JobSelector 
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
package com.me.eng.ui.selectors;

import com.me.eng.domain.Client;
import com.me.eng.domain.Job;
import com.me.eng.ui.Callback;
import com.me.eng.ui.pickers.JobPicker;

/**
 *
 * @author Matheus
 */
public class JobSelector
    extends 
        AbstractFieldSelector<Job>
{
    private Client client;

    public JobSelector()
    {
        setEnableClearButton( true );
    }
    
    public void setClient( Client client )
    {
        this.client = client;
    }
    
    @Override
    protected void chooseItem( Callback callback )
    {
        JobPicker.pick( this, client, callback );
    }
}
