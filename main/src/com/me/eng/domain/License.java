/* 
 *  Filename:    License 
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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author artur
 */
@Entity
@Table( name = "eng_licenses" )
public class License
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
    @Column(name = "module_name")
    private String module;
    
    @Basic(optional = false)
    @Column(name = "session_id")
    private String session;
    
    @JoinColumn(name = "ref_user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    
    @Basic(optional = false)
    @Column(name = "dt_created", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Transient
    private int lease = 0;
    
    
    /**
     * License
     */
    public License(){}

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
     * getModule
     * 
     * @return String
     */
    public String getModule()
    {
        return module;
    }

    /**
     * setModule
     * 
     * @param module String
     */
    public void setModule( String module )
    {
        this.module = module;
    }

    /**
     * getSession
     * 
     * @return String
     */
    public String getSession() 
    {
        return session;
    }

    /**
     * setSession
     * 
     * @param session String
     */
    public void setSession( String session )
    {
        this.session = session;
    }

    /**
     * getUser
     * 
     * @return User
     */
    public User getUser()
    {
        return user;
    }

    /**
     * setUser
     * 
     * @param user User
     */
    public void setUser( User user )
    {
        this.user = user;
    }

    /**
     * getDate
     * 
     * @return Date
     */
    public Date getDate() 
    {
        return date;
    }

    /**
     * setDate
     * 
     * @param date Date
     */
    public void setDate( Date date )
    {
        this.date = date;
    }

    /**
     * lease
     * 
     */
    public void lease()
    {
        this.lease++;
    }
    
    /**
     * unlease
     * 
     */
    public void unlease()
    {
        this.lease--;
    }

    /**
     * countLease
     * 
     * @return int
     */
    public int countLease()
    {
        return lease;
    }
    
    /**
     * key
     * 
     * @return String
     */
    public String key()
    {
        return this.module  + "@" + 
               this.session + "#" + 
               this.user.getId();
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
        if ( object instanceof License  )
        {
            License other = (License) object;
            
            return key().equals( other.key() );
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
        return key();
    }
}
