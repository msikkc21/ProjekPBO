/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Model;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author ASUS
 */
public class TabelModelUstadz extends AbstractTableModel{
    List<Ustadz> lu;
    
    public TabelModelUstadz(List<Ustadz> lu){
        this.lu = lu;
    }
    
    @Override
    public int getColumnCount(){
        return 7;
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
                return "Nama Ustadz";
            case 2:
                return "Tanggal Lahir";
            case 3:
                return "Alamat";
            case 4:
                return "Nomor Telepon";
            case 5:
                return "Tanggal Bergabung";
            case 6:
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
                return lu.get(row).getNama();
            case 2:
                return lu.get(row).getTanggal_Lahir();
            case 3:
                return lu.get(row).getAlamat();
            case 4:
                return lu.get(row).getNomor_Telepon();
            case 5:
                return lu.get(row).getTanggal_Bergabung();
            case 6:
                return lu.get(row).getStatus();
            default:
                return null;
        }
    }
       
}
