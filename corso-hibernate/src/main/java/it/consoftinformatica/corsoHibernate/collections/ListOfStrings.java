package it.consoftinformatica.corsoHibernate.collections;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import it.consoftinformatica.corsoHibernate.domain.Item;

public class ListOfStrings {

	private SessionFactory sessionFactory = null;

	public ListOfStrings() {
		init();
	}

	private void init() {

		try {
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure("hibernate.cfg.xml").build();
			Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
			sessionFactory = metaData.getSessionFactoryBuilder().build();
		} catch (Throwable th) {
			System.err.println("Initial SessionFactory creation failed" + th);
			throw new ExceptionInInitializerError(th);
		}

	}

	public void storeLoadCollection() throws Exception {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.beginTransaction();
		Item someItem = new Item();

		someItem.getImages().add("foo.jpg");
		someItem.getImages().add("bar.jpg");
		someItem.getImages().add("baz.jpg");
		someItem.getImages().add("baz.jpg");

		session.persist(someItem);
		session.getTransaction().commit();
		session.close();
		Long ITEM_ID = someItem.getId();
	}

	public static void main(String[] args) {
		ListOfStrings movieManager = new ListOfStrings();
		try {
			movieManager.storeLoadCollection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
