import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class DungeonExploreLayout {
  JPanel mainPanel = new JPanel(new GridBagLayout()) {
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(new ImageIcon("assets/Dungeon.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
    }
  
    @Override
    public Dimension getPreferredSize() {
      return new Dimension(600, 300);
    }
  };
  JLabel labelImg = new JLabel("");

  JTextArea teaInfo = new JTextArea("");
  
  JLabel labelWelcome = new JLabel("Welcome to Dungeon");
  JLabel labelInfoFind = new JLabel("Search Monster...");
  
  JButton btnExplore = new JButton("Explore");
  JButton btnBattle = new JButton("Battle");
  JButton btnBack = new JButton("Back");

  DungeonExploreListener listener;

  Monster wildMonster;

  Random random;

  public DungeonExploreLayout(DungeonExploreListener listener) {
    random = new Random();
    this.listener = listener;
  }

  public JPanel create() {


    labelWelcome.setFont(FontUtils.getPixelBoldFont().deriveFont(34f));
    labelInfoFind.setFont(FontUtils.getPixelBoldFont().deriveFont(24f));
    labelInfoFind.setVerticalTextPosition(JLabel.BOTTOM);
    labelInfoFind.setHorizontalTextPosition(JLabel.CENTER);
    labelInfoFind.setForeground(Color.ORANGE);
    labelInfoFind.setVisible(false);

    labelImg.setVisible(false);
    labelImg.setHorizontalAlignment(JLabel.CENTER);
    teaInfo.setVisible(false);
    
    btnExplore.addActionListener(e -> {
      processExplore();
      // listener.explore();
    });
    btnBattle.setVisible(false);
    btnBattle.addActionListener(e -> {
      listener.battle(wildMonster);
    });
    btnBack.addActionListener(e -> {
      listener.back();
    });
  
    mainPanel.add(labelWelcome, GridBagUtils.createConstraints(0, 0, new Insets(0, 0, 24, 0)));
    mainPanel.add(labelInfoFind, GridBagUtils.createConstraints(0, 1, new Insets(0, 0, 12, 0)));
    mainPanel.add(labelImg, GridBagUtils.createConstraints(0, 2, new Insets(0, 8, 0, 0)));
    mainPanel.add(teaInfo, GridBagUtils.createConstraints(0, 3, new Insets(0, 8, 0, 0)));
    mainPanel.add(btnBattle, GridBagUtils.createConstraints(0, 4, new Insets(8, 0, 0, 0)));
    mainPanel.add(btnExplore, GridBagUtils.createConstraints(0, 5, new Insets(8, 0, 0, 0)));
    mainPanel.add(btnBack, GridBagUtils.createConstraints(0, 6, new Insets(8, 0, 0, 0)));
    return mainPanel;
  }

  private void processExplore() {
    btnExplore.setEnabled(false);
    labelInfoFind.setVisible(true);
    labelImg.setVisible(false);
    teaInfo.setVisible(false);
    labelInfoFind.setText("Search Monster..");

    Thread one = new Thread() {
      public void run() {
        try {
          Thread.sleep(2000);
          btnExplore.setEnabled(false);
          labelInfoFind.setVisible(true);
          if  (random.nextInt(100) < 50) {
            wildMonster = listener.getMonsterRnd();

            ImageIcon icon = new ImageIcon(wildMonster.imgPath);
            ImageIcon icon2 = new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));

            labelImg.setIcon(icon2);
            teaInfo.setText(
              "Name: " + wildMonster.getNama() + 
              "\n Level: " + wildMonster.getLevel() +
              "\n Exp Point: " + wildMonster.getExpPoint() +
              "\n HP Point: " + wildMonster.getHealthPoint() +
              "\n Elements: " + (wildMonster.getElement().isEmpty() ? "None" : wildMonster.getElement().get(0).getNama())
            );

            labelImg.setVisible(true);
            teaInfo.setVisible(true);
            btnBattle.setVisible(true);
            btnExplore.setEnabled(true);
            btnExplore.setText("Explore Again");
            labelInfoFind.setVisible(false);
          } else {
            btnExplore.setEnabled(true);
            btnExplore.setText("Explore Again");
            labelInfoFind.setVisible(true);
            labelInfoFind.setText("Monster not fund! Try Again!");
          }
        } catch (Exception v) {
          System.out.println(v);
        }
      }
    };

    one.start();
  }
}
