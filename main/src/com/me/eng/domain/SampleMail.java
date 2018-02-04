package com.me.eng.domain;

import com.me.eng.infrastructure.Mail.Status;
import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "eng_sample_mails")
public class SampleMail
    extends 
        Observable
    implements 
        Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "ref_sample", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sample sample;
    
    @JoinColumn(name = "ref_contact", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Contact contact;
    
    @Basic(optional = false)
    @Column(name = "status")
    private Integer status = Status.IDLE.ordinal();
    
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    
    @Basic(optional = false)
    @Column(name = "reason")
    private String reason = "";
    
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public SampleMail()
    {
    }
    
    public boolean isCompleted()
    {
        Status s = Status.values()[ status ];
        
        return s == Status.FAIL || s == Status.SUCCESS;
    }
    
    public boolean isFail()
    {
        return Status.values()[ status ] == Status.FAIL;
    }
    
    public boolean isSending()
    {
        return Status.values()[ status ] == Status.SENDING;
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact( Contact contact )
    {
        this.contact = contact;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason( String reason )
    {
        this.reason = reason;
    }
    
    public Sample getSample()
    {
        return sample;
    }

    public void setSample( Sample sample )
    {
        this.sample = sample;
    }

    public void setStatus( Integer status )
    {
        int oldStatus = this.status;
        
        if ( oldStatus != status )
        {
            this.status = status;
            
            setChanged();
            notifyObservers();
        }
    }
    
    public Status getStatus()
    {
        return Status.values()[ status ];
    }
}
