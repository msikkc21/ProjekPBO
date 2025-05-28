/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Controller;
import java.awt.HeadlessException;
import java.time.LocalDate;
import mvc.DAO.DAOSetoran;
import mvc.DAOInterface.ISetoran;
import mvc.Model.Setoran;
import mvc.Model.TabelModelSetoran;
import mvc.View.FormSetoran;
import java.util.List;
import javax.swing.JOptionPane;
import mvc.View.FormSetoran;

import javax.swing.JOptionPane;
import mvc.DAO.SantriDAO;
import mvc.DAO.DAOUstadz;
import mvc.DAOInterface.ISantri;
import mvc.DAOInterface.IUstadz;
import mvc.Model.Santri;

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
    
    public ControllerSetoran(FormSetoran formsetoran){
        this.formsetoran = formsetoran;
        implSetoran = new DAOSetoran();
        implSantri = new SantriDAO(); // Inisialisasi
        implUstadz = new DAOUstadz(); // Inisialisasi
        list = implSetoran.getAll();
        isiTable(); // Panggil ini setelah inisialisasi
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
             s.setSantriid((santri) formsetoran.getSantriText().getSelectedItem());
             s.setTanggal(LocalDate.parse(formsetoran.getTanggalText().getText()));
             s.setWaktu((String) formsetoran.getWaktuText().getSelectedItem()); // String
             s.setJuz(Integer.valueOf(formsetoran.getJuzText().getText()));
             s.setHalaman(Integer.valueOf(formsetoran.getHalamanText().getText())); // int
             s.setKeterangan(formsetoran.getKeteranganText().getText()); // String
             s.setNilai((String) formsetoran.getNilaiText().getSelectedItem()); // String
            
            

            implSetoran.insert(s);
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
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
             s.setSantriid((santri) formsetoran.getSantriText().getSelectedItem());
             s.setTanggal(LocalDate.parse(formsetoran.getTanggalText().getText()));
             s.setWaktu((String) formsetoran.getWaktuText().getSelectedItem()); // String
             s.setJuz(Integer.valueOf(formsetoran.getJuzText().getText()));
             s.setHalaman(Integer.valueOf(formsetoran.getHalamanText().getText())); // int
             s.setKeterangan(formsetoran.getKeteranganText().getText()); // String
             s.setNilai((String) formsetoran.getNilaiText().getSelectedItem()); // String
            

            implSetoran.update(s);
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin diubah");
        }
    }
        
    // Menghapus data
     // Cari berdasarkan santri
    public void cariSantri() {
        int idSantri = formsetoran.getSantriText().getSelectedIndex();
        list = implSetoran.getCariSantri(idSantri);
        TabelModelSetoran model = new TabelModelSetoran(list);
        formsetoran.getTabelData().setModel(model);
    }

    // Reset input
    public void reset() {
        formsetoran.getTanggalText().setText("");
        formsetoran.getWaktuText().setSelectedIndex(0);
        formsetoran.getJuzText().setText("");
        formsetoran.getHalamanText().setText("");
        formsetoran.getKeteranganText().setText("");
        formsetoran.getNilaiText().setSelectedIndex(0);
    }
}
