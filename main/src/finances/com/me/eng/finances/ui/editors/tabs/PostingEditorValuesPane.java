/*
 *  Filename:    PostingEditorValuesPane
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.ui.editors.tabs;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.ui.editors.Errors;
import com.me.eng.core.ui.editors.tabs.SubEditorPanel;
import com.me.eng.core.ui.parts.TableLayout;
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.domain.PostingState;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;

/**
 *
 * @author Artur Tomasi
 */
public class PostingEditorValuesPane 
    extends 
        SubEditorPanel<Posting>
{
    private Posting posting;
    private HashMap<Integer, Datebox> mapDates    = new HashMap();
    private HashMap<Integer, Doublebox> mapValues = new HashMap();
    private Calendar calendar = Calendar.getInstance();
    /**
     * PostingEditorValuesPane
     * 
     */
    public PostingEditorValuesPane()
    {
        initComponents();
    }

    /**
     * getInput
     * 
     * @param source Posting
     */
    @Override
    public void getInput( Posting source ) 
    {
        try
        {
            this.posting = source;

            posting.setPortion( 1 );

            if( posting.isRepeat() )
            {
                source.getChilds().clear();

                for ( int i = 1; i < posting.getPortionTotal() ; i++ )
                {
                    Posting p = source.clone();

                    p.setEstimateDate( mapDates.get( i - 1 ).getValue() );
                    p.setEstimateValue( mapValues.get( i - 1 ).getValue() );
                    p.setPortion( i + 1 );

                    source.addChild( p );
                }

                if( ! source.getChilds().isEmpty() && posting.getState() != PostingState.PROGRESS )
                {
                    posting.getChilds().get( 0 ).setState( PostingState.PROGRESS );
                }
            }
        }
        
        catch ( CloneNotSupportedException | WrongValueException e )
        {
            ApplicationContext.getInstance().handleException( e );
        }
    }

    /**
     * setInput
     * 
     * @param source Posting
     */
    @Override
    public void setInput( Posting source )
    {
        posting = source;
        
        updateGrid();
    }

    /**
     * validateInput
     * 
     * @param erros Errors
     */
    @Override
    public void validateInput( Errors erros ) 
    {
        if( posting.isRepeat() && posting.getPortionTotal() - 1 != mapDates.size() )
        {
            erros.addError( "Verifique todos os valores das parcelas" );
        }
        
        if( posting.isRepeat() )
        {
            mapDates.values().stream().forEach( (field) ->
            {
                if ( field.getValue() == null )
                    erros.addError( "Preencha todas as datas \"ABA VALORES\"" ); return;

            } );

            mapValues.values().stream().forEach( (value) -> 
            {
                if( value.getValue() == null || value.getValue() <= 0.0 )
                    erros.addError( "Preencha todas os valores \"ABA VALORES\"" ); return;
            } );
        }
    }
    
    /**
     * updateGrid
     * 
     */
    private void updateGrid()
    {
        if( posting != null && posting.isRepeat() )
        {
            grid.getChildren().clear();

            grid.addRow( new Label( "Parcelas" ), new Label( "Data Estimada" ), new Label( "Valor Estimado" ) );

            calendar.setTime( posting.getEstimateDate() );
            
            for ( int i = 0; i < posting.getPortionTotal() - 1; i++ )
            {
                Datebox dateField   = new Datebox();
                Doublebox numberField = new Doublebox();
                numberField.setFormat( "R$ ##0.00" );
                
                if( mapDates.get( i ) != null && mapValues.get( i ) != null ) //Troca de abas
                {
                    calendar.setTime( mapDates.get( i ).getValue() );

                    dateField.setValue( new Date( calendar.getTimeInMillis() ) );
                    numberField.setValue( mapValues.get( i ).getValue() );
                }
                
                else 
                {
                    calendar.add( Calendar.MONTH, 1 );

                    dateField.setValue( new Date( calendar.getTimeInMillis() ) );
                    numberField.setValue( posting.getEstimateValue() );
                }
                
                mapDates.put( i,  dateField );
                mapValues.put( i, numberField );
                
                grid.addRow( new Label( calendar.getDisplayName( Calendar.MONTH, Calendar.LONG, Locale.getDefault() ) ), dateField, numberField );
            }
        }
    }
    
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        appendChild( grid );
    }
    
    private TableLayout grid = new TableLayout();
}
