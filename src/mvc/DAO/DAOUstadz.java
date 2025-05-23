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
import java.text.SimpleDateFormat; // Import untuk format tanggal jika diperlukan

/**
 *
 * @author ASUS
 */
public class DAOUstadz implements IUstadz {
    Connection connection;

    final String insert = "INSERT INTO ustadz (nama_ustadz, tanggal_lahir, alamat, nomor_telepon, tanggal_bergabung, status) VALUES (?, ?, ?, ?, ?, ?);";
    final String update = "UPDATE ustadz SET nama_ustadz=?, tanggal_lahir=?, alamat=?, nomor_telepon=?, tanggal_bergabung=?, status=? WHERE id=?;";
    final String delete = "DELETE FROM ustadz WHERE id=?;";
    final String selectById = "SELECT * FROM ustadz WHERE id=?;"; // Perubahan: query spesifik untuk ID
    final String selectAll = "SELECT * FROM ustadz;"; // Perubahan: query untuk semua data
    final String carinama = "SELECT * FROM ustadz WHERE nama_ustadz LIKE ?;";

    public DAOUstadz() {
        connection = Koneksi.getConnection();
    }

    @Override
    public void insert(Ustadz u) {
        try (PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, u.getNama());
            statement.setDate(2, u.getTanggal_Lahir() != null ? new java.sql.Date(u.getTanggal_Lahir().getTime()) : null);
            statement.setString(3, u.getAlamat());
            statement.setString(4, u.getNomor_Telepon());
            statement.setDate(5, u.getTanggal_Bergabung() != null ? new java.sql.Date(u.getTanggal_Bergabung().getTime()) : null);
            statement.setString(6, u.getStatus());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getInt(1));
            }
            // Tidak perlu System.out.println di DAO, biarkan Controller yang menanganinya
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
            // Anda bisa melempar exception kustom di sini jika ingin error ditangani di Controller
        }
    }

    @Override
    public void update(Ustadz u) {
        try (PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, u.getNama());
            // Perbaikan: Pastikan Anda menggunakan Tanggal_Lahir untuk tanggal_lahir di update statement.
            // Di kode asli Anda, Tanggal_Bergabung digunakan untuk tanggal_lahir.
            statement.setDate(2, u.getTanggal_Lahir() != null ? new java.sql.Date(u.getTanggal_Lahir().getTime()) : null);
            statement.setString(3, u.getAlamat());
            statement.setString(4, u.getNomor_Telepon());
            statement.setDate(5, u.getTanggal_Bergabung() != null ? new java.sql.Date(u.getTanggal_Bergabung().getTime()) : null);
            statement.setString(6, u.getStatus());
            statement.setInt(7, u.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public Ustadz getById(int id) { // Metode baru untuk mendapatkan satu Ustadz
        Ustadz ustadz = null;
        try (PreparedStatement statement = connection.prepareStatement(selectById)) {
            statement.setInt(1, id); // Menggunakan setInt untuk parameter ID
            ResultSet rs = statement.executeQuery();
            if (rs.next()) { // Gunakan if karena kita hanya mencari satu record
                ustadz = new Ustadz();
                ustadz.setId(rs.getInt("id"));
                ustadz.setNama(rs.getString("nama_ustadz"));
                ustadz.setTanggal_Lahir(rs.getDate("tanggal_lahir"));
                ustadz.setAlamat(rs.getString("alamat"));
                ustadz.setNomor_Telepon(rs.getString("nomor_telepon"));
                ustadz.setTanggal_Bergabung(rs.getDate("tanggal_bergabung"));
                ustadz.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUstadz.class.getName()).log(Level.SEVERE, null, e);
        }
        return ustadz;
    }

    @Override
    public List<Ustadz> getAll() { // Metode baru untuk mendapatkan semua Ustadz
        List<Ustadz> list = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(selectAll);
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