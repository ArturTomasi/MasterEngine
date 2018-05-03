/* 
 *  Filename:    ConfigurationTree 
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
package com.me.eng.core.ui.parts;

import com.me.eng.core.ui.trees.ConfigurationTreeModelBuilder;
import com.me.eng.core.domain.ConfigurationNode;
import com.me.eng.core.domain.ConfigurationNodeImpl;
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
