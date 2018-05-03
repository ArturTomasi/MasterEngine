/*
 *  Filename:    LicensePurger
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
package com.me.eng.core.license.controller;

import com.me.eng.core.application.ApplicationContext;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zkoss.zk.au.http.AuExtension;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;

/**
 *
 * @author Artur Tomasi
 */
public class LicensePurger 
    implements 
        AuExtension
{
    /**
     * init
     * 
     * @param servlet DHtmlUpdateServlet
     * @throws ServletException
     */
    @Override
    public void init( DHtmlUpdateServlet servlet ) throws ServletException {}

    /**
     * service
     * 
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param string String
     * @throws ServletException
     * @throws java.io.IOException
     */
    @Override
    public void service( HttpServletRequest req, HttpServletResponse res, String string ) throws ServletException, IOException 
    {
        try
        {
             LicenseManager.getInstance().purgeLicense( req.getParameter( "module" ) );
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().handleException( e );
        }
    }
    
    /**
     * destroy
     * 
     */
    @Override
    public void destroy(){}
}