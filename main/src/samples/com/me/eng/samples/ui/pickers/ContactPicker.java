/* 
 *  Filename:    ContactPicker 
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

import com.me.eng.core.domain.Client;
import com.me.eng.core.domain.Contact;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.pickers.DefaultPicker;
import com.me.eng.core.ui.pickers.PickerPanel;
import com.me.eng.samples.ui.tables.ContactTable;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author Matheus
 */
public class ContactPicker
    extends 
        PickerPanel<Contact>
{
    public static void pick( Component owner, Client client, Callback<Contact> callback )
    {
        ContactPicker contactPicker = new ContactPicker();
        contactPicker.setClient( client );
        
        DefaultPicker.createPicker( owner, contactPicker, callback );
    }

    public ContactPicker()
    {
        initComponents();
    }
    
    private void setClient( Client client )
    {
        contactTable.setElements( client.getContacs() );
    }

    @Override
    public void setSelectedItem( Contact source )
    {
        contactTable.setSelectedElement( source );
    }
    
    @Override
    public Contact getSelectedItem()
    {
        return contactTable.getSelectedElement();
    }
    
    private void initComponents()
    {
        appendChild( contactTable );
    }
    
    private ContactTable contactTable = new ContactTable();
}
