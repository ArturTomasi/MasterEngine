package com.me.eng.ui.panes;

import com.me.eng.domain.repositories.UserRepository;
import javax.inject.Inject;
import org.zkoss.zul.Div;

/**
 *
 * @author Matheus
 */
public class SamplePane
    extends 
        Div
{
    @Inject UserRepository userRepository;
    
    public SamplePane()
    {
        System.out.println( userRepository );
    }
}

