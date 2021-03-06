/*
 *  Filename:    CompletionType
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
public enum CompletionType 
{
    CASH            ( "Dinheiro" ),
    CARD            ( "Cartão" ),
    VOUCHER         ( "Vale" ),
    SERVICES        ( "Serviço" ),
    OTHER           ( "Outros" );
    
    private String name;
    
    /**
     * CompletionType
     * 
     */
    private CompletionType( String name ) 
    {
        this.name = name;
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
