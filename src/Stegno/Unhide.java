package Stegno;

import java.awt.*;
import java.io.File;   // used to create file object
import java.io.IOException;     //to catch exception while reading writing file
import java.awt.image.BufferedImage;            //used to create image object
import javax.imageio.ImageIO;               //used to perform read write operations

public class Unhide {

   private char[] convert(int msg) {
      StringBuilder sb = new StringBuilder();
      StringBuilder binstr = new StringBuilder(Integer.toBinaryString(msg));
      int l = binstr.length();                                                      //convert pixel into 8bit binary
      for (int j = 0; j < 8 - l; j++)
         binstr.insert(0, 0);
      sb.append(binstr);
      String s = sb.toString();
      char ans[] = s.toCharArray();
      return ans;
   }

   private StringBuilder resolve(BufferedImage image, int w, int x, int y, int z, int p, int check) {
      StringBuilder ans = new StringBuilder();                             //main answer
      StringBuilder letter = new StringBuilder();                          //binary bits extracted to from image (in group of 8)
      char l = '0';                                                        //individual letter made from 8 binary bits
      int flag = p;                                                        //to check the second condition
      for (int i = w; i < x; i++) {
         for (int j = y; j < z; j++) {
            int pchanged = p;                                              //to determine what was the last pixel accesed
            String s;
            Color c = new Color(image.getRGB(i, j));           //getting  r g b value for the specified pixel
            int red = c.getRed();
            int green = c.getGreen();
            int blue = c.getBlue();
            char[] rs = convert(red);
            char[] gs = convert(green);
            char[] bs = convert(blue);
            if (p == 0) {
               letter.append(rs[7]);
               if (letter.length() == 8) {
                  red = Integer.parseInt(letter.toString(), 2);
                  l = (char) red;                                                //converting letter to char
                  ans.append(l);                                               //adding to ans
                  letter.setLength(0);                                         //clearing the letter to add next
                  pchanged = 0;
               }
               p = 1;
            }
            if (p == 1) {
               letter.append(gs[7]);
               if (letter.length() == 8) {
                  red = Integer.parseInt(letter.toString(), 2);
                  l = (char) red;                                                //converting letter to char
                  ans.append(l);                                               //adding to ans
                  letter.setLength(0);                                         //clearing the letter to add next
                  pchanged = 1;

               }
               p = 2;
            }
            if (p == 2) {
               letter.append(bs[7]);
               if (letter.length() == 8) {
                  red = Integer.parseInt(letter.toString(), 2);
                  l = (char) red;                                                //converting letter to char
                  ans.append(l);                                               //adding to ans
                  letter.setLength(0);                                         //clearing the letter to add next
                  pchanged = 2;

               }
               p = 0;
            }
            if (ans.length() == check)                                           //if message length required return ans
               return ans;
            if (w == 0 && x == 1 && y == 10 && z == 50 && flag == 2 && l == '/') {     //if message contains hide then this is called
               ans.deleteCharAt(ans.length() - 1);
               int lth = Integer.parseInt(ans.toString());
               pchanged = (pchanged + 1) % 3;
               if (pchanged == 0)
                  j = j + 1;
               ans = resolve(image, 0, image.getWidth(), j, image.getHeight(), pchanged, lth);  //we have now length of hiddn mssg and now we extract it
               return ans;
            }

         }
      }
      return ans;                                  //this will return if we hide or not
   }

   Unhide(String path) {
      BufferedImage image = null;
      File f = null;
      try {
//         path="D:\\Ouput.jpg"
         f = new File(path);                                   //opening image file
         image = ImageIO.read(f);
      } catch (IOException e) {
         System.out.println("Error: " + e);
      }
      StringBuilder ans = resolve(image, 0, 1, 0, 11, 0, Integer.MAX_VALUE);
      if (!ans.toString().equals("hide")) {
         System.out.println("No hidden data");
         return;
      } else {
         ans = resolve(image, 0, 1, 10, 50, 2, Integer.MAX_VALUE);
         System.out.println("Hidden text is -> "+ans);
      }

   }

}
