package com.me.eng.application;

import com.me.eng.domain.repositories.CapstoneRepository;
import com.me.eng.domain.repositories.CityRepository;
import com.me.eng.domain.repositories.ClientRepository;
import com.me.eng.domain.repositories.EquipmentRepository;
import com.me.eng.domain.repositories.JobRepository;
import com.me.eng.domain.repositories.RoleRepository;
import com.me.eng.domain.repositories.RuleRepository;
import com.me.eng.domain.repositories.SampleRepository;
import com.me.eng.domain.repositories.UserRepository;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public class ApplicationService
    implements 
        Serializable
{
    @Inject
    private SampleRepository sampleRepository;
    
    @Inject
    private ClientRepository clientRepository;
    
    @Inject
    private RuleRepository ruleRepository;
    
    @Inject
    private EquipmentRepository equipmentRepository;
    
    @Inject
    private CapstoneRepository capstoneRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private RoleRepository roleRepository;
    
    @Inject
    private JobRepository jobRepository;
    
    @Inject
    private CityRepository cityRepository;

    public CityRepository getCityRepository()
    {
        return cityRepository;
    }

    public JobRepository getJobRepository()
    {
        return jobRepository;
    }
    
    public CapstoneRepository getCapstoneRepository()
    {
        return capstoneRepository;
    }

    public ClientRepository getClientRepository()
    {
        return clientRepository;
    }

    public EquipmentRepository getEquipmentRepository()
    {
        return equipmentRepository;
    }

    public RuleRepository getRuleRepository()
    {
        return ruleRepository;
    }
    
    public SampleRepository getSampleRepository()
    {
        return sampleRepository;
    }

    public UserRepository getUserRepository()
    {
        return userRepository;
    }

    public RoleRepository getRoleRepository()
    {
        return roleRepository;
    }
}
