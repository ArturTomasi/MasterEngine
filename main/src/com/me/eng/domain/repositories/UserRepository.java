package com.me.eng.domain.repositories;

import com.me.eng.domain.User;
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
