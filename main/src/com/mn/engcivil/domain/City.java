package com.mn.engcivil.domain;

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
@Table(name = "eng_cities")
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

    public State getState()
    {
        return state;
    }

    public void setState( State state )
    {
        this.state = state;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode( this.id );
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
        final City other = (City) obj;
        if ( !Objects.equals( this.id, other.id ) )
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return name;
    }
}