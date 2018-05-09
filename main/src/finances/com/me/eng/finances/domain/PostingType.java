/*
 *  Filename:    PostingType
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
package com.me.eng.finances.domain;

/**
 *
 * @author Artur Tomasi
 */
public enum PostingType
{
    REVENUE    ( "Receitas", "finances/fi_revenue.png" ),
    COST       ( "Despesas", "finances/fi_cost.png" );

    private String name, icon;
    
    /**
     * PostingType
     * 
     */
    private PostingType( String name, String icon ) 
    {
        this.name = name;
        this.icon = icon;
    }

    /**
     * icon
     * 
     * @return String
     */
    public String icon()
    {
        return icon;
    }
    
    /**
     * toString
     * 
     * @return String
     */
    @Override
    public String toString() 
    {
        return name;
    }
}
