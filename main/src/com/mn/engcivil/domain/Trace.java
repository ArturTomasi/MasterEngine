package com.mn.engcivil.domain;

import java.io.Serializable;

/**
 *
 * @author Matheus
 */
//@Entity
//@Table(name = "eng_contacts")
//@XmlRootElement
//@NamedQueries(
//{
//    @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c")
//})
public class Trace
    implements 
        Serializable
{
    private static final long serialVersionUID = 1L;
    
//    @Id
//    @GeneratedValue( strategy = GenerationType.IDENTITY )
//    @Basic(optional = false)
//    @Column(name = "id")
    private Integer id;
    
//    @Basic(optional = false)
//    @Column(name = "name")
    private String name;

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

    @Override
    public String toString()
    {
        return name;
    }
}
