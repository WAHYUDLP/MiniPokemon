import javax.swing.*;
import java.awt.*;

public class WelcomeLayout {
    private JPanel mainPanel = new JPanel(new GridBagLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(new ImageIcon("assets/whats-your-name.png").getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 300);
        }
    };

    private JTextField input = new JTextField();
    private JButton btnLogin = new JButton("Masuk");

    private WelcomeListener listener;

    public WelcomeLayout(WelcomeListener listener) {
        this.listener = listener;
    }

    public JPanel create() {
        btnLogin.addActionListener(e -> {
            listener.login(input.getText());
        });

        // Set the preferred size of the input field to maintain its size
        input.setPreferredSize(new Dimension(200, 30));

        mainPanel.add(input, GridBagUtils.createConstraints(0, 0));
        mainPanel.add(btnLogin, GridBagUtils.createConstraints(0, 1));
        return mainPanel;
    }
}
