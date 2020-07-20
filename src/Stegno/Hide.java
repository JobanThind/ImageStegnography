package Stegno;

import java.awt.*;
import java.io.File;   // used to create file object
import java.io.IOException;     //to catch exception while reading writing file
import java.awt.image.BufferedImage;            //used to create image object
import javax.imageio.ImageIO;               //used to perform read write operations

public class Hide {

   Hide(String path, String opath, String msg) {
      char ans[] = convert(msg);
      BufferedImage image = null;
      File f = null;
      try {
//         path="D:\\Taj.jpg"
         f = new File(path);                                   //opening image file
         image = ImageIO.read(f);
      } catch (IOException e) {
         System.out.println("Error: " + e);
      }
      int count = 0;
      for (int i = 0; i < image.getWidth(); i++) {
         if (count >= ans.length)
            break;
         for (int j = 0; j < image.getHeight(); j++) {
            String s;

            Color c = new Color(image.getRGB(i, j));           //getting  r g b value for the specified pixel
            int red = c.getRed();
            int green = c.getGreen();
            int blue = c.getBlue();
            if (count < ans.length) {
               char[] rs = convert(red);
               rs[7] = ans[count];
               count++;
               s = new String(rs);
               red = Integer.parseInt(s, 2);
            }
            if (count < ans.length) {
               char[] gs = convert(green);
               gs[7] = ans[count];
               count++;
               s = new String(gs);
               green = Integer.parseInt(s, 2);

            }
            if (count < ans.length) {
               char[] bs = convert(blue);
               bs[7] = ans[count];
               count++;
               s = new String(bs);
               blue = Integer.parseInt(s, 2);

            }
            pixelset(image, red, green, blue, i, j);

         }
      }
      try {
//         opath="D:\\Output.jpg"
         f = new File(opath);
         ImageIO.write(image, "png", f);                             // saving the file as output
      } catch (IOException e) {
         System.out.println("Error: " + e);
      }


   private char[] convert(String msg) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < msg.length(); i++) {
         char c = msg.charAt(i);
         StringBuilder binstr = new StringBuilder(Integer.toBinaryString((int) c));
         int l = binstr.length();
         for (int j = 0; j < 8 - l; j++)
            binstr.insert(0, 0);
         sb.append(binstr);
      }
      msg = sb.toString();
      char ans[] = msg.toCharArray();
      return ans;
   }

   private char[] convert(int msg) {
      StringBuilder sb = new StringBuilder();
      StringBuilder binstr = new StringBuilder(Integer.toBinaryString(msg));
      int l = binstr.length();
      for (int j = 0; j < 8 - l; j++)
         binstr.insert(0, 0);
      sb.append(binstr);
      String s = sb.toString();
      char ans[] = s.toCharArray();
      return ans;
   }

   private void pixelset(BufferedImage image, int r, int g, int b, int i, int j) {
      int p = 0;
      p = (r << 16) | (g << 8) | b;                             // setting new value of pixel
      image.setRGB(i, j, p);
   }

}
