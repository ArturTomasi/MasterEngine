package com.me.eng.application;

import com.me.eng.domain.Configuration;
import com.me.eng.domain.ConfigurationCompositeNode;
import com.me.eng.domain.ConfigurationNode;
import com.me.eng.domain.ConfigurationNodeImpl;
import java.util.Properties;
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
    public class ConfigurationTreeModel
        extends 
            AbstractTreeModel<ConfigurationNode>
    {
        public ConfigurationTreeModel( ConfigurationNode root )
        {
            super( root );
        }
        
        @Override
        public boolean isLeaf( ConfigurationNode e )
        {
            return e.children().isEmpty();
        }

        @Override
        public ConfigurationNode getChild( ConfigurationNode e, int i )
        {
            return e.children().get( i );
        }

        @Override
        public int getChildCount( ConfigurationNode e )
        {
            return e.children().size();
        }
    }
    
    private static final ConfigurationTreeModelBuilder instance = new ConfigurationTreeModelBuilder();
    
    public static ConfigurationTreeModelBuilder getInstance()
    {
        return instance;
    }
    
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
