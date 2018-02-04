package com.me.eng.ui.editors.tabs;

import com.me.eng.application.ApplicationServices;
import com.me.eng.domain.Client;
import com.me.eng.domain.Cnpj;
import com.me.eng.ui.editors.Errors;
import com.me.eng.ui.parts.TableLayout;
import com.me.eng.ui.selectors.CitySelector;
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
