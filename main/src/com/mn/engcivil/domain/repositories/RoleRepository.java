package com.mn.engcivil.domain.repositories;

import com.mn.engcivil.domain.Role;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public interface RoleRepository
    extends 
        EntityRepository<Role>
{
    public Role getAdministratorRole() throws Exception;
    public Role getRole( String rolename ) throws Exception;
}
