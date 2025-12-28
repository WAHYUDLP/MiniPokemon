import java.io.File;
import java.io.IOException;
import java.awt.*;

public class FontUtils {
  public static Font getPixelFont() {
    File font_file = new File("assets/SF Pixelate.ttf");
    Font font1 = null;
    try {
      font1 = Font.createFont(Font.TRUETYPE_FONT, font_file);
      
    } catch (FontFormatException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return font1;
  }

  public static Font getPixelBoldFont() {
    File font_file = new File("assets/SF Pixelate Bold.ttf");
    Font font1 = null;
    try {
      font1 = Font.createFont(Font.TRUETYPE_FONT, font_file);
      
    } catch (FontFormatException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return font1;
  }
}

