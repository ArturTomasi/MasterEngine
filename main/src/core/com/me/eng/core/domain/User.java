/* 
 *  Filename:    User 
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

import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.LinkedHashSet;
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
    
    public static final int PROFILE_OPERATOR      = 0;
    public static final int PROFILE_ADMINISTRATOR = 1;
    
    public static final String PROFILE_NAMES[] = 
    {
        "Operador",
        "Administrador",
        "Gerenciador de Receitas"
    };
    
    public static final String PROFILE_ROLES[] = 
    {
        "operator",
        "administrator",
        "recipes"
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
    private Set<Role> roles = new LinkedHashSet();

    /**
     * User
     * 
     */
    public User(){}

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
     * getLogin
     * 
     * @return String
     */
    public String getLogin()
    {
        return login;
    }

    /**
     * setLogin
     * 
     * @param login String
     */
    public void setLogin( String login )
    {
        this.login = login;
    }

    /**
     * getPassword
     * 
     * @return String
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * setPassword
     * 
     * @param password String
     */
    public void setPassword( String password )
    {
        this.password = password;
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
     * grant
     * 
     * @param role Role
     */
    public void grant( Role role )
    {
        roles.add( role );
    }
    
    /**
     * revokeAll
     * 
     */
    public void revokeAll()
    {
        roles.clear();
    }
    
    /**
     * revoke
     * 
     * @param role Role
     */
    public void revoke( Role role )
    {
        roles.remove( role );
    }

    /**
     * getProfile
     * 
     * @return Integer
     */
    public Integer getProfile()
    {
        return profile;
    }

    /**
     * setProfile
     * 
     * @param profile Integer
     */
    public void setProfile( Integer profile )
    {
        this.profile = profile;
    }
    
    /**
     * isSystemUser
     * 
     * @return boolean
     */
    public boolean isSystemUser()
    {
        return id != null && id == 1;
    }

    /**
     * isAdministrator
     * 
     * @return boolean
     */
    public boolean isAdministrator()
    {
        return profile >= PROFILE_ADMINISTRATOR;
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
        if ( object instanceof User )
        {
            return Objects.equal( this.getId(), User.class.cast( object ).getId() );
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
