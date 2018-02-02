package com.mn.engcivil.interfaces.parts;

import com.mn.engcivil.application.ConfigurationTreeModelBuilder;
import com.mn.engcivil.domain.ConfigurationNode;
import com.mn.engcivil.domain.ConfigurationNodeImpl;
import java.util.Properties;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

/**
 *
 * @author Matheus
 */
public class ConfigurationTree
    extends 
        Tree
{
    private ConfigurationTreeModelBuilder.ConfigurationTreeModel model;

    public ConfigurationTree()
    {
        setItemRenderer( new TreeitemRenderer<ConfigurationNode>()
        {
            @Override
            public void render( Treeitem trtm, ConfigurationNode t, int i ) throws Exception
            {
                Treecell cell1 = new Treecell();
                Treecell cell2 = new Treecell();
                Treerow row = new Treerow();
                
                cell1.setLabel( t.getName() );
                
                if ( t instanceof ConfigurationNodeImpl )
                {
                    cell2.appendChild( ( (ConfigurationNodeImpl) t ).getComponent() );
                }
                
                row.appendChild( cell1 );
                row.appendChild( cell2 );
                
                trtm.appendChild( row );
                trtm.setOpen( true );
            }
        } );
    }
    
    public void refreshContent() throws Exception
    {
        model = ConfigurationTreeModelBuilder.getInstance().build();
        
        setModel( model );
    }
    
    public ConfigurationNode getConfigurationNodeRoot()
    {
        return model.getRoot();
    }
}
