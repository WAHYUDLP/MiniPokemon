import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WelcomeBaseLayout {
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

  private JLabel labelWelcome = new JLabel("Welcome back to Home Base!");

  private JButton btnLevelUp = new JButton("Level Up");
  private JButton btnHeal = new JButton("Heal Monster");
  private JButton btnEnvolve = new JButton("Evolve Monster");
  private JButton btnBuyItem = new JButton("Buy Item");
  private JButton btnChooseMonster = new JButton("Choose Monster");
  
  private JButton btnBack = new JButton("Back and Save Home Base");

  private List<PlayerMonster> playerMonsters;
  private WelcomeBaseListener listener;

  public WelcomeBaseLayout(List<PlayerMonster> playerMonsters, WelcomeBaseListener listener) {
    this.playerMonsters = playerMonsters;
    this.listener = listener;
  }

  public JPanel create() {
    labelWelcome.setFont(FontUtils.getPixelBoldFont().deriveFont(24f));
    labelWelcome.setForeground(Color.ORANGE);

    btnLevelUp.addActionListener(e -> {
      listener.levelUp();
    });

    btnHeal.addActionListener(e -> {
      listener.healMonster();
    });
    btnEnvolve.addActionListener(e -> {
      listener.envolve();
    });
    btnBuyItem.addActionListener(e -> {
      listener.buyItem2();
    });

    btnBack.addActionListener(e -> {
      listener.goBack();
    });

    btnChooseMonster.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnChooseMonster.setAlignmentY(Component.CENTER_ALIGNMENT);
    btnChooseMonster.addActionListener(e -> {
      listener.chooseMonster();
    });

    initCompToPanelMenu();
    return mainPanel;
  }

  private void initCompToPanelMenu() {
    mainPanel.add(labelWelcome, GridBagUtils.createConstraints(0, 0));
    mainPanel.add(btnLevelUp, GridBagUtils.createConstraints(0, 1));
    mainPanel.add(btnHeal, GridBagUtils.createConstraints(0, 2));
    mainPanel.add(btnEnvolve, GridBagUtils.createConstraints(0, 3));
    mainPanel.add(btnBuyItem, GridBagUtils.createConstraints(0, 4));
    mainPanel.add(btnChooseMonster, GridBagUtils.createConstraints(0, 5));
    mainPanel.add(btnBack, GridBagUtils.createConstraints(0, 6));
  }
}

