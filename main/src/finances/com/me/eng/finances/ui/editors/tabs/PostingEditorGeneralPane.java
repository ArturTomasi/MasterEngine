/*
 *  Filename:    PostingEditorGeneralPane
 *
 *  Author:      Artur Tomasi
 *  EMail:       tomasi.artur@gmail.com
 *  Internet:    www.masterengine.com.br
 *
 *  Copyright © 2018 by Over Line Ltda.
 *  100900-038, LAJEADO, RS
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
import com.me.eng.core.ui.selectors.UserSelector;
import com.me.eng.core.ui.util.Prompts;
import com.me.eng.finances.controllers.PostingController;
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.ui.editors.PostingEditor;
import com.me.eng.finances.ui.selectors.CompanySelector;
import com.me.eng.finances.ui.selectors.CompletionTypeSelector;
import com.me.eng.finances.ui.selectors.PostingCategorySelector;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Artur Tomasi
 */
public class PostingEditorGeneralPane 
    extends 
        SubEditorPanel<Posting>
{
    public static class Events
    {
        public static final String ON_CHANGE_VALUES = "onChangeValues";
        public static final String ON_CHANGE        = org.zkoss.zk.ui.event.Events.ON_CHANGE;
        public static final String ON_CHECK         = org.zkoss.zk.ui.event.Events.ON_CHECK;
    }
    
    private PostingEditor.Mode mode;
    
    private Posting source;
    
    /**
     * PostingEditorGeneralPane
     * 
     */
    public PostingEditorGeneralPane() 
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
        source.setName( nameField.getValue() );
        source.setRealDate( realDateFiled.getValue() );
        source.setEstimateDate( estimateDateField.getValue() );
        source.setRealDate( realDateFiled.getValue() );
        source.setRealValue( realValueField.getValue() );
        source.setEstimateValue( estimateValueField.getValue() );
        source.setOwner( userCombo.getSelectedItem() );
        source.setCompletionType( completionTypeCombo.getSelectedItem() );
        source.setCompletionAuto( ckCompletionAuto.isChecked() );
        source.setRepeat( ckRepeat.isChecked() );
        source.setPortionTotal( portionTotalFiled.getValue() );
        source.setCategory( postingCategoryCombo.getSelectedItem() );
        source.setCompany( companyCombo.getSelectedItem() );
        
        source.setState( PostingController.getInstance().makeState( source ) );
    }

    /**
     * setInput
     * 
     * @param source Posting
     */
    @Override
    public void setInput( Posting source ) 
    {
        this.source = source;
        
        nameField.setText( source.getName() );
        estimateValueField.setValue( source.getEstimateValue() );
        estimateDateField.setValue( source.getEstimateDate() );
        portionTotalFiled.setValue( source.getPortionTotal() );
        ckRepeat.setChecked( source.isRepeat() );
        ckCompletionAuto.setChecked( source.isCompletionAuto() );
        userCombo.setSelectedItem( source.getOwner() == null ? ApplicationContext.getInstance().getActiveUser() : source.getOwner() );
        completionTypeCombo.setSelectedItem( source.getCompletionType() );
        postingCategoryCombo.setSelectedItem( source.getCategory() );
        companyCombo.setSelectedItem( source.getCompany() );
        
        if( mode == PostingEditor.Mode.FINISH )
        {
            realDateFiled.setValue( source.getEstimateDate() );
            realValueField.setValue( source.getEstimateValue() );
        }
    }

    /**
     * validateInput
     * 
     * @param errors Errors
     */
    @Override
    public void validateInput( Errors errors )
    {
        boolean notFinish = mode != PostingEditor.Mode.FINISH;
        
        if( notFinish && estimateDateField.getValue() == null )
            errors.addError( "A data estimada não pode estar vazia !" );
      
        if( notFinish && ! estimateValueField.isValid() )
            errors.addError( "O valor estimado está inválido !" );
        
        if ( ! nameField.isValid() )
            errors.addError( "O Nome não pode estar vazio!" );
        
        if( ( ckCompletionAuto.isChecked() || ! notFinish ) && completionTypeCombo.getSelectedItem() == null )
            errors.addError( "O tipo de finalização não pode estar vazio" );
        
        if( realDateFiled.getValue() != null && completionTypeCombo.getSelectedItem() == null )
            errors.addError( "O tipo de finalização não pode estar vazio" );
        
        if( notFinish && companyCombo.getSelectedItem() == null )
            errors.addError( "A companhia do lançamento não pode estar vazio" );
        
        if( notFinish && userCombo.getSelectedItem() == null )
            errors.addError( "A responsável do lançamento não de estar vazio" );
        
        if( notFinish && postingCategoryCombo.getSelectedItem() == null )
            errors.addError( "A categoria do lançamento não pode estar vazio" );
        
        if( ! notFinish && ! realValueField.isValid()  )
            errors.addError( "O valor real não pode estar vazia !" );
        
        if( ! notFinish &&  realDateFiled.getValue() == null )
            errors.addError( "A data real não pode estar vazia !" );
        
        if( notFinish && realDateFiled.getValue() != null && ! realValueField.isValid() )
            errors.addError( "A valor real não pode ser 0!" );

        if( notFinish && realValueField.isValid() &&  realDateFiled.getValue() == null )
            errors.addError( "A data real não pode estar vazia !" );
    }
    
    /**
     * loadField
     * 
     * @param mode PostingEditor.Mode
     */
    public void loadField( PostingEditor.Mode mode )
    {
        this.mode = mode;
        
        ckRepeat.setDisabled( mode != PostingEditor.Mode.NEW );
        portionTotalFiled.setDisabled( ( source == null || ! source.isRepeat() ) && mode == PostingEditor.Mode.NEW );
        
        if( mode == PostingEditor.Mode.FINISH )
        {
            grid.getChildren().clear();
            
            nameField.setDisabled( true );
            
            grid.addRow( lbName, nameField );
            
            grid.addRow( lbRealDate, realDateFiled );
            grid.addRow( lbRealValue, realValueField );
            grid.addRow( lbCategory, postingCategoryCombo );
            grid.addRow( lbCompletion, completionTypeCombo );

            grid.setStyle( "width: 100%;" );
            grid.setRowHeight( "30px" );

            grid.setColspan( 0, 1, 3 );
            grid.setColspan( 1, 1, 3 );
            grid.setColspan( 2, 1, 3 );
            grid.setColspan( 3, 1, 3 );
            
            setInput( source ); //refresh postings
        }
        
        else if ( mode != PostingEditor.Mode.FINISH && mode != PostingEditor.Mode.NEW )
        {
            ckRepeat.setDisabled( true );
            lbPortion.setValue( "Parcela: " + source.getPortion() + " de " + source.getPortionTotal() );
            portionTotalFiled.setVisible( false ); 
            
            grid.setColspan( 7, 2, 2 );
        }
        
        completionTypeCombo.getParent().getParent().setVisible( mode == PostingEditor.Mode.FINISH );
    }
    
     
    /**
     * showCompletionType
     * 
     */
    private void showCompletionType()
    {
        boolean visible = ckCompletionAuto.isChecked() || realDateFiled.getValue() != null || realValueField.getValue() != null;
        
        completionTypeCombo.getParent().getParent().setVisible( visible );
    }
    
    
    /**
     * fireEvent
     * 
     */
    private void fireEvent()
    {
        getInput( source );
        
        if( source.getEstimateDate() != null )
        {
            Executions.getCurrent().postEvent( new Event( Events.ON_CHANGE_VALUES, this, source ) );
        }
        
        else
        {
            Prompts.info( "Para selecionar está opção é necessário preencher uma data estimada" );
            
            ckRepeat.setChecked( false );
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setStyle( "overflow-x: hidden !important;" );
        nameField.setWidth( "100%" );
        lbName.setStyle( "width: 115px; display: block;" );
        
        realValueField.setWidth( "200px" );
        estimateValueField.setWidth( "200px" );
        portionTotalFiled.setWidth( "200px" );
        realDateFiled.setWidth( "200px" );
        estimateDateField.setWidth( "200px" );
        companyCombo.setHflex( null );
        companyCombo.setWidth( "640px" );
        
        realValueField.setFormat( "R$ ##0.00" );
        estimateValueField.setFormat( "R$ ##0.00" );
        portionTotalFiled.setConstraint( "no empty,min 1 max 12" );
        
        
        grid.addRow( lbName, nameField );
        grid.addRow( lbEstimateDate, estimateDateField, lbRealDate, realDateFiled );
        grid.addRow( lbEstimateValue, estimateValueField, lbRealValue, realValueField );
        grid.addRow( lbCategory, postingCategoryCombo );
        grid.addRow( lbCompany, companyCombo );
        grid.addRow( lbCompletion, completionTypeCombo );
        grid.addRow( lbUser, userCombo );
        grid.addRow( ckRepeat, ckCompletionAuto, lbPortion, portionTotalFiled );
        
        grid.setStyle( "width: 100%;" );
        grid.setRowHeight( "30px" );
        
        grid.setColspan( 0, 1, 3 );
        grid.setColspan( 3, 1, 3 );
        grid.setColspan( 4, 1, 3 );
        grid.setColspan( 5, 1, 3 );
        grid.setColspan( 6, 1, 3 );
        
        appendChild( grid );
        
        ckRepeat.addEventListener( Events.ON_CHECK, e ->
        {
            portionTotalFiled.setDisabled( ! ckRepeat.isChecked() );
            fireEvent();
        } );
        
        ckCompletionAuto.addEventListener( Events.ON_CHECK, e -> showCompletionType() );
        
        realDateFiled.addEventListener( Events.ON_CHANGE, e -> showCompletionType() );
       
        realValueField.addEventListener( Events.ON_CHANGE, e -> showCompletionType() );
        
    }
    
    private TableLayout grid = new TableLayout();
    
    private Textbox nameField                               = new Textbox();
    private Label lbName                                    = new Label( "Nome: " );
    
    private Label  lbRealDate                               = new Label( "Data Real: " );
    private Datebox realDateFiled                           = new Datebox();
    
    private Label  lbEstimateDate                           = new Label( "Data Estimada: " );
    private Datebox estimateDateField                       = new Datebox();
    
    private Label lbRealValue                               = new Label( "Valor Real: " );
    private Doublebox realValueField                        = new Doublebox();
    
    private Label lbEstimateValue                           = new Label( "Valor Estimado: " );
    private Doublebox estimateValueField                    = new Doublebox();
    
    private Checkbox ckCompletionAuto                       = new Checkbox( "Finaliza Automaticamente" );
    private Checkbox ckRepeat                               = new Checkbox( "Repete" ); 
    
    private Label lbPortion                                 = new Label( "Parcelas:" );
    private Spinner portionTotalFiled                       = new Spinner();

    private Label lbCategory                                = new Label( "Categoria:" );
    private PostingCategorySelector postingCategoryCombo    = new PostingCategorySelector();

    private Label lbCompletion                              = new Label( "Tipo de Finalização: " );
    private CompletionTypeSelector completionTypeCombo      = new CompletionTypeSelector();

    private Label lbUser                                    = new Label( "Responsável: " );
    private UserSelector userCombo                          = new UserSelector();
    
    private Label lbCompany                                 = new Label( "Companhia: " );
    private CompanySelector companyCombo                    = new CompanySelector();
}
