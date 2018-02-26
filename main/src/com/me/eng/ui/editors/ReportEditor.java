/* 
 *  Filename:    ReportEditor 
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
package com.me.eng.ui.editors;

import com.me.eng.domain.reports.SampleReport;
import com.me.eng.ui.Callback;
import com.me.eng.ui.parts.TableLayout;
import com.me.eng.ui.selectors.ContactComboboxSelector;
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
