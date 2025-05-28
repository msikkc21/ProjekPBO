/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.DAOInterface;

import java.util.List;
import mvc.Model.Santri;
import mvc.Model.Setoran;

/**
 *
 * @author Acer
 */
public interface ISetoran {
    public void insert (Setoran a);
    public void update(Setoran a);
    public void delete(int id);
    public List<Setoran>getAll();
    public Santri getCariSantri(int id_santri);
    public void GetAllSantri();
    
}
