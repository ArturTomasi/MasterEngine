/* 
 *  Filename:    Rule 
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
package com.me.eng.samples.domain;

import com.google.common.base.Objects;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "eng_rules")
public class Rule 
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
    @Column(name = "info")
    private String info;

    /**
     * Rule
     * 
     */
    public Rule()
    {
    }

    /**
     * Rule
     * 
     * @param name String
     * @param info String
     */
    public Rule( String name, String info )
    {
        this.name = name;
        this.info = info;
    }

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
     * hashCode
     * 
     * @return int
     */
    @Override
    public int hashCode()
    {
        int hash = 0;
        
        hash += ( id != null ? id.hashCode() : 0 );
        
        return hash;
    }

    /**
     * equals
     * 
     * @param object Object
     * @return boolean
     */
    @Override
    public boolean equals( Object object )
    {
        if ( object instanceof Rule )
        {
            return Objects.equal( this.getId(), Rule.class.cast( object ).getId() );
        }
        
        return false;
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
