import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BeginAttackLayout {
  private PlayerMonster currentMonster;
  private Monster wildMonster;

  private JPanel panelBtn = new JPanel();
  private JPanel panelKiri = new JPanel(new BorderLayout());
  private JPanel panelKanan = new JPanel(new BorderLayout());
  private JPanel panelKiriStatusCon = new JPanel(new GridBagLayout());
  private JPanel panelKiriStatus = new JPanel(new GridBagLayout());
  private JPanel panelKiriStatusBawah = new JPanel(new GridBagLayout());
  private JPanel panelKananStatus = new JPanel(new GridBagLayout());

  private JButton btnBasicAttack = new JButton("Basic Attack");
  private JButton btnSpecialAttack = new JButton("Special Attack");
  private JButton btnElementalAttack = new JButton("Elemental Attack");
  private JButton btnUseItem = new JButton("Use Item");
  private JButton btnFlee = new JButton("Flee");
  private JButton btnHeath = new JButton("Use Heal");
  private JButton btnElemental = new JButton("Use Elemental");
  private JButton btnFire = new JButton("Fire");
  private JButton btnIce = new JButton("Ice");
  private JButton btnWind = new JButton("Wind");
  private JButton btnEarth = new JButton("Earth");
  private JButton btnWater = new JButton("Water");
  private JButton btnCloseEl = new JButton("X");

  private JButton btnBack = new JButton("Back");

  private ImageIcon playerMonsterIcon;
  private JLabel playerMonsterLabel;
  private ImageIcon wildMonsterIcon;
  private JLabel wildMonsterLabel;
  private JLabel labelPrimaryInfo = new JLabel("");

  private JLabel labelNameKiri = new JLabel("");
  private JProgressBar progressBarKiri = new JProgressBar(0, 100);
  private JLabel labelLvlKiri = new JLabel("");

  // Untuk panel kanan
  private JLabel labelNameKanan = new JLabel("");
  private JProgressBar pbKanan = new JProgressBar(0, 100);
  private JLabel labelLvlKanan = new JLabel("");

  private BeginAttackListener listener;

  public BeginAttackLayout(PlayerMonster currentMonster, Monster wildMonster, BeginAttackListener listener) {
    playerMonsterIcon = new ImageIcon(new ImageIcon(currentMonster.imgPath)
    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
    playerMonsterLabel = new JLabel(playerMonsterIcon);
    wildMonsterIcon = new ImageIcon(new ImageIcon(wildMonster.imgPath)
    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
    wildMonsterLabel = new JLabel(wildMonsterIcon);
    this.currentMonster = currentMonster;
    this.wildMonster = wildMonster;
    this.listener = listener;
  }

  public JPanel create() {
    JPanel mainPanel = new JPanel(new BorderLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("assets/BattleArena.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(600, 300);
      }
    };

    
    labelPrimaryInfo.setFont(FontUtils.getPixelBoldFont().deriveFont(Font.BOLD, 40f));
    // labelPrimaryInfo.setBounds(0, 200, labelPrimaryInfo.getWidth(),
    // labelPrimaryInfo.getHeight());
    labelPrimaryInfo.setForeground(Color.ORANGE);
    labelPrimaryInfo.setHorizontalAlignment(SwingConstants.CENTER);

    playerMonsterLabel.setText("");
    playerMonsterLabel.setFont(FontUtils.getPixelBoldFont().deriveFont(Font.BOLD,42f));
    playerMonsterLabel.setForeground(Color.red);
    playerMonsterLabel.setVerticalTextPosition(SwingConstants.TOP);
    playerMonsterLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    wildMonsterLabel.setText("");
    wildMonsterLabel.setFont(FontUtils.getPixelBoldFont().deriveFont(Font.BOLD,42f));
    wildMonsterLabel.setForeground(Color.red);
    wildMonsterLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
    wildMonsterLabel.setHorizontalTextPosition(SwingConstants.CENTER);

    panelKiri.setOpaque(false);
    panelKanan.setOpaque(false);
    panelBtn.setOpaque(false);

    // Status Panel kiri
    labelNameKiri.setText(currentMonster.getNama());
    labelNameKiri.setFont(FontUtils.getPixelBoldFont().deriveFont(14f));
    labelLvlKiri.setText("Lv" + currentMonster.getLevel());
    labelLvlKiri.setFont(FontUtils.getPixelBoldFont().deriveFont(14f));
    progressBarKiri.setValue(currentMonster.getHealthPoint());

    // Status Panel Kanan
    labelNameKanan.setText(wildMonster.getNama());
    labelNameKanan.setFont(FontUtils.getPixelBoldFont().deriveFont(14f));
    labelLvlKanan.setText("Lv" + wildMonster.getLevel());
    labelLvlKanan.setFont(FontUtils.getPixelBoldFont().deriveFont(14f));
    pbKanan.setValue(wildMonster.getHealthPoint());

    btnBasicAttack.setFont(FontUtils.getPixelBoldFont().deriveFont(16f));
    btnBasicAttack.addActionListener(e -> {
      attack(1);
    });
    btnSpecialAttack.setFont(FontUtils.getPixelBoldFont().deriveFont(16f));
    btnSpecialAttack.addActionListener(e -> {
      attack(2);
    });

    btnElementalAttack.setFont(FontUtils.getPixelBoldFont().deriveFont(16f));
    btnElementalAttack.addActionListener(e -> {
      attack(3);
    });

    btnFlee.addActionListener(e -> {
      listener.attack(this, 5);
    });
    btnHeath.addActionListener(e -> {
      listener.heal(this);
    });

    btnBack.setFont(FontUtils.getPixelBoldFont().deriveFont(16f));
    btnBack.addActionListener(e -> {
      listener.backToDungeon();
    });

    btnElemental.addActionListener(e -> {
      if (panelKiriStatusBawah.isVisible()) {
        panelKiriStatusBawah.setVisible(false);
      } else {
        panelKiriStatusBawah.setVisible(true);
      }
    });

    btnFire.addActionListener(e -> {
      listener.elemntalApply(this, 1);
    });
    btnIce.addActionListener(e -> {
      listener.elemntalApply(this, 2);
    });
    btnWind.addActionListener(e -> {
      listener.elemntalApply(this, 3);
    });
    btnEarth.addActionListener(e -> {
      listener.elemntalApply(this, 4);
    });
    btnWater.addActionListener(e -> {
      listener.elemntalApply(this, 5);
    });

    btnCloseEl.addActionListener(e -> {
      panelKiriStatusBawah.setVisible(false);
    });

    panelKiriStatusBawah.setVisible(false);
    panelKiriStatusCon.setOpaque(false);
    panelKiriStatusBawah.setOpaque(false);

    panelKananStatus.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelKananStatus.add(labelNameKanan, GridBagUtils.createConstraints(0, 0, 8, 0));
    panelKananStatus.add(pbKanan, GridBagUtils.createConstraints(1, 0, 8, 0));
    panelKananStatus.add(labelLvlKanan, GridBagUtils.createConstraints(2, 0, 8, 0));
    panelKananStatus.add(labelLvlKanan, GridBagUtils.createConstraints(2, 0, 8, 0));
    panelKiriStatus.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelKiriStatus.add(labelNameKiri, GridBagUtils.createConstraints(0, 0, 8, 0));
    panelKiriStatus.add(progressBarKiri, GridBagUtils.createConstraints(1, 0, 8, 0));
    panelKiriStatus.add(labelLvlKiri, GridBagUtils.createConstraints(2, 0, 8, 0));
    panelKiriStatus.add(btnHeath, GridBagUtils.createConstraints(0, 1,  new Insets(12, 0, 0, 12)));
    panelKiriStatus.add(btnElemental, GridBagUtils.createConstraints(1, 1,  new Insets(12, 0, 0, 0)));
    panelKiriStatusBawah.add(btnFire, GridBagUtils.createConstraints(0, 0,  new Insets(0, 0, 0, 4)));
    panelKiriStatusBawah.add(btnIce, GridBagUtils.createConstraints(1, 0,  new Insets(0, 0, 0, 4)));
    panelKiriStatusBawah.add(btnWind, GridBagUtils.createConstraints(2, 0,  new Insets(0, 0, 0, 0)));
    panelKiriStatusBawah.add(btnEarth, GridBagUtils.createConstraints(0, 1,  new Insets(0, 0, 0, 4)));
    panelKiriStatusBawah.add(btnWater, GridBagUtils.createConstraints(1, 1,  new Insets(0, 0, 0, 4)));
    panelKiriStatusBawah.add(btnCloseEl, GridBagUtils.createConstraints(2, 1,  new Insets(0, 0, 0, 0)));
    panelKiriStatusCon.add(panelKiriStatus, GridBagUtils.createConstraints(0, 0,  new Insets(0, 0, 0, 0)));
    panelKiriStatusCon.add(panelKiriStatusBawah, GridBagUtils.createConstraints(0, 1,  new Insets(12, 0, 0, 0)));
    panelKanan.add(panelKananStatus, BorderLayout.SOUTH);
    panelKanan.add(wildMonsterLabel, BorderLayout.NORTH);
    panelKiri.add(panelKiriStatusCon, BorderLayout.NORTH);
    // panelKiri.add(panelKiriStatusBawah, BorderLayout.NORTH);
    panelKiri.add(playerMonsterLabel, BorderLayout.SOUTH);
    panelBtn.add(btnBasicAttack);
    panelBtn.add(btnSpecialAttack);
    panelBtn.add(btnElementalAttack);
    panelBtn.add(btnFlee);
    mainPanel.add(panelKiri, BorderLayout.WEST);
    mainPanel.add(labelPrimaryInfo, BorderLayout.CENTER);
    mainPanel.add(panelKanan, BorderLayout.EAST);
    mainPanel.add(panelBtn, BorderLayout.SOUTH);
    // rootPanel.setContentPane(mainPanel);
    // rootPanel.revalidate();
    // rootPanel.repaint();
    return mainPanel;
  }

  public void attack(int action) {
    int currentWildHealth = wildMonster.getHealthPoint();
    int currentPlayerHealth = currentMonster.getHealthPoint();
    String result = listener.attack(this, action);
    
    Thread one = new Thread() {
      public void run() {
        try {
          btnBasicAttack.setEnabled(false);
          btnElementalAttack.setEnabled(false);
          btnSpecialAttack.setEnabled(false);
          btnUseItem.setEnabled(false);
          btnFlee.setEnabled(false);

          wildMonsterLabel.setText("-" + (currentWildHealth - wildMonster.getHealthPoint()) + " HP");
          pbKanan.setValue(wildMonster.getHealthPoint());

          Thread.sleep(2000);

          wildMonsterLabel.setText("");
          playerMonsterLabel.setText("-" + (currentPlayerHealth - currentMonster.getHealthPoint()) + " HP");
          progressBarKiri.setValue(currentMonster.getHealthPoint());

          Thread.sleep(2000);

          playerMonsterLabel.setText("");
          btnBasicAttack.setEnabled(true);
          btnElementalAttack.setEnabled(true);
          btnSpecialAttack.setEnabled(true);
          btnUseItem.setEnabled(true);
          btnFlee.setEnabled(true);
        } catch (InterruptedException v) {
          System.out.println(v);
        }
      }
    };

    one.start();
  }

  public void win() {
    labelPrimaryInfo.setText("You Win");
    panelBtn.removeAll();
    panelBtn.invalidate();
    panelBtn.repaint();
    panelBtn.add(btnBack);
  }

  public void lose() {
    labelPrimaryInfo.setText("You Lost");
    panelBtn.removeAll();
    panelBtn.invalidate();
    panelBtn.repaint();
    panelBtn.add(btnBack);
  }

  public void flee() {
    labelPrimaryInfo.setText("You Flee");
    panelBtn.removeAll();
    panelBtn.invalidate();
    panelBtn.repaint();
    panelBtn.add(btnBack);
  }

  public void refreshStatus() {
    pbKanan.setValue(wildMonster.getHealthPoint());
    progressBarKiri.setValue(currentMonster.getHealthPoint());
  }

  public void fleeInvalid() {
    showInfo("Flee Invalid");
  }

  public void showInfo(String text) {
    Thread one = new Thread() {
      public void run() {
        try {
          labelPrimaryInfo.setText(text);
          Thread.sleep(3000);
          labelPrimaryInfo.setText("");
        } catch (InterruptedException v) {
          System.out.println(v);
        }
      }
    };

    one.start();
  }
}