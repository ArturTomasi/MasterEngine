/* 
 *  Filename:    ClientEditorGeneralPane 
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
package com.me.eng.samples.ui.editors.tabs;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.domain.Client;
import com.me.eng.core.domain.Cnpj;
import com.me.eng.core.ui.editors.Errors;
import com.me.eng.core.ui.editors.tabs.SubEditorPanel;
import com.me.eng.core.ui.parts.TableLayout;
import com.me.eng.samples.ui.selectors.CitySelector;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Matheus
 */
public class ClientEditorGeneralPane
    extends 
        SubEditorPanel<Client>
{
    private Client source;
    
    public ClientEditorGeneralPane()
    {
        initComponents();
    }
    
    @Override
    public void getInput( Client source )
    {
        source.setAddress( tfAddress.getValue() );
        source.setName( tfName.getValue() );
        source.setInfo( tfInfo.getValue() );
        source.setCity( citySelector.getSelectedItem() );
        source.setCnpj( new Cnpj( tfCNPJ.getValue() ) );
    }

    @Override
    public void setInput( Client source )
    {
        this.source = source;
        
        tfName.setValue( source.getName() );
        tfAddress.setValue( source.getAddress() );
        tfInfo.setValue( source.getInfo() );
        tfCNPJ.setValue( source.getCnpj().getNumber() );
        citySelector.setSelectedItem( source.getCity() );
    }

    @Override
    public void validateInput( Errors e )
    {
        if ( tfName.getValue().trim().isEmpty() )
        {
            e.addError( "Nome" );
        }
        
        if ( citySelector.getSelectedItem() == null )
        {
            e.addError( "Cidade" );
        }
        
        if ( tfAddress.getValue() == null || tfAddress.getValue().trim().isEmpty() )
        {
            e.addError( "Endereço" );
        }
        
        Cnpj cnpj = new Cnpj( tfCNPJ.getValue() );
        
        if ( ! cnpj.isValid() )
        {
            e.addError( "CNPJ inválido!" );
        }
        
        try
        {
            Client client = ApplicationServices.getCurrent()
                    .getClientRepository()
                    .getClientByCNPJ( cnpj );
            
            if ( client != null && ! client.equals( source ) )
            {
                e.addError( "CNPJ já registrado para: " + client );
            }
        }
        
        catch ( Exception ex )
        {
            handleException( ex );
        }
    }
    
    private void initComponents()
    {
        lbName.setValue( "Nome:" );
        lbCity.setValue( "Cidade:" );
        lbAddress.setValue( "Endereço:" );
        lbInfo.setValue( "Informações:" );
        lbCNPJ.setValue( "CNPJ:" );
        
        tfName.setHflex( "true" );
        tfAddress.setHflex( "true" );
        tfAddress.setHflex( "true" );
        tfCNPJ.setHflex( "true" );
        
        tfCNPJ.setClientAttribute( "data-mask", Cnpj.MASK );
        
        tfInfo.setMultiline( true );
        tfInfo.setRows( 15 );
        tfInfo.setStyle( "resize:none" );
        tfInfo.setWidth( "530px" );
        tfInfo.setVflex( "true" );
        
        tableLayout.addRow( lbName, tfName );
        tableLayout.addRow( lbCity, citySelector );
        tableLayout.addRow( lbAddress, tfAddress );
        tableLayout.addRow( lbCNPJ, tfCNPJ );
        tableLayout.addRow( tfInfo );
        
        tableLayout.setWidths( "70px" );
        
        tableLayout.setColspan( 4, 0, 2 );
        
        appendChild( tableLayout );
    }
    
    private Textbox tfName = new Textbox();
    private Textbox tfCity = new Textbox();
    private Textbox tfCNPJ = new Textbox();
    private Textbox tfAddress = new Textbox();
    private Textbox tfInfo = new Textbox();
    
    private Label lbName = new Label();
    private Label lbCity = new Label();
    private Label lbCNPJ = new Label();
    private Label lbAddress = new Label();
    private Label lbInfo = new Label();
    
    private CitySelector citySelector = new CitySelector();
    
    private TableLayout tableLayout = new TableLayout();
}
