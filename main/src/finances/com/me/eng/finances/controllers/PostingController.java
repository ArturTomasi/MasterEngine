/*
 *  Filename:    PostingController
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
package com.me.eng.finances.controllers;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.domain.PostingState;
import com.me.eng.finances.repositories.PostingRepository;

/**
 *
 * @author Artur Tomasi
 */
public class PostingController 
{
    private static PostingController instance;
    
    private PostingController(){}
    
    /**
     * getInstance
     * 
     * @return PostingController
     */
    public static PostingController getInstance()
    {
        if( instance == null )
        {
            instance = new PostingController();
        }
        
        return instance;
    }
    
    /**
     * addPosting
     * 
     * @param posting Posting
     */
    public void addPosting( Posting posting )
    {
        try
        {
            if ( posting.getId() == null || posting.getId() == 0 )
            {
                PostingRepository repository = ApplicationServices.getCurrent().getPostingRepository();
                
                repository.add( posting );
                
                if ( posting.isRepeat() )
                {
                    for ( Posting p : posting.getChilds() )
                    {
                        p.setParent( posting );
                
                        repository.add( p );
                    }
                }
            }
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().logException( e );
        }
    }
    
    /**
     * editPosting
     * 
     * @param posting Posting
     */
    public void editPosting( Posting posting )
    {
        try
        {
            PostingRepository repository = ApplicationServices.getCurrent().getPostingRepository();
                
            repository.update( posting );
            
            if( posting.isRepeat() && posting.getPortion() < posting.getPortionTotal() &&
                posting.getState() == PostingState.FINISHED )
            {
                repository.updateNextPortion( posting );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().logException( e );
        }
    }
    
    /**
     * finishPosting
     * 
     * @param posting Posting
     */
    public void finishPosting( Posting posting )
    {
        try
        {
            PostingRepository repository = ApplicationServices.getCurrent().getPostingRepository();

            repository.update( posting );
            
            if( posting.isRepeat() && posting.getPortion() < posting.getPortionTotal() )
            {
                repository.updateNextPortion( posting );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().logException( e );
        }
    }
    
    /**
     * deletePosting
     * 
     * @param posting Posting
     */
    public void deletePosting( Posting posting )
    {
        try
        {
            PostingRepository repository = ApplicationServices.getCurrent().getPostingRepository();
                
            repository.delete( posting );
            
            if( posting.isRepeat() && posting.getPortion() < posting.getPortionTotal() )
            {
               repository.deleteNextPortions( posting );
            }
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().logException( e );
        }
    }

    
    public void reversePosting( Posting posting )
    {
        try
        {
//            if( posting.getState() == Posting.STATE_FINISHED )
//            {
//                posting.setState( Posting.STATE_PROGRESS );
//                posting.setRealDate( null );
//                posting.setRealValue( 0d );
//                
//                if( posting.isCompletionAuto() )
//                        posting.setCompletionType( 0 );
//                    
//                com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().updateValue( posting );
//
//                if( posting.isRepeat() && posting.getPortion() < posting.getPortionTotal() )
//                {
//                    com.pa.helpfin.model.ModuleContext.getInstance().getPostingManager().updateNextPortion( posting );
//                }
//            }
        }
        
        catch( Exception e )
        {
            ApplicationContext.getInstance().logException( e );
        }
    }
    
    /**
     * makeState
     * 
     * @param posting Posting
     * @return PostingState
     */
    public PostingState makeState( Posting posting )
    {
        PostingState newState = PostingState.REGISTRED;
        
        try
        {
            if( posting.getPortion() > 1 )
            {
                Posting last = ApplicationServices.getCurrent().getPostingRepository().getLastPosting( posting );
                
                if( last != null && last.getState() == PostingState.FINISHED )
                {
                    newState = PostingState.PROGRESS;
                }
            }

            else
            {
                newState = posting.getRealDate() != null ? PostingState.FINISHED : PostingState.PROGRESS;
            }
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().logException( e );
        }
        
        return newState;
    }
    
    /**
     * validateFinish
     * 
     * @param posting Posting
     * @return String
     */
    public String validateFinish( Posting posting )
    {
        if( posting == null )
            return "Selecione um Lançamento para finalizar !";
        
        if( posting.getState() == PostingState.FINISHED )
            return "Lançamento está finalizado,\n não é possivel finaliza-lo duas vezes !";
        
        if( posting.getState() != PostingState.PROGRESS && posting.getPortionTotal() > 1 )
            return "Lançamento não está correte,\n não é possivel finaliza-lo antes das outras parcelas!";
        
        return null;
    }
    
    /**
     * validateEdit
     * 
     * @param posting Posting
     * @return String
     */
    public String validateEdit( Posting posting )
    {
        if( posting == null )
            return "Selecione um Lançamento para editar !";
        
        if( posting.getState() == PostingState.FINISHED )
            return "Lançamento está finalizado,\n não é possivel edita-lo após finalizado!";
        
        return null;
    }
    
    /**
     * validateDelete
     * 
     * @param posting Posting
     * @return String
     */
    public String validateDelete( Posting posting )
    {
        if ( posting == null )
            return "Selecione um Lançamento para excluir !";
        
        if ( posting.getState() == PostingState.FINISHED )
            return "Lançamento está finalizado,\n não é possivel excluir após finalizado!";
        
        return null;
    }
    
    /**
     * validateReserve
     * 
     * @param posting Posting
     * @return String
     */
    public String validateReserve( Posting posting )
    {
        if( posting == null )
            return "Selecione um Lançamento para extornar!";
        
        if( posting.getState() != PostingState.FINISHED )
            return "Lançamento deve estár finalizado para extornar";
        
        return null;
    }
}
