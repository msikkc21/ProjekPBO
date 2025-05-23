/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Koneksi;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author izzaa
 */
public class Koneksi {
    static Connection con;
    
    public static Connection getConnection(){
        if(con == null){
            MysqlDataSource data = new MysqlDataSource();
            data.setDatabaseName("hafalan_santri");
            data.setUser("root");
            data.setPassword("");
            try{
                con = data.getConnection();
                System.out.println("Koneksi Database Berhasil");
            }catch(SQLException e){
                System.out.println("Koneksi Gagal");
            }
        }
        return con;
    }

   
}
