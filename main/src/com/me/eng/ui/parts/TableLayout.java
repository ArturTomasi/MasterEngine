/* 
 *  Filename:    TableLayout 
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
package com.me.eng.ui.parts;

import java.util.List;
import org.zkoss.zhtml.Table;
import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author Matheus
 */
public class TableLayout
    extends 
        Table
{
    public void setWidths( String... widths )
    {
        for ( Tr tr : (List<Tr>) (List) getChildren() )
        {
            for ( int index = 0; index < widths.length; index++ )
            {
                Td td = (Td) tr.getChildren().get( index );
                
                td.setDynamicProperty( "width", widths[ index ] );
            }
        }
    }
    
    public void setColspan( int row, int col, int colspan )
    {
        Tr tr = (Tr) getChildren().get( row );
        Td td = (Td) tr.getChildren().get( col );
        td.setDynamicProperty( "colspan", colspan );
    }
    
    public void addRow( Component... components )
    {
        Tr tr = new Tr();
        
        for ( Component component : components )
        {
            Td td = new Td();
            td.appendChild( component );
            
            tr.appendChild( td );
        }
        
        appendChild( tr );
    }
}
