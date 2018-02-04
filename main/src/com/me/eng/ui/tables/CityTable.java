package com.me.eng.ui.tables;

import com.me.eng.domain.City;

/**
 *
 * @author Matheus
 */
public class CityTable
    extends 
        AbstractTable<City>
{
    public enum Columns
        implements 
            Column<City>
    
    {
        UF( "UF" ) 
        {
            @Override
            public Object getValueAt( City value )
            {
                return value.getState().getUF();
            }
        },
        
        NAME( "Nome" )
        {
            @Override
            public Object getValueAt( City value )
            {
                return value.getName();
            }

            @Override
            public String getWidth()
            {
                return "true";
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
