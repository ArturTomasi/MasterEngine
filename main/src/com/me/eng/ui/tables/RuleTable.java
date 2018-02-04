package com.me.eng.ui.tables;

import com.me.eng.domain.Rule;

/**
 *
 * @author Matheus
 */
public class RuleTable
    extends 
        AbstractTable<Rule>
{
    public enum Columns
        implements 
            Column<Rule>
    {
        CODE( "Código" )
        {
            @Override
            public Object getValueAt( Rule value )
            {
                return value.getName();
            }
        },
        DESCRIPTION( "Descrição" )
        {
            @Override
            public Object getValueAt( Rule value )
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
