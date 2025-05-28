/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Controller;
import java.awt.HeadlessException;
import mvc.DAO.DAOSetoran;
import mvc.DAOInterface.ISetoran;
import mvc.Model.Setoran;
import mvc.Model.TabelModelSetoran;
import mvc.View.FormSetoran;
import java.util.List;
import javax.swing.JOptionPane;
import mvc.DAO.SantriDAO;
import mvc.DAO.DAOUstadz;
import mvc.DAOInterface.ISantri;
import mvc.DAOInterface.IUstadz;
import mvc.Model.Santri;
import mvc.Model.Ustadz; // Import the Ustadz model
import java.text.SimpleDateFormat; // Import SimpleDateFormat
import java.text.ParseException; // Import ParseException
import java.util.Date; // Import java.util.Date

/**
 *
 * @author Acer
 */
public class ControllerSetoran {
    private ISantri implSantri;
    private IUstadz implUstadz;
    FormSetoran formsetoran;
    ISetoran implSetoran;
    List<Setoran>list;
    private Ustadz loggedInUstadz;

    public ControllerSetoran(FormSetoran formsetoran){
        this.formsetoran = formsetoran;
        implSetoran = new DAOSetoran();
        implSantri = new SantriDAO();
        implUstadz = new DAOUstadz(); // Inisialisasi
        // Dapatkan objek Ustadz yang sedang login dari formsetoran (jika ada)
        // Ini diasumsikan formsetoran akan memberikan ID Ustadz yang login
        // Jika tidak, Anda perlu cara untuk mendapatkan ID ustadz yang login di sini
        // Misalnya, dari konstruktor FormSetoran atau global state.
        // Untuk saat ini, kita asumsikan ustadzId sudah disetel di FormSetoran
        int ustadzIdFromView = formsetoran.getUstadzId(); // Asumsikan ada getter ini di FormSetoran
        this.loggedInUstadz = implUstadz.getById(ustadzIdFromView); // Dapatkan objek Ustadz lengkap

        list = implSetoran.getAll(); // Panggil ini setelah inisialisasi
        isiTable();
        isiComboBoxSantri(); // Panggil metode ini saat inisialisasi
    }

    public void isiComboBoxSantri() {
        List<Santri> santriList = implSantri.getAll(); // Ambil semua santri
        formsetoran.getSantriText().removeAllItems(); // Bersihkan item lama
        for (Santri s : santriList) {
            formsetoran.getSantriText().addItem(s); // Tambahkan objek Santri ke combobox
        }
    }

    public void insert() {
        try {
            Setoran s = new Setoran();
            s.setSantriid((mvc.Model.Santri) formsetoran.getSantriText().getSelectedItem());
            if (loggedInUstadz != null) {
                s.setUstadzid(loggedInUstadz);
            } else {
                JOptionPane.showMessageDialog(null, "Error: Data Ustadz tidak ditemukan. Gagal menyimpan setoran.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // UBAH FORMAT DI SINI
            Date tglSetoran = sdf.parse(formsetoran.getTanggalText().getText());
            s.setTanggal(tglSetoran);

            s.setWaktu((String) formsetoran.getWaktuText().getSelectedItem());
            s.setJuz(Integer.valueOf(formsetoran.getJuzText().getText()));
            s.setHalaman(Integer.valueOf(formsetoran.getHalamanText().getText()));
            s.setKeterangan(formsetoran.getKeteranganText().getText());
            s.setNilai((String) formsetoran.getNilaiText().getSelectedItem());

            implSetoran.insert(s);
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            isiTable();
            reset();
        } catch (HeadlessException | NumberFormatException | ParseException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: Pastikan format tanggal benar (DD-MM-YYYY) dan semua data diisi. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menampilkan data ke tabel
    public void isiTable() {
        list = implSetoran.getAll();
        TabelModelSetoran model = new TabelModelSetoran(list);
        formsetoran.getTabelData().setModel(model);
    }

    // Menghapus data
    public void delete() {
        int row = formsetoran.getTabelData().getSelectedRow();
        if (row != -1) {
            int id = list.get(row).getId();
            implSetoran.delete(id);
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            isiTable(); // Refresh tabel
            reset(); // Reset form
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus");
        }
    }

    // Mengubah data
    public void update() {
        int row = formsetoran.getTabelData().getSelectedRow();
        if (row != -1) {
            Setoran s = new Setoran();
            s.setId(list.get(row).getId());
            s.setSantriid((mvc.Model.Santri) formsetoran.getSantriText().getSelectedItem());
            if (loggedInUstadz != null) {
                s.setUstadzid(loggedInUstadz);
            } else {
                JOptionPane.showMessageDialog(null, "Error: Data Ustadz tidak ditemukan. Gagal memperbarui setoran.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // UBAH FORMAT DI SINI
            Date tglSetoran;
            try {
                tglSetoran = sdf.parse(formsetoran.getTanggalText().getText());
                s.setTanggal(tglSetoran);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Gagal memperbarui data: Format tanggal salah. Gunakan DD-MM-YYYY. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            s.setWaktu((String) formsetoran.getWaktuText().getSelectedItem());
            s.setJuz(Integer.valueOf(formsetoran.getJuzText().getText()));
            s.setHalaman(Integer.valueOf(formsetoran.getHalamanText().getText()));
            s.setKeterangan(formsetoran.getKeteranganText().getText());
            s.setNilai((String) formsetoran.getNilaiText().getSelectedItem());

            implSetoran.update(s);
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            isiTable();
            reset();
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin diubah");
        }
    }
    
    public void selectRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= list.size()) {
            return; // Indeks tidak valid
        }

        Setoran selectedSetoran = list.get(rowIndex);

        // Mengisi field tanggal
        if (selectedSetoran.getTanggal() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            formsetoran.getTanggalText().setText(sdf.format(selectedSetoran.getTanggal()));
        } else {
            formsetoran.getTanggalText().setText("");
        }

        // Mengisi ComboBox santri
        // Perlu mencari Santri yang cocok di ComboBox berdasarkan ID santri setoran
        for (int i = 0; i < formsetoran.getSantriText().getItemCount(); i++) {
            Santri itemSantri = formsetoran.getSantriText().getItemAt(i);
            if (itemSantri != null && selectedSetoran.getSantriid() != null &&
                itemSantri.getId().equals(selectedSetoran.getSantriid().getId())) {
                formsetoran.getSantriText().setSelectedIndex(i);
                break;
            }
        }
        // Pastikan Santri.toString() sudah di-override agar ComboBox menampilkan nama santri

        // Mengisi ComboBox waktu
        formsetoran.getWaktuText().setSelectedItem(selectedSetoran.getWaktu());

        // Mengisi field juz
        formsetoran.getJuzText().setText(String.valueOf(selectedSetoran.getJuz()));

        // Mengisi field halaman
        formsetoran.getHalamanText().setText(String.valueOf(selectedSetoran.getHalaman()));

        // Mengisi field keterangan
        formsetoran.getKeteranganText().setText(selectedSetoran.getKeterangan());

        // Mengisi ComboBox nilai
        formsetoran.getNilaiText().setSelectedItem(selectedSetoran.getNilai());
    }
    
    public void kembaliToDashboard() {
        if (loggedInUstadz != null && loggedInUstadz.getUserId() != null) {
            formsetoran.dispose(); // Tutup form setoran
            // Buka kembali dashboard Ustadz dengan ID user yang benar
            new mvc.View.DasboardUstadz(loggedInUstadz.getUserId()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(formsetoran, "Gagal kembali ke Dashboard. User ID Ustadz tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            // Opsional: Redirect ke halaman login jika user ID tidak ditemukan
            // new mvc.View.Auth.FormLogin().setVisible(true);
        }
    }

    // Cari berdasarkan santri
    public void cariSantri() {
        Santri selectedSantri = (mvc.Model.Santri) formsetoran.getSantriText().getSelectedItem();
        if (selectedSantri != null) {
            int idSantri = selectedSantri.getId();
            list = implSetoran.getCariSantri(idSantri);
            TabelModelSetoran model = new TabelModelSetoran(list);
            formsetoran.getTabelData().setModel(model);
        } else {
            JOptionPane.showMessageDialog(null, "Pilih Santri terlebih dahulu!");
        }
    }

    // Reset input
    public void reset() {
        formsetoran.getSantriText().setSelectedIndex(0); // Reset combobox santri
        formsetoran.getTanggalText().setText("");
        formsetoran.getWaktuText().setSelectedIndex(0);
        formsetoran.getJuzText().setText("");
        formsetoran.getHalamanText().setText("");
        formsetoran.getKeteranganText().setText("");
        formsetoran.getNilaiText().setSelectedIndex(0);
    }
}
