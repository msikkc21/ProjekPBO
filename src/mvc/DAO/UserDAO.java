/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.DAO;

import mvc.DAOInterface.IUser;
import mvc.Model.User;
import mvc.Koneksi.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.Utils.PasswordUtils;
/**
 *
 * @author izzaa
 */
public class UserDAO implements IUser{

    Connection con;
    
    public UserDAO(){
        con = Koneksi.getConnection();
    }
    
    public void insert(User user) {
        String query = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal register user: " + e.getMessage());
        }
    }

    public User login(String username, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password); // Hash dulu input password

        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Gagal login: " + e.getMessage());
        }
        return null;
    }
        
}
