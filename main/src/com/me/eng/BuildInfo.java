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
     * 
     * @return 
     */
    public static String getVersion()
    {
        return 	RELEASE + "." + 
                HOTFIX  + "." +
                BUILD   + "-" + TODAY;
    }
    
    /**
     * 
     * @param args 
     */
    public static void main( String[] args )
    {
        System.out.println( getVersion() );
    }
}