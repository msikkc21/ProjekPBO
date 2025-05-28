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
 * @author User
 */
public class TableModelSantri extends AbstractTableModel{
    List<Santri> lb;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public TableModelSantri(List<Santri> lb){
        this.lb =lb;
    } 
    
    public int getColumnCount(){
        return 8;
    }
    
    public int getRowCount(){
        return lb.size();
    }
    
    public String getColummName(int column){
        switch (column){
            case 0:
                return "ID";
            case 1:
                return "Nama Santri";
            case 2:
                return "Tanggal Lahir";
            case 3:
                return "Alamat";
            case 4:
                return "Nomor Telepon";
            case 5:
                return "Nama Wali";
            case 6:
                return "Tanggal Masuk";
            case 7:
                return "Status";
            default:
                return null;
        }
    }
    
    public Object getValueAt(int row, int column){
        switch (column){
            case 0:
                return lb.get(row).getId();
            case 1:
                return lb.get(row).getNama_santri();
            case 2:
                // Format tanggal lahir
                return lb.get(row).getTanggal_lahir() != null ? dateFormat.format(lb.get(row).getTanggal_lahir()) : "";
            case 3:
                return lb.get(row).getAlamat();
            case 4:
                return lb.get(row).getNomor_telepon();
            case 5:
                return lb.get(row).getNama_wali();
            case 6:
                // Format tanggal masuk
                return lb.get(row).getTanggal_masuk() != null ? dateFormat.format(lb.get(row).getTanggal_masuk()) : "";
            case 7:
                return lb.get(row).getStatus();
            default:
                return null;
        }
    }
}
