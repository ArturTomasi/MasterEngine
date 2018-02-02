package com.mn.engcivil.interfaces.tables;

import com.mn.engcivil.domain.Client;

/**
 *
 * @author Matheus
 */
public class ClientTable
    extends 
        AbstractTable<Client>

{
    public enum Columns
        implements 
            Column<Client>
    {
        NAME( "Nome" )
        {
            @Override
            public Object getValueAt( Client value )
            {
                return value.getName();
            }
        },
        
        ADDRESS( "Endereço" )
        {
            @Override
            public Object getValueAt( Client value )
            {
                return value.getAddress();
            }
        },
        
        INFO( "Informações" )
        {
            @Override
            public Object getValueAt( Client value )
            {
                return value.getInfo();
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
