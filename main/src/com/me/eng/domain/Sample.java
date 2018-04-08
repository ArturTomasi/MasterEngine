/* 
 *  Filename:    Sample 
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
package com.me.eng.domain;

import com.google.common.collect.Iterables;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "eng_samples")
public class Sample 
    implements 
        Serializable,
        Cloneable
{
    private static final long serialVersionUID = 1L;
    
    public static final Integer DEFAULT_AMOUNT_PROOF = 3;
    public static final Long DEFAULT_DAYS_PROOF      = 7l;
    
    public static final Character[] RUPTURE_TYPES = 
    {
        'A', 'B', 'C', 'D', 'E', 'F', 'G'
    };
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "dt_created")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    
    @Basic(optional = false)
    @Column(name = "dt_executed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExecuted;
    
    @Column(name = "dt_rupture")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRupture;
    
    @Basic(optional = false)
    @Column(name = "dim_w")
    private int dimW;
    
    @Basic(optional = false)
    @Column(name = "dim_h")
    private int dimH;
    
    @Basic(optional = false)
    @Column(name = "dim_l")
    private int dimL;
    
    @Basic(optional = true)
    @Column(name = "resistence")
    private Double resistence;
    
    @Basic(optional = true)
    @Column(name = "notification_rupture")
    private Long notificationRupture;
    
    @Basic(optional = true)
    @Column(name = "notification_rupture_date")
    @Temporal(TemporalType.DATE)
    private Date notificationRuptureDate;
    
    @Basic(optional = true)
    @Column(name = "estimated_rupture")
    private Long estimatedRupture;
    
    @Basic(optional = true)
    @Column(name = "estimated_unit_rupture")
    private Integer estimatedUnitRupture = TimeUnit.DAY.ordinal();
    
    @Basic(optional = false)
    @Column(name = "rupture_type")
    private Character ruptureType;
    
    @Basic(optional = false)
    @Column(name = "nf", nullable = true)
    private Integer nf;
    
    @JoinColumn(name = "ref_user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    
    @JoinColumn(name = "ref_rule", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Rule rule;
    
    @Basic(optional = true)
    @Column(name = "trace", nullable = true)
    private String trace;
    
    @JoinColumn(name = "ref_client", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Client client;

    @JoinTable( name = "eng_sample_equipments",
                joinColumns = {@JoinColumn(name = "ref_sample")},
                inverseJoinColumns = {@JoinColumn(name = "ref_equipment")})
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Equipment> equipaments = new LinkedList<>();
    
    @JoinTable( name = "eng_sample_capstones",
                joinColumns = {@JoinColumn(name = "ref_sample")},
                inverseJoinColumns = {@JoinColumn(name = "ref_capstone")})
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Capstone> capstones = new LinkedList<>();
    
    @ManyToOne
    @JoinColumn(name = "ref_parent")
    private Sample parent;
    
    @OneToMany( mappedBy = "parent",
                targetEntity = Sample.class,
                cascade = CascadeType.MERGE )
    private List<Sample> proofs = new LinkedList<Sample>();
    
    @JoinColumn(name = "ref_job", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Job job;
    
    @OneToMany(mappedBy = "sample", 
               targetEntity = SampleMail.class, 
               cascade = CascadeType.REMOVE,
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<SampleMail> mails = new LinkedList<>();
    
    /**
     * Sample
     * 
     */
    public Sample(){}

    /**
     * generateMail
     * 
     * @return SampleMail
     */
    public SampleMail generateMail()
    {
        SampleMail sampleMail = new SampleMail();
        sampleMail.setSample( this );
        sampleMail.setDate( new Date() );
        sampleMail.setContact( getContact() );
        
        if ( getContact() != null )
        {
            sampleMail.setEmail( getContact().getEmail() );
        }
        
        return sampleMail;
    }
    
    /**
     * daysToRupture
     * 
     * @return long
     */
    public long daysToRupture()
    {
        if ( dateRupture != null )
        {
            return TimeUnit.DAY.between( new Date(), dateRupture );
        }
        
        return -1;
    }

    /**
     * getMails
     * 
     * @return List&lt;SampleMail&gt;
     */
    public List<SampleMail> getMails()
    {
        return mails;
    }
    
    /**
     * getTrace
     * 
     * @return String
     */
    public String getTrace()
    {
        return trace;
    }

    /**
     * setTrace
     * 
     * @param trace String
     */
    public void setTrace( String trace )
    {
        this.trace = trace;
    }
    
    /**
     * getNotificationRuptureDate
     * 
     * @return Date
     */
    public Date getNotificationRuptureDate()
    {
        return notificationRuptureDate;
    }

    /**
     * setNotificationRuptureDate
     * 
     * @param notificationRuptureDate Date
     */
    public void setNotificationRuptureDate( Date notificationRuptureDate )
    {
        this.notificationRuptureDate = notificationRuptureDate;
    }

    /**
     * getNotificationRupture
     * 
     * @return Long
     */
    public Long getNotificationRupture()
    {
        return notificationRupture;
    }

    /**
     * setNotificationRupture
     * 
     * @param notificationRupture Long
     */
    public void setNotificationRupture( Long notificationRupture )
    {
        this.notificationRupture = notificationRupture;
    }
    
    /**
     * setJob
     * 
     * @param job Job
     */
    public void setJob( Job job )
    {
        this.job = job;
        
        for ( Sample proof : proofs )
        {
            proof.setJob( job );
        }
    }

    /**
     * getJob
     * 
     * @return Job
     */
    public Job getJob()
    {
        return job;
    }
    
    /**
     * setNf
     * 
     * @param nf Integer
     */
    public void setNf( Integer nf )
    {
        this.nf = nf;
    }

    /**
     * getNf
     * 
     * @return Integer
     */
    public Integer getNf()
    {
        return nf;
    }

    /**
     * getParent
     * 
     * @return Sample
     */
    public Sample getParent()
    {
        return parent;
    }

    /**
     * setParent
     * 
     * @param parent Sample
     */
    public void setParent( Sample parent )
    {
        this.parent = parent;
    }
    
    /**
     * getId
     * 
     * @return Integer
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * setId
     * 
     * @param id Integer
     */
    public void setId( Integer id )
    {
        this.id = id;
    }

    /**
     * getDateCreated
     * 
     * @return Date
     */
    public Date getDateCreated()
    {
        return dateCreated;
    }

    /**
     * setDateCreated
     * 
     * @param dateCreated Date
     */
    public void setDateCreated( Date dateCreated )
    {
        this.dateCreated = dateCreated;
    }

    /**
     * getDateExecuted
     * 
     * @return Date
     */
    public Date getDateExecuted()
    {
        return dateExecuted;
    }

    /**
     * setDateExecuted
     * 
     * @param dateExecuted Date
     */
    public void setDateExecuted( Date dateExecuted )
    {
        this.dateExecuted = dateExecuted;
    }

    /**
     * getDateRupture
     * 
     * @return Date
     */
    public Date getDateRupture()
    {
        return dateRupture;
    }

    /**
     * setDateRupture
     * 
     * @param dateRupture Date
     */
    public void setDateRupture( Date dateRupture )
    {
        if ( estimatedUnitRupture == TimeUnit.DAY.ordinal() )
        {
            dateRupture = TimeUnit.fixDate( dateRupture );
        }
        
        this.dateRupture = dateRupture;
    }

    /**
     * getDimW
     * 
     * @return int
     */
    public int getDimW()
    {
        return dimW;
    }

    /**
     * setDimW
     * 
     * @param dimW int
     */
    public void setDimW( int dimW )
    {
        this.dimW = dimW;
    }

    /**
     * getDimH
     * 
     * @return int
     */
    public int getDimH()
    {
        return dimH;
    }

    /**
     * setDimH
     * 
     * @param dimH int
     */
    public void setDimH( int dimH )
    {
        this.dimH = dimH;
    }

    /**
     * getDimL
     * 
     * @return int
     */
    public int getDimL()
    {
        return dimL;
    }

    /**
     * setDimL
     * 
     * @param dimL int
     */
    public void setDimL( int dimL )
    {
        this.dimL = dimL;
    }

    /**
     * getResistence
     * 
     * @return Double
     */
    public Double getResistence()
    {
        return resistence == null ? 0 : resistence;
    }

    /**
     * setResistence
     * 
     * @param resistence Double
     */
    public void setResistence( Double resistence )
    {
        this.resistence = resistence;
    }

    /**
     * getRuptureType
     * 
     * @return Character
     */
    public Character getRuptureType()
    {
        return ruptureType;
    }

    /**
     * setRuptureType
     * 
     * @param ruptureType Character
     */
    public void setRuptureType( Character ruptureType )
    {
        this.ruptureType = ruptureType;
    }

    /**
     * getUser
     * 
     * @return User
     */
    public User getUser()
    {
        return user;
    }

    /**
     * setUser
     * 
     * @param user User
     */
    public void setUser( User user )
    {
        this.user = user;
    }

    /**
     * getRule
     * 
     * @return Rule
     */
    public Rule getRule()
    {
        return rule;
    }

    /**
     * setRule
     * 
     * @param rule Rule
     */
    public void setRule( Rule rule )
    {
        this.rule = rule;
    }

    /**
     * getClient
     * 
     * @return Client
     */
    public Client getClient()
    {
        return client;
    }

    /**
     * setClient
     * 
     * @param client Client
     */
    public void setClient( Client client )
    {
        this.client = client;
    }

    /**
     * getName
     * 
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * setName
     * 
     * @param name String
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * setEquipaments
     * 
     * @param equipaments List&lt;Equipment&gt;
     */
    public void setEquipaments( List<Equipment> equipaments )
    {
        this.equipaments = equipaments;
    }
    
    /**
     * getEquipaments
     * 
     * @return List&lt;Equipment&gt;
     */
    public List<Equipment> getEquipaments()
    {
        return equipaments;
    }

    /**
     * setCapstones
     * 
     * @param capstones List&lt;Capstone&gt;
     */
    public void setCapstones( List<Capstone> capstones )
    {
        this.capstones = capstones;
    }

    /**
     * getCapstones
     * 
     * @return List&lt;Capstone&gt;
     */
    public List<Capstone> getCapstones()
    {
        return capstones;
    }

    /**
     * getContact
     * 
     * @return Contact
     */
    public Contact getContact()
    {
        if ( getJob() != null && ! getJob().getContacs().isEmpty() )
        {
            return Iterables.getFirst( getJob().getContacs(), null );
        }
        
        else if ( getClient() != null && ! getClient().getContacs().isEmpty() )
        {
            return Iterables.getFirst( getClient().getContacs(), null );
        }
        
        return null;
    }

    /**
     * getEstimatedRupture
     * 
     * @return Long
     */
    public Long getEstimatedRupture()
    {
        return estimatedRupture;
    }

    /**
     * setEstimatedRupture
     * 
     * @param estimatedRupture Long
     */
    public void setEstimatedRupture( Long estimatedRupture )
    {
        this.estimatedRupture = estimatedRupture;
    }

    /**
     * getEstimatedUnitRupture
     * 
     * @return Integer
     */
    public Integer getEstimatedUnitRupture()
    {
        return estimatedUnitRupture;
    }

    /**
     * setEstimatedUnitRupture
     * 
     * @param estimatedUnitRupture Integer
     */
    public void setEstimatedUnitRupture( Integer estimatedUnitRupture )
    {
        this.estimatedUnitRupture = estimatedUnitRupture;
    }

    /**
     * getProofs
     * 
     * @return List&lt;Sample&gt;
     */
    public List<Sample> getProofs()
    {
        return proofs;
    }

    /**
     * setProofs
     * 
     * @param proofs List&lt;Sample&gt;
     */
    public void setProofs( List<Sample> proofs ) 
    {
        this.proofs = proofs;
    }
    
    /**
     * getAmountProofs
     * 
     * @return int
     */
    public int getAmountProofs()
    {
        if ( proofs == null || proofs.isEmpty() )
        {
            return DEFAULT_AMOUNT_PROOF;
        }
        
        return proofs.size();
    }
    
    /**
     * getAllProofs
     * 
     * @return List&lt;Sample&gt;
     */
    public List<Sample> getAllProofs()
    {
        List<Sample> list = new LinkedList<>();
        list.add( this );
        
        collectProofs( list, this );
        
        return list;
    }
    
    /**
     * collectProofs
     * 
     * @param samples List&lt;Sample&gt;
     * @param sample Sample
     */
    private void collectProofs( List<Sample> samples, Sample sample )
    {
        for ( Sample proof : sample.getProofs() )
        {
            samples.add( proof );
            
            collectProofs( samples, proof );
        }
    }
    
    /**
     * unbound
     * 
     * @return Sample
     */
    public Sample unbound()
    {
        Sample p = parent;
        
        if ( parent != null )
        {
            p.proofs.remove( this );
            
            parent = null;
        }
        
        return p;
    }
    
    /**
     * createProof
     * 
     * @return Sample
     * @throws Exception
     */
    public Sample createProof() throws Exception
    {
        Sample proof = this.clone();
        proof.id = null;
        proof.parent = this;
        proof.proofs = new LinkedList<>();

        proofs.add( proof );
        
        return proof;
    }
    
    /**
     * getSerial
     * 
     * @return Serial
     */
    public Serial getSerial()
    {
        return new Serial( this );
    }
    
    /**
     * isNew
     * 
     * @return boolean
     */
    public boolean isNew()
    {
        return this.getId() == null || this.getId() == 0;
    }

    /**
     * hashCode
     * 
     * @return int
     */
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += ( id != null ? id.hashCode() : 0 );
        return hash;
    }

    /**
     * equals
     * 
     * @param object Object
     * @return boolean
     */
    @Override
    public boolean equals( Object object )
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if ( !( object instanceof Sample ) )
        {
            return false;
        }
        Sample other = (Sample) object;
        if ( ( this.id == null && other.id != null ) || ( this.id != null && !this.id.equals( other.id ) ) )
        {
            return false;
        }
        return true;
    }
    
    /**
     * toString
     * 
     * @return String
     */
    @Override
    public String toString()
    {
        return name;
    }

    /**
     * clone
     * 
     * @return Sample
     * @throws CloneNotSupportedException
     */
    @Override
    public Sample clone() throws CloneNotSupportedException
    {
        return (Sample) super.clone(); 
    }
}
