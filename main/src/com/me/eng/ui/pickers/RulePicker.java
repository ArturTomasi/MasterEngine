package com.me.eng.ui.pickers;

import com.me.eng.application.ApplicationContext;
import com.me.eng.application.ApplicationServices;
import com.me.eng.domain.Rule;
import com.me.eng.domain.repositories.RuleRepository;
import com.me.eng.ui.Callback;
import com.me.eng.ui.apps.Action;
import com.me.eng.ui.editors.RuleEditor;
import com.me.eng.ui.tables.RuleTable;
import com.me.eng.ui.util.Prompts;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Messagebox;
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
    public static void pick( Component owner, Callback<Rule> callback )
    {
        DefaultPicker.createPicker( owner, new RulePicker(), callback );
    }
    
    private RulePicker()
    {
        initComponents();
        
        loadRules();
    }
    
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

    @Override
    public void setSelectedItem( Rule source )
    {
        ruleTable.setSelectedElement( source );
    }

    @Override
    public Rule getSelectedItem()
    {
        return ruleTable.getSelectedElement();
    }
    
    private Rule getSelectedRule()
    {
        Rule rule = getSelectedItem();
        
        if ( rule == null )
        {
            Messagebox.show( "Selecione uma Norma!" );
        }
        
        return rule;
    }
    
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
                                    Messagebox.show( "Esta norma está em uso por " + v + " amostra(s).\n" + 
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
