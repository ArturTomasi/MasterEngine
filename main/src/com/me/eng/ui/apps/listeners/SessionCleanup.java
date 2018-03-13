/*
 *  Filename:    SessionCleanup
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
package com.me.eng.ui.apps.listeners;

import com.me.eng.license.controller.LicenseManager;
import org.zkoss.zk.ui.Session;

/**
 *
 * @author Artur Tomasi
 */
public class SessionCleanup
    implements 
        org.zkoss.zk.ui.util.SessionCleanup
{
    /**
     * cleanup
     * 
     * @param sn Session
     * @throws Exception
     */
    @Override
    public void cleanup( Session sn ) throws Exception 
    {
        LicenseManager.getInstance().purgeLicense( sn );
    }
}
