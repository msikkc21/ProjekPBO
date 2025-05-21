package mvc.View;

import javax.swing.*;

public class DashboardSantri extends JFrame {
    public DashboardSantri(String username) {
        setTitle("Dashboard Santri");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Selamat datang di Dashboard Santri, " + username + "!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
