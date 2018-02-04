package com.me.eng.domain;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author Matheus
 */
public class ConfigurationNodeImpl
    implements 
        ConfigurationNode
{
    private Configuration configuration;

    public ConfigurationNodeImpl( Configuration configuration )
    {
        this.configuration = configuration;
    }
    
    public Component getComponent()
    {
        return configuration.getType().accept( new TreecellRendererTypeVisitor( configuration ) );
    }

    @Override
    public String getName()
    {
        return configuration.getLabel();
    }

    @Override
    public void add( ConfigurationNode node )
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ConfigurationNode> children()
    {
        return Collections.emptyList();
    }

    @Override
    public void store( Properties properties )
    {
        configuration.store( properties );
    }

    @Override
    public String toString()
    {
        return configuration.getLabel();
    }
}
