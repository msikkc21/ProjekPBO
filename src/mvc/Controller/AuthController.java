/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.Controller;
import javax.swing.JOptionPane;
import mvc.DAO.UserDAO;
import mvc.DAOInterface.IUser;
import mvc.Model.User;
import mvc.Model.TabelModelUser;
import mvc.Utils.PasswordUtils;
import mvc.View.Auth.FormLogin;
import mvc.View.Auth.FormRegister;
import mvc.View.DashboardSantri;
import mvc.View.DashboardUstadz;
import mvc.View.FormDataSantri;
import mvc.View.FormDataUstadz;
/**
 *
 * @author izzaa
 */
public class AuthController {
    FormLogin frameLogin;
    FormRegister frameRegister;
    IUser implUser;
    
    public AuthController(FormLogin frameLogin, FormRegister frameRegister){
        this.frameLogin = frameLogin;
        this.frameRegister = frameRegister;
        implUser = new UserDAO();
    }
    
    public void insert() {
        String username = frameRegister.getTxtUsername().getText().trim();
        String password = new String(frameRegister.getTxtPass().getPassword()).trim();
        String confirmPassword = new String(frameRegister.getTxtPassConfirm().getPassword()).trim();
        String role = frameRegister.getRole().getSelectedItem().toString().toLowerCase();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frameRegister, "Semua field wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frameRegister, "Konfirmasi password tidak cocok!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hash password
        String hashedPassword = PasswordUtils.hashPassword(password);

        // Buat objek user baru
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRole(role);

        // Simpan ke database
        implUser.insert(user);

        JOptionPane.showMessageDialog(frameRegister, "Registrasi berhasil! Silakan isi data diri.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        frameRegister.dispose();

        // Arahkan ke form pengisian data diri
        if (role.equals("santri")) {
            new FormDataSantri(username).setVisible(true);
        } else if (role.equals("ustadz")) {
            new FormDataUstadz(username).setVisible(true);
        }
    }
    
    public void login() {
        String username = frameLogin.getTxtUsername().getText().trim();
        String password = new String(frameLogin.getTxtPass().getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frameLogin, "Username atau password tidak boleh kosong!");
            return;
        }

        User user = implUser.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(frameLogin, "Login berhasil!");

            frameLogin.dispose();

            if (user.getRole().equalsIgnoreCase("santri")) {
                new DashboardSantri(user.getUsername()).setVisible(true);
            } else if (user.getRole().equalsIgnoreCase("ustadz")) {
                new DashboardUstadz(user.getUsername()).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(frameLogin, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     
    
    
}
