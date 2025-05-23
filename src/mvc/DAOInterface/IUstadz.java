/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.DAOInterface;
import mvc.Model.Ustadz;
import java.util.List;
/**
 *
 * @author ASUS
 */
public interface IUstadz {
    public void insert(Ustadz u);
    public void update(Ustadz u);
    public void delete(int id);
    public Ustadz getById(int id);
    public List<Ustadz> getAll();
    public List<Ustadz> getCariNama(String nama);
}
