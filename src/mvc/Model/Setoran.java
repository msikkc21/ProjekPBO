/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Model;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Acer
 */
public class Setoran {

    /**
     * @return the juz
     */
    public Integer getJuz() {
        return juz;
    }

    /**
     * @param juz the juz to set
     */
    public void setJuz(Integer juz) {
        this.juz = juz;
    }

    /**
     * @return the halaman
     */
    public Integer getHalaman() {
        return halaman;
    }

    /**
     * @param halaman the halaman to set
     */
    public void setHalaman(Integer halaman) {
        this.halaman = halaman;
    }

    /**
     * @return the keterangan
     */
    public String getKeterangan() {
        return keterangan;
    }

    /**
     * @param keterangan the keterangan to set
     */
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    /**
     * @return the nilai
     */
    public String getNilai() {
        return nilai;
    }

    /**
     * @param nilai the nilai to set
     */
    public void setNilai(String nilai) {
        this.nilai = nilai;
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
     * @return the santriid
     */
    public Santri getSantriid() {
        return santriid;
    }

    /**
     * @param santriid the santriid to set
     */
    public void setSantriid(Santri santriid) {
        this.santriid = santriid;
    }

    /**
     * @return the ustadzid
     */
    public Ustadz getUstadzid() {
        return ustadzid;
    }

    /**
     * @param ustadzid the ustadzid to set
     */
    public void setUstadzid(Ustadz ustadzid) {
        this.ustadzid = ustadzid;
    }

    /**
     * @return the tanggal
     */
    public Date getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the waktu
     */
    public String getWaktu() {
        return waktu;
    }

    /**
     * @param waktu the waktu to set
     */
    public void setWaktu(String waktu) {
        if (!waktu.equalsIgnoreCase("Pagi") && !waktu.equalsIgnoreCase("Sore")) {
            throw new IllegalArgumentException("Waktu hanya boleh 'Pagi' atau 'Sore'");
    }
        this.waktu = waktu;
    }
    private Integer id;
    private Santri santriid;
    private Ustadz ustadzid;
    private Date tanggal;
    private String waktu;
    private Integer juz;
    private Integer halaman;
    private String keterangan;
    private String nilai;
    
}
