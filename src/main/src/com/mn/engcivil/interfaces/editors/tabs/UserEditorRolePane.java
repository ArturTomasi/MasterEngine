package com.mn.engcivil.interfaces.editors.tabs;

import com.mn.engcivil.application.ApplicationServices;
import com.mn.engcivil.domain.Role;
import com.mn.engcivil.domain.User;
import com.mn.engcivil.interfaces.editors.Errors;
import com.mn.engcivil.interfaces.tables.RoleTable;

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
