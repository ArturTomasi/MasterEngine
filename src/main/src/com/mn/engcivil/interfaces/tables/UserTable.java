package com.mn.engcivil.interfaces.tables;

import com.mn.engcivil.domain.User;

/**
 *
 * @author Matheus
 */
public class UserTable
    extends 
        AbstractTable<User>
{
    public enum Columns
        implements 
            Column<User>
    {
        LOGIN( "Login" )
        {
            @Override
            public Object getValueAt( User value )
            {
                return value.getLogin();
            }
        },
        NAME( "Nome" )
        {
            @Override
            public Object getValueAt( User value )
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
