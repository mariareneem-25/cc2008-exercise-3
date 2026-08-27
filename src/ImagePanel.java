import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * A panel that paints a BufferedImage scaled to whatever size the layout
 * manager gives it. Its preferred size is the image's natural size, so it
 * plays nicely with pack() and with layouts that respect preferred sizes.
 */
public class ImagePanel extends JPanel {
  private BufferedImage image;
  private boolean preserveAspectRatio = true;

  public ImagePanel() {
    this(null);
  }

  public ImagePanel(BufferedImage image) {
    this.image = image;
    setOpaque(false);
  }

  public void setImage(BufferedImage image) {
    this.image = image;
    revalidate();
    repaint();
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setPreserveAspectRatio(boolean preserve) {
    this.preserveAspectRatio = preserve;
    repaint();
  }

  /**
   * Override rather than calling setPreferredSize(), so the value stays
   * correct if the image is swapped out later.
   */
  @Override
  public Dimension getPreferredSize() {
    if (isPreferredSizeSet() || image == null) {
      return super.getPreferredSize();
    }
    Insets in = getInsets();
    return new Dimension(
        image.getWidth() + in.left + in.right,
        image.getHeight() + in.top + in.bottom);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g); // fills background when opaque
    if (image == null) {
      return;
    }

    Insets in = getInsets();
    int availW = getWidth() - in.left - in.right;
    int availH = getHeight() - in.top - in.bottom;
    if (availW <= 0 || availH <= 0) {
      return;
    }

    int imgW = image.getWidth();
    int imgH = image.getHeight();

    int drawW = availW;
    int drawH = availH;
    int x = in.left;
    int y = in.top;

    if (preserveAspectRatio) {
      double scale = Math.min((double) availW / imgW, (double) availH / imgH);
      drawW = (int) Math.round(imgW * scale);
      drawH = (int) Math.round(imgH * scale);
      x += (availW - drawW) / 2;
      y += (availH - drawH) / 2;
    }

    // Copy the Graphics so hint changes don't leak to sibling components.
    Graphics2D g2 = (Graphics2D) g.create();
    try {
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.setRenderingHint(RenderingHints.KEY_RENDERING,
          RenderingHints.VALUE_RENDER_QUALITY);
      g2.drawImage(image, x, y, drawW, drawH, null);
    } finally {
      g2.dispose();
    }
  }
}