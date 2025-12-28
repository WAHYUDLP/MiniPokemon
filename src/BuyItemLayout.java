import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BuyItemLayout {
  private JPanel mainPanel = new JPanel(new GridBagLayout()) {
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(new ImageIcon("assets/HomeBase.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(600, 300);
    }
  };
  private JPanel panelMonster = new JPanel(new GridBagLayout());

  private JLabel labelWelcome = new JLabel("Buy Item!");

  private JButton btnHealth = new JButton("Health Potion (+20 HP)");
  private JButton btnElemental = new JButton("Elemental Potion (Change Element)");
  private JButton btnBack = new JButton("Back");

  private JButton btnFire = new JButton("Fire");
  private JButton btnIce = new JButton("Ice");
  private JButton btnWind = new JButton("Wind");
  private JButton btnEarth = new JButton("Earth");
  private JButton btnWater = new JButton("Water");

  private BuyItemListener listener;

  public BuyItemLayout(BuyItemListener listener) {
    this.listener = listener;
  }

  public JPanel create() {
    labelWelcome.setFont(FontUtils.getPixelBoldFont().deriveFont(24f));
    labelWelcome.setForeground(Color.ORANGE);

    btnFire.setVisible(false);
    btnIce.setVisible(false);
    btnWind.setVisible(false);
    btnEarth.setVisible(false);
    btnWater.setVisible(false);

    btnHealth.addActionListener(e -> {
      listener.health();
      
    });

    btnElemental.addActionListener(e -> {
      // listener.action(2);
      toogleBtnElemental();
    });
    btnFire.addActionListener(e -> {
      listener.elemental(1);
    });
    btnIce.addActionListener(e -> {
      listener.elemental(2);
    });
    btnWind.addActionListener(e -> {
      listener.elemental(3);
    });
    btnEarth.addActionListener(e -> {
      listener.elemental(4);
    });
    btnWater.addActionListener(e -> {
      listener.elemental(5);
    });

    btnBack.addActionListener(e -> {
      if (btnFire.isVisible()) {
        toogleBtnElemental();
      } else {
        listener.goBack();
      }
    });

    initCompToPanelMenu();
    // initChooseMonsterToPanel();
    return mainPanel;
  }

  private void initCompToPanelMenu() {
    mainPanel.add(labelWelcome, GridBagUtils.createConstraints(0, 0));
    mainPanel.add(btnHealth, GridBagUtils.createConstraints(0, 1));
    mainPanel.add(btnElemental, GridBagUtils.createConstraints(0, 2));
    mainPanel.add(btnFire, GridBagUtils.createConstraints(0, 1));
    mainPanel.add(btnIce, GridBagUtils.createConstraints(0, 2));
    mainPanel.add(btnWind, GridBagUtils.createConstraints(0, 3));
    mainPanel.add(btnEarth, GridBagUtils.createConstraints(0, 4));
    mainPanel.add(btnWater, GridBagUtils.createConstraints(0, 5));
    mainPanel.add(btnBack, GridBagUtils.createConstraints(0, 6));
  }

  private void toogleBtnElemental() {
    if (btnFire.isVisible()) {
      btnElemental.setVisible(true);
      btnHealth.setVisible(true);
      btnFire.setVisible(false);
      btnIce.setVisible(false);
      btnWind.setVisible(false);
      btnEarth.setVisible(false);
      btnWater.setVisible(false);
    } else {
      btnElemental.setVisible(false);
      btnHealth.setVisible(false);
      btnFire.setVisible(true);
      btnIce.setVisible(true);
      btnWind.setVisible(true);
      btnEarth.setVisible(true);
      btnWater.setVisible(true);
    }
  }
}
