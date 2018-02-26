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
package com.me.eng.domain;

import java.util.Properties;

/**
 *
 * @author Matheus
 */
public class Configuration
{
    public interface TypeVisitor<T>
    {
        public T visitString();
        public T visitInteger();
        public T visitFlag();
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

    public String getEncodedValue()
    {
        return type.accept( new EncodeTypeVisitor( this ) );
    }
    
    public void setEncodedValue( String value )
    {
        this.value = type.accept( new DecodeTypeVisitor( this ) );
    }
    
    public Type getType()
    {
        return type;
    }

    public void setType( Type type )
    {
        this.type = type;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty( String property )
    {
        this.property = property;
    }

    public <T> T getDecodedValue()
    {
        return (T) value;
    }

    public void setDecodedValue( Object value )
    {
        this.value = value;
    }
    
    public void store( Properties properties )
    {
        properties.setProperty( getProperty(), getEncodedValue() );
    }
}
