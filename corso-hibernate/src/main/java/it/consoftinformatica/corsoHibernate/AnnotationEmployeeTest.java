package it.consoftinformatica.corsoHibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import it.consoftinformatica.corsoHibernate.domain.Employee;

public class AnnotationEmployeeTest {
    private SessionFactory factory = null;
    
	protected static SessionFactory createSessionFactory() {
		/*
		 * This builder helps you create the immutable service registry with chained
		 * method calls.
		 */
		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

		/*
		 * Configure the services registry by applying settings.
		 */
		serviceRegistryBuilder
				// .applySetting("hibernate.connection.datasource", "myDS")
				.applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
				.applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/corso_hibernate")
				.applySetting("hibernate.connection.username", "federico")
				.applySetting("hibernate.connection.password", "password")
				.applySetting("hibernate.show_sql", "true")
				.applySetting("hibernate.use_sql_comments", "true")
				.applySetting("hibernate.format_sql", "true")
				.applySetting("hibernate.hbm2ddl.auto", "create-drop");

		// Enable JTA (this is a bit crude because Hibernate devs still believe that JTA
		// is
		// used only in monstrous application servers and you'll never see this code).
		/*
		 * serviceRegistryBuilder.applySetting(
		 * Environment.TRANSACTION_COORDINATOR_STRATEGY,
		 * JtaTransactionCoordinatorBuilderImpl.class );
		 */

		ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

		/*
		 * You can only enter this configuration stage with an existing service
		 * registry.
		 */
		MetadataSources metadataSources = new MetadataSources(serviceRegistry);

		/*
		 * Add your persistent classes to the (mapping) metadata sources.
		 */
		metadataSources.addAnnotatedClass(it.consoftinformatica.corsoHibernate.domain.Employee.class);		
		// Add hbm.xml mapping files
		// metadataSources.addFile(...);
		// Read all hbm.xml mapping files from a JAR
		// metadataSources.addJar(...)
		MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();
		Metadata metadata = metadataBuilder.build();
		SessionFactory sessionFactory = metadata.buildSessionFactory();

		return sessionFactory;
	}

    
    public static void main(String[] args) {
		SessionFactory sessionFactory = createSessionFactory();
        
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
        Employee emp1 = new Employee("Daniele Dormiente");
        session.save(emp1);
        Employee emp2 = new Employee("Bill Gates");
        session.save(emp2);
        // dirty checking
        emp2.setName("Larry Ellison");
		tx.commit();
		session.close();
		
		Session newSession = sessionFactory.openSession();
		Transaction newTransaction = newSession.beginTransaction();
		List<Employee> impiegati = newSession.createQuery("from TBL_EMPLOYEE m order by m.name asc").list();
		System.out.println(impiegati.size() + " employee(s) found:");
		for (Employee imp: impiegati) {
		System.out.println("Name:" + imp.getName());}
		newTransaction.commit();
		newSession.close();
		
    }
}
