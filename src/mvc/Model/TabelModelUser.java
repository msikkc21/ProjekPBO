/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Model;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author izzaa
 */
public class TabelModelUser extends AbstractTableModel {
    List<User> lb;
    
    public TabelModelUser(List<User> lb){
        this.lb = lb;
    }
    
    public int getColumnCount(){
        return 4;
    }
    
    public int getRowCount(){
        return lb.size();
    }
    
    public String getColumnName(int column){
        switch(column){
            case 0:
                return "ID";
            case 1:
                return "Username";
            case 2:
                return "Password";
            case 3:
                return "role";
            default:
                return null;
        }
    }
    
    public Object getValueAt(int row, int column){
        switch(column){
            case 0:
                return lb.get(row).getId();
            case 1:
                return lb.get(row).getUsername();
            case 2:
                return lb.get(row).getPassword();
            case 3:
                return lb.get(row).getRole();
            default:
                return null;
        }
    }
}
