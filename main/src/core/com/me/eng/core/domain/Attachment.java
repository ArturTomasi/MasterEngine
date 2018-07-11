/*
 *  Filename:    Attachment
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
package com.me.eng.core.domain;

import java.io.File;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Artur Tomasi
 */
@Entity
@Table( name = "core_attachments" )
public class Attachment 
    implements 
        Serializable
{
    private static final long serialVersionUID = 1L;
   
    public static enum Family
    {
        POSTING();
    }
    
    public static enum Type
    {
        IMAGE   ( "core/tb_picture.png",    "image/*" ),
        DOCUMENT( "core/tb_document.png",   "application/*" ),
        VIDEO   ( "core/tb_video.png",      "video/*" ),
        AUDIO   ( "core/tb_audio.png",      "audio/*" );
        
        private String icon, type;
        
        /**
         * Type
         * 
         * @param icon String
         * @param type String
         */
        Type( String icon, String type )
        {
            this.icon = icon;
            this.type = type;
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
         * contentType
         * 
         * @return String
         */
        public String contentType()
        {
            return type;
        }
        
        /**
         * typeOf
         * 
         * @param content String
         * @return Type
         */
        public static Type typeOf( String content )
        {
            Type type = DOCUMENT;
            
            if ( content.contains( "image" ) )
            {
                type = IMAGE;
            }
            
            else if ( content.contains( "video" ) )
            {
                type = VIDEO;
            }
            
            else if ( content.contains( "audio" ) )
            {
                type = AUDIO;
            }
            
            return type;
        }
    }
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Enumerated( EnumType.ORDINAL )
    @Basic(optional = false)
    @Column( name = "family" )
    private Family family;

    @Enumerated( EnumType.STRING )
    @Basic(optional = false)
    @Column( name = "type" )
    private Type type = Type.DOCUMENT;

    @Basic(optional = false)
    @Column( name = "ref_source" )
    private Integer source;
    
    @Transient
    private File file;
    
    /**
     * getId
     * 
     * @return Integer
     */
    public Integer getId() 
    {
        return id;
    }

    /**
     * setId
     * 
     * @param id Integer
     */
    public void setId( Integer id ) 
    {
        this.id = id;
    }

    /**
     * getName
     * 
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * setName
     * 
     * @param name String
     */
    public void setName( String name ) 
    {
        this.name = name;
    }

    /**
     * getFamily
     * 
     * @return Family
     */
    public Family getFamily() 
    {
        return family;
    }

    /**
     * setFamily
     * 
     * @param family Family
     */
    public void setFamily( Family family ) 
    {
        this.family = family;
    }

    /**
     * getSource
     * 
     * @return Integer
     */
    public Integer getSource() 
    {
        return source;
    }

    /**
     * setSource
     * 
     * @param source Integer
     */
    public void setSource( Integer source ) 
    {
        this.source = source;
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
     * getFile
     * 
     * @return File
     */
    public File getFile()
    {
        return file;
    }
    
    /**
     * setFile
     * 
     * @param file File
     */
    public void setFile( File file )
    {
        this.file = file;
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
