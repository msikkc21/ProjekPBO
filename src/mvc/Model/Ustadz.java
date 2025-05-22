/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Model;

import java.util.Date;
/**
 *
 * @author ASUS
 */
public class Ustadz {
    private Integer id;
    private String nama;
    private Date tanggal_lahir;
    private String alamat;
    private String nomor_telepon;
    private Date tanggal_bergabung;
    private String status;
    
    public Ustadz(){}
    
    public Ustadz(int id, String nama,Date tanggal_lahir, String alamat, String nomor_telepon, Date tanggal_bergabung, String status) {
        this.id = id;
        this.nama = nama;
        this.tanggal_lahir = tanggal_lahir;
        this.alamat = alamat;
        this.nomor_telepon = nomor_telepon;
        this.tanggal_bergabung = tanggal_bergabung;
        this.status = status;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the tanggal_lahir
     */
    public Date getTanggal_Lahir() {
        return tanggal_lahir;
    }

    /**
     * @param tanggal_lahir the tanggal_lahir to set
     */
    public void setTanggal_Lahir(Date tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    /**
     * @return the alamat
     */
    public String getAlamat() {
        return alamat;
    }

    /**
     * @param alamat the alamat to set
     */
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    /**
     * @return the nomor_telepon
     */
    public String getNomor_Telepon() {
        return nomor_telepon;
    }

    /**
     * @param nomor_telepon the nomor_telepon to set
     */
    public void setNomor_Telepon(String nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
    }

    /**
     * @return the tanggal_bergabung
     */
    public Date getTanggal_Bergabung() {
        return tanggal_bergabung;
    }

    /**
     * @param tanggal_bergabung the tanggal_bergabung to set
     */
    public void setTanggal_Bergabung(Date tanggal_bergabung) {
        this.tanggal_bergabung = tanggal_bergabung;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    } 
}
