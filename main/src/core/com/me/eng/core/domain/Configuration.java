/* 
 *  Filename:    Configuration 
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
package com.me.eng.core.domain;

import java.util.Properties;

/**
 *
 * @author Matheus
 */
public class Configuration
{
    public interface TypeVisitor<T>
    {
        /**
         * visitString
         * 
         * @return T
         */
        public T visitString();
        
        /**
         * visitInteger
         * 
         * @return T
         */
        public T visitInteger();
        
        /**
         * visitFlag
         * 
         * @return T
         */
        public T visitFlag();
        
        /**
         * visitEncrypt
         * 
         * @return T
         */
        public T visitEncrypt();
    }
    
    public enum Type
    {
        string
        {
            @Override
            public <T> T accept( TypeVisitor<T> visitor )
            {
                return visitor.visitString();
            }
        },
        
        integer
        {
            @Override
            public <T> T accept( TypeVisitor<T> visitor )
            {
                return visitor.visitInteger();
            }
        },
        
        flag
        {
            @Override
            public <T> T accept( TypeVisitor<T> visitor )
            {
                return visitor.visitFlag();
            }
        },
        
        encrypt
        {
            @Override
            public <T> T accept( TypeVisitor<T> visitor )
            {
                return visitor.visitEncrypt();
            }
        };
        
        public abstract <T> T accept( TypeVisitor<T> visitor );
    }
    
    public enum FlagOptions
    {
        TRUE( "sim" ),
        FALSE( "não" );
        
        private String label;

        private FlagOptions( String label )
        {
            this.label = label;
        }
        
        public static FlagOptions valueOf( Boolean b )
        {
            if ( b == null )
            {
                return FALSE;
            }
            
            return valueOf( b.toString().toUpperCase() );
        }
        
        public boolean boolenValue()
        {
            return Boolean.valueOf( name() );
        }

        @Override
        public String toString()
        {
            return label;
        }
    }
    
    private Type type;
    private String label;
    private String property;
    private Object value;

    /**
     * getEncodedValue
     * 
     * @return String
     */
    public String getEncodedValue()
    {
        return type.accept( new EncodeTypeVisitor( this ) );
    }
    
    /**
     * setEncodedValue
     * 
     * @param value String
     */
    public void setEncodedValue( String value )
    {
        this.value = type.accept( new DecodeTypeVisitor( this ) );
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

    /**
     * setType
     * 
     * @param type Type
     */
    public void setType( Type type )
    {
        this.type = type;
    }

    /**
     * getLabel
     * 
     * @return String
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * setLabel
     * 
     * @param label String
     */
    public void setLabel( String label )
    {
        this.label = label;
    }

    /**
     * getProperty
     * 
     * @return String
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * setProperty
     * 
     * @param property String
     */
    public void setProperty( String property )
    {
        this.property = property;
    }

    /**
     * T
     * 
     * @return &lt;T&gt;
     * @ignored getDecodedValue
     */
    public <T> T getDecodedValue()
    {
        return (T) value;
    }

    /**
     * setDecodedValue
     * 
     * @param value Object
     */
    public void setDecodedValue( Object value )
    {
        this.value = value;
    }
    
    /**
     * store
     * 
     * @param properties Properties
     */
    public void store( Properties properties )
    {
        properties.setProperty( getProperty(), getEncodedValue() );
    }
}
