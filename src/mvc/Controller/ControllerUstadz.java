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
import mvc.View.DasboardUstadz;
import mvc.View.EditDashboardUstadz;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Date; // Penting untuk Date object
import java.text.SimpleDateFormat; // Untuk memformat dan mengurai tanggal

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

            Ustadz u = formUstadzView.getUstadzDataFromForm();
            if (u != null) {
                implUstadz.insert(u); // Setelah insert, objek 'u' akan memiliki ID yang baru dihasilkan dari database
                JOptionPane.showMessageDialog(formUstadzView, "Data Ustadz berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                formUstadzView.clearForm();
                formUstadzView.dispose(); // Tutup form registrasi

                // Buka DasboardUstadz dengan ID ustadz yang baru saja di-insert
                if (u.getId() != null) {
                    DasboardUstadz newDashboard = new DasboardUstadz(u.getId());
                    newDashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mendapatkan ID Ustadz yang baru.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            System.out.println("FormUstadz view tidak diatur untuk insert.");
        }
    }

    // --- Metode untuk DasboardUstadz (Menampilkan data ustadz yang login) ---
    public void displayLoggedInUstadzData(int ustadzId) {
        if (dashboardUstadzView != null) {
            Ustadz ustadz = implUstadz.getById(ustadzId); // Mengambil satu Ustadz berdasarkan ID
            dashboardUstadzView.displayUstadz(ustadz); // Memanggil metode di view untuk menampilkan
            if (ustadz == null) {
                JOptionPane.showMessageDialog(dashboardUstadzView, "Data Ustadz dengan ID " + ustadzId + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("DasboardUstadz view tidak diatur untuk display.");
        }
    }

    // --- Metode untuk EditDashboardUstadz (Mengedit data ustadz) ---
    public void prepareEditForm(int ustadzId) {
        Ustadz ustadz = implUstadz.getById(ustadzId); // Ambil data dari DAO
        if (ustadz != null) {
            EditDashboardUstadz editForm = new EditDashboardUstadz(ustadzId); // Buat instance form edit
            editForm.setUstadzDataToForm(ustadz); // <-- Ini yang mengisi data ke form
            editForm.setVisible(true); // Tampilkan form
            // ... (opsional: tutup dashboard)
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

            Ustadz u = editUstadzView.getUstadzDataFromForm(); // Mengambil data dari form
            if (u != null) { // Pastikan tidak ada error parsing tanggal
                implUstadz.update(u);
                JOptionPane.showMessageDialog(editUstadzView, "Data Ustadz berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                editUstadzView.dispose(); // Tutup form edit setelah update

                // Buka kembali dashboard dengan data yang diperbarui
                // u.getId() harusnya sudah terisi karena ini operasi update
                if (u.getId() != null) {
                    DasboardUstadz newDashboard = new DasboardUstadz(u.getId());
                    newDashboard.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mengembalikan ke Dashboard. ID Ustadz tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
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
        // new LoginForm().setVisible(true);
    }

    // Metode untuk mengisi tabel (jika ada tabel di dashboard lain atau admin)
    public void fillTableAllUstadz() {
        if (dashboardUstadzView != null) { // Atau view lain yang punya tabel
            listUstadz = implUstadz.getAll();
            TabelModelUstadz tmu = new TabelModelUstadz(listUstadz);
            // dashboardUstadzView.getTableData().setModel(tmu); // Anda perlu getter getTableData di DasboardUstadz
        }
    }

    // Metode untuk mengisi field di form edit/insert (jika ada tabel dan ingin mengedit dari pemilihan baris)
    // Sepertinya metode ini lebih cocok di `prepareEditForm` atau `setUstadzDataToForm` di View.
    // Dihapus karena sudah diganti dengan `prepareEditForm` dan `setUstadzDataToForm`.

    // Metode insert (sudah di atas)
    // Metode update (sudah di atas)

    public void deleteUstadz(int id) { // ID diterima sebagai parameter
        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            implUstadz.delete(id);
            JOptionPane.showMessageDialog(null, "Data Ustadz berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            // Refresh tabel atau dashboard setelah penghapusan
            // fillTableAllUstadz();
        }
    }

    public void searchUstadzByName(String nama) {
        listUstadz = implUstadz.getCariNama(nama);
        TabelModelUstadz tmu = new TabelModelUstadz(listUstadz);
        // Jika ada tabel di dashboard/view admin
        // dashboardUstadzView.getTableData().setModel(tmu);
        if (listUstadz.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada ustadz dengan nama '" + nama + "' ditemukan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}