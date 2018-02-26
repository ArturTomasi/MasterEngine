/* 
 *  Filename:    ConfigurationTreeModelBuilder 
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
package com.me.eng.ui.trees;

import com.me.eng.application.ConfigurationManager;
import com.me.eng.application.ResourceLocator;
import com.me.eng.domain.Configuration;
import com.me.eng.domain.ConfigurationCompositeNode;
import com.me.eng.domain.ConfigurationNode;
import com.me.eng.domain.ConfigurationNodeImpl;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.zul.AbstractTreeModel;

/**
 *
 * @author Matheus
 */
public class ConfigurationTreeModelBuilder
{
    /**
     * ConfigurationTreeModel
     * 
     * @return class
     * @ignored extends
     * @ignored AbstractTreeModel&lt;ConfigurationNode&gt;
     */
    public class ConfigurationTreeModel
        extends 
            AbstractTreeModel<ConfigurationNode>
    {
        /**
         * ConfigurationTreeModel
         * 
         * @param root ConfigurationNode
         */
        public ConfigurationTreeModel( ConfigurationNode root )
        {
            super( root );
        }
        
        /**
         * isLeaf
         * 
         * @param e ConfigurationNode
         * @return boolean
         */
        @Override
        public boolean isLeaf( ConfigurationNode e )
        {
            return e.children().isEmpty();
        }

        /**
         * getChild
         * 
         * @param e ConfigurationNode
         * @param i int
         * @return ConfigurationNode
         */
        @Override
        public ConfigurationNode getChild( ConfigurationNode e, int i )
        {
            return e.children().get( i );
        }

        /**
         * getChildCount
         * 
         * @param e ConfigurationNode
         * @return int
         */
        @Override
        public int getChildCount( ConfigurationNode e )
        {
            return e.children().size();
        }
    }
    
    private static ConfigurationTreeModelBuilder instance;
    
    /**
     * getInstance
     * 
     * @return ConfigurationTreeModelBuilder
     */
    public static ConfigurationTreeModelBuilder getInstance()
    {
        if ( instance == null )
        {
            instance = new ConfigurationTreeModelBuilder();
        }
        
        return instance;
    }
    
    /**
     * build
     * 
     * @return ConfigurationTreeModel
     * @throws Exception
     */
    public ConfigurationTreeModel build() throws Exception
    {
        ConfigurationCompositeNode config = new ConfigurationCompositeNode();
        config.setName( "Configurações" );

        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse( ResourceLocator.getResourceAsStream( "/config/config.xml" ) );

        NodeList nodes = doc.getElementsByTagName( "configuration" );

        for ( int index = 0; index < nodes.getLength(); index++ )
        {
            parseNode( config, nodes.item( index ) ); 
        }

        ConfigurationNode root = new ConfigurationCompositeNode();
        root.add( config );
        
        return new ConfigurationTreeModel( root );
    }
    
    /**
     * parseNode
     * 
     * @param e ConfigurationNode
     * @param node Node
     */
    private void parseNode( ConfigurationNode e, Node node )
    {
        if ( node.getNodeType() != Node.ELEMENT_NODE )
        {
            return;
        }
        
        if ( node.getNodeName().equals( "item" ) )
        {
            NamedNodeMap attrs = node.getAttributes();
            
            Configuration configuration = new Configuration();
            configuration.setLabel( attrs.getNamedItem( "label" ).getNodeValue() );
            configuration.setProperty( attrs.getNamedItem( "property" ).getNodeValue() );
            configuration.setType( Configuration.Type.valueOf( attrs.getNamedItem( "type" ).getNodeValue() ) );
            configuration.setEncodedValue( ConfigurationManager.getInstance().getProperty( configuration.getProperty() ) );
            
            e.add( new ConfigurationNodeImpl( configuration ) );
        }
        
        else
        {
            if ( node.getNodeName().equals( "category" ) )
            {
                ConfigurationCompositeNode category = new ConfigurationCompositeNode();
                category.setName( node.getAttributes().getNamedItem( "label" ).getNodeValue() );
                
                e.add( category );
                
                e = category;
            }
            
            NodeList nodes = node.getChildNodes();
            
            for ( int index = 0; index < nodes.getLength(); index++ )
            {
                parseNode( e, nodes.item( index ) ); 
            }
        }
    }
}
