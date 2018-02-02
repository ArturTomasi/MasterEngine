package com.mn.engcivil.interfaces.editors;

import com.mn.engcivil.domain.reports.SampleReport;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.parts.TableLayout;
import com.mn.engcivil.interfaces.selectors.ContactComboboxSelector;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Matheus
 */
public class ReportEditor
    extends 
        EditorPanel<SampleReport>
{
    public static void edit( Component owner, Callback<SampleReport> callback )
    {
        DefaultEditor.createEditor( owner, new ReportEditor(), callback );
    }
    
    private SampleReport source;
    
    private ReportEditor()
    {
        initComponents();
    }

    @Override
    public void setInput( SampleReport value )
    {
        this.source = value;
        
//        contactSelector.setClient( value.getSample().getClient() );
    }

    @Override
    public void getInput( SampleReport value )
    {
    }
    
    private void initComponents()
    {
        lbMaterial.setValue( "Material ensaiado:" );
        lbCapeamento.setValue( "Tipo de capeamento:" );
        contactSelector.setValue( "Contato:" );
        
        TableLayout tableLayout = new TableLayout();
        tableLayout.addRow( lbMaterial, tfMaterial );
        tableLayout.addRow( lbCapeamento, tfCapeamento );
        tableLayout.addRow( lbContact, contactSelector );
        
        tableLayout.setWidths( "70px" );
        
        appendChild( tableLayout );
    }
    
    private Label lbMaterial = new Label();
    private Label lbCapeamento = new Label();
    private Label lbContact = new Label();
    
    private Textbox tfMaterial = new Textbox();
    private Textbox tfCapeamento = new Textbox();
    
    private ContactComboboxSelector contactSelector = new ContactComboboxSelector();
}
