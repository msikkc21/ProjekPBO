/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mvc.DAOInterface;

import mvc.Model.User;

/**
 *
 * @author izzaa
 */
public interface IUser {
    public void insert(User u);
    public void read(int id);
    public void update(User u);
    public void delete(int id);
}
