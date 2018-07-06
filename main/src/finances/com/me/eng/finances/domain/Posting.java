/*
 *  Filename:    Posting
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.domain;

import com.me.eng.core.domain.User;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Artur Tomasi
 */
@Entity
@Table( name = "fin_postings" )
public class Posting 
    implements 
        Serializable, Cloneable
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Lob
    @Basic(optional = true)
    @Column(name = "info")
    private String info;
    
    @Basic(optional = true)
    @Column(name = "real_date")
    @Temporal(TemporalType.DATE)
    private Date realDate;
    
    @Basic(optional = false)
    @Column(name = "estimate_date")
    @Temporal(TemporalType.DATE)
    private Date estimateDate;
    
    @Basic(optional = true)
    @Column(name = "real_value")
    private Double realValue;
    
    @Basic(optional = false)
    @Column(name = "estimate_value")
    private Double estimateValue;
    
    @Basic(optional = false)
    @Column(name = "fl_completion_auto")
    private Boolean completionAuto = false;
    
    @Basic(optional = false)
    @Column(name = "fl_repeat")
    private Boolean repeat = false;
    
    @Basic(optional = false)
    @Column(name = "portion")
    private Integer portion = 1;
    
    @Basic(optional = false)
    @Column(name = "portion_total")
    private Integer portionTotal = 1;
    
    @Enumerated( EnumType.ORDINAL )
    @Basic(optional = false)
    @Column( name = "state" )
    private PostingState state = PostingState.REGISTRED;
    
    @Enumerated( EnumType.ORDINAL )
    @Basic(optional = false)
    @Column(name = "completion_type")
    private CompletionType completionType = CompletionType.CASH;
    
    @JoinColumn(name = "ref_category", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PostingCategory category;
    
    @JoinColumn(name = "ref_company", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Company company;
    
    @JoinColumn(name = "ref_user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User owner;
    
    @ManyToOne
    @JoinColumn(name = "ref_posting")
    private Posting parent;

    @Transient
    private List<Posting> childs = new LinkedList();
    /**
     * Posting
     * 
     */
    public Posting() {}

    
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
     * getInfo
     * 
     * @return String
     */
    public String getInfo() 
    {
        return info;
    }

    /**
     * setInfo
     * 
     * @param info String
     */
    public void setInfo( String info ) 
    {
        this.info = info;
    }

    /**
     * getRealDate
     * 
     * @return Date
     */
    public Date getRealDate()
    {
        return realDate;
    }

    /**
     * setRealDate
     * 
     * @param realDate Date
     */
    public void setRealDate( Date realDate ) 
    {
        this.realDate = realDate;
    }

    /**
     * getEstimateDate
     * 
     * @return Date
     */
    public Date getEstimateDate() 
    {
        return estimateDate;
    }

    /**
     * setEstimateDate
     * 
     * @param estimateDate Date
     */
    public void setEstimateDate( Date estimateDate ) 
    {
        this.estimateDate = estimateDate;
    }

    /**
     * getRealValue
     * 
     * @return Double
     */
    public Double getRealValue() 
    {
        return realValue;
    }

    /**
     * setRealValue
     * 
     * @param realValue Double
     */
    public void setRealValue( Double realValue )
    {
        this.realValue = realValue;
    }

    /**
     * getEstimateValue
     * 
     * @return Double
     */
    public Double getEstimateValue() 
    {
        return estimateValue;
    }

    /**
     * setEstimateValue
     * 
     * @param estimateValue Double
     */
    public void setEstimateValue( Double estimateValue )
    {
        this.estimateValue = estimateValue;
    }

    /**
     * isCompletionAuto
     * 
     * @return Boolean
     */
    public Boolean isCompletionAuto() 
    {
        return completionAuto;
    }

    /**
     * setCompletionAuto
     * 
     * @param completionAuto Boolean
     */
    public void setCompletionAuto( Boolean completionAuto )
    {
        this.completionAuto = completionAuto;
    }

    /**
     * isRepeat
     * 
     * @return Boolean
     */
    public Boolean isRepeat() 
    {
        return repeat;
    }

    /**
     * setRepeat
     * 
     * @param repeat Boolean
     */
    public void setRepeat( Boolean repeat ) 
    {
        this.repeat = repeat;
    }

    /**
     * getPortion
     * 
     * @return Integer
     */
    public Integer getPortion() 
    {
        return portion;
    }

    /**
     * setPortion
     * 
     * @param portion Integer
     */
    public void setPortion( Integer portion )
    {
        this.portion = portion;
    }

    /**
     * getPortionTotal
     * 
     * @return Integer
     */
    public Integer getPortionTotal() 
    {
        return portionTotal;
    }

    /**
     * setPortionTotal
     * 
     * @param portionTotal Integer
     */
    public void setPortionTotal( Integer portionTotal ) 
    {
        this.portionTotal = portionTotal;
    }

    /**
     * getState
     * 
     * @return PostingState
     */
    public PostingState getState() 
    {
        return state;
    }

    /**
     * setState
     * 
     * @param state PostingState
     */
    public void setState( PostingState state ) 
    {
        this.state = state;
    }

    /**
     * getCompletionType
     * 
     * @return CompletionType
     */
    public CompletionType getCompletionType()
    {
        return completionType;
    }

    /**
     * setCompletionType
     * 
     * @param completionType CompletionType
     */
    public void setCompletionType( CompletionType completionType ) 
    {
        this.completionType = completionType;
    }

    /**
     * getCategory
     * 
     * @return PostingCategory
     */
    public PostingCategory getCategory() 
    {
        return category;
    }

    /**
     * setCategory
     * 
     * @param category PostingCategory
     */
    public void setCategory( PostingCategory category ) 
    {
        this.category = category;
    }

    /**
     * getCompany
     * 
     * @return Client
     */
    public Company getCompany() 
    {
        return company;
    }

    /**
     * setClient
     * 
     * @param company Company
     */
    public void setCompany( Company company )
    {
        this.company = company;
    }

    /**
     * getOwner
     * 
     * @return User
     */
    public User getOwner() 
    {
        return owner;
    }

    /**
     * setOwner
     * 
     * @param owner User
     */
    public void setOwner( User owner ) 
    {
        this.owner = owner;
    }

    /**
     * getParent
     * 
     * @return Posting
     */
    public Posting getParent() 
    {
        return parent;
    }

    /**
     * setParent
     * 
     * @param parent Posting
     */
    public void setParent( Posting parent )
    {
        this.parent = parent;
    }
    
    /**
     * addChild
     * 
     * @param value Posting
     */
    public void addChild( Posting value )
    {
        childs.add( value );
    }

    /**
     * getChilds
     * 
     * @return List&lt;Posting&gt;
     */
    public List<Posting> getChilds() 
    {
        return childs;
    }

    /**
     * toString
     * 
     * @return String
     */
    @Override
    public String toString() 
    {
        return getName();
    }

    /**
     * clone
     * 
     * @return Posting
     * @throws CloneNotSupportedException
     */
    @Override
    public Posting clone() throws CloneNotSupportedException 
    {
        Posting clone = (Posting)super.clone();
        clone.setId( -1 );
        clone.setRealDate( null );
        clone.setRealValue( null );
        clone.setState( PostingState.REGISTRED );
        
        return clone;
    }
    
    
}
