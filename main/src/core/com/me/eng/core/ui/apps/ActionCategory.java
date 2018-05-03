/* 
 *  Filename:    ActionCategory 
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
package com.me.eng.core.ui.apps;

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

    /**
     * ActionCategory
     * 
     * @param label String
     * @param actions Action...
     */
    public ActionCategory( String label, Action... actions )
    {
        this( label, Arrays.asList( actions ) );
    }
    
    /**
     * ActionCategory
     * 
     * @param label String
     * @param actions List&lt;Action&gt;
     */
    public ActionCategory( String label, List<Action> actions )
    {
        this.label = label;
        this.actions = actions;
    }

    /**
     * getLabel
     * 
     * @return String
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * getActions
     * 
     * @return List&lt;Action&gt;
     */
    public List<Action> getActions()
    {
        return actions;
    }
}
