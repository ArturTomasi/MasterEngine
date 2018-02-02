package com.mn.engcivil.domain;

import java.util.List;
import java.util.Properties;

/**
 *
 * @author Matheus
 */
public interface ConfigurationNode
{
    public String getName();
    public void add( ConfigurationNode node );
    public List<ConfigurationNode> children();
    public void store( Properties properties );
}
