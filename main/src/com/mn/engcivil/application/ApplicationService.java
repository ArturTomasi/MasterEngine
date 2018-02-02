package com.mn.engcivil.application;

import com.mn.engcivil.domain.repositories.CapstoneRepository;
import com.mn.engcivil.domain.repositories.CityRepository;
import com.mn.engcivil.domain.repositories.ClientRepository;
import com.mn.engcivil.domain.repositories.EquipmentRepository;
import com.mn.engcivil.domain.repositories.JobRepository;
import com.mn.engcivil.domain.repositories.RoleRepository;
import com.mn.engcivil.domain.repositories.RuleRepository;
import com.mn.engcivil.domain.repositories.SampleRepository;
import com.mn.engcivil.domain.repositories.UserRepository;
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
