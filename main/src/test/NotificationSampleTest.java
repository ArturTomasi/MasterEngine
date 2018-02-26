/* 
 *  Filename:    NotificationSampleTest 
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
