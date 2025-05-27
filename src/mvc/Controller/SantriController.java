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
import java.util.Date; // Penting untuk Date object
import java.text.SimpleDateFormat; // Untuk memformat dan mengurai tanggal
import mvc.View.Auth.FormLogin;

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
    
    
    public SantriController(FormDashSantri frameView, FormInputSantri frameInputView, FormEditSantri frameEditView){
        this.frameView = frameView;
        this.frameInputView = frameInputView;
        this.frameEditView = frameEditView;
        implSantri = new SantriDAO();
        // listSantri = implSantri.getAll(); // Hanya ambil data jika diperlukan di awal
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
                frameInputView.dispose();// Tutup form registrasi
                
                // Buka DasboardUstadz dengan ID ustadz yang baru saja di-insert
                if(s.getId() !=null){
                    FormDashSantri newframeView = new FormDashSantri(s.getUser_id());
                    newframeView.setVisible(true);       
                }else{
                    JOptionPane.showMessageDialog(null,"Gagal mendapatkan ID Santri yang baru.", "Error", JOptionPane.ERROR_MESSAGE);                  
                }
            }
        } else {
            System.out.println("Frame Santri view tidak diatur untuk insert.");
        }
    }
    
    // --- Metode untuk DasboardSantri (Menampilkan data santri yang login) ---
    public void displayLoggedInSantriData(int userId) {
        if (frameView != null) {
            Santri santri = implSantri.getByUserId(userId); // Mengambil satu Santri berdasarkan ID
            frameView.displaySantri(santri); // Memanggil metode di view untuk menampilkan
            if (santri == null) {
                JOptionPane.showMessageDialog(frameView, "Data Santri dengan ID " + userId + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("DashboardSantri view tidak diatur untuk display.");
        }
    }

    // --- Metode untuk EditFormview (Mengedit data santri) ---
    public void prepareEditForm(int userId) {
        Santri santri = implSantri.getByUserId(userId); // Ambil data dari DAO
        if (santri != null) {
            FormEditSantri editForm = new FormEditSantri(userId); // Buat instance form edit
            editForm.setSantriDataToForm(santri); // <-- Ini yang mengisi data ke form
            editForm.setVisible(true); // Tampilkan form
            // ... (opsional: tutup dashboard)
        } else {
            JOptionPane.showMessageDialog(null, "Data Santri tidak ditemukan untuk pengeditan.", "Error", JOptionPane.ERROR_MESSAGE);
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

                // Buka kembali dashboard dengan data yang diperbarui
                // u.getId() harusnya sudah terisi karena ini operasi update
                if (s.getId() != null) {
                    FormDashSantri newDashboard = new FormDashSantri(s.getId());
                    newDashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mengembalikan ke Dashboard. ID Sanntri tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            System.out.println("EditDashboardUstadz view tidak diatur untuk update.");
        }
    }

    // --- Metode lain yang mungkin Anda perlukan ---

    // Metode untuk logout (contoh sederhana)
    public void logout() {
        // Lakukan logika logout di sini, seperti menghapus sesi atau mengarahkan ke halaman login
        JOptionPane.showMessageDialog(null, "Anda telah logout.", "Logout", JOptionPane.INFORMATION_MESSAGE);
        if (frameView != null) {
            frameView.dispose(); // Tutup dashboard
        }
        // Redirect ke halaman login (misalnya)
        new FormLogin().setVisible(true);
    }

    // Metode untuk mengisi tabel (jika ada tabel di dashboard lain atau admin)
    public void fillTableAllSantri() {
        if (frameView != null) { // Atau view lain yang punya tabel
            listSantri = implSantri.getAll();
            TableModelSantri tmu = new TableModelSantri(listSantri);
            // dashboardUstadzView.getTableData().setModel(tmu); // Anda perlu getter getTableData di DasboardUstadz
        }
    }

    // Metode untuk mengisi field di form edit/insert (jika ada tabel dan ingin mengedit dari pemilihan baris)
    // Sepertinya metode ini lebih cocok di `prepareEditForm` atau `setSantriDataToForm` di View.
    // Dihapus karena sudah diganti dengan `prepareEditForm` dan `setSantriDataToForm`.

    // Metode insert (sudah di atas)
    // Metode update (sudah di atas)

    public void deleteSantri(int id) { // ID diterima sebagai parameter
        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            implSantri.delete(id);
            JOptionPane.showMessageDialog(null, "Data Santri berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            // Refresh tabel atau dashboard setelah penghapusan
            // fillTableAllUstadz();
        }
    }

//    public void searchSantriBySetoran(String setoran) {
//        List<Setoran> listSetoran = implSantri.getAllSetoran(); // Panggil metode yang benar
    
    // Anda kemungkinan besar memerlukan TableModel baru untuk Setoran
    // Misalnya: TableModelSetoran tms = new TableModelSetoran(listSetoran);
    // Lalu, Anda akan mengatur model ini ke tabel tampilan setoran Anda.
    // Misalnya: dashboardView.getTableSetoran().setModel(tms);

    // Karena Anda tidak ingin menggunakan "getCari" atau "searchSantriBySomething"
    // maka bagian kode yang sebelumnya berhubungan dengan TableModelSantri
    // kemungkinan besar tidak relevan lagi di sini.
    // listSantri = implSantri.getCari(); // Hapus baris ini
    // TableModelSantri tmu = new TableModelSantri(listSantri); // Hapus baris ini
}
