package com.me.eng.ui.tables;

import com.me.eng.domain.Contact;

/**
 *
 * @author Matheus
 */
public class ContactTable
    extends 
        AbstractTable<Contact>
{
    public enum Columns
        implements 
            Column<Contact>
    {
        NAME( "Nome" )
        {
            @Override
            public Object getValueAt( Contact value )
            {
                return value.getName();
            }
        },
        PHONE( "Telefone" )
        {
            @Override
            public Object getValueAt( Contact value )
            {
                return value.getTelephone();
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
