/* 
 *  Filename:    SampleColumns 
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

import com.me.eng.core.services.FormulaApplicationServices;
import com.me.eng.samples.domain.Sample;
import com.me.eng.samples.domain.SampleFormmater;
import com.me.eng.core.ui.tables.Column;
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
            return SampleFormmater.newInstance().formatName( value );
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
 
    FCK( "FCK" )
    {
        @Override
        public Object getValueAt( Sample value )
        {
            return SampleFormmater.newInstance().formatFCK( value );
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
            return SampleFormmater.newInstance().formatRupture( value );
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

    /**
     * SampleColumns
     * 
     * @param label String
     */
    private SampleColumns( String label )
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
