package com.me.eng.domain;

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
    private List<ConfigurationNode> children = new LinkedList<ConfigurationNode>();
    
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
    
    @Override
    public void store( Properties properties )
    {
        for ( ConfigurationNode child : children )
        {
            child.store( properties );
        }
    }

    @Override
    public void add( ConfigurationNode node )
    {
        children.add( node );
    }

    @Override
    public List<ConfigurationNode> children()
    {
        return children;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
