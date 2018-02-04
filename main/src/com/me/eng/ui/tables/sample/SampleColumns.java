package com.me.eng.ui.tables.sample;

import com.me.eng.application.FormulaApplicationServices;
import com.me.eng.domain.Sample;
import com.me.eng.domain.SampleFormmater;
import com.me.eng.ui.tables.Column;
import org.zkoss.zul.Label;

/**
 *
 * @author Matheus
 */
public enum SampleColumns
    implements 
        Column<Sample>
{
    NAME( "Amostra" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return value.getName();
        }
    },
    JOB( "Obra" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return value.getJob();
        }
    },
    RESISTENCE( "Resistência" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return SampleFormmater.newInstance().formatResistence( value );
        }
    },
    DIM_W( "L" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return value.getDimW();
        }
    },
    DIM_H( "A" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return value.getDimH();
        }
    },
    CP( "C.P. (Nº)" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return SampleFormmater.newInstance().formatId( value );
        }
    },
    FC( "Fc (N)" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return SampleFormmater.newInstance().formatFC( FormulaApplicationServices.getCurrent()
                         .getNewton().evaluate( value.getResistence() ) );
        }
    },
    RC( "Rc (MPa)" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return SampleFormmater.newInstance().formatRC( FormulaApplicationServices.getCurrent()
                            .getRc()
                            .evaluate( value.getResistence() ) );
        }
    },
    TYPE_RUPTURE( "Tipo de Ruptura" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return value.getRuptureType();
        }
    },

    AGE( "Idade" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return SampleFormmater.newInstance().formatAge( value );
        }
    },

    MOLD_DATE( "Moldagem" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return SampleFormmater.newInstance().formatDate( value.getDateExecuted() );
        }
    },

    RUPTURE_DATE( "Ruptura" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return SampleFormmater.newInstance().formatDate( value.getDateRupture() );
        }
    },
    
    PROOF( "Contra Prova" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            if ( value.getProofs().isEmpty() )
            {
                return "";
            }
            
            Label label = new Label( SampleFormmater.newInstance().formatProofs( value ) );
            label.setPre( true );
            label.setStyle( "font-size: 10px; font-weight: bold;" );
            
            return label;
        }
    },

    CLIENT( "Cliente" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return value.getClient();
        }
    };

    private final String label;

    private SampleColumns( String label )
    {
        this.label = label;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
}
