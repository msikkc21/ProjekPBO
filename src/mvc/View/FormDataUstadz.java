package mvc.View;

import javax.swing.*;

public class FormDataUstadz extends JFrame {
    public FormDataUstadz(String username) {
        setTitle("Form Data Ustadz");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Isi data diri ustadz, " + username);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
    }
}
