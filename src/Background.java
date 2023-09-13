import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.text.StyledEditorKit.BoldAction;

public class Background {

  static int level = 1;
  int targetPoints = level * 15;
  static int totalPoint = 0;
  static int waterNumber = 3;
  static boolean waterFlag = false;
  // start time
  long startTime;
  //end time
  long endTime;
  // the price of water
  int price = (int)(Math.random() * 10);
  // check whether user go to the shop
  boolean shop = false;
  Image water = Toolkit.getDefaultToolkit().getImage("imgs/water.png");
  Image bg = Toolkit.getDefaultToolkit().getImage("imgs/bg.jpg");
  Image bg1 = Toolkit.getDefaultToolkit().getImage("imgs/bg1.jpg");
  Image peo = Toolkit.getDefaultToolkit().getImage("imgs/peo.png");
  void paintSelf(Graphics g){
    g.drawImage(bg, 0, 200, null);
    g.drawImage(bg1, 0, 0, null);
    switch(GameWin.state){
      case 0:
        drawWord(g, Color.green, 80, "Start", 300, 400);
        break;
      case 1:
        g.drawImage(peo, 310, 50, null);
        drawWord(g, Color.BLACK, 30, "Points: " + totalPoint, 30, 150);
        // water
        g.drawImage(water, 500, 40, null);
        drawWord(g, Color.BLACK, 30, "*" + waterNumber, 570, 70);
        // level number
        drawWord(g, Color.BLACK, 20, "LEVEL: " + level, 30, 60);
        //target points
        drawWord(g, Color.BLACK, 30, "Target: " + targetPoints, 30, 110);
        // display time
        endTime = System.currentTimeMillis();
        long time = 30 - (endTime - startTime) / 1000;
        drawWord(g, Color.BLACK, 30, "Time: " + (time > 0 ? time: 0), 520, 150);
        break;
      case 2:
        g.drawImage(water, 300, 400, null);
        drawWord(g, Color.BLACK, 30, "Price: " + price, 300, 500);
        drawWord(g, Color.BLACK, 30, "Buy it?", 300, 550);
        if(shop){
          totalPoint -= price;
          waterNumber++;
          shop = false;
          GameWin.state = 1;
          startTime = System.currentTimeMillis();
        }
        break;
      case 3:
        drawWord(g, Color.cyan, 80, "Failed", 250, 350);
        drawWord(g, Color.BLACK, 80, "Points: " + totalPoint, 200, 450);
        break;
      case 4:
        drawWord(g, Color.RED, 80, "You pass!", 250, 350);
        drawWord(g, Color.BLACK, 80, "Points: " + totalPoint, 200, 450);
        break;
      default:
    }
  }

  // true: finish; false: in progress
  boolean gameTime(){
    long time = (endTime - startTime) / 1000;
    if(time > 30){
      return true;
    }
    return false;
  }

  // reset the elements
  void reGame(){
    level = 1;
    targetPoints = level * 15;
    totalPoint = 0;
    waterNumber = 3;
    waterFlag = false;
  }


  public static void drawWord(Graphics g, Color color, int size, String str, int x, int y){
    g.setColor(color);
    g.setFont(new Font("Arial", Font.BOLD, size));
    g.drawString(str, x, y);
  }
}
