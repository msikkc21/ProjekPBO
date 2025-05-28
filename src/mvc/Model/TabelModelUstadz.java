/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Model;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author ASUS
 */
public class TabelModelUstadz extends AbstractTableModel{
    List<Ustadz> lu;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public TabelModelUstadz(List<Ustadz> lu){
        this.lu = lu;
    }

    @Override
    public int getColumnCount(){
        return 8; // Perbaikan: Ubah dari 7 menjadi 8 karena ada kolom User_Id
    }

    @Override
    public int getRowCount() {
        return lu.size();
    }

    @Override
    public String getColumnName(int column){
        switch(column){
            case 0:
                return "ID";
            case 1:
                return "User_Id";
            case 2:
                return "Nama Ustadz";
            case 3:
                return "Tanggal Lahir";
            case 4:
                return "Alamat";
            case 5:
                return "Nomor Telepon";
            case 6:
                return "Tanggal Bergabung";
            case 7:
                return "Status";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return lu.get(row).getId();
            case 1:
                return lu.get(row).getUserId();
            case 2:
                return lu.get(row).getNama();
            case 3:
                // Format tanggal lahir
                return lu.get(row).getTanggal_lahir() != null ? dateFormat.format(lu.get(row).getTanggal_lahir()) : "";
            case 4:
                return lu.get(row).getAlamat();
            case 5:
                return lu.get(row).getNomor_telepon();
            case 6:
                // Format tanggal bergabung
                return lu.get(row).getTanggal_bergabung() != null ? dateFormat.format(lu.get(row).getTanggal_bergabung()) : "";
            case 7:
                return lu.get(row).getStatus();
            default:
                return null;
        }
    }
}