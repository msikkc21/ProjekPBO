/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mvc.DAOInterface;

import java.util.List;
import mvc.Model.Santri;
import mvc.Model.Setoran;
/**
 *
 * @author User
 */
public interface ISantri {
    public void insert(Santri b);
    public void update(Santri b);
    public void delete (int id);
    public Santri getById(int id);
    public Santri getByUserId(int userId);
    public List<Santri> getAll();
    public List<Setoran> getAllSetoranBySantriId(int santriId); // Metode baru untuk mendapatkan setoran santri
    public List<Setoran> getSetoranSantriByKeyword(int santriId, String keyword); // Metode baru untuk mencari setoran
}
