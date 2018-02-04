package test;

import com.me.eng.domain.Sample;
import com.me.eng.domain.repositories.SampleRepository;
import com.me.eng.infrastructure.JPAManager;
import com.me.eng.infrastructure.SampleDAO;
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
