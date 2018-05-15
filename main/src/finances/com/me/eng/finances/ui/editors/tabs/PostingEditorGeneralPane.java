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
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.ui.selectors.CompletionTypeSelector;
import com.me.eng.finances.ui.selectors.PostingCategorySelector;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
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
        source.setName( nameField.getName() );
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
    }

    /**
     * setInput
     * 
     * @param source Posting
     */
    @Override
    public void setInput( Posting source ) 
    {
        nameField.setText( source.getName() );
        realValueField.setValue( source.getRealValue() );
        estimateValueField.setValue( source.getEstimateValue() );
        estimateDateField.setValue( source.getEstimateDate() );
        realDateFiled.setValue( source.getRealDate() );
        portionTotalFiled.setValue( source.getPortionTotal() );
        ckRepeat.setChecked( source.isRepeat() );
        ckCompletionAuto.setChecked( source.isCompletionAuto() );
        userCombo.setSelectedItem( source.getOwner() == null ? ApplicationContext.getInstance().getActiveUser() : source.getOwner() );
        completionTypeCombo.setSelectedItem( source.getCompletionType() );
        postingCategoryCombo.setSelectedItem( source.getCategory() );
    }

    /**
     * validateInput
     * 
     * @param e Errors
     */
    @Override
    public void validateInput( Errors e )
    {
        if ( ! nameField.isValid() )
            e.addError( "Nome" );
        
        if ( ! estimateValueField.isValid() )
            e.addError( "Valor estimado" );
        
        if ( ! estimateDateField.isValid() )
            e.addError( "Data estimada" );
        
        if ( userCombo.getSelectedItem() == null )
            e.addError( "Usuário" );
        
        if ( completionTypeCombo.getSelectedItem() == null )
            e.addError( "Tipo de Finalização" );
        
        if ( postingCategoryCombo.getSelectedItem() == null )
            e.addError( "Categoria de lançamento" );
    }
    
     
    /**
     * showCompletionType
     * 
     */
    private void showCompletionType()
    {
        boolean visible = ckCompletionAuto.isChecked()|| realDateFiled.getValue() != null || realValueField.isValid();
        
        completionTypeCombo.setVisible( visible );
        
        lbCompletion.setVisible( visible );
    }
    
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        nameField.setWidth( "100%" );
        
        realValueField.setWidth( "200px" );
        estimateValueField.setWidth( "200px" );
        portionTotalFiled.setWidth( "200px" );
        realDateFiled.setWidth( "200px" );
        estimateDateField.setWidth( "200px" );
        
        realValueField.setFormat( "R$ ##0.00" );
        estimateValueField.setFormat( "R$ ##0.00" );
        portionTotalFiled.setConstraint( "no empty,min 1 max 12" );
        
        grid.addRow( lbName, nameField );
        grid.addRow( lbEstimateDate, estimateDateField, lbRealDate, realDateFiled );
        grid.addRow( lbEstimateValue, estimateValueField, lbRealValue, realValueField );
        grid.addRow( lbCategory, postingCategoryCombo );
        grid.addRow( lbCompletion, completionTypeCombo );
        grid.addRow( lbUser, userCombo );
        grid.addRow( ckRepeat, ckCompletionAuto, lbPortion, portionTotalFiled );
        
        grid.setStyle( "width: 100%;" );
        grid.setRowHeight( "30px" );
        
        grid.setColspan( 0, 1, 3 );
        grid.setColspan( 3, 1, 3 );
        grid.setColspan( 4, 1, 3 );
        grid.setColspan( 5, 1, 3 );
        
        appendChild( grid );
        
        ckRepeat.addEventListener( Events.ON_CHECK, (Event t) ->
        {
            portionTotalFiled.setDisabled( ! ckRepeat.isChecked() );
//            fireEvent();
        } );
        
        ckRepeat.addEventListener( Events.ON_CHECK, (Event t) ->
        {
            showCompletionType();
        } );
        
        realDateFiled.addEventListener( Events.ON_CHANGE, (Event t) ->
        {
            showCompletionType();
        } );
       
        realValueField.addEventListener( Events.ON_CHANGE, (Event t) -> 
        {
            showCompletionType();
        } );
        
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
//    private Label lbEntity                          = new Label( "Entidade", true );
//    private EntitySelector entityCombo                   = new EntitySelector();
//    
    
}
