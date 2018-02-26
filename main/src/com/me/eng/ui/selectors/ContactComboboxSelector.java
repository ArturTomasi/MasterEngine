/* 
 *  Filename:    ContactComboboxSelector 
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
package com.me.eng.ui.selectors;

import com.me.eng.domain.Client;
import com.me.eng.domain.Contact;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class ContactComboboxSelector
    extends 
        AbstractComboboxSelector<Contact>
{
    private Client client;
    
    public void setClient( Client client )
    {
        this.client = client;
        
        refreshContent();
    }

    @Override
    public List<Contact> getElements()
    {
        if ( client != null )
        {
            return client.getContacs();
        }
        
        return Collections.emptyList();
    }
}
