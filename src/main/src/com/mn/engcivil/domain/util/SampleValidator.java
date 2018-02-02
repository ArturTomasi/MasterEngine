package com.mn.engcivil.domain.util;

import com.mn.engcivil.application.ApplicationContext;
import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.interfaces.editors.Errors;
import java.util.List;

/**
 *
 * @author artur
 */
public class SampleValidator 
{
    private static SampleValidator instance;
    
    public static final SampleValidator getInstance()
    {
        if ( instance == null )
        {
            instance = new SampleValidator();
        }
        
        return instance;
    }
    
    private SampleValidator() {}
    
    public boolean isValidResistence( Sample sample, Double resistence )
    {
        Errors e = new Errors();
        
        isValidResistence( sample, resistence, e );
        
        return e.validate( ApplicationContext.getInstance().getRoot() );
    }
    
    public boolean isValidResistence( Sample sample, Double resistence, Errors errors )
    {
        if ( resistence == null || resistence == 0 )
        {
            return true;
        }
 
        if ( resistence < 0 )
        {
            errors.addError( "Resistência não pode ser negativa"  );
            
            return false;
        }
        
        Sample parent = sample.getParent();
        
        if ( parent != null )
        {
            for ( Sample proof : parent.getAllProofs() )
            {
                if ( proof.getResistence() > 0 )
                {
                    if ( sample.getEstimatedRupture() < proof.getEstimatedRupture() )
                    {
                        if ( resistence > proof.getResistence() )
                        {
                            errors.addError( "Resistência não pode ser maior que contra prova anterior"  );

                            return false;
                        }
                    }

                    else 
                    {
                        if ( resistence < proof.getResistence() )
                        {
                            errors.addError( "Resistência não pode ser maior que contra prova anterior"  );

                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    public String dontHaveResistences( List<Sample> items )
    {
        if ( ! items.isEmpty() )
        {
            String ids = "";
            
            for ( Sample s : items )
            {
                if ( s.getResistence() == null || s.getResistence() == 0 )
                {
                    ids += "\n" + s.getSerial().getMajorNumber();
                }
            }
            
            if ( ! ids.isEmpty() )
            {
                return "Amostras abaixo não possuem uma resistência válida!" + ids;
            }
        }
        
        return null;
    }
}
