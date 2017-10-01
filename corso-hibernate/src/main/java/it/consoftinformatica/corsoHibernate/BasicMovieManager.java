package it.consoftinformatica.corsoHibernate.lezione1;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import it.consoftinformatica.corsoHibernate.domain.Movie;

public class BasicMovieManager {

	private SessionFactory sessionFactory = null;

	public BasicMovieManager() {
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

	private void persistMovie() {
		Movie movie = new Movie();
		movie.setId(2);
		movie.setDirector("Steven Spielberg");
		movie.setTitle("Lo Squalo");
		movie.setSynopsis("Ti farà perdere il gusto del bagno al mare!");
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(movie);
		session.getTransaction().commit();
	}

	private void findMovie(int i) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Movie movie = (Movie) session.load(Movie.class, i);
		System.out.println("Titolo:" + movie.getTitle());
		session.getTransaction().commit();
	}

	private void findAll() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Movie> movies = session.createQuery("from Movie").list();
		session.getTransaction().commit();
		for (Movie movie: movies) {
		System.out.println("Titolo:" + movie.getTitle());}

	}

	public static void main(String[] args) {
		BasicMovieManager movieManager = new BasicMovieManager();
		movieManager.persistMovie();
		movieManager.findMovie(2);
		movieManager.findAll();
	}
}