/*
 *  Filename:    PostingCategory
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

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Artur Tomasi
 */
@Entity
@Table( name = "fin_posting_categories" )
public class PostingCategory 
  implements 
        Serializable 
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Lob
    @Column(name = "info")
    private String info;
    
    @Enumerated( EnumType.ORDINAL )
    @Basic(optional = false)
    @Column(name = "posting_type")
    private PostingType type = PostingType.COST;

    /**
     * PostingCategory
     * 
     */
    public PostingCategory() {}

    
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
     * getInfo
     * 
     * @return String
     */
    public String getInfo() 
    {
        return info;
    }

    /**
     * setInfo
     * 
     * @param info String
     */
    public void setInfo( String info ) 
    {
        this.info = info;
    }

    /**
     * getType
     * 
     * @return PostingType
     */
    public PostingType getType() 
    {
        return type;
    }

    /**
     * setType
     * 
     * @param type PostingType
     */
    public void setType( PostingType type )
    {
        this.type = type;
    }
    
    /**
     * hashCode
     * 
     * @return int
     */
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * equals
     * 
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) 
    {
        if ( this == obj ) 
        {
            return true;
        }
        
        if ( obj == null ) 
        {
            return false;
        }
        
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        
        final PostingCategory other = (PostingCategory) obj;
        
        return Objects.equals( this.id, other.id );
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
