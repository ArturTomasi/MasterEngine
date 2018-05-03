/* 
 *  Filename:    RoleTable 
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
package com.me.eng.core.ui.tables;

import com.me.eng.core.domain.Role;

/**
 *
 * @author Matheus
 */
public class RoleTable
    extends 
        AbstractTable<Role>
{
    public enum Columns
        implements 
            Column<Role>
    {
        NAME( "Nome" )
        {
            @Override
            public Object getValueAt( Role value )
            {
                return value.getName();
            }
        };
        
        private String label;

        /**
         * Columns
         * 
         * @param label String
         */
        private Columns( String label )
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

    /**
     * getColumns
     * 
     * @return Column[]
     */
    @Override
    protected Column[] getColumns()
    {
        return Columns.values();
    }
}
