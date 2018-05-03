/* 
 *  Filename:    CapstoneTable 
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
package com.me.eng.samples.ui.tables;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.samples.domain.Capstone;
import com.me.eng.samples.repositories.CapstoneRepository;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.tables.AbstractTable;
import com.me.eng.core.ui.tables.ActionColumn;
import com.me.eng.core.ui.tables.Column;
import com.me.eng.samples.ui.editors.CapstoneEditor;
import com.me.eng.core.ui.util.Prompts;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Matheus
 */
public class CapstoneTable
    extends 
        AbstractTable<Capstone>
{
    public enum Columns
        implements 
            Column<Capstone>
    {
        NAME( "Nome" )
        {
            @Override
            public String getWidth() { return "true"; }
            
            @Override
            public Object getValueAt( Capstone value )
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

    private ActionColumn<Capstone> editColumn = new ActionColumn<Capstone>( "Editar" ) 
    {
        @Override
        protected void execute( Capstone value )
        {
            CapstoneEditor.edit( getParent(), new Callback<Capstone>( value )
            {
                @Override
                public void acceptInput()
                {
                    try
                    {
                        ApplicationServices.getCurrent()
                                .getCapstoneRepository()
                                .update( getSource() );

                        updateElement( getSource() );
                    }

                    catch ( Exception e )
                    {
                        handleException( e );
                    }
                }
            } );
        }
    };
    
    private ActionColumn<Capstone> removeColumn = new ActionColumn<Capstone>( "Excluir" ) 
    {
        @Override
        protected void execute( Capstone value )
        {
            Prompts.confirm( "Realmente excluir \"" + value + "\"?", new Callback<Capstone>( value )
            {
                @Override
                public void acceptInput()
                {
                    try
                    {
                        CapstoneRepository repository = ApplicationServices.getCurrent()
                                .getCapstoneRepository();
                        
                        int v = repository.countSamples( getSource() );
                        
                        if ( v > 0 )
                        {
                            Messagebox.show( "Este Tipo de capeamento está em uso por " + v + " amostra(s).\n" + 
                                             "Não é possível proceder com a exclusão." );
                        }
                        
                        else
                        {
                            repository.delete( getSource() );

                            removeElement( getSource() );
                        }
                    }

                    catch ( Exception e )
                    {
                        handleException( e );
                    }
                }
            } );
        }
    };
    
    /**
     * CapstoneTable
     * 
     */
    public CapstoneTable(){}
    
    /**
     * setEditable
     * 
     * @param editable boolean
     */
    public final void setEditable( boolean editable ) 
    {
        if ( editable )
        {
            addColumn( editColumn );
            addColumn( removeColumn );
        }
        
        else
        {
            removeColumn( editColumn );
            removeColumn( removeColumn );
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
