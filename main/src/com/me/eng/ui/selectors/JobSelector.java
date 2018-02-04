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
