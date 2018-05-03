/* 
 *  Filename:    BuildInfo 
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
package com.me.eng;

/**
 *
 * @author Matheus
 */
public class BuildInfo
{
    public static final String NUMBER   = "@number@";
    public static final String RELEASE  = "@release@";
    public static final String HOTFIX   = "@hotfix@";
    public static final String MODE     = "@mode@";
    public static final String BUILD    = "@build@";
    public static final String COMMIT   = "@commit@";
    public static final String TODAY    = "@today@";
  
    /**
     * getVersion
     * 
     * @return String
     */
    public static String getVersion()
    {
        return 	"Over Line - " + NUMBER  + "." + RELEASE + "." + HOTFIX;
    }
    
    /**
     * main
     * 
     * @param args String[]
     */
    public static void main( String[] args )
    {
        System.out.println( getVersion() );
    }
}
