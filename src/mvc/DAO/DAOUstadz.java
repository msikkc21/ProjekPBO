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
    Connection con;

    final String insert = "INSERT INTO ustadz (user_id, nama_ustadz, tanggal_lahir, alamat, nomor_telepon, tanggal_bergabung, status) VALUES (?, ?, ?, ?, ?, ?, ?);"; //
    final String update = "UPDATE ustadz SET nama_ustadz=?, tanggal_lahir=?, alamat=?, nomor_telepon=?, tanggal_bergabung=?, status=? WHERE id=?;";
    final String delete = "DELETE FROM ustadz WHERE id=?;";
    final String selectById = "SELECT * FROM ustadz WHERE id=?;";
    final String selectByUserId = "SELECT * FROM ustadz WHERE user_id=?;"; // <--- BARU: Query untuk mencari berdasarkan user_id
    final String selectAll = "SELECT * FROM ustadz;";
    final String carinama = "SELECT * FROM ustadz WHERE nama_ustadz LIKE ?;";
    
    public DAOUstadz() {
        con = Koneksi.getConnection();
    }

    @Override
    public void insert(Ustadz u) {
        try (PreparedStatement statement = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) { // Tambahkan RETURN_GENERATED_KEYS lagi untuk ustadz_id jika auto-increment
            statement.setInt(1, u.getUserId()); // <--- BARU: Set user_id
            statement.setString(2, u.getNama());
            statement.setDate(3, u.getTanggal_lahir() != null ? new java.sql.Date(u.getTanggal_lahir().getTime()) : null);
            statement.setString(4, u.getAlamat());
            statement.setString(5, u.getNomor_telepon());
            statement.setDate(6, u.getTanggal_bergabung() != null ? new java.sql.Date(u.getTanggal_bergabung().getTime()) : null);
            statement.setString(7, u.getStatus());
            statement.executeUpdate();

            // Ambil ID yang dihasilkan untuk objek Ustadz itu sendiri (jika primary key ustadz auto-increment)
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getInt(1)); // Set ID Ustadz yang baru dihasilkan
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void update(Ustadz u) {
        try (PreparedStatement statement = con.prepareStatement(update)) {
            statement.setString(1, u.getNama());
            // Perbaikan: Pastikan Anda menggunakan Tanggal_Lahir untuk tanggal_lahir di update statement.
            // Di kode asli Anda, Tanggal_Bergabung digunakan untuk tanggal_lahir.
            statement.setDate(2, u.getTanggal_lahir() != null ? new java.sql.Date(u.getTanggal_lahir().getTime()) : null);
            statement.setString(3, u.getAlamat());
            statement.setString(4, u.getNomor_telepon());
            statement.setDate(5, u.getTanggal_bergabung() != null ? new java.sql.Date(u.getTanggal_bergabung().getTime()) : null);
            statement.setString(6, u.getStatus());
            statement.setInt(7, u.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement = con.prepareStatement(delete)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Ustadz getByUserId(int userId) { // <--- BARU: Implementasi metode getByUserId
        Ustadz ustadz = null;
        try (PreparedStatement statement = con.prepareStatement(selectByUserId)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                ustadz = new Ustadz();
                ustadz.setId(rs.getInt("id"));
                ustadz.setUserId(rs.getInt("user_id"));
                ustadz.setNama(rs.getString("nama_ustadz"));
                ustadz.setTanggal_lahir(rs.getDate("tanggal_lahir"));
                ustadz.setAlamat(rs.getString("alamat"));
                ustadz.setNomor_telepon(rs.getString("nomor_telepon"));
                ustadz.setTanggal_bergabung(rs.getDate("tanggal_bergabung"));
                ustadz.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
        return ustadz;
    }

    @Override
    public Ustadz getById(int id) {
        Ustadz ustadz = null;
        try (PreparedStatement statement = con.prepareStatement(selectById)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                ustadz = new Ustadz();
                ustadz.setId(rs.getInt("id"));
                ustadz.setUserId(rs.getInt("user_id")); // <--- BARU: Ambil user_id
                ustadz.setNama(rs.getString("nama_ustadz"));
                ustadz.setTanggal_lahir(rs.getDate("tanggal_lahir"));
                ustadz.setAlamat(rs.getString("alamat"));
                ustadz.setNomor_telepon(rs.getString("nomor_telepon"));
                ustadz.setTanggal_bergabung(rs.getDate("tanggal_bergabung"));
                ustadz.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
        return ustadz;
    }
    
    public List<Ustadz> getAll() { //
        List<Ustadz> list = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(selectAll);
            while (rs.next()) {
                Ustadz u = new Ustadz();
                u.setId(rs.getInt("id"));
                u.setUserId(rs.getInt("user_id")); // <--- BARU: Ambil user_id
                u.setNama(rs.getString("nama_ustadz"));
                u.setTanggal_lahir(rs.getDate("tanggal_lahir"));
                u.setAlamat(rs.getString("alamat"));
                u.setNomor_telepon(rs.getString("nomor_telepon"));
                u.setTanggal_bergabung(rs.getDate("tanggal_bergabung"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    @Override
    public List<Ustadz> getCariNama(String nama) { //
        List<Ustadz> list = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(carinama)) {
            st.setString(1, "%" + nama + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Ustadz u = new Ustadz();
                u.setId(rs.getInt("id"));
                u.setUserId(rs.getInt("user_id")); // <--- BARU: Ambil user_id
                u.setNama(rs.getString("nama_ustadz"));
                u.setTanggal_lahir(rs.getDate("tanggal_lahir"));
                u.setAlamat(rs.getString("alamat"));
                u.setNomor_telepon(rs.getString("nomor_telepon"));
                u.setTanggal_bergabung(rs.getDate("tanggal_bergabung"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
}