/*
 *  Filename:    ChartData
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
package com.me.eng.core.data;

import java.util.HashMap;

/**
 *
 * @author Artur Tomasi
 */
public class ChartData 
    extends 
        HashMap<String, Object>
{
    public static final String ATTR_SERIES          = "series";
    public static final String ATTR_CATEGORY        = "category";
    public static final String ATTR_VALUE           = "value";
    
    /**
     * getDouble
     * 
     * @param attr String
     * @return Double
     */
    public Double getDouble( String attr )
    {
        return (Double) getOrDefault( attr, 0d );
    }
    
    /**
     * getInteger
     * 
     * @param attr String
     * @return Integer
     */
    public Integer getInteger( String attr )
    {
        return (Integer) getOrDefault( attr, 0 );
    }
    
    /**
     * getString
     * 
     * @param attr String
     * @return String
     */
    public String getString( String attr )
    {
        return (String) getOrDefault( attr, "n/d" );
    }
}
