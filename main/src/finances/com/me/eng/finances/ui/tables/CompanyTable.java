/*
 *  Filename:    CompanyTable
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

import com.me.eng.core.ui.tables.AbstractTable;
import com.me.eng.core.ui.tables.Column;
import com.me.eng.finances.domain.Company;

/**
 *
 * @author Artur Tomasi
 */
public class CompanyTable 
    extends 
        AbstractTable<Company>
{

    /**
     * CompanyTable
     * 
     */
    public CompanyTable() {}
    
    /**
     * getColumns
     * 
     * @return Column[]
     */
    @Override
    protected Column[] getColumns()
    {
        return CompanyColumns.values();
    }
    
    /**
     * CompanyColumns
     * 
     * @return enum
     * @ignored implements
     * @ignored Column&lt;Company&gt;
     */
    public enum CompanyColumns 
        implements 
            Column<Company>
    {
        NAME( "Nome" )
        {
            @Override
            public Object getValueAt( Company value ) 
            {
                return value.getName();
            }
        },        

        CONTACT( "Contato" )
        {
            @Override
            public Object getValueAt( Company value) 
            {
                return value.getContact();
            }
        };

        private String label;

        /**
         * CompanyColumns
         * 
         * @param label String
         */
        private CompanyColumns( String label )
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
}
