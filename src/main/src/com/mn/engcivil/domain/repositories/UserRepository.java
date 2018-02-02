package com.mn.engcivil.domain.repositories;

import com.mn.engcivil.domain.User;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public interface UserRepository
    extends 
        EntityRepository<User>
{
    public User findByLogin( String login ) throws Exception;
}
