/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Controller;
import mvc.DAO.SantriDAO;
import mvc.DAOInterface.ISantri;
import mvc.Model.Santri;
import mvc.Model.TableModelSantri;
import mvc.View.FormDashSantri;
import mvc.View.FormInputSantri;
import mvc.View.FormEditSantri;
import java.util.List;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import mvc.View.Auth.FormLogin;
import mvc.Model.Setoran; // Import Setoran model
import mvc.Model.TabelModelSetoran; // Import TabelModelSetoran

/**
 *
 * @author User
 */
public class SantriController {
    FormInputSantri frameInputView;
    FormDashSantri frameView;
    FormEditSantri frameEditView;
    ISantri implSantri;
    List<Santri> listSantri;
    private int loggedInUserId; // Ubah nama variabel untuk lebih jelas bahwa ini adalah ID dari tabel 'user'

    // Konstruktor untuk FormInputSantri (saat registrasi)
    public SantriController(FormInputSantri frameInputView) { // Hapus parameter frameView dan frameEditView yang tidak digunakan di konstruktor ini
        this.frameInputView = frameInputView;
        this.frameView = null; // Set null karena tidak digunakan
        this.frameEditView = null; // Set null karena tidak digunakan
        implSantri = new SantriDAO();
    }

    // Konstruktor untuk FormDashSantri (dashboard santri)
    public SantriController(FormDashSantri frameView, int loggedInUserId) {
        this.frameView = frameView;
        this.loggedInUserId = loggedInUserId;
        this.frameInputView = null; // Set null
        this.frameEditView = null; // Set null
        implSantri = new SantriDAO();
    }

    // Konstruktor untuk FormEditSantri (saat edit profil santri)
    public SantriController(FormEditSantri frameEditView) { // Hanya perlu parameter frameEditView
        this.frameEditView = frameEditView;
        this.frameInputView = null;
        this.frameView = null;
        implSantri = new SantriDAO();
    }

    // --- Metode untuk FormInputSantri (Mengisi data setelah registrasi) ---
    public void insertSantriAndShowDashboard(){
        if (frameInputView != null) {
            if (frameInputView.getTxtNama().getText().trim().isEmpty() ||
                frameInputView.getTxtNomorTlp().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frameInputView, "Nama dan Nomor Telepon tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Santri s = frameInputView.getSantriDataFromForm();
            if(s != null){
                implSantri.insert(s);
                JOptionPane.showMessageDialog(frameInputView, "Data Santri berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                frameInputView.clearForm();
                frameInputView.dispose(); // Tutup form registrasi

                // Buka FormDashSantri dengan USER ID yang baru saja di-insert
                if(s.getUser_id() != null){
                    FormDashSantri newframeView = new FormDashSantri(s.getUser_id());
                    newframeView.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"Gagal mendapatkan User ID Santri yang baru.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            System.out.println("Frame FormInputSantri view tidak diatur untuk insert.");
        }
    }

    // --- Metode untuk DasboardSantri (Menampilkan data santri yang login) ---
    public void displayLoggedInSantriData(int userId) {
        System.out.println("DEBUG: displayLoggedInSantriData dipanggil untuk User ID: " + userId);
        if (frameView != null) {
            Santri santri = implSantri.getByUserId(userId);
            if (santri != null) {
                System.out.println("DEBUG: Santri ditemukan: " + santri.getNama_santri() + " (ID unik santri: " + santri.getId() + ")");
            } else {
                System.out.println("DEBUG: Santri TIDAK ditemukan untuk User ID: " + userId);
            }
            frameView.displaySantri(santri);
            if (santri == null) {
                JOptionPane.showMessageDialog(frameView, "Data Santri dengan User ID " + userId + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("DashboardSantri view tidak diatur untuk display.");
        }
    }

    // --- Metode untuk EditFormview (Mengedit data santri) ---
    public void prepareEditForm(int userId) { // Parameter adalah userId dari tabel user
        System.out.println("DEBUG: prepareEditForm dipanggil untuk User ID: " + userId);
        Santri santri = implSantri.getByUserId(userId); // Ambil data dari DAO berdasarkan user_id
        if (santri != null) {
            System.out.println("DEBUG: Data Santri berhasil ditemukan untuk pengeditan. Santri ID unik: " + santri.getId());
            // Kita perlu meneruskan ID unik santri ke FormEditSantri, bukan user_id.
            // FormEditSantri akan menggunakan ID unik santri untuk memuat data dan untuk operasi UPDATE.
            FormEditSantri editForm = new FormEditSantri(santri.getId()); // Kirim ID unik santri (primary key di tabel santri)
            editForm.setSantriDataToForm(santri); // Set data santri ke form edit
            editForm.setVisible(true);
            if (frameView != null) { // Tutup dashboard jika prepareEditForm dipanggil dari dashboard
                frameView.dispose();
            }
        } else {
            System.out.println("DEBUG: Data Santri TIDAK ditemukan untuk pengeditan untuk User ID: " + userId);
            JOptionPane.showMessageDialog(null, "Data Santri tidak ditemukan untuk pengeditan. Harap lengkapi data diri Anda terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateSantri() {
        if (frameEditView != null) {
            // Validasi sederhana
            if (frameEditView.getTxtNama().getText().trim().isEmpty() ||
                frameEditView.getTxtNomorTlp().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frameEditView, "Nama dan Nomor Telepon tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Santri s = frameEditView.getSantriDataFromForm(); // Mengambil data dari form
            if (s != null) { // Pastikan tidak ada error parsing tanggal
                implSantri.update(s);
                JOptionPane.showMessageDialog(frameEditView, "Data Santri berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                frameEditView.dispose(); // Tutup form edit setelah update

                // Buka kembali dashboard dengan user_id.
                // Objek 's' yang dikembalikan dari `getSantriDataFromForm()` di `FormEditSantri`
                // sekarang akan memiliki `user_id` karena kita menyimpannya di View.
                if (s.getUser_id() != null) { // Pastikan user_id tidak null
                    FormDashSantri newDashboard = new FormDashSantri(s.getUser_id()); // Kirim user_id
                    newDashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mengembalikan ke Dashboard. User ID Santri tidak ditemukan setelah update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            System.out.println("EditDashboardSantri view tidak diatur untuk update.");
        }
    }

    public void fillSetoranTableForLoggedInSantri() {
        System.out.println("DEBUG: Memanggil fillSetoranTableForLoggedInSantri(). loggedInUserId: " + loggedInUserId);
        if (frameView != null && loggedInUserId != 0) {
            Santri santri = implSantri.getByUserId(loggedInUserId); // Dapatkan objek Santri lengkap

            if (santri != null) {
                System.out.println("DEBUG: Santri ditemukan untuk setoran. Santri ID unik: " + santri.getId());
                if (santri.getId() != null) { // Pastikan ID unik santri tidak null
                    List<Setoran> listSetoran = implSantri.getAllSetoranBySantriId(santri.getId()); // Gunakan ID unik santri
                    System.out.println("DEBUG: Jumlah setoran yang ditemukan: " + listSetoran.size());

                    TabelModelSetoran model = new TabelModelSetoran(listSetoran);
                    frameView.getjTable1().setModel(model);
                } else {
                    System.out.println("DEBUG: Santri ditemukan, tapi ID unik santri adalah NULL saat mengisi tabel setoran.");
                    JOptionPane.showMessageDialog(frameView, "Data santri tidak lengkap. ID santri tidak ditemukan untuk mengisi tabel setoran.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("DEBUG: Objek Santri tidak ditemukan untuk loggedInUserId: " + loggedInUserId + " saat mengisi tabel setoran.");
                JOptionPane.showMessageDialog(frameView, "Data santri tidak ditemukan untuk menampilkan setoran.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("DEBUG: frameView null atau loggedInUserId 0 saat mengisi tabel setoran.");
        }
    }

    public void searchSetoranForLoggedInSantri() {
        System.out.println("DEBUG: Memanggil searchSetoranForLoggedInSantri(). loggedInUserId: " + loggedInUserId);
        if (frameView != null && loggedInUserId != 0) {
            String keyword = frameView.getTxtCari().getText();
            Santri santri = implSantri.getByUserId(loggedInUserId);

            if (santri != null) {
                System.out.println("DEBUG: Santri ditemukan untuk pencarian setoran. Santri ID unik: " + santri.getId());
                if (santri.getId() != null) {
                    List<Setoran> listSetoran = implSantri.getSetoranSantriByKeyword(santri.getId(), keyword);
                    System.out.println("DEBUG: Jumlah setoran yang ditemukan oleh pencarian: " + listSetoran.size());
                    TabelModelSetoran model = new TabelModelSetoran(listSetoran);
                    frameView.getjTable1().setModel(model);

                    if (listSetoran.isEmpty() && !keyword.isEmpty()) {
                        JOptionPane.showMessageDialog(frameView, "Tidak ada setoran yang cocok dengan kata kunci '" + keyword + "'.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    System.out.println("DEBUG: Santri ditemukan, tapi ID unik santri adalah NULL saat mencari setoran.");
                    JOptionPane.showMessageDialog(frameView, "Data santri tidak lengkap. ID santri tidak ditemukan untuk mencari setoran.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("DEBUG: Objek Santri tidak ditemukan untuk loggedInUserId: " + loggedInUserId + " saat mencari setoran.");
                JOptionPane.showMessageDialog(frameView, "Data santri tidak ditemukan untuk mencari setoran.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- Metode lain yang mungkin Anda perlukan ---

    // Metode untuk logout (contoh sederhana)
    public void logout() {
        JOptionPane.showMessageDialog(null, "Anda telah logout.", "Logout", JOptionPane.INFORMATION_MESSAGE);
        if (frameView != null) {
            frameView.dispose();
        }
        new FormLogin().setVisible(true);
    }

    // Metode untuk mengisi tabel (jika ada tabel di dashboard lain atau admin)
    public void fillTableAllSantri() {
        if (frameView != null) {
            listSantri = implSantri.getAll();
            TableModelSantri tmu = new TableModelSantri(listSantri);
        }
    }

    public void deleteSantri(int id) {
        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            implSantri.delete(id);
            JOptionPane.showMessageDialog(null, "Data Santri berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}