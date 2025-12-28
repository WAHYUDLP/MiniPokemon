import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChooseMonsterLayout {
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

  private JLabel labelWelcome = new JLabel("Welcome back to Home Base!");

  private JButton btnBack = new JButton("Back");

  private List<PlayerMonster> playerMonsters;
  private List<PlayerMonster> choosenMonsters;
  private ChooseMonsterListener listener;

  public ChooseMonsterLayout(List<PlayerMonster> playerMonsters, List<PlayerMonster> choosenMonsters,
      ChooseMonsterListener listener) {
    this.playerMonsters = playerMonsters;
    this.choosenMonsters = choosenMonsters;
    this.listener = listener;
  }

  public JPanel create() {
    labelWelcome.setFont(FontUtils.getPixelBoldFont().deriveFont(24f));
    labelWelcome.setForeground(Color.ORANGE);

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
    mainPanel.add(btnBack, GridBagUtils.createConstraints(0, 1));
  }

  private void initChooseMonsterToPanel() {
    for (PlayerMonster playerMonster : playerMonsters) {
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
          "\n Elements: " + (playerMonster.getElement().isEmpty() ? "None" : playerMonster.getElement().get(0).getNama())+
          "\n Evolved: " + (playerMonster.hasEvolved() ? "Yes" : "No")
          );

      Boolean aww = choosenMonsters.stream().filter(e -> {
        return e.getNama() == playerMonster.getNama();
      }).findAny().isPresent();
      JButton btn = new JButton(aww ? "Deactive" : "Active");

      panelMonster2.setLayout(new BoxLayout(panelMonster2, BoxLayout.Y_AXIS));
      panelMonster2.setOpaque(false);

      labelImg.setAlignmentX(Component.CENTER_ALIGNMENT);

      btn.setOpaque(false);
      btn.setAlignmentX(Component.CENTER_ALIGNMENT);
   
      btn.setFocusable(false);
      btn.addActionListener(e -> {
        // listener.primaryAction(playerMonster);
        if (aww) {
          choosenMonsters.removeIf(aws -> {
            
            return aws.getNama() == playerMonster.getNama();
          });
        } else {
          choosenMonsters.add(playerMonster);
        }

        refreshMonster();
      });

      panelMonster2.add(labelImg);
      panelMonster2.add(teaInfo);
      panelMonster2.add(btn);

      panelMonster.add(panelMonster2);
    }
  }

  public void refreshMonster() {
    panelMonster.removeAll();
    initChooseMonsterToPanel();
    panelMonster.revalidate();
    panelMonster.repaint();
  }
}
