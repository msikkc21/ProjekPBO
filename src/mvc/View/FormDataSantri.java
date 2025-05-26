package mvc.View;

import javax.swing.*;

public class FormDataSantri extends JFrame {
    public FormDataSantri(String username) {
        setTitle("Form Data Santri");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Isi data diri santri, " + username);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
