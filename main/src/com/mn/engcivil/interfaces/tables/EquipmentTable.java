package com.mn.engcivil.interfaces.tables;

import com.mn.engcivil.application.ApplicationServices;
import com.mn.engcivil.domain.Equipment;
import com.mn.engcivil.domain.repositories.EquipmentRepository;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.editors.EquipmentEditor;
import com.mn.engcivil.interfaces.util.Prompts;
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
    
    public EquipmentTable()
    {
    }
    
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
    
    @Override
    protected Column[] getColumns()
    {
        return Columns.values();
    }
}
