package com.me.eng.ui.tables;

import com.me.eng.application.ApplicationServices;
import com.me.eng.domain.Capstone;
import com.me.eng.domain.repositories.CapstoneRepository;
import com.me.eng.ui.Callback;
import com.me.eng.ui.editors.CapstoneEditor;
import com.me.eng.ui.util.Prompts;
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
    
    public CapstoneTable()
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
