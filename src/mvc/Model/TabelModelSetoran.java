/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Model;
import java.util.List;
import javax.swing.table.AbstractTableModel;
//2. membuat tabelmodelsetoran untuk membuat tabelnya nanti
/**
 *
 * @author Acer
 */
public class TabelModelSetoran extends AbstractTableModel{
    List<Setoran>lb;
    
    public TabelModelSetoran(List<Setoran>lb){
        this.lb=lb;
    }
    @Override
    public int getRowCount() {
        return lb.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }
//      private Integer id;
//    private Santri santriid;
//    private Ustadz ustadzid;
//    private LocalDate tanggal;
//    private String waktu;
//    private Integer juz;
//    private Integer halaman;
//    private String keterangan;
//    private String nilai;
    
    public String getColumnName (int column){
        switch (column){
            case 0:
                  return "ID";
              case 1:
                  return "ID Santri";
              case 2:
                  return "ID Ustadz";
              case 3:
                  return "Tanggal";
              case 4:
                  return "Waktu";
              case 5:
                  return "Juz";
              case 6:
                  return "Halaman";
              case 7:
                  return "Keterangan";
              case 8:
                  return "Nilai";
              default:
                  return null;
              }
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column){
            case 0:
                return lb.get(row).getId();
            case 1:
                return lb.get(row).getSantriid();
            case 2:
                return lb.get(row).getUstadzid();
            case 3:
                return lb.get(row).getTanggal();
            case 4:
                return lb.get(row).getWaktu();
            case 5:
                return lb.get(row).getJuz();
            case 6:
                return lb.get(row).getHalaman();
            case 7:
                return lb.get(row).getKeterangan();
            case 8:
                return lb.get(row).getNilai();
            default:
                return null;
                
       
        }
            
    }
    
    
}
