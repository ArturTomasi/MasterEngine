/*
 *  Filename:    CategoryColumns
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.ui.tables;

import com.me.eng.core.ui.tables.Column;
import com.me.eng.finances.domain.PostingCategory;

/**
 *
 * @author Artur Tomasi
 */
public enum CategoryColumns 
    implements 
        Column<PostingCategory>
{
    NAME( "Nome" )
    {
        @Override
        public Object getValueAt( PostingCategory value ) 
        {
            return value.getName();
        }
    },        
    
    TYPE( "Tipo" )
    {
        @Override
        public Object getValueAt(PostingCategory value) 
        {
            return value.getType();
        }
    };
    
    private String label;
    
    /**
     * CategoryColumns
     * 
     * @param label String
     */
    private CategoryColumns( String label )
    {
        this.label = label;
    }

    /**
     * getLabel
     * 
     * @return String
     */
    @Override
    public String getLabel() 
    {
        return label;
    }    
}
