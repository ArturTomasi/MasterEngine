/* 
 *  Filename:    ConfigurationCompositeNode 
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

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Matheus
 */
public class ConfigurationCompositeNode
    implements 
        ConfigurationNode
{
    private List<ConfigurationNode> children = new LinkedList();
    
    private String name;

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
     * store
     * 
     * @param properties Properties
     */
    @Override
    public void store( Properties properties )
    {
        for ( ConfigurationNode child : children )
        {
            child.store( properties );
        }
    }

    /**
     * add
     * 
     * @param node ConfigurationNode
     */
    @Override
    public void add( ConfigurationNode node )
    {
        children.add( node );
    }

    /**
     * children
     * 
     * @return List&lt;ConfigurationNode&gt;
     */
    @Override
    public List<ConfigurationNode> children()
    {
        return children;
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
