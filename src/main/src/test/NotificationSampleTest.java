package test;

import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.domain.repositories.SampleRepository;
import com.mn.engcivil.infrastructure.JPAManager;
import com.mn.engcivil.infrastructure.SampleDAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Matheus
 */
public class NotificationSampleTest
{
    public static void main( String[] args ) throws Exception
    {
        JPAManager jpa = new JPAManager();
        EntityManagerFactory factory = jpa.createFactory();
        EntityManager manager = factory.createEntityManager();
        
        SampleRepository repository = new SampleDAO( manager );

        for ( Sample sample : repository.getSamplesToNofitication() )
        {
            System.out.println( sample.getId() + " " + sample.daysToRupture() );
        }
        
        manager.close();
        factory.close();
    }
}
