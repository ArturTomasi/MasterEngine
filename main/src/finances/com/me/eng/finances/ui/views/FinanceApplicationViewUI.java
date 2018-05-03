/*
 *  Filename:    FinanceApplicationView
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
package com.me.eng.finances.ui.views;

import com.me.eng.core.ui.views.ApplicationViewUI;

/**
 *
 * @author Artur Tomasi
 */
public class FinanceApplicationViewUI 
    extends 
        ApplicationViewUI
{

    /**
     * FinanceApplicationViewUI
     * 
     */
    public FinanceApplicationViewUI() 
    {
        setLabel( "Finanças" );
        setIcon( "finances/sb_finance.png" );
    }
    
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents() 
    {
    }
}
