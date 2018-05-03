/* 
 *  Filename:    ConfigurationNodeImpl 
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

    /**
     * ConfigurationNodeImpl
     * 
     * @param configuration Configuration
     */
    public ConfigurationNodeImpl( Configuration configuration )
    {
        this.configuration = configuration;
    }
    
    /**
     * getComponent
     * 
     * @return Component
     */
    public Component getComponent()
    {
        return configuration.getType().accept( new TreecellRendererTypeVisitor( configuration ) );
    }

    /**
     * getName
     * 
     * @return String
     */
    @Override
    public String getName()
    {
        return configuration.getLabel();
    }

    /**
     * add
     * 
     * @param node ConfigurationNode
     */
    @Override
    public void add( ConfigurationNode node )
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * children
     * 
     * @return List&lt;ConfigurationNode&gt;
     */
    @Override
    public List<ConfigurationNode> children()
    {
        return Collections.emptyList();
    }

    /**
     * store
     * 
     * @param properties Properties
     */
    @Override
    public void store( Properties properties )
    {
        configuration.store( properties );
    }

    /**
     * toString
     * 
     * @return String
     */
    @Override
    public String toString()
    {
        return configuration.getLabel();
    }
}
