package com.mn.engcivil.interfaces.editors.tabs;

import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.domain.SampleFormmater;
import com.mn.engcivil.domain.TimeUnit;
import com.mn.engcivil.domain.util.SampleValidator;
import com.mn.engcivil.interfaces.editors.Errors;
import com.mn.engcivil.interfaces.parts.TableLayout;
import com.mn.engcivil.interfaces.selectors.JobSelector;
import com.mn.engcivil.interfaces.selectors.RuleSelector;
import com.mn.engcivil.interfaces.selectors.RuptureTypeSelector;
import com.mn.engcivil.interfaces.selectors.SampleNameSelector;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

/**
 *
 * @author Matheus
 */
public class SampleEditorGeneralPane
    extends 
        SubEditorPanel<Sample>
{
    private Sample source;
    
    public SampleEditorGeneralPane()
    {
        initComponents();
    }
    
    @Override
    public void setInput( Sample value )
    {
        this.source = value;
        
        jobSelector.setClient( value.getClient() );
        
        jobSelector.setSelectedItem( value.getJob() );
        tfResistence.setValue( value.getResistence() );
        tfDimW.setValue( value.getDimW() );
        tfDimH.setValue( value.getDimH() );
        dtCreated.setValue( value.getDateCreated() );
        dtExecuted.setValue( value.getDateExecuted() );
        dtRupture.setValue( value.getDateRupture() );
        ruleSelector.setSelectedItem( value.getRule() );
        sampleNameSelector.setSelectedValue( value.getName() );
        ruptureTypeSelector.setSelectedValue( value.getRuptureType() );
        tfNF.setValue( value.getNf() );
        tfNumber.setValue( SampleFormmater.newInstance().formatId( value ) );
        
        tfEstimatedUnitRuptureDate.setValue( value.getEstimatedRupture() );
        radiogroup.setSelectedIndex( value.getEstimatedUnitRupture() );
        
        tfNotificationRupture.setValue( value.getNotificationRupture() );
        
        tfTrace.setValue( value.getTrace() );
        
        initProofField();
        
        updateProofFields();
        updateRuptureFields();
        updateNotificationFields();
    }

    @Override
    public void getInput( Sample value )
    {
        try
        {
            value.setJob( jobSelector.getSelectedItem() );
            value.setResistence( tfResistence.getValue() );
            value.setDimW( tfDimW.getValue() );
            value.setDimH( tfDimH.getValue() );
            value.setDateExecuted( dtExecuted.getValue() );
            value.setDateRupture( dtRupture.getValue() );
            value.setRule( ruleSelector.getSelectedItem() );
            value.setName( sampleNameSelector.getText() );
            value.setRuptureType( ruptureTypeSelector.getSelectedValue() );
            value.setNf( tfNF.getValue() );
            value.setEstimatedRupture( tfEstimatedUnitRuptureDate.getValue() );
            value.setEstimatedUnitRupture( radiogroup.getSelectedIndex() );
            value.setNotificationRupture( tfNotificationRupture.getValue() );
            value.setNotificationRuptureDate( getNotificationRuptureDate() );
            value.setTrace( tfTrace.getValue() );

            if ( value.isNew() )
            {
                for ( Map.Entry<Integer, Long> entry : mapProofDates.entrySet() ) 
                {
                    Sample proof = value.createProof();
                    
                    proof.setEstimatedRupture( entry.getValue() );
                    proof.setDateRupture( TimeUnit.values()[value.getEstimatedUnitRupture()].plus( proof.getDateExecuted(), entry.getValue() ) );
                }
            }
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void initProofField()
    {
        if ( ! source.isNew() )
        {
            tableProofLayout.getChildren().clear();
        }
        
        else
        {
            tfCountProof.setValue( source.getAmountProofs() );
            
            if ( ! source.getProofs().isEmpty() )
            {
                for ( int i = 0; i < source.getProofs().size(); i++ )
                {
                    mapProofDates.put( i, source.getProofs().get( i ).getEstimatedRupture() );
                }
            }
            
            source.setProofs( new LinkedList() ); //remove last proof
            
            updateProofFields();
        }
    }
    
    private void updateRuptureDate()
    {
        Long value = tfEstimatedUnitRuptureDate.getValue();
        
        if ( value != null )
        {
            TimeUnit unit = radiogroup.getSelectedItem().getValue();
            
            Date date = unit.plus( dtExecuted.getValue(), value );
            
            dtRupture.setValue( date );
        }
        
        updateNotificationFields();
    }
    
    private void updateEstimatedRuptureUnit()
    {
        if ( dtRupture.getValue() != null )
        {
            TimeUnit unit = radiogroup.getSelectedItem().getValue();
            
            tfEstimatedUnitRuptureDate.setValue( unit.between( dtExecuted.getValue(), dtRupture.getValue() ) );
        }
        
        else
        {
            tfEstimatedUnitRuptureDate.setValue( null );
        }
        
        updateNotificationFields();
    }
    
    private void updateRuptureFields()
    {
        boolean enabled = dtExecuted.getValue() != null;
        
        tfEstimatedUnitRuptureDate.setDisabled( ! enabled );
        
        for ( Radio radio : radiogroup.getItems() )
        {
            radio.setDisabled( ! enabled );
        }
        
        dtRupture.setDisabled( ! enabled );   
    }
    
    private void updateNotificationFields()
    {
        if ( tfNotificationRupture.getValue() != null )
        {
            Date estimated = getNotificationRuptureDate();
            
            if ( estimated != null )
            {
                lbNotificationDate.setValue( "(" + SampleFormmater.newInstance().formatDate( estimated ) + ")" );
            }
        }
        
        else
        {
            lbNotificationDate.setValue( "" );
        }
    }
    
    private void updateProofFields()
    {
        if ( tfCountProof.intValue() > 0 && dtExecuted.getValue() != null )
        {
            booxProof.getChildren().clear();
            
            for ( int i = 0; i < tfCountProof.intValue(); i++ )
            {   
                Long days = mapProofDates.getOrDefault( i, Sample.DEFAULT_DAYS_PROOF );
                
                Date date = mapProof( i, days );
                
                booxProof.appendChild( createProofComponent( i, date, days ) );
            }
        }
    }

    private Date getNotificationRuptureDate()
    {
        if ( dtRupture.getValue() != null && tfNotificationRupture.getValue() != null )
        {
            return TimeUnit.DAY.plus( dtRupture.getValue(), -tfNotificationRupture.getValue() );
        }
        
        return null;
    }
    
    private Date mapProof( int index, long days )
    {
        mapProofDates.put( index, days );
        
        return TimeUnit.DAY.plus( dtExecuted.getValue(), days );
    }
    
    private Component createProofComponent( final int index, Date date, Long days )
    {
        Label lb = new Label( "#" + (index + 1) + " Contra Prova" );
        lb.setWidth( "120px" );
        
        final Longbox longbox = new Longbox();
        longbox.setValue( days );
        longbox.setWidth( "100px" );
        
        final Datebox datebox = new Datebox();
        datebox.setValue( date );
        datebox.setDisabled( true );
        
        Hbox box = new Hbox();
        box.setSpacing( "0px" );
        box.setWidths( "120px,120px,75px" );
        box.appendChild( lb );
        box.appendChild( longbox );
        box.appendChild( datebox );
        
        longbox.addEventListener( Events.ON_CHANGE, (Event t) -> 
        {
            if ( t.getTarget() instanceof Longbox )
            {
                Longbox self = (Longbox)t.getTarget();
                
                datebox.setValue( mapProof( index, self.getValue() ) );
            }
        } );
        
        return box;
    }

    @Override
    public void validateInput( Errors e )
    {
        SampleValidator.getInstance().isValidResistence( source, tfResistence.getValue(), e );
        
        if ( Objects.isNull( sampleNameSelector.getText() ) || sampleNameSelector.getText().trim().isEmpty() )
        {
            e.addError( "Corpo de Prova" );
        }
        
        if ( Objects.isNull( ruleSelector.getSelectedItem() ) )
        {
            e.addError( "Norma" );
        }
        
        if ( Objects.isNull( jobSelector.getSelectedItem() ) )
        {
            e.addError( "Obra" );
        }
        
        if ( ! Objects.isNull( dtExecuted.getValue() ) && 
             ! Objects.isNull( dtRupture.getValue() ) )
        {
            if ( dtExecuted.getValue().after( dtRupture.getValue() ) )
            {
                e.addError( "Data de ruptura deve ser igual ou superior a data de moldagem" );
            }
        }
        
        if ( tableProofLayout.isVisible() )
        {
            for ( Map.Entry<Integer, Long> entry : mapProofDates.entrySet() ) 
            {
                if ( entry.getValue() == null || entry.getValue() <= 0 )
                {
                    e.addError( "Contra(s) prova(s) devem possuir um valor para  as datas" );
                }
            }
        }
    }
    
    private void initComponents()
    {
        lbTrace.setValue( "Traço" );
        lbJob.setValue( "Obra:" );
        lbCapstore.setValue( "Tipo de capeamento:" );
        lbRule.setValue( "Norma:" );
        lbNumber.setValue( "C.P. (Nº):" );
        lbNF.setValue( "Nota Fiscal:" );
        lbName.setValue( "Corpo de Prova" );
        lbResistence.setValue( "Resistência" );
        lbDimension.setValue( "Dimensão (LxA):" );
        lbX.setValue( "X" );
        lbDateCreated.setValue( "Data de cadastro:" );
        lbDateExecuted.setValue( "Data de moldagem:" );
        lbDateRupture.setValue( "Data de ruptura:" );
        lbRuptureType.setValue( "Tipo de Ruptura:" );
        lbNotificationRupture.setValue( "Notificação (dias):" );
        lbCountProof.setValue( "Contra Prova (Nº):" );
        
        tfNotificationRupture.setWidth( "70px" );
        tfEstimatedUnitRuptureDate.setWidth( "70px" );
        
        lbNotificationDate.setStyle( "color: gray" );
        
        radiogroup.setOrient( "vertical" );
        
        tfResistence.setFormat( "#,##0.##" );
        tfResistence.setConstraint( "no negative" );
        
        Hbox notificationBox = new Hbox();
        notificationBox.setAlign( "middle" );
        notificationBox.setSpacing( "10px" );
        notificationBox.appendChild( tfNotificationRupture );
        notificationBox.appendChild( lbNotificationDate );
        
        Hbox estimatedBox = new Hbox();
        estimatedBox.setAlign( "center" );

        for ( TimeUnit unit : TimeUnit.values() )
        {
            Radio radio = new Radio();
            radio.setLabel( unit.getLabel() );
            radio.setValue( unit );

            radiogroup.appendChild( radio );
        }
        
        estimatedBox.appendChild( tfEstimatedUnitRuptureDate );
        estimatedBox.appendChild( radiogroup );
                
        tfNumber.setReadonly( true );
        dtCreated.setDisabled( true );
        
        tableLayout.addRow( lbJob, jobSelector );
        tableLayout.addRow( lbNumber, tfNumber );
        tableLayout.addRow( lbNF, tfNF );
        tableLayout.addRow( lbName, sampleNameSelector );
        tableLayout.addRow( lbResistence, tfResistence );
        tableLayout.addRow( lbRuptureType, ruptureTypeSelector );
        tableLayout.addRow( lbTrace, tfTrace );
        tableLayout.addRow( lbDimension, tfDimW, lbX, tfDimH );
        tableLayout.addRow( lbRule, ruleSelector );
        tableLayout.addRow( lbDateCreated, dtCreated );
        tableLayout.addRow( lbDateExecuted, dtExecuted );
        tableLayout.addRow( lbDateRupture, estimatedBox, dtRupture );
        tableLayout.addRow( lbNotificationRupture, notificationBox );
        
        tableLayout.setColspan( 0, 1, 3 );
        tableLayout.setColspan( 1, 1, 3 );
        tableLayout.setColspan( 2, 1, 3 );
        tableLayout.setColspan( 3, 1, 3 );
        tableLayout.setColspan( 4, 1, 3 );
        tableLayout.setColspan( 5, 1, 3 );
        tableLayout.setColspan( 6, 1, 3 );
        tableLayout.setColspan( 8, 1, 3 );
        tableLayout.setColspan( 9, 1, 3 );
        tableLayout.setColspan( 10, 1, 3 );
        tableLayout.setColspan( 11, 1, 2 );
        tableLayout.setColspan( 12, 1, 3 );
        
        tableLayout.setWidths( "120px" );

        booxProof.setVflex( "true" );
        booxProof.setHflex( "true" );
        booxProof.setSpacing( "5px" );
        booxProof.setStyle( "border: 1px solid #cfcfcf; padding: 5px;" );
        
        tableProofLayout.setStyle( "width: 100%" );
        tableProofLayout.addRow( lbCountProof, tfCountProof );
        tableProofLayout.addRow( booxProof );
        tableProofLayout.setColspan( 1, 0, 2 );
        tableProofLayout.setWidths( "120px" );
        
        appendChild( tableLayout );
        appendChild( tableProofLayout );
        
        dtExecuted.addEventListener( org.zkoss.zk.ui.event.Events.ON_CHANGE, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                updateRuptureFields();
                updateRuptureDate();
                updateProofFields();
            }
        } );
        
        dtRupture.addEventListener( org.zkoss.zk.ui.event.Events.ON_CHANGE, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                updateEstimatedRuptureUnit();
            }
        } );
        
        radiogroup.addEventListener( org.zkoss.zk.ui.event.Events.ON_CHECK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                CheckEvent e = (CheckEvent) t;
                
                radiogroup.setSelectedItem( (Radio) e.getTarget() );
                
                updateRuptureDate();
            }
        } );
        
        tfEstimatedUnitRuptureDate.addEventListener( org.zkoss.zk.ui.event.Events.ON_CHANGE, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                updateRuptureDate();
            }
        } );
        
        tfNotificationRupture.addEventListener( org.zkoss.zk.ui.event.Events.ON_CHANGE, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                updateNotificationFields();
            }
        } );
        
        tfCountProof.addEventListener( org.zkoss.zk.ui.event.Events.ON_CHANGE, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                mapProofDates.clear();
                
                updateProofFields();
            }
        } );
    }
    
    private Label lbResistence = new Label();
    private Label lbNF = new Label();
    private Label lbNumber = new Label();
    private Label lbDimension = new Label();
    private Label lbName = new Label();
    private Label lbDateCreated = new Label();
    private Label lbDateExecuted = new Label();
    private Label lbDateRupture = new Label();
    private Label lbX = new Label();
    private Label lbRule = new Label();
    private Label lbCapstore = new Label();
    private Label lbRuptureType = new Label();
    private Label lbJob = new Label();
    private Label lbNotificationRupture = new Label();
    private Label lbNotificationDate = new Label();
    private Label lbTrace = new Label();
    private Label lbCountProof = new Label();
    
    private Textbox tfTrace = new Textbox();
    private Longbox tfEstimatedUnitRuptureDate = new Longbox();
    private Longbox tfNotificationRupture = new Longbox();
    private Intbox tfNF = new Intbox();
    private Intbox tfCountProof = new Intbox();
    private Doublebox tfResistence = new Doublebox();
    private Textbox tfNumber = new Textbox();
    private Intbox tfDimW = new Intbox();
    private Intbox tfDimH = new Intbox();
    private SampleNameSelector sampleNameSelector = new SampleNameSelector();
    private Datebox dtCreated = new Datebox();
    private Datebox dtExecuted = new Datebox();
    private Datebox dtRupture = new Datebox();
    
    private Radiogroup radiogroup = new Radiogroup();
    
    private RuptureTypeSelector ruptureTypeSelector = new RuptureTypeSelector();
    
    private RuleSelector ruleSelector = new RuleSelector();
    
    private JobSelector jobSelector = new JobSelector();
    
    private TableLayout tableLayout = new TableLayout();
    
    private TableLayout tableProofLayout = new TableLayout();
    private Vbox booxProof = new Vbox();
    private HashMap<Integer, Long> mapProofDates = new HashMap();
}