/* 
 *  Filename:    EquipmentTable 
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
package com.me.eng.ui.tables;

import com.me.eng.services.ApplicationServices;
import com.me.eng.domain.Equipment;
import com.me.eng.domain.repositories.EquipmentRepository;
import com.me.eng.ui.Callback;
import com.me.eng.ui.editors.EquipmentEditor;
import com.me.eng.ui.util.Prompts;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Matheus
 */
public class EquipmentTable
    extends 
        AbstractTable<Equipment>
{
    public enum Columns
        implements 
            Column<Equipment>
    {
        NAME( "Nome" )
        {
            @Override
            public Object getValueAt( Equipment value )
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

    private ActionColumn<Equipment> editColumn = new ActionColumn<Equipment>( "Editar" )
    {
        @Override
        protected void execute( Equipment value )
        {
            EquipmentEditor.edit( getParent(), new Callback<Equipment>( value )
            {
                @Override
                public void acceptInput()
                {
                    try
                    {
                        ApplicationServices.getCurrent()
                                .getEquipmentRepository()
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
    
    private ActionColumn<Equipment> removeColumn = new ActionColumn<Equipment>( "Excluir" )
    {
        @Override
        protected void execute( Equipment value )
        {
            Prompts.confirm( "Realmente excluir \"" + value + "\"?", new Callback<Equipment>( value )
            {
                @Override
                public void acceptInput()
                {
                    try
                    {
                        EquipmentRepository repository = ApplicationServices.getCurrent()
                                .getEquipmentRepository();

                        int v = repository.countSamples( getSource() );
                        
                        if ( v > 0 )
                        {
                            Messagebox.show( "Este equipamento está em uso por " + v + " amostra(s).\n" + 
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
    
    public EquipmentTable() {}
    
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
