package test;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 *
 * @author Matheus
 */
public class WeldJUnitRunner
    extends 
        BlockJUnit4ClassRunner
{
    private Weld weld;
    private WeldContainer weldContainer;
    private Class klass;
    
    public WeldJUnitRunner( Class<?> klass ) throws InitializationError
    {
        super( klass );
        
        this.klass = klass;
        this.weld = new Weld();
        this.weldContainer = weld.initialize();
    }
}
