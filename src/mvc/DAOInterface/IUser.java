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
    int insert(User user);
    User login(String username, String password);
}
