package com.mn.engcivil.interfaces.editors.tabs;

import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Contact;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.editors.ContactEditor;
import com.mn.engcivil.interfaces.tables.ContactTable;
import com.mn.engcivil.interfaces.util.Prompts;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public class ClientEditorContactPane
    extends 
        SubEditorPanel<Client>
{
    private Client client;
    
    public ClientEditorContactPane()
    {
        initComponents();
    }
    
    @Override
    public void getInput( Client source )
    {
        List<Contact> items = contactTable.getElements();
        
        int position = 0;
        
        for ( Contact c : items )
        {
            c.setPosition( position++ );
        }
        
        source.setContacs( items );
    }

    @Override
    public void setInput( Client source )
    {
        this.client = source;
        
        List<Contact> items = new ArrayList<>( source.getContacs() );
        
        Collections.sort( items );
        
        contactTable.setElements( items );
    }
    
    private Contact getSelectedContact()
    {
        Contact contact = contactTable.getSelectedElement();
        
        if ( contact == null )
        {
            Messagebox.show( "Selecione um contato!" );
        }
        
        return contact;
    }
    
    private void initComponents()
    {
        btAdd.setLabel( "Adicionar" );
        btEdit.setLabel( "Editar" );
        btDelete.setLabel( "Excluir" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" );
        vlayout.appendChild( btAdd );
        vlayout.appendChild( btEdit );
        vlayout.appendChild( btDelete );
        
        Hlayout hlayout = new Hlayout();
        hlayout.setVflex( "true" );
        hlayout.setHflex( "true" );
        hlayout.appendChild( contactTable );
        hlayout.appendChild( vlayout );
        
        appendChild( hlayout );
        
        btAdd.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                ContactEditor.edit( ClientEditorContactPane.this, new Callback<Contact>( new Contact() )
                {
                    @Override
                    public void acceptInput()
                    {
                        getSource().setRefClient( client );

                        contactTable.addElement( getSource() );
                    }
                } );
            }
        } );
        
        btEdit.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Contact contact = getSelectedContact();
                
                if ( contact != null )
                {
                    ContactEditor.edit( ClientEditorContactPane.this, new Callback<Contact>( contact )
                    {
                        @Override
                        public void acceptInput()
                        {
                            contactTable.updateElement( getSource() );
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
                final Contact contact = getSelectedContact();
                
                if ( contact != null )
                {
                    Prompts.confirm( "Realmente excluir \"" + contact.getName() + "\"?", new Callback()
                    {
                        @Override
                        public void acceptInput()
                        {
                            contactTable.removeElement( contact );
                        }
                    } );
                }
            }
        } );
    }
    
    private Toolbarbutton btAdd = new Toolbarbutton();
    private Toolbarbutton btEdit = new Toolbarbutton();
    private Toolbarbutton btDelete = new Toolbarbutton();
    private ContactTable contactTable = new ContactTable();
}
