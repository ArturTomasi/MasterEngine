package com.me.eng.domain.repositories;

import com.me.eng.domain.Role;
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
