/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.DAO;
import mvc.Koneksi.Koneksi;
import mvc.Model.Santri;
import mvc.DAOInterface.ISantri;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.Model.Setoran;
import mvc.Model.Ustadz;

/**
 *
 * @author User
 */
public class SantriDAO implements ISantri{
    Connection con;
    DAOSetoran daoSetoran;
    
    final String insert = "INSERT INTO santri (user_id, nama_santri, tanggal_lahir, alamat, nomor_telepon, nama_wali, tanggal_masuk, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    final String update = "UPDATE santri set nama_santri=?, tanggal_lahir=?, alamat=?, nomor_telepon=?, nama_wali=?, tanggal_masuk=?, status=? WHERE id=?;";
    final String delete = "DELETE FROM santri where id=?;";
    final String selectById = "SELECT id, user_id, nama_santri, tanggal_lahir, alamat, nomor_telepon, nama_wali, tanggal_masuk, status FROM santri WHERE id=?;";
    final String selectByUserId = "SELECT id, user_id, nama_santri, tanggal_lahir, alamat, nomor_telepon, nama_wali, tanggal_masuk, status FROM santri WHERE user_id=?;";
    final String select = "SELECT id, user_id, nama_santri, tanggal_lahir, alamat, nomor_telepon, nama_wali, tanggal_masuk, status FROM santri;";
    
    public SantriDAO (){
        con = Koneksi.getConnection();
        daoSetoran = new DAOSetoran();
    } 
    
    public List<Setoran> getAllSetoranBySantriId(int santriId) {
        return daoSetoran.getSetoranBySantriId(santriId); // Panggil metode baru di DAOSetoran
    }
    
    public List<Setoran> getSetoranSantriByKeyword(int santriId, String keyword) {
        return daoSetoran.getSetoranBySantriIdAndKeyword(santriId, keyword); // Panggil metode baru di DAOSetoran
    }
    
    public void insert(Santri s){
        try(PreparedStatement statement = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, s.getUser_id());
            statement.setString(2, s.getNama_santri());
            statement.setDate(3, s.getTanggal_lahir ()!= null ? new java.sql.Date(s.getTanggal_lahir().getTime()) : null);
            statement.setString(4, s.getAlamat());
            statement.setString(5, s.getNomor_telepon());
            statement.setString(6, s.getNama_wali());
            statement.setDate(7, s.getTanggal_masuk()!= null ? new java.sql.Date(s.getTanggal_masuk().getTime()) : null);
            statement.setString (8, s.getStatus());
            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                s.setId(rs.getInt(1));
            }
            // Tidak perlu System.out.println di DAO, biarkan Controller yang menanganinya
        } catch (SQLException e) {
            Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, null, e);
            // Anda bisa melempar exception kustom di sini jika ingin error ditangani di Controller
        }
    }
    
    public void update(Santri s){
        try(PreparedStatement statement = con.prepareStatement(update)){
            statement.setString(1, s.getNama_santri());
            statement.setDate(2, s.getTanggal_lahir()!= null ? new java.sql.Date(s.getTanggal_lahir().getTime()) : null);
            statement.setString(3, s.getAlamat());
            statement.setString(4, s.getNomor_telepon());
            statement.setString(5, s.getNama_wali());
            statement.setDate(6, s.getTanggal_masuk() != null ? new java.sql.Date(s.getTanggal_masuk().getTime()) : null);
            statement.setString (7, s.getStatus());
            statement.setInt(8, s.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void delete(int id){
        try(PreparedStatement statement = con.prepareStatement(delete)){
            
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (SQLException e){
            Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Santri getById(int id) { // Metode baru untuk mendapatkan satu Ustadz
        Santri santri = null;
        try (PreparedStatement statement = con.prepareStatement(selectById)) {
            statement.setInt(1, id); // Menggunakan setInt untuk parameter ID
            ResultSet rs = statement.executeQuery();
            if (rs.next()) { // Gunakan if karena kita hanya mencari satu record
                santri = new Santri();
                santri.setId(rs.getInt("id"));
                santri.setNama_santri(rs.getString("nama_santri"));
                santri.setTanggal_lahir(rs.getDate("tanggal_lahir"));
                santri.setAlamat(rs.getString("alamat"));
                santri.setNomor_telepon(rs.getString("nomor_telepon"));
                santri.setTanggal_masuk(rs.getDate("tanggal_masuk"));
                santri.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return santri;
    }
    
    // Metode baru: getByUserId
    @Override
    public Santri getByUserId(int userId) {
        Santri santri = null;
        try (PreparedStatement statement = con.prepareStatement(selectByUserId)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                santri = new Santri();
                santri.setId(rs.getInt("id"));
                santri.setUser_id(rs.getInt("user_id"));
                santri.setNama_santri(rs.getString("nama_santri"));
                santri.setTanggal_lahir(rs.getDate("tanggal_lahir"));
                santri.setAlamat(rs.getString("alamat"));
                santri.setNomor_telepon(rs.getString("nomor_telepon"));
                santri.setNama_wali(rs.getString("nama_wali"));
                santri.setTanggal_masuk(rs.getDate("tanggal_masuk"));
                santri.setStatus(rs.getString("status"));
                // Asumsi ada kolom user_id di tabel Santri
                // santri.setUserId(rs.getInt("user_id")); // Jika ada setter untuk user_id di Model Santri
            }
        } catch (SQLException e) {
            Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return santri;
    }
    
    public List<Santri>getAll(){
        List<Santri> list = null;
        try{
            list = new ArrayList<Santri>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()){
                Santri s = new Santri();
                s.setId(rs.getInt("id"));
                s.setNama_santri(rs.getString("nama_santri"));
                s.setTanggal_lahir(rs.getDate("tanggal_lahir"));
                s.setAlamat(rs.getString("Alamat"));
                s.setNomor_telepon(rs.getString("nomor_telepon"));
                s.setNama_wali(rs.getString("nama_wali"));
                s.setTanggal_masuk(rs.getDate("tanggal_masuk"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }
        }catch(SQLException ex){
            Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE,null, ex);
        }
        return list;
    }
    
    // Di dalam file SantriDAO.java
// ... (bagian atas kelas) ...

//@Override
//public List<Setoran> getAllSetoran() {
//    List<Setoran> listSetoran = new ArrayList<>();
//    PreparedStatement statement = null;
//    ResultSet rs = null;
//    try {
//        // Asumsi ada tabel 'tblsetoran' di database Anda
//        // Dan Anda memiliki kelas model 'Setoran' di package mvc.Model
//        String selectAllSetoran = "SELECT * FROM tblsetoran"; // Sesuaikan nama tabel setoran Anda
//        statement = con.prepareStatement(selectAllSetoran);
//        rs = statement.executeQuery();
//
//        while (rs.next()) {
//            Setoran setoran = new Setoran();
//            // Sesuaikan dengan nama kolom di tabel 'tblsetoran' Anda
//            setoran.setId(rs.getInt("id_setoran")); // Contoh: id_setoran (primary key setoran)
//            setoran.setTanggal(rs.getDate("tanggal_setoran")); // Contoh: tanggal_setoran
//            setoran.setJumlah(rs.getDouble("jumlah_setoran")); // Contoh: jumlah_setoran
//            setoran.setSantriId(rs.getInt("santri_id")); // Contoh: foreign key ke ID santri
//
//            listSetoran.add(setoran);
//        }
//    } catch (SQLException ex) {
//        Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, "Error while fetching all setoran", ex);
//    } finally {
//        try {
//            if (rs != null) rs.close();
//            if (statement != null) statement.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
//        }
//    }
//    return listSetoran;
//}
//
//// Di dalam file SantriDAO.java
//// ... (bagian atas kelas) ...
//
//@Override
//public List<Setoran> getAllSetoran() {
//    List<Setoran> listSetoran = new ArrayList<>();
//    PreparedStatement statement = null;
//    ResultSet rs = null;
//    try {
//        // Asumsi ada tabel 'tblsetoran' di database Anda
//        // Dan Anda memiliki kelas model 'Setoran' di package mvc.Model
//        String selectAllSetoran = "SELECT * FROM tblsetoran"; // Sesuaikan nama tabel setoran Anda
//        statement = con.prepareStatement(selectAllSetoran);
//        rs = statement.executeQuery();
//
//        while (rs.next()) {
//            Setoran setoran = new Setoran();
//            // Sesuaikan dengan nama kolom di tabel 'tblsetoran' Anda
//            setoran.setId(rs.getInt("id_setoran")); // Contoh: id_setoran (primary key setoran)
//            setoran.setTanggal(rs.getDate("tanggal_setoran")); // Contoh: tanggal_setoran
//            setoran.setJumlah(rs.getDouble("jumlah_setoran")); // Contoh: jumlah_setoran
//            setoran.setSantriId(rs.getInt("santri_id")); // Contoh: foreign key ke ID santri
//
//            listSetoran.add(setoran);
//        }
//    } catch (SQLException ex) {
//        Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, "Error while fetching all setoran", ex);
//    } finally {
//        try {
//            if (rs != null) rs.close();
//            if (statement != null) statement.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
//        }
//    }
//    return listSetoran;
//}
//
//@Override
//public List<Setoran> getAllSetoran() {
//    List<Setoran> listSetoran = new ArrayList<>();
//    PreparedStatement statement = null;
//    ResultSet rs = null;
//    try {
//        // Asumsi ada tabel 'tblsetoran' di database Anda
//        // Dan Anda memiliki kelas model 'Setoran' di package mvc.Model
//        String selectAllSetoran = "SELECT * FROM tblsetoran"; // Sesuaikan nama tabel setoran Anda
//        statement = con.prepareStatement(selectAllSetoran);
//        rs = statement.executeQuery();
//
//        while (rs.next()) {
//            Setoran setoran = new Setoran();
//            // Sesuaikan dengan nama kolom di tabel 'tblsetoran' Anda
//            setoran.setId(rs.getInt("id_setoran")); // Contoh: id_setoran (primary key setoran)
//            setoran.setTanggal(rs.getDate("tanggal_setoran")); // Contoh: tanggal_setoran
//            setoran.setJumlah(rs.getDouble("jumlah_setoran")); // Contoh: jumlah_setoran
//            setoran.setSantriId(rs.getInt("santri_id")); // Contoh: foreign key ke ID santri
//
//            listSetoran.add(setoran);
//        }
//    } catch (SQLException ex) {
//        Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, "Error while fetching all setoran", ex);
//    } finally {
//        try {
//            if (rs != null) rs.close();
//            if (statement != null) statement.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(SantriDAO.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
//        }
//    }
//    return listSetoran;
//}
}

