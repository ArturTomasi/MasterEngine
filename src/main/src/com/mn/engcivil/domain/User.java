/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mn.engcivil.domain;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "core_users")
public class User 
    implements 
        Serializable
{
    private static final long serialVersionUID = 1L;
    
    public static final int PROFILE_OPERATOR = 0;
    public static final int PROFILE_ADMINISTRATOR = 1;
    
    public static final String PROFILE_NAMES[] = 
    {
        "Operador",
        "Administrador"
    };
    
    public static final String PROFILE_ROLES[] = 
    {
        "operator",
        "administrator"
    };
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "profile")
    private Integer profile = PROFILE_OPERATOR;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "core_user_roles",
                joinColumns = {@JoinColumn(name = "ref_user")},
                inverseJoinColumns = {@JoinColumn(name = "ref_role")})
    private Set<Role> roles = new LinkedHashSet<Role>();

    public User()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
    
    public void grant( Role role )
    {
        roles.add( role );
    }
    
    public void revokeAll()
    {
        roles.clear();
    }
    
    public void revoke( Role role )
    {
        roles.remove( role );
    }

    public Integer getProfile()
    {
        return profile;
    }

    public void setProfile( Integer profile )
    {
        this.profile = profile;
    }
    
    public boolean isSystemUser()
    {
        return id != null && id == 1;
    }

    public boolean isAdministrator()
    {
        return profile >= PROFILE_ADMINISTRATOR;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += ( id != null ? id.hashCode() : 0 );
        return hash;
    }

    @Override
    public boolean equals( Object object )
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if ( !( object instanceof User ) )
        {
            return false;
        }
        User other = (User) object;
        if ( ( this.id == null && other.id != null ) || ( this.id != null && !this.id.equals( other.id ) ) )
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
