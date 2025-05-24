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
    
    public int insert(User user) {
        String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        int userId = -1; // Nilai default -1 jika terjadi kegagalan

        try (PreparedStatement statement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1); // Ambil ID yang dihasilkan
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tangani pengecualian dengan tepat, misalnya lempar pengecualian khusus
        }
        return userId;
    }

    public User login(String username, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password); // Hash dulu input password

        // Ambil ID juga dari hasil query
        String query = "SELECT id, username, role FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id")); // <--- Baris ini yang mengatur ID
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Gagal login: " + e.getMessage());
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
        
}
