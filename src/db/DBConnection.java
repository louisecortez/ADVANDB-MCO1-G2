package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.util.*;

/**
 * Create a "db.config" file in the project root folder.
 * 
 * !!! Sample format
 * File: "db.config"
 * +------------------------------------------------------+
 * | jdbc:mysql://localhost:3306/librarydb?user=root      |
 * | username                                             |
 * | password                                             |
 * |                                                      |
 * |                                                      |
 * |                                                      |
 * |                                                      |
 * |                                                      |
 * |                                                      |
 * +------------------------------------------------------+
 * 
 */

public class DBConnection {

	private String url;
	private String username;
	private String password;

	private Connection connection;
	private Statement statement;
	private ResultSet result;

	public DBConnection() throws IOException {
		configure();
	}

	public void configure() throws IOException {
		File file = new File("db.config");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		this.url = br.readLine();
		this.username = br.readLine();
		this.password = br.readLine();
		br.close();
		fr.close();
	}

	public boolean editRow(String query) {
		try {
			// Get a connection to database
			this.connection = DriverManager.getConnection(url, username, password);

			// Create a statement
			this.statement = connection.createStatement();

			System.out.println("(INSERT/UPDATE/DELETE) Database connected");
			System.out.println("Query: " + query);

			// Execute SQL query
			return statement.executeUpdate(query) != 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public void select(String query) {
		try {
			// Get a connection to database
			this.connection = DriverManager.getConnection(url, username, password);

			// Create a statement
			this.statement = connection.createStatement();

			System.out.println("(SELECT) Database connected");
			System.out.println("Query: " + query);

			// Execute SQL query
			this.result = statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (result != null)
				result.close();

			if (statement != null)
				statement.close();

			if (connection != null)
				connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getResult() {
		return result;
	}

}
