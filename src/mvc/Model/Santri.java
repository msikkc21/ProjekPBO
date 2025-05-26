/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Model;

import java.util.Date;

/**
 *
 * @author User
 */
public class Santri {
   private Integer id;
    private String nama_santri;
    private Date tanggal_lahir;
    private String alamat;
    private String nomor_telepon;
    private String nama_wali;
    private Date tanggal_masuk;
    private String status;
    
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
     * @return the nama_santri
     */
    public String getNama_santri() {
        return nama_santri;
    }

    /**
     * @param nama_santri the nama_santri to set
     */
    public void setNama_santri(String nama_santri) {
        this.nama_santri = nama_santri;
    }

    /**
     * @return the tanggal_lahir
     */
    public Date getTanggal_lahir() {
        return tanggal_lahir;
    }

    /**
     * @param tanggal_lahir the tanggal_lahir to set
     */
    public void setTanggal_lahir(Date tanggal_lahir) {
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
    public String getNomor_telepon() {
        return nomor_telepon;
    }

    /**
     * @param nomor_telepon the nomor_telepon to set
     */
    public void setNomor_telepon(String nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
    }

    /**
     * @return the nama_wali
     */
    public String getNama_wali() {
        return nama_wali;
    }

    /**
     * @param nama_wali the nama_wali to set
     */
    public void setNama_wali(String nama_wali) {
        this.nama_wali = nama_wali;
    }

    /**
     * @return the tanggal_masuk
     */
    public Date getTanggal_masuk() {
        return tanggal_masuk;
    }

    /**
     * @param tanggal_masuk the tanggal_masuk to set
     */
    public void setTanggal_masuk(Date tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
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
