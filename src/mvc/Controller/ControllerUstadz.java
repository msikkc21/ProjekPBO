/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Controller;
import mvc.DAO.DAOUstadz;
import mvc.DAOInterface.IUstadz;
import mvc.Model.Ustadz;
import mvc.Model.TabelModelUstadz; // Pastikan ini TabelModelUstadz, bukan TableModelUstadz
import mvc.View.FormUstadz;
import mvc.View.Auth.FormLogin;
import mvc.View.DasboardUstadz;
import mvc.View.EditDashboardUstadz;
import mvc.View.Auth.FormLogin;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Date; // Penting untuk Date object
import java.text.SimpleDateFormat; // Untuk memformat dan mengurai tanggal
import mvc.View.FormSetoran;

/**
 *
 * @author ASUS
 */
public class ControllerUstadz {
    FormUstadz formUstadzView;
    DasboardUstadz dashboardUstadzView;
    EditDashboardUstadz editUstadzView;
    IUstadz implUstadz;
    List<Ustadz> listUstadz; // Mengganti lb menjadi listUstadz

    // Konstruktor disesuaikan untuk menerima view yang berbeda
    public ControllerUstadz(FormUstadz formUstadzView, DasboardUstadz dashboardUstadzView, EditDashboardUstadz editUstadzView) {
        this.formUstadzView = formUstadzView;
        this.dashboardUstadzView = dashboardUstadzView;
        this.editUstadzView = editUstadzView;
        implUstadz = new DAOUstadz();
        // listUstadz = implUstadz.getAll(); // Hanya ambil data jika diperlukan di awal
    }

    // --- Metode untuk FormUstadz (Mengisi data setelah registrasi) ---
    public void insertUstadzAndShowDashboard() {
        if (formUstadzView != null) {
            if (formUstadzView.getTxtNama().getText().trim().isEmpty() ||
                formUstadzView.getTxtNomorTelepon().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(formUstadzView, "Nama dan Nomor Telepon tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Ustadz u = formUstadzView.getUstadzDataFromForm(); // Objek 'u' sudah memiliki userId dari form
            if (u != null) {
                implUstadz.insert(u); // Simpan objek Ustadz, ID ustadz akan di-generate otomatis oleh DB. user_id sudah diset.
                JOptionPane.showMessageDialog(formUstadzView, "Data Ustadz berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                formUstadzView.clearForm();
                formUstadzView.dispose();

                // Buka DasboardUstadz dengan USER ID yang baru saja di-insert
                // Penting: Di sini kita meneruskan userId (dari tabel user)
                if (u.getUserId() != null) { // Gunakan getUserId() karena itu yang kita pakai untuk mencari data ustadz
                    DasboardUstadz newDashboard = new DasboardUstadz(u.getUserId()); // <--- Kirim USER ID ke Dashboard
                    newDashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(formUstadzView, "Gagal mendapatkan ID User Ustadz yang baru.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            System.out.println("FormUstadz view tidak diatur untuk insert.");
        }
    }

    // --- Metode untuk DasboardUstadz (Menampilkan data ustadz yang login) ---
    public void displayLoggedInUstadzData(int userId) {
        if (dashboardUstadzView != null) {
            Ustadz ustadz = implUstadz.getByUserId(userId); // Mengambil Ustadz berdasarkan user_id
            dashboardUstadzView.displayUstadz(ustadz);
            if (ustadz == null) {
                JOptionPane.showMessageDialog(dashboardUstadzView, "Data Ustadz dengan User ID " + userId + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("DasboardUstadz view tidak diatur untuk display.");
        }
    }
    
    public void fillEditForm(int ustadzId) { // Menerima ID unik Ustadz dari EditDashboardUstadz
        Ustadz ustadz = implUstadz.getById(ustadzId); // Ambil data ustadz berdasarkan ID uniknya
        if (editUstadzView != null) {
            editUstadzView.setUstadzDataToForm(ustadz);
        } else {
            JOptionPane.showMessageDialog(null, "View EditDashboardUstadz tidak disetel untuk mengisi form.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Metode untuk EditDashboardUstadz (Mengedit data ustadz) ---
    public void prepareEditForm(int userId) { // Parameter adalah userId
        Ustadz ustadz = implUstadz.getByUserId(userId); // Ambil data dari DAO berdasarkan user_id
        if (ustadz != null) {
            // Kita perlu meneruskan ID unik ustadz ke form edit jika update membutuhkan ID ustadz itu sendiri
            EditDashboardUstadz editForm = new EditDashboardUstadz(ustadz.getId()); // Kirim ID unik ustadz
            editForm.setUstadzDataToForm(ustadz);
            editForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Data Ustadz tidak ditemukan untuk pengeditan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateUstadz() {
        if (editUstadzView != null) {
            // Validasi sederhana
            if (editUstadzView.getTxtNama().getText().trim().isEmpty() ||
                editUstadzView.getTxtNomorTelepon().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(editUstadzView, "Nama dan Nomor Telepon tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Ustadz u = editUstadzView.getUstadzDataFromForm(); // Objek 'u' sekarang akan memiliki userId yang terkait
            if (u != null) {
                implUstadz.update(u);
                JOptionPane.showMessageDialog(editUstadzView, "Data Ustadz berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                editUstadzView.dispose();

                // Buka kembali dashboard dengan USER ID yang benar
                if (u.getUserId() != null) { // Sekarang u.getUserId() seharusnya memiliki nilai yang benar
                    DasboardUstadz newDashboard = new DasboardUstadz(u.getUserId());
                    newDashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mengembalikan ke Dashboard. User ID Ustadz tidak ditemukan setelah update.", "Error", JOptionPane.ERROR_MESSAGE);
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
        if (dashboardUstadzView != null) {
            dashboardUstadzView.dispose(); // Tutup dashboard
        }
        // Redirect ke halaman login (misalnya)
        new FormLogin().setVisible(true);
    }
    
    public void showSetoran(int id) {
        Ustadz u = implUstadz.getByUserId(id);
        if (dashboardUstadzView != null) {
            dashboardUstadzView.dispose();
        }
        if (u != null) {
            new FormSetoran(u.getId()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Data Ustadz tidak ditemukan untuk membuka Form Setoran.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metode untuk mengisi tabel (jika ada tabel di dashboard lain atau admin)
    public void fillUstadzTable() {
        if (dashboardUstadzView != null) { // Atau view lain yang punya tabel
            listUstadz = implUstadz.getAll();
            TabelModelUstadz tmu = new TabelModelUstadz(listUstadz);
            dashboardUstadzView.getTblUstadz().setModel(tmu); // Anda perlu getter getTableData di DasboardUstadz
        }
    }

    // Metode untuk mengisi field di form edit/insert (jika ada tabel dan ingin mengedit dari pemilihan baris)
    // Sepertinya metode ini lebih cocok di `prepareEditForm` atau `setUstadzDataToForm` di View.
    // Dihapus karena sudah diganti dengan `prepareEditForm` dan `setUstadzDataToForm`.

    // Metode insert (sudah di atas)
    // Metode update (sudah di atas)

    public void deleteUstadz(int ustadzId) { // Parameter adalah ID unik ustadz
        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            implUstadz.delete(ustadzId); // Hapus berdasarkan ID unik ustadz
            JOptionPane.showMessageDialog(null, "Data Ustadz berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dashboardUstadzView.dispose();
            new FormLogin().setVisible(true);
        }
    }

    public void CariNama(String nama) {
        listUstadz = implUstadz.getCariNama(nama);
        TabelModelUstadz tmu = new TabelModelUstadz(listUstadz);
        // Jika ada tabel di dashboard/view admin
        dashboardUstadzView.getTblUstadz().setModel(tmu);
        if (listUstadz.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada ustadz dengan nama '" + nama + "' ditemukan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}