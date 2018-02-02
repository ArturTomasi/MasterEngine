package com.mn.engcivil;

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

    public static String getVersion()
    {
        return "1.3.1";
    }
    
    public static void main( String[] args )
    {
        System.out.println( getVersion() );
    }
}
