package com.mn.engcivil.interfaces.tables;

import com.mn.engcivil.domain.Job;

/**
 *
 * @author Matheus
 */
public class JobTable
    extends 
        AbstractTable<Job>
{
    public enum Columns
        implements 
            Column<Job>
    {
        NAME( "Nome" )
        {
            @Override
            public Object getValueAt( Job value )
            {
                return value.getName();
            }
        },
        
        CLIENT( "Cliente" )
        {
            @Override
            public Object getValueAt( Job value )
            {
                return value.getClient();
            }
        };
        
        private String label;

        private Columns( String label )
        {
            this.label = label;
        }

        public String getLabel()
        {
            return label;
        }

        @Override
        public String toString()
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
