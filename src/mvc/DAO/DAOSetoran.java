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
/**
 *
 * @author Acer
 */
public class DAOSetoran implements ISetoran {
    Connection connection;
    final String insert = "INSERT INTO setoran (santri_id, ustadz_id, tanggal, waktu, juz, halaman, keterangan, nilai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    final String update = "UPDATE setoran SET santri_id = ?, ustadz_id = ?, tanggal = ?, waktu = ?, juz = ?, halaman = ?, keterangan = ?, nilai = ? WHERE id = ?";
    final String delete = "DELETE FROM setoran where id=?";
    final String select = "SELECT * FROM setoran";
    final String CariSantri = "SELECT * FROM setoran WHERE nama_santri LIKE ?";
    
    public DAOSetoran(){
        connection = Koneksi.getConnection();
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
                Setoran a = new Setoran();
                a.setId(rs.getInt("id"));
            
            // Buat objek Santri dulu, set id dan atribut lain jika ada
                    Santri santri = new Santri();
                    santri.setId(rs.getInt("santri_id"));
                    a.setSantriid(santri);

                    // Buat objek Ustadz
                    Ustadz ustadz = new Ustadz();
                    ustadz.setId(rs.getInt("ustadz_id"));
                    a.setUstadzid(ustadz);

                    a.setTanggal(rs.getDate("tanggal").toLocalDate());  // dari java.sql.Date ke LocalDate
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

    public List<Setoran> getCariSantri(String nama) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
