/* 
 *  Filename:    Trace 
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
