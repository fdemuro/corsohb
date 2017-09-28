package it.consoftinformatica.corsoHibernate.lezione1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.consoftinformatica.corsoHibernate.domain.Movie;

public class MovieManagerJDBC {

	private Connection connection = null;
	private String username = "federico";
	private String password = "password";
	private String url = "jdbc:mysql://localhost/corso_hibernate";
	private String driverClass = "com.mysql.jdbc.Driver";
	private String tableSql = "create table MOVIES (ID integer not null, TITLE varchar(255), DIRECTOR varchar(255), SYNOPSIS varchar(255), primary key (ID))";
	private String insertSql = "INSERT INTO MOVIES VALUES (?,?,?,?)";

	public MovieManagerJDBC() {
	}

	private void init() {
		// Create a connection
		createConnection();
	}

	private void createConnection() {
		try {
			Class.forName(driverClass).newInstance();
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception ex) {
			System.err.println("Exception while creating a connection:" + ex.getMessage());
		}
		System.out.println("Connection created successfully");
	}

	private Connection getConnection() {

		if (connection == null) {
			createConnection();
		}

		return connection;
	}

	private void createTable() {
		try {
			Statement stmt = getConnection().createStatement();
			stmt.executeUpdate(tableSql);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.err.println(ex.getMessage());
		}
	}

	private void persistMovie() {
		try {
			PreparedStatement pst = getConnection().prepareStatement(insertSql);

			pst.setInt(1, 1);
			pst.setString(2, "Dracula di Bram Stoker");
			pst.setString(3, "Francis Ford Coppola");
			pst.setString(4, "Per la falsa notizia della sconfitta e morte "
					+ "sul campo di Vlad III, la moglie Elisabetta si suicida... ");

			pst.execute();
			System.out.println("Movie persisted successfully!");

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private void queryMovies() {
		List<Movie> movies = new ArrayList<Movie>();
		Movie m = null;
		try {
			Statement st = getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM MOVIES");
			while (rs.next()) {
				m = new Movie();
				m.setId(rs.getInt("ID"));
				m.setTitle(rs.getString("TITLE"));
				movies.add(m);
				System.out.println("Movie Found: " + rs.getInt("ID") + ", Title:" + rs.getString("TITLE"));
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public static void main(String[] args) {
		MovieManagerJDBC movieManager = new MovieManagerJDBC();
		movieManager.init();
		movieManager.createTable();
		movieManager.persistMovie();
		movieManager.queryMovies();

	}
}
