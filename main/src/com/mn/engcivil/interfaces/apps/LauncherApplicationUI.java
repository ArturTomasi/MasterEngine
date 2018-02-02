package com.mn.engcivil.interfaces.apps;

import com.mn.engcivil.interfaces.views.LauncherApplicationViewUI;

/**
 *
 * @author Matheus
 */
public class LauncherApplicationUI
    extends 
        ApplicationUI
{
    public LauncherApplicationUI()
    {
        addView( new LauncherApplicationViewUI() );
    }
}
