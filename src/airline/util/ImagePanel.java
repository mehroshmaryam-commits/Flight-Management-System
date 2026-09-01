package airline.util;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
  private Image image;

  public ImagePanel(String imagePath) {
    image = new ImageIcon(imagePath).getImage();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Draw the image scaled to panel size, smooth scaling
    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
  }
}
