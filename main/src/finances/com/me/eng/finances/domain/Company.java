/*
 *  Filename:    Company
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
@Table( name = "fin_companies" )
public class Company
    implements 
        Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Lob
    @Basic(optional = true)
    @Column(name = "info")
    private String info;

    @Basic(optional = true)
    @Column(name = "contact")
    private String contact;
    
    /**
     * Company
     * 
     */
    public Company(){}

    
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
     * getContact
     * 
     * @return String
     */
    public String getContact() 
    {
        return contact;
    }

    /**
     * setContact
     * 
     * @param contact String
     */
    public void setContact( String contact )
    {
        this.contact = contact;
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
        
        final Company other = (Company) obj;
        
        return Objects.equals( this.id, other.id );
    }
}
