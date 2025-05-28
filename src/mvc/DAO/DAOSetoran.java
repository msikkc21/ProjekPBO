/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.DAO;
import mvc.Koneksi.Koneksi;
import mvc.Model.Setoran;
import mvc.DAOInterface.ISetoran;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.DAOInterface.ISetoran;
import mvc.DAO.SantriDAO;
import mvc.Model.Santri;
import mvc.Model.Ustadz;
/**
 *
 * @author Acer
 */
public class DAOSetoran implements ISetoran {
    Connection connection;
    SantriDAO cbtSantri;
    final String insert = "INSERT INTO setoran (santri_id, ustadz_id, tanggal, waktu, juz, halaman, keterangan, nilai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    final String update = "UPDATE setoran SET santri_id = ?, ustadz_id = ?, tanggal = ?, waktu = ?, juz = ?, halaman = ?, keterangan = ?, nilai = ? WHERE id = ?";
    final String delete = "DELETE FROM setoran where id=?";
    final String select = "SELECT s.*, sa.nama_santri, sa.tanggal_lahir AS santri_tgl_lahir, sa.alamat AS santri_alamat, sa.nomor_telepon AS santri_nomor_telepon, sa.nama_wali, sa.tanggal_masuk, sa.status AS santri_status, " +
                      "u.nama_ustadz, u.tanggal_lahir AS ustadz_tgl_lahir, u.alamat AS ustadz_alamat, u.nomor_telepon AS ustadz_nomor_telepon, u.tanggal_bergabung, u.status AS ustadz_status " +
                      "FROM setoran s " +
                      "JOIN santri sa ON s.santri_id = sa.id " +
                      "JOIN ustadz u ON s.ustadz_id = u.id";
    final String CariSantri = "SELECT s.*, sa.nama_santri, sa.tanggal_lahir AS santri_tgl_lahir, sa.alamat AS santri_alamat, sa.nomor_telepon AS santri_nomor_telepon, sa.nama_wali, sa.tanggal_masuk, sa.status AS santri_status, " +
                          "u.nama_ustadz, u.tanggal_lahir AS ustadz_tgl_lahir, u.alamat AS ustadz_alamat, u.nomor_telepon AS ustadz_nomor_telepon, u.tanggal_bergabung, u.status AS ustadz_status " +
                          "FROM setoran s " +
                          "JOIN santri sa ON s.santri_id = sa.id " +
                          "JOIN ustadz u ON s.ustadz_id = u.id " +
                          "WHERE s.santri_id = ?";
    
    public DAOSetoran(){
        connection = Koneksi.getConnection();
        this.cbtSantri = new SantriDAO();
    }
 
    public void insert(Setoran a) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, a.getSantriid().getId());  // ambil id santri
            statement.setInt(2, a.getUstadzid().getId()); 
            statement.setDate(3,java.sql.Date.valueOf(a.getTanggal())); 
            statement.setString(4, a.getWaktu());
            statement.setInt(5, a.getJuz());
            statement.setInt(6, a.getHalaman());
            statement.setString(7, a.getKeterangan());
            statement.setString(8, a.getNilai());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()){
                a.setId(rs.getInt(1));
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }finally {
            try {
                statement.close();
            }catch (SQLException ex){
                System.out.println("Gagal Input");
            }
            
        }
    }

    @Override
    public void update(Setoran a) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setInt(1, a.getSantriid().getId());  // ambil id santri
            statement.setInt(2, a.getUstadzid().getId()); 
            statement.setDate(3,java.sql.Date.valueOf(a.getTanggal())); 
            statement.setString(4, a.getWaktu());
            statement.setInt(5, a.getJuz());
            statement.setInt(6, a.getHalaman());
            statement.setString(7, a.getKeterangan());
            statement.setString(8, a.getNilai());
            statement.setInt(9, a.getId());
            statement.executeUpdate();

        } catch (SQLException ex){
            System.out.println("Berhasil Update");
        }finally {
            try {
                statement.close();
            }catch (SQLException ex){
                System.out.println("Gagal Input");
            }
            
        } // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
            PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException ex){
            System.out.println("Berhasil Delete");
        }finally {
            try {
                statement.close();
            }catch (SQLException ex){
                System.out.println("Gagal Delete");
            }
            
        }
    }

    @Override
    public List<Setoran> getAll() {
                List<Setoran> lb = null;
        try {
            lb = new ArrayList<>();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                // Dalam loop ResultSet di getAll() atau getCariSantri()
                Setoran a = new Setoran();
                a.setId(rs.getInt("id"));

                // Membuat dan mengisi objek Santri
                Santri santri = new Santri();
                santri.setId(rs.getInt("santri_id"));
                santri.setNama_santri(rs.getString("nama_santri"));
                santri.setTanggal_lahir(rs.getDate("santri_tgl_lahir")); // Perhatikan alias kolom
                santri.setAlamat(rs.getString("santri_alamat"));
                santri.setNomor_telepon(rs.getString("santri_nomor_telepon"));
                santri.setNama_wali(rs.getString("nama_wali"));
                santri.setTanggal_masuk(rs.getDate("tanggal_masuk"));
                santri.setStatus(rs.getString("santri_status"));
                a.setSantriid(santri);

                // Membuat dan mengisi objek Ustadz
                Ustadz ustadz = new Ustadz();
                ustadz.setId(rs.getInt("ustadz_id"));
                ustadz.setNama(rs.getString("nama_ustadz"));
                ustadz.setTanggal_lahir(rs.getDate("ustadz_tgl_lahir")); // Perhatikan alias kolom
                ustadz.setAlamat(rs.getString("ustadz_alamat"));
                ustadz.setNomor_telepon(rs.getString("ustadz_nomor_telepon"));
                ustadz.setTanggal_bergabung(rs.getDate("tanggal_bergabung"));
                ustadz.setStatus(rs.getString("ustadz_status"));
                a.setUstadzid(ustadz);

                a.setTanggal(rs.getDate("tanggal").Date());
                a.setWaktu(rs.getString("waktu"));
                a.setJuz(rs.getInt("juz"));
                a.setHalaman(rs.getInt("halaman"));
                a.setKeterangan(rs.getString("keterangan"));
                a.setNilai(rs.getString("nilai"));

                lb.add(a);
            }
        } catch(SQLException ex){
            Logger.getLogger(DAOSetoran.class.getName()).log(Level.SEVERE,null, ex);
        }
        return lb;
    }

    public List<Setoran> getCariSantri(int id_santri) {
         List<Setoran> lb = new ArrayList<>();
        String query = "SELECT * FROM setoran WHERE santri_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_santri);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Setoran a = new Setoran();
                a.setId(rs.getInt("id"));

                Santri santri = new Santri();
                santri.setId(rs.getInt("santri_id"));
                a.setSantriid(santri);

                Ustadz ustadz = new Ustadz();
                ustadz.setId(rs.getInt("ustadz_id"));
                a.setUstadzid(ustadz);

                a.setTanggal(rs.getDate("tanggal").toLocalDate());
                a.setWaktu(rs.getString("waktu"));
                a.setJuz(rs.getInt("juz"));
                a.setHalaman(rs.getInt("halaman"));
                a.setKeterangan(rs.getString("keterangan"));
                a.setNilai(rs.getString("nilai"));

                lb.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSetoran.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lb;
    }
    
    public void getAllSantri(){
        cbtSantri.getAll(); 
    }
}
