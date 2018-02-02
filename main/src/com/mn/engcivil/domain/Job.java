package com.mn.engcivil.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "eng_jobs")
public class Job
    implements 
        Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = true)
    @Column(name = "address")
    private String address;
    
    @Basic(optional = false)
    @Column(name = "cei")
    private String cei;
    
    @JoinColumn(name = "ref_city", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private City city;
    
    @OneToMany(mappedBy = "refJob", 
               targetEntity = Contact.class, 
               cascade = CascadeType.ALL,
               orphanRemoval = true)    
    private List<Contact> contacs = new LinkedList<>();

    
    @JoinColumn(name = "ref_client", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Client client;
    
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient( Client client )
    {
        this.client = client;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public City getCity() 
    {
        return city;
    }

    public void setCity(City city) 
    {
        this.city = city;
    }

    public String getCEI() 
    {
        return cei;
    }

    public void setCEI( String cei ) 
    {
        this.cei = cei;
    }

    public List<Contact> getContacs() 
    {
        return contacs;
    }

    public void setContacs(List<Contact> contacs) 
    {
        this.contacs = contacs;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode( this.id );
        return hash;
    }

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
        final Job other = (Job) obj;
        if ( !Objects.equals( this.id, other.id ) )
        {
            return false;
        }
        return true;
    }
}
