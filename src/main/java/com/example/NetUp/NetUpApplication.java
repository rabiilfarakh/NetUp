package com.example.NetUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class NetUpApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(NetUpApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		testDatabaseConnection();
	}

	public void testDatabaseConnection() {
		try (Connection connection = dataSource.getConnection()) {
			if (connection != null) {
				System.out.println("Connexion à la base de données réussie !");
			}
		} catch (SQLException e) {
			System.out.println("Erreur lors de la connexion à la base de données: " + e.getMessage());
		}
	}
}
