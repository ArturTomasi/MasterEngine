/* 
 *  Filename:    UserEditorRolePane 
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
package com.me.eng.core.ui.editors.tabs;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.domain.Role;
import com.me.eng.core.domain.User;
import com.me.eng.core.ui.editors.Errors;
import com.me.eng.core.ui.tables.RoleTable;

/**
 *
 * @author Matheus
 */
@Deprecated
public class UserEditorRolePane
    extends 
        SubEditorPanel<User>
{
    private User user;
    
    public UserEditorRolePane()
    {
        roleTable.setMultiple( true );
        roleTable.setCheckmark( true );
        
        appendChild( roleTable );
        
        try
        {
            roleTable.setElements( ApplicationServices.getCurrent()
                                        .getRoleRepository()
                                        .findAll() );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    @Override
    public void getInput( User source )
    {
//        source.setRoles( roleTable.getSelectedElements() );
    }

    @Override
    public void setInput( User source )
    {
        this.user = source;
        
//        roleTable.setSelectedElements( new ArrayList<>( source.getRoles() ) );
    }

    @Override
    public void validateInput( Errors e )
    {
        try
        {
            Role role = ApplicationServices.getCurrent()
                                .getRoleRepository()
                                .getAdministratorRole();

            if ( user.isSystemUser() )
            {
                if ( ! roleTable.getSelectedElements().contains( role ) )
                {
                    e.addError( "Este é o usuário Administrador e esta regra não pode ser removida!" );
                }
            }
        }
        
        catch ( Exception ex )
        {
            handleException( ex );
        }
    }
    
    private RoleTable roleTable = new RoleTable();
}
