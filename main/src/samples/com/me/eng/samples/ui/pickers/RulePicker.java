/* 
 *  Filename:    RulePicker 
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
package com.me.eng.samples.ui.pickers;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.samples.domain.Rule;
import com.me.eng.samples.repositories.RuleRepository;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.pickers.DefaultPicker;
import com.me.eng.core.ui.pickers.PickerPanel;
import com.me.eng.samples.ui.editors.RuleEditor;
import com.me.eng.samples.ui.tables.RuleTable;
import com.me.eng.core.ui.util.Prompts;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class RulePicker
    extends 
        PickerPanel<Rule>
{
    /**
     * pick
     * 
     * @param owner Component
     * @param callback Callback&lt;Rule&gt;
     */
    public static void pick( Component owner, Callback<Rule> callback )
    {
        DefaultPicker.createPicker( owner, new RulePicker(), callback );
    }
    
    /**
     * RulePicker
     * 
     */
    private RulePicker()
    {
        initComponents();
        
        loadRules();
    }
    
    /**
     * loadRules
     * 
     */
    private void loadRules()
    {
        try
        {
            ruleTable.setElements( ApplicationServices.getCurrent()
                                        .getRuleRepository()
                                        .findAll() );
        }
        
        catch ( Exception ex )
        {
            handleException( ex );
        }
    }

    /**
     * setSelectedItem
     * 
     * @param source Rule
     */
    @Override
    public void setSelectedItem( Rule source )
    {
        ruleTable.setSelectedElement( source );
    }

    /**
     * getSelectedItem
     * 
     * @return Rule
     */
    @Override
    public Rule getSelectedItem()
    {
        return ruleTable.getSelectedElement();
    }
    
    /**
     * getSelectedRule
     * 
     * @return Rule
     */
    private Rule getSelectedRule()
    {
        Rule rule = getSelectedItem();
        
        if ( rule == null )
        {
            Prompts.alert( "Selecione uma Norma!" );
        }
        
        return rule;
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        btAdd.setLabel( "Novo" );
        btEdit.setLabel( "Editar" );
        btDelete.setLabel( "Excluir" );
        
        ruleTable.setCheckmark( true );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" );
        
        vlayout.appendChild( btAdd );
        vlayout.appendChild( btEdit );
        vlayout.appendChild( btDelete );
        
        Hlayout hlayout = new Hlayout();
        hlayout.setHflex( "true" );
        hlayout.setVflex( "true" );
        
        hlayout.appendChild( ruleTable );
        hlayout.appendChild( vlayout );
        
        appendChild( hlayout );
        
        btAdd.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                RuleEditor.edit( RulePicker.this, new Callback<Rule>( new Rule() )
                {
                    @Override
                    public void acceptInput()
                    {
                        try
                        {
                            ApplicationServices.getCurrent()
                                    .getRuleRepository()
                                    .add( getSource() );
                            
                            ruleTable.addElement( getSource() );
                            ruleTable.setSelectedElement( getSource() );
                        }
                        
                        catch ( Exception e )
                        {
                            handleException( e );
                        }
                    }
                } );
            }
        } );
        
        btEdit.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Rule rule = getSelectedRule();
                
                if ( rule != null )
                {
                    RuleEditor.edit( RulePicker.this, new Callback<Rule>( rule )
                    {
                        @Override
                        public void acceptInput()
                        {
                            try
                            {
                                ApplicationServices.getCurrent()
                                        .getRuleRepository()
                                        .update( getSource() );

                                ruleTable.updateElement( getSource() );
                            }

                            catch ( Exception e )
                            {
                                handleException( e );
                            }
                        }
                    } );
                }
            }
        } );
        
        btDelete.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Rule rule = getSelectedRule();
                
                if ( rule != null )
                {
                    Prompts.confirm( "Realmente excluir \"" + rule + "\"?", new Callback<Rule>( rule )
                    {
                        @Override
                        public void acceptInput()
                        {
                            try
                            {
                                RuleRepository repository = ApplicationServices.getCurrent()
                                        .getRuleRepository();

                                int v = repository.countSamples( getSource() );
                                
                                if ( v > 0 )
                                {
                                    Prompts.alert( "Esta norma está em uso por " + v + " amostra(s).\n" + 
                                                     "Não é possível proceder com a exclusão." ); 
                                }
                                
                                else
                                {
                                    repository.delete( getSource() );

                                    ruleTable.removeElement( getSource() );
                                }
                            }

                            catch ( Exception e )
                            {
                                handleException( e );
                            }
                        }
                    } );
                }
            }
        } );
    }
    
    private RuleTable ruleTable = new RuleTable();
    private Toolbarbutton btAdd = new Toolbarbutton();
    private Toolbarbutton btEdit = new Toolbarbutton();
    private Toolbarbutton btDelete = new Toolbarbutton();
}
