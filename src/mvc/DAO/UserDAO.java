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
/**
 *
 * @author izzaa
 */
public class UserDAO implements IUser{

    Connection con;
    final String insert = "INSERT INTO user(username, password, role) VALUES (?, ?, ?);";
    final String update = "UPDATE user SET password=? WHERE id=?;";
    final String read = "SELECT * FROM user WHERE id=?;";
    final String delete = "DELETE FROM user WHERE id=?;";

    public UserDAO(){
        con = Koneksi.getConnection();
    }
    public void insert(User u) {
        PreparedStatement statement = null;
        try{
            statement = con.prepareStatement(insert);
            statement.setString(1, u.getUsername());
            statement.setString(2, u.getPassword());
            statement.setString(3, u.getRole());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while(rs.next()){
                u.setId(rs.getInt(1));
            }
        } catch(SQLException e){
            System.out.println("Berhasil Menambahkan User");
        } finally {
            try{
                statement.close();
            } catch(SQLException e) {
                System.out.println("Gagal Input");
            }
        }
    }

    public void read(int id) {
        PreparedStatement statement = null;
        try{
            statement = con.prepareStatement(read);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("psasword"));
            u.setRole(rs.getString("role"));
        } catch(SQLException e){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void update(User u) {
        PreparedStatement statement = null;
        try{
            statement = con.prepareStatement(update);
            statement.setString(1, u.getPassword());
            statement.setInt(2, u.getId());
            statement.executeUpdate();
        } catch(SQLException e){
            System.out.println("Berhasil Mengedit User");
        } finally {
            try{
                statement.close();
            } catch(SQLException e) {
                System.out.println("Gagal Update");
            }
        }
    }

    public void delete(int id) {
        PreparedStatement statement = null;
        try{
            statement = con.prepareStatement(delete);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch(SQLException e){
            System.out.println("Berhasil Menghapus User");
        } finally {
            try{
                statement.close();
            } catch(SQLException e) {
                System.out.println("Gagal Hapus");
            }
        }
    }
    
}
