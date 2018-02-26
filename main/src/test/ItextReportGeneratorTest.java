/* 
 *  Filename:    ItextReportGeneratorTest 
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
