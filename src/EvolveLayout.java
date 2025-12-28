 import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EvolveLayout {
  private JPanel mainPanel = new JPanel(new GridBagLayout()) {
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(new ImageIcon("assets/Homebase.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(600, 300);
    }
  };
  private JPanel panelMonster = new JPanel();

  private JLabel labelWelcome = new JLabel("Evolve your monster!");

  private JButton btnFire = new JButton("Fire");
  private JButton btnIce = new JButton("Ice");
  private JButton btnWater = new JButton("Water");
  private JButton btnEarth = new JButton("Earth");
  private JButton btnWind = new JButton("Wind");
  private JButton btnBack = new JButton("Back");

  private PlayerMonster playerMonster;
  private EvolveListener listener;
  private String actionName;

  public EvolveLayout(PlayerMonster playerMonster, EvolveListener listener) {
    this.playerMonster = playerMonster;
    this.listener = listener;
  }

  public JPanel create() {
    labelWelcome.setFont(FontUtils.getPixelBoldFont().deriveFont(24f));
    labelWelcome.setForeground(Color.ORANGE);

    btnFire.addActionListener(e -> {
      listener.doEnvolve(this, playerMonster, 1);
    });
    btnIce.addActionListener(e -> {
      listener.doEnvolve(this, playerMonster, 2);
    });
    btnWind.addActionListener(e -> {
      listener.doEnvolve(this, playerMonster, 3);
    });
    btnEarth.addActionListener(e -> {
      listener.doEnvolve(this, playerMonster, 4);
    });
    btnWater.addActionListener(e -> {
      listener.doEnvolve(this,playerMonster, 5);
    });

    btnBack.addActionListener(e -> {
      listener.goBack();
    });

    panelMonster.setOpaque(false);
    initChooseMonsterToPanel();
    initCompToPanelMenu();
    return mainPanel;
  }

  private void initCompToPanelMenu() {

    mainPanel.add(panelMonster, GridBagUtils.createConstraints(0, 0));
    mainPanel.add(btnFire, GridBagUtils.createConstraints(0, 1));
    mainPanel.add(btnIce, GridBagUtils.createConstraints(0, 2));
    mainPanel.add(btnWind, GridBagUtils.createConstraints(0, 3));
    mainPanel.add(btnEarth, GridBagUtils.createConstraints(0, 4));
    mainPanel.add(btnWater, GridBagUtils.createConstraints(0, 5));
    mainPanel.add(btnBack, GridBagUtils.createConstraints(0, 6));
  }

  private void initChooseMonsterToPanel() {

    JPanel panelMonster2 = new JPanel();

    ImageIcon icon = new ImageIcon(playerMonster.imgPath);
    ImageIcon icon2 = new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));

    JLabel labelImg = new JLabel(icon2);
    JTextArea teaInfo = new JTextArea(
        "Name: " + playerMonster.getNama() +
            "\n Level: " + playerMonster.getLevel() +
            "\n Exp Point: " + playerMonster.getExpPoint() +
            "\n HP Point: " + playerMonster.getHealthPoint() +
            "\n Wins: " + playerMonster.getWins() +
            "\n Elements: "
            + (playerMonster.getElement().isEmpty() ? "None" : playerMonster.getElement().get(0).getNama()) +
            "\n Evolved: " + (playerMonster.hasEvolved() ? "Yes" : "No"));
    // JButton btn = new JButton(actionName);

    panelMonster2.setLayout(new BoxLayout(panelMonster2, BoxLayout.Y_AXIS));
    panelMonster2.setOpaque(false);

    labelImg.setAlignmentX(Component.CENTER_ALIGNMENT);


    panelMonster2.add(labelImg);
    panelMonster2.add(teaInfo);

    panelMonster.add(panelMonster2);

  }

  public void refreshMonster() {
    panelMonster.removeAll();
    initChooseMonsterToPanel();
    mainPanel.revalidate();
    mainPanel.repaint();
  }
}