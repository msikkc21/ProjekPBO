package mvc.Model;

import java.util.Date;

public class Ustadz {
    private Integer id; // ID unik untuk entitas Ustadz
    private Integer userId; // <--- BARU: Foreign key ke tabel user
    private String nama;
    private Date tanggal_lahir;
    private String alamat;
    private String nomor_telepon;
    private Date tanggal_bergabung;
    private String status;

    public Ustadz(){}

    // Konstruktor untuk membuat objek Ustadz baru (ID mungkin 0 atau null jika belum di-insert)
    // Tambahkan userId ke konstruktor ini
    public Ustadz(Integer userId, String nama, Date tanggal_lahir, String alamat, String nomor_telepon, Date tanggal_bergabung, String status) {
        this.userId = userId; // <--- BARU
        this.nama = nama;
        this.tanggal_lahir = tanggal_lahir;
        this.alamat = alamat;
        this.nomor_telepon = nomor_telepon;
        this.tanggal_bergabung = tanggal_bergabung;
        this.status = status;
    }

    // Konstruktor lengkap, biasanya digunakan setelah mengambil data dari database
    // Tambahkan userId ke konstruktor ini
    public Ustadz(Integer id, Integer userId, String nama, Date tanggal_lahir, String alamat, String nomor_telepon, Date tanggal_bergabung, String status) {
        this.id = id;
        this.userId = userId; // <--- BARU
        this.nama = nama;
        this.tanggal_lahir = tanggal_lahir;
        this.alamat = alamat;
        this.nomor_telepon = nomor_telepon;
        this.tanggal_bergabung = tanggal_bergabung;
        this.status = status;
    }

    // ... (getter dan setter yang sudah ada) ...

    /**
     * @return the userId
     */
    public Integer getUserId() { // <--- BARU: Getter untuk userId
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) { // <--- BARU: Setter untuk userId
        this.userId = userId;
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
     * @return the tanggal_bergabung
     */
    public Date getTanggal_bergabung() {
        return tanggal_bergabung;
    }

    /**
     * @param tanggal_bergabung the tanggal_bergabung to set
     */
    public void setTanggal_bergabung(Date tanggal_bergabung) {
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