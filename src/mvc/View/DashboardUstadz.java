package mvc.View;

import javax.swing.*;

public class DashboardUstadz extends JFrame {
    public DashboardUstadz(String username) {
        setTitle("Dashboard Ustadz");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Selamat datang di Dashboard Ustadz, " + username + "!");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
