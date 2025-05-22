/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.DAO;

import mvc.Koneksi.Koneksi;
import mvc.Model.Ustadz;
import mvc.DAOInterface.IUstadz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ASUS
 */
public class DAOUstadz implements IUstadz {
    Connection connection;
    
    final String insert = "INSERT INTO ustadz (nama_ustadz, tanggal_lahir, alamat, nomor_telepon, tanggal_bergabung, status) VALUES (?, ?, ?, ?, ?, ?);";
    final String update = "UPDATE ustadz SET nama_ustadz=?, tanggal_lahir=?, alamat=?, nomor_telepon=?, tanggal_bergabung=?, status=? WHERE id=?;";
    final String delete = "DELETE FROM ustadz WHERE id=?;";
    final String select = "SELECT * FROM ustadz;";
    final String carinama = "SELECT * FROM ustadz WHERE nama_ustadz LIKE ?;";

    public DAOUstadz() {
        connection = Koneksi.getConnection();
    }

    @Override
    public void insert(Ustadz u) {
        try (PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, u.getNama());
            statement.setDate(2, u.getTanggal_Lahir()!= null ? new java.sql.Date(u.getTanggal_Lahir().getTime()) : null);
            statement.setString(3, u.getAlamat());
            statement.setString(4, u.getNomor_Telepon());
            statement.setDate(5, u.getTanggal_Bergabung()!= null ? new java.sql.Date(u.getTanggal_Bergabung().getTime()) : null);
            statement.setString(6, u.getStatus());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Gagal Insert Ustadz: " + e.getMessage());
        }
    }

    @Override
    public void update(Ustadz u) {
        try (PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, u.getNama());
            statement.setDate(2, u.getTanggal_Lahir()!= null ? new java.sql.Date(u.getTanggal_Bergabung().getTime()) : null);
            statement.setString(3, u.getAlamat());
            statement.setString(4, u.getNomor_Telepon());
            statement.setDate(5, u.getTanggal_Bergabung()!= null ? new java.sql.Date(u.getTanggal_Bergabung().getTime()) : null);
            statement.setString(6, u.getStatus());
            statement.setInt(7, u.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal Update Ustadz: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal Delete Ustadz: " + e.getMessage());
        }
    }

    @Override
    public List<Ustadz> getAll() {
        List<Ustadz> list = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                Ustadz u = new Ustadz();
                u.setId(rs.getInt("id"));
                u.setNama(rs.getString("nama_ustadz"));
                u.setTanggal_Lahir(rs.getDate("tanggal_lahir"));
                u.setAlamat(rs.getString("alamat"));
                u.setNomor_Telepon(rs.getString("nomor_telepon"));
                u.setTanggal_Bergabung(rs.getDate("tanggal_bergabung"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    @Override
    public List<Ustadz> getCariNama(String nama) {
        List<Ustadz> list = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(carinama)) {
            st.setString(1, "%" + nama + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Ustadz u = new Ustadz();
                u.setId(rs.getInt("id"));
                u.setNama(rs.getString("nama_ustadz"));
                u.setTanggal_Lahir(rs.getDate("tanggal_lahir"));
                u.setAlamat(rs.getString("alamat"));
                u.setNomor_Telepon(rs.getString("nomor_telepon"));
                u.setTanggal_Bergabung(rs.getDate("tanggal_bergabung"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
}
