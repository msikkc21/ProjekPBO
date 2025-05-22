/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Controller;
import mvc.DAO.DAOUstadz;
import mvc.DAOInterface.IUstadz;
import mvc.Model.Ustadz;
import mvc.Model.TabelModelUstadz;
import mvc.View.FormUstadz;

import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author ASUS
 */
public class ControllerUstadz {
    FormUstadz frame;
    IUstadz implUstadz;
    List<Ustadz> lb;

    public ControllerUstadz(FormUstadz frame) {
        this.frame = frame;
        implUstadz = new DAOUstadz();
        lb = implUstadz.getAll();
    }

    public void reset() {
        frame.getTxtID().setText("");
        frame.getTxtNama().setText("");
        frame.getTxtTanggalLahir().setText("");
        frame.getTxtAlamat().setText("");
        frame.getTxtNoTelepon().setText("");
        frame.getTxtTanggalBergabung().setText("");
        frame.getTxtStatus().setSelectedItem("");
    }

    public void isiTable() {
        lb = implUstadz.getAll();
        TabelModelUstadz tmu = new TabelModelUstadz(lb);
        frame.getTableData().setModel(tmu);
    }

    public void isiField(int row) {
        frame.getTxtID().setText(lb.get(row).getId().toString());
        frame.getTxtNama().setText(lb.get(row).getNama());
        frame.getTxtTanggalLahir().setText(lb.get(row).getTanggal_Lahir().toString());
        frame.getTxtAlamat().setText(lb.get(row).getAlamat());
        frame.getTxtNoTelepon().setText(lb.get(row).getNomor_Telepon());
        frame.getTxtTanggalBergabung().setText(lb.get(row).getTanggal_Bergabung().toString());
        frame.getTxtStatus().setSelectedItem(lb.get(row).getStatus());
    }

    public void insert() {
        if (!frame.getTxtNama().getText().trim().isEmpty() && !frame.getTxtNoTelepon().getText().trim().isEmpty()) {
            Ustadz u = new Ustadz();
            u.setNamaUstadz(frame.getTxtNama().getText());
            u.setTanggalLahir(java.sql.Date.valueOf(frame.getTxtTanggalLahir().getText()));
            u.setAlamat(frame.getTxtAlamat().getText());
            u.setNomorTelepon(frame.getTxtNoTelepon().getText());
            u.setTanggalBergabung(java.sql.Date.valueOf(frame.getTxtTanggalBergabung().getText()));
            u.setStatus(frame.getTxtStatus().getSelectedItem().toString());
            implUstadz.insert(u);
            JOptionPane.showMessageDialog(null, "Simpan Data Sukses");
        } else {
            JOptionPane.showMessageDialog(frame, "Data Tidak Boleh Kosong");
        }
    }

    public void update() {
        if (!frame.getTxtID().getText().trim().isEmpty() &&
            !frame.getTxtNama().getText().trim().isEmpty() &&
            !frame.getTxtNoTelepon().getText().trim().isEmpty()) {
            Ustadz u = new Ustadz();
            u.setId(Integer.parseInt(frame.getTxtID().getText()));
            u.setNamaUstadz(frame.getTxtNama().getText());
            u.setTanggalLahir(java.sql.Date.valueOf(frame.getTxtTanggalLahir().getText()));
            u.setAlamat(frame.getTxtAlamat().getText());
            u.setNomorTelepon(frame.getTxtNoTelepon().getText());
            u.setTanggalBergabung(java.sql.Date.valueOf(frame.getTxtTanggalBergabung().getText()));
            u.setStatus(frame.getTxtStatus().getSelectedItem().toString());
            implUstadz.update(u);
            JOptionPane.showMessageDialog(null, "Update Data Sukses");
        } else {
            JOptionPane.showMessageDialog(frame, "Data Tidak Boleh Kosong");
        }
    }

    public void delete() {
        if (!frame.getTxtID().getText().trim().isEmpty()) {
            int id = Integer.parseInt(frame.getTxtID().getText());
            implUstadz.delete(id);
            JOptionPane.showMessageDialog(null, "Hapus Data Sukses");
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih Data yang akan dihapus");
        }
    }

    public void isiTableCariNama() {
        lb = implUstadz.getCariNama(frame.getTxtCariNama().getText());
        TableModelUstadz tmu = new TableModelUstadz(lb);
        frame.getTableData().setModel(tmu);
    }

    public void carinama() {
        if (!frame.getTxtCariNama().getText().trim().isEmpty()) {
            implUstadz.getCariNama(frame.getTxtCariNama().getText());
            isiTableCariNama();
        } else {
            JOptionPane.showMessageDialog(frame, "Silakan masukkan nama yang ingin dicari");
        }
    }
}
