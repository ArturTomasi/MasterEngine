package com.mn.engcivil.interfaces.selectors;

import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Job;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.pickers.JobPicker;

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
