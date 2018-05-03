/* 
 *  Filename:    City 
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

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "core_cities")
public class City 
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
    
    @JoinColumn(name = "ref_state", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private State state;

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
     * getState
     * 
     * @return State
     */
    public State getState()
    {
        return state;
    }

    /**
     * setState
     * 
     * @param state State
     */
    public void setState( State state )
    {
        this.state = state;
    }

    /**
     * hashCode
     * 
     * @return int
     */
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode( this.id );
        return hash;
    }

    /**
     * equals
     * 
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        final City other = (City) obj;
        if ( !Objects.equals( this.id, other.id ) )
        {
            return false;
        }
        return true;
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