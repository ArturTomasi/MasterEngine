package com.me.eng.ui.apps;

import com.me.eng.ui.views.LauncherApplicationViewUI;

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
