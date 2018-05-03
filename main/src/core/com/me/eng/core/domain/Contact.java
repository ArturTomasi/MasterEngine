/* 
 *  Filename:    Contact 
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

import com.me.eng.samples.domain.Job;
import java.io.Serializable;
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
@Table(name = "core_contacts")
public class Contact 
    implements 
        Serializable,
        Comparable<Contact>
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "position")
    private Integer position;
    @Basic(optional = false)
    @Column(name = "telephone")
    private String telephone;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "ref_client", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client refClient;
    
    @JoinColumn(name = "ref_job", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Job refJob;

    /**
     * Contact
     * 
     */
    public Contact()
    {
    }

    /**
     * getEmail
     * 
     * @return String
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * setEmail
     * 
     * @param email String
     */
    public void setEmail( String email )
    {
        this.email = email;
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
     * getTelephone
     * 
     * @return String
     */
    public String getTelephone()
    {
        return telephone;
    }

    /**
     * setTelephone
     * 
     * @param telephone String
     */
    public void setTelephone( String telephone )
    {
        this.telephone = telephone;
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
     * getRefClient
     * 
     * @return Client
     */
    public Client getRefClient()
    {
        return refClient;
    }

    /**
     * setRefClient
     * 
     * @param refClient Client
     */
    public void setRefClient( Client refClient )
    {
        this.refClient = refClient;
    }

    /**
     * getPosition
     * 
     * @return Integer
     */
    public Integer getPosition()
    {
        return position;
    }

    /**
     * setPosition
     * 
     * @param position Integer
     */
    public void setPosition( Integer position )
    {
        this.position = position;
    }

    /**
     * getRefJob
     * 
     * @return Job
     */
    public Job getRefJob()
    {
        return refJob;
    }

    /**
     * setRefJob
     * 
     * @param refJob Job
     */
    public void setRefJob( Job refJob ) 
    {
        this.refJob = refJob;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if ( !( object instanceof Contact ) )
        {
            return false;
        }
        Contact other = (Contact) object;
        if ( ( this.id == null && other.id != null ) || ( this.id != null && !this.id.equals( other.id ) ) )
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

    /**
     * compareTo
     * 
     * @param o Contact
     * @return int
     */
    @Override
    public int compareTo( Contact o )
    {
        return getPosition().compareTo( o.getPosition() );
    }
}
