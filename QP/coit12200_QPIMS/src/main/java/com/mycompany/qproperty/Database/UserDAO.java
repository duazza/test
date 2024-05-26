package com.mycompany.qproperty.Database;

import com.mycompany.qproperty.Model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for managing user records in the database.
 * 
 * Author: Duane Powell
 */
public class UserDAO {
    private Database database;

    /**
     * Constructor that initializes the database connection.
     */
    public UserDAO() {
        try {
            database = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hashes a password using SHA-256.
     * 
     * @param password The password to be hashed.
     * @return The hashed password.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Validates a user's credentials.
     * 
     * @param username The username to validate.
     * @param password The password to validate.
     * @return True if the credentials are valid, false otherwise.
     * @throws SQLException If an error occurs during the database operation.
     */
    public boolean validateUser(String username, String password) throws SQLException {
        String hashedPassword = hashPassword(password);
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = database.connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("SQL error during user validation: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Adds a new user to the database.
     * 
     * @param user The user to be added.
     * @throws SQLException If an error occurs during the database operation.
     */
    public void addUser(User user) throws SQLException {
        String hashedPassword = hashPassword(user.getPassword());
        String query = "INSERT INTO users (first_name, last_name, email, username, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = database.connection.prepareStatement(query)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, hashedPassword);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL error during user registration: " + e.getMessage());
            throw e;
        }
    }
}