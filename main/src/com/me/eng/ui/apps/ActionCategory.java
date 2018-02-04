package com.me.eng.ui.apps;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class ActionCategory
{
    private String label;
    
    private List<Action> actions;

    public ActionCategory( String label, Action... actions )
    {
        this( label, Arrays.asList( actions ) );
    }
    
    public ActionCategory( String label, List<Action> actions )
    {
        this.label = label;
        this.actions = actions;
    }

    public String getLabel()
    {
        return label;
    }

    public List<Action> getActions()
    {
        return actions;
    }
}
