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
    @Temporal(TemporalType.DATE)
    private Date dateExecuted;
    
    @Column(name = "dt_rupture")
    @Temporal(TemporalType.DATE)
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
    
    public Sample()
    {
    }

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
    
    public long daysToRupture()
    {
        if ( dateRupture != null )
        {
            return TimeUnit.DAY.between( new Date(), dateRupture );
        }
        
        return -1;
    }

    public List<SampleMail> getMails()
    {
        return mails;
    }
    
    public String getTrace()
    {
        return trace;
    }

    public void setTrace( String trace )
    {
        this.trace = trace;
    }
    
    public Date getNotificationRuptureDate()
    {
        return notificationRuptureDate;
    }

    public void setNotificationRuptureDate( Date notificationRuptureDate )
    {
        this.notificationRuptureDate = notificationRuptureDate;
    }

    public Long getNotificationRupture()
    {
        return notificationRupture;
    }

    public void setNotificationRupture( Long notificationRupture )
    {
        this.notificationRupture = notificationRupture;
    }
    
    public void setJob( Job job )
    {
        this.job = job;
        
        for ( Sample proof : proofs )
        {
            proof.setJob( job );
        }
    }

    public Job getJob()
    {
        return job;
    }
    
    public void setNf( Integer nf )
    {
        this.nf = nf;
    }

    public Integer getNf()
    {
        return nf;
    }

    public Sample getParent()
    {
        return parent;
    }

    public void setParent( Sample parent )
    {
        this.parent = parent;
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated( Date dateCreated )
    {
        this.dateCreated = dateCreated;
    }

    public Date getDateExecuted()
    {
        return dateExecuted;
    }

    public void setDateExecuted( Date dateExecuted )
    {
        this.dateExecuted = dateExecuted;
    }

    public Date getDateRupture()
    {
        return dateRupture;
    }

    public void setDateRupture( Date dateRupture )
    {
        this.dateRupture = dateRupture;
    }

    public int getDimW()
    {
        return dimW;
    }

    public void setDimW( int dimW )
    {
        this.dimW = dimW;
    }

    public int getDimH()
    {
        return dimH;
    }

    public void setDimH( int dimH )
    {
        this.dimH = dimH;
    }

    public int getDimL()
    {
        return dimL;
    }

    public void setDimL( int dimL )
    {
        this.dimL = dimL;
    }

    public Double getResistence()
    {
        return resistence == null ? 0 : resistence;
    }

    public void setResistence( Double resistence )
    {
        this.resistence = resistence;
    }

    public Character getRuptureType()
    {
        return ruptureType;
    }

    public void setRuptureType( Character ruptureType )
    {
        this.ruptureType = ruptureType;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public Rule getRule()
    {
        return rule;
    }

    public void setRule( Rule rule )
    {
        this.rule = rule;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient( Client client )
    {
        this.client = client;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setEquipaments( List<Equipment> equipaments )
    {
        this.equipaments = equipaments;
    }
    
    public List<Equipment> getEquipaments()
    {
        return equipaments;
    }

    public void setCapstones( List<Capstone> capstones )
    {
        this.capstones = capstones;
    }

    public List<Capstone> getCapstones()
    {
        return capstones;
    }

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

    public Long getEstimatedRupture()
    {
        return estimatedRupture;
    }

    public void setEstimatedRupture( Long estimatedRupture )
    {
        this.estimatedRupture = estimatedRupture;
    }

    public Integer getEstimatedUnitRupture()
    {
        return estimatedUnitRupture;
    }

    public void setEstimatedUnitRupture( Integer estimatedUnitRupture )
    {
        this.estimatedUnitRupture = estimatedUnitRupture;
    }

    public List<Sample> getProofs()
    {
        return proofs;
    }

    public void setProofs(List<Sample> proofs) {
        this.proofs = proofs;
    }
    
    public int getAmountProofs()
    {
        if ( proofs == null || proofs.isEmpty() )
        {
            return DEFAULT_AMOUNT_PROOF;
        }
        
        return proofs.size();
    }
    
    public List<Sample> getAllProofs()
    {
        List<Sample> list = new LinkedList<>();
        list.add( this );
        
        collectProofs( list, this );
        
        return list;
    }
    
    private void collectProofs( List<Sample> samples, Sample sample )
    {
        for ( Sample proof : sample.getProofs() )
        {
            samples.add( proof );
            
            collectProofs( samples, proof );
        }
    }
    
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
    
    public Sample createProof() throws Exception
    {
        Sample proof = this.clone();
        proof.id = null;
        proof.parent = this;
        proof.proofs = new LinkedList<>();

        proofs.add( proof );
        
        return proof;
    }
    
    public Serial getSerial()
    {
        return new Serial( this );
    }
    
    public boolean isNew()
    {
        return this.getId() == null || this.getId() == 0;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += ( id != null ? id.hashCode() : 0 );
        return hash;
    }

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
    
    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public Sample clone() throws CloneNotSupportedException
    {
        return (Sample) super.clone(); 
    }
}
