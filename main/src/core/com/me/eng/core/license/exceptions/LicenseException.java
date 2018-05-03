/* 
 *  Filename:    LicenseException 
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
package com.me.eng.core.license.exceptions;

/**
 *
 * @author artur
 */
public class LicenseException
    extends 
        Exception
{
    /**
     * Type
     * 
     */
    public enum Type 
    {
        MAX_SEAT( "Número de licenças foi atingido!" ),
        MODULE_NOT_LICENSED( "O módulo não está licensiado!"  ),
        ERROR_LICENSE( "Linceça inválida" ),
        INVALID_DATE( "Linceça está com o periodo inválido!" );
        
        private String message;

        /**
         * Type
         * 
         * @param message String
         */
        private Type( String message ) 
        {
            this.message = message;
        }
        
        /**
         * message
         * 
         * @return String
         */
        public String message()
        {
            return message;
        }
    }
    
    private Type type;
    
    /**
     * LicenseException
     * 
     * @param type Type
     */
    public LicenseException( Type type ) 
    {
        super( type.message() );
    
        this.type = type;
    }
    
    /**
     * LicenseException
     * 
     * @param message
     */
    public LicenseException( String message ) 
    {
        super( message );
    
        this.type = Type.ERROR_LICENSE;
    }

    /**
     * getType
     * 
     * @return Type
     */
    public Type getType() 
    {
        return type;
    }
}
