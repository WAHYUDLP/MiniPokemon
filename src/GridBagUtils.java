import javax.swing.*;
import java.awt.*;


public class GridBagUtils {
  public static GridBagConstraints createConstraints(int gridx, int gridy) {
    GridBagConstraints g = new GridBagConstraints();
    g.fill = GridBagConstraints.HORIZONTAL;
    g.gridx = gridx;
    g.gridy = gridy;
    return g;
  }

  public static GridBagConstraints createConstraints(int gridx, int gridy,int ipadx,int ipady) {
    GridBagConstraints g = new GridBagConstraints();
    g.fill = GridBagConstraints.HORIZONTAL;
    g.gridx = gridx;
    g.gridy = gridy;
    g.ipadx = ipadx;
    g.ipady = ipady;
    return g;
  }

  public static GridBagConstraints createConstraints(int gridx, int gridy, Insets insets) {
    GridBagConstraints g = new GridBagConstraints();
    g.fill = GridBagConstraints.HORIZONTAL;
    g.gridx = gridx;
    g.gridy = gridy;
    g.insets = insets;
    return g;
  }
}