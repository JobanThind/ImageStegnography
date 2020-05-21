package Stegno;

import java.util.Scanner;

public class Main {

   public static void main(String args[]) {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter 1 to hide info");
      System.out.println("Enter 2 to reveal info");
      int e = sc.nextInt();
      if (e == 1) {
         System.out.println("Enter message to be hidden ->");
         String y = sc.next();
         System.out.println("Enter input file (ex- D:\\\\Images\\\\Input.png)");
         String path = sc.next();
         System.out.println("Enter path for output file (ex-D:\\\\Images\\\\Output.png )");
         String opath = sc.next();
         String msg = "hide" + y.length() + "/" + y;          //to check if the pic have a hidden info first  hidden word is hide
         Hide h = new Hide(path, opath, msg);

      }
      if(e==2){
         System.out.println("Enter input file (ex- D:\\\\Images\\\\Input.png)");
         String path = sc.next();
      Unhide uh = new Unhide(path);
      }

   }
}
