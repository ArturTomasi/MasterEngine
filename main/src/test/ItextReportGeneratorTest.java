package test;

import com.me.eng.domain.NewtownFormula;
import com.me.eng.domain.RCFormula;
import com.me.eng.domain.Sample;
import com.me.eng.domain.reports.ItextReportGenerator;
import com.me.eng.domain.reports.SampleReport;
import com.me.eng.domain.repositories.SampleRepository;
import com.me.eng.infrastructure.JPAManager;
import com.me.eng.infrastructure.SampleDAO;
import java.awt.Desktop;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Matheus
 */
public class ItextReportGeneratorTest
{
    public static void main( String[] args )
    {
        try
        {
            JPAManager jpa = new JPAManager();
            EntityManagerFactory factory = jpa.createFactory();
            EntityManager manager = factory.createEntityManager();
            
            SampleRepository repository = new SampleDAO();
            
            Sample sample = repository.findAll().get( 0 );
            
            SampleReport report = new SampleReport( sample );
            
            File out = new File( "C:\\Users\\Matheus\\Desktop\\out.pdf" );
            
            ItextReportGenerator reportGenerator = new ItextReportGenerator();
            reportGenerator.setNewton( new NewtownFormula() );
            reportGenerator.setRc( new RCFormula() );
            reportGenerator.generateReport( report, out );
            
            manager.close();
            factory.close();
            
            Desktop.getDesktop().open( out );
        }
        
        catch ( Exception ex )
        {
            Logger.getLogger( ItextReportGeneratorTest.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }
}
