/* 
 *  Filename:    ApplicationService 
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
package com.me.eng.core.services;

import com.me.eng.samples.repositories.CapstoneRepository;
import com.me.eng.core.repositories.CityRepository;
import com.me.eng.core.repositories.ClientRepository;
import com.me.eng.samples.repositories.EquipmentRepository;
import com.me.eng.samples.repositories.JobRepository;
import com.me.eng.core.repositories.LicenseRepository;
import com.me.eng.core.repositories.RoleRepository;
import com.me.eng.samples.repositories.RuleRepository;
import com.me.eng.samples.repositories.SampleRepository;
import com.me.eng.core.repositories.UserRepository;
import com.me.eng.finances.repositories.CompanyRepository;
import com.me.eng.finances.repositories.PostingCategoryRepository;
import com.me.eng.finances.repositories.PostingRepository;
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
    
    @Inject
    private LicenseRepository licenseRepository;
    
    @Inject
    private PostingCategoryRepository postingCategoryRepository;
    
    @Inject
    private PostingRepository postingRepository;
    
    @Inject
    private CompanyRepository companyRepository;

    /**
     * getCityRepository
     * 
     * @return CityRepository
     */
    public CityRepository getCityRepository()
    {
        return cityRepository;
    }

    /**
     * getJobRepository
     * 
     * @return JobRepository
     */
    public JobRepository getJobRepository()
    {
        return jobRepository;
    }
    
    /**
     * getCapstoneRepository
     * 
     * @return CapstoneRepository
     */
    public CapstoneRepository getCapstoneRepository()
    {
        return capstoneRepository;
    }

    /**
     * getClientRepository
     * 
     * @return ClientRepository
     */
    public ClientRepository getClientRepository()
    {
        return clientRepository;
    }

    /**
     * getEquipmentRepository
     * 
     * @return EquipmentRepository
     */
    public EquipmentRepository getEquipmentRepository()
    {
        return equipmentRepository;
    }

    /**
     * getRuleRepository
     * 
     * @return RuleRepository
     */
    public RuleRepository getRuleRepository()
    {
        return ruleRepository;
    }
    
    /**
     * getSampleRepository
     * 
     * @return SampleRepository
     */
    public SampleRepository getSampleRepository()
    {
        return sampleRepository;
    }

    /**
     * getUserRepository
     * 
     * @return UserRepository
     */
    public UserRepository getUserRepository()
    {
        return userRepository;
    }

    /**
     * getRoleRepository
     * 
     * @return RoleRepository
     */
    public RoleRepository getRoleRepository()
    {
        return roleRepository;
    }

    /**
     * getLicenseRepository
     * 
     * @return LicenseRepository
     */
    public LicenseRepository getLicenseRepository() 
    {
        return licenseRepository;
    }

    /**
     * getPostingCategoryRepository
     * 
     * @return PostingCategoryRepository
     */
    public PostingCategoryRepository getPostingCategoryRepository() 
    {
        return postingCategoryRepository;
    }

    /**
     * getCompanyRepository
     * 
     * @return CompanyRepository
     */
    public CompanyRepository getCompanyRepository() 
    {
        return companyRepository;
    }

    /**
     * getPostingRepository
     * 
     * @return PostingRepository
     */
    public PostingRepository getPostingRepository() 
    {
        return postingRepository;
    }
}
