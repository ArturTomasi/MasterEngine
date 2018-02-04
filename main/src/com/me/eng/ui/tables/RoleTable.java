package com.me.eng.ui.tables;

import com.me.eng.domain.Role;

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

        private Columns( String label )
        {
            this.label = label;
        }

        @Override
        public String getLabel()
        {
            return label;
        }
    }

    @Override
    protected Column[] getColumns()
    {
        return Columns.values();
    }
}
