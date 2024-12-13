package org.example;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class Database {
    private final String DB_URL;
    private final String USER;
    private final String PASSWORD;

    public Database() {
        Dotenv dotenv = Dotenv.load();

        USER = dotenv.get("MSSQL_USER");
        PASSWORD = dotenv.get("MSSQL_SA_PASSWORD");
        String database = dotenv.get("MSSQL_DATABASE");

        DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=" + database;
    }

    // Create
    public void createUser(String name, String email) {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);

            int rows = preparedStatement.executeUpdate();
            System.out.println(rows + " user(s) added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read
    public void readUsers() {
        String query = "SELECT * FROM users";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Users in database:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Name: " + resultSet.getString("name") +
                        ", Email: " + resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update
    public void updateUser(int id, String name, String email) {
        String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, id);

            int rows = preparedStatement.executeUpdate();
            System.out.println(rows + " user(s) updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public void deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();
            System.out.println(rows + " user(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
