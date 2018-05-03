/* 
 *  Filename:    Client 
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
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "core_clients")
public class Client 
    implements 
        Serializable 
{
    /**
     * isPersisted
     * 
     * @param client Client
     * @return boolean
     */
    public static boolean isPersisted( Client client )
    {
        return client != null && ! client.isNew();
    }
    
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
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    
    @Embedded
    private Cnpj cnpj = new Cnpj();
    
    @JoinColumn(name = "ref_city", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private City city;
    
    @OneToMany(mappedBy = "refClient", 
               targetEntity = Contact.class, 
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<Contact> contacs = new LinkedList<>();

    /**
     * Client
     * 
     */
    public Client()
    {
    }

    /**
     * Client
     * 
     * @param name String
     */
    public Client( String name )
    {
        this.name = name;
    }

    /**
     * getCity
     * 
     * @return City
     */
    public City getCity()
    {
        return city;
    }

    /**
     * setCity
     * 
     * @param city City
     */
    public void setCity( City city )
    {
        this.city = city;
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
     * getCnpj
     * 
     * @return Cnpj
     */
    public Cnpj getCnpj()
    {
        return cnpj;
    }

    /**
     * setCnpj
     * 
     * @param cnpj Cnpj
     */
    public void setCnpj( Cnpj cnpj )
    {
        this.cnpj = cnpj;
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
     * getAddress
     * 
     * @return String
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * setAddress
     * 
     * @param address String
     */
    public void setAddress( String address )
    {
        this.address = address;
    }

    /**
     * getContacs
     * 
     * @return List&lt;Contact&gt;
     */
    public List<Contact> getContacs()
    {
        return contacs;
    }

    /**
     * setContacs
     * 
     * @param contacs List&lt;Contact&gt;
     */
    public void setContacs( List<Contact> contacs )
    {
        this.contacs = contacs;
    }
    
    /**
     * isNew
     * 
     * @return boolean
     */
    public boolean isNew()
    {
        return id == null;
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
        if ( !( object instanceof Client ) )
        {
            return false;
        }
        Client other = (Client) object;
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
}
