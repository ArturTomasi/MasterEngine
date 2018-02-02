package com.mn.engcivil.interfaces.editors.tabs;

import com.mn.engcivil.domain.Cnpj;
import com.mn.engcivil.domain.Job;
import com.mn.engcivil.interfaces.editors.Errors;
import com.mn.engcivil.interfaces.parts.TableLayout;
import com.mn.engcivil.interfaces.selectors.CitySelector;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author arturtfName
 */
public class JobEditorGeneralPane 
    extends 
        SubEditorPanel<Job>
{

    public JobEditorGeneralPane() 
    {
        initComponents();
    }

    @Override
    public void getInput( Job source ) 
    {
        source.setCity( citySelector.getSelectedItem() );
        source.setAddress( tfAddress.getText() );
        source.setName( tfName.getText() );
        source.setCEI( tfCEI.getValue() );
    }

    @Override
    public void setInput( Job source )
    {
        tfAddress.setText( source.getAddress() );
        tfName.setText( source.getName() );
        tfCEI.setValue( source.getCEI() );
        citySelector.setSelectedItem( source.getCity() );
    }

    @Override
    public void validateInput( Errors e) 
    {
        if ( tfName.getValue().trim().isEmpty() )
        {
            e.addError( "Nome" );
        }
        
        if ( tfCEI.getValue().isEmpty() || tfCEI.getValue().length() != 12 )
        {
            e.addError( "CEI inválido!" );
        }
    }
    
    
    private void initComponents()
    {
        lbName.setValue( "Nome:" );
        lbCity.setValue( "Cidade:" );
        lbAddress.setValue( "Endereço:" );
        lbCnpj.setValue( "CEI:" );
        
        tfCEI.setClientAttribute( "type", "number" );
        tfCEI.setMaxlength( 12 );
                
        grid.addRow( lbName, tfName );
        grid.addRow( lbCity, citySelector );
        grid.addRow( lbAddress, tfAddress );
        grid.addRow( lbCnpj, tfCEI );
        
        grid.setWidths( "120px" );
        
        appendChild( grid );
    }
     
    private TableLayout grid = new TableLayout();
                
    private Label lbName = new Label();
    private Label lbCity = new Label();
    private Label lbAddress = new Label();
    private Label lbCnpj = new Label();
    
    private Textbox tfName = new Textbox();
    private Textbox tfAddress = new Textbox();
    private Textbox tfCEI = new Textbox();
    private CitySelector citySelector = new CitySelector();
}
