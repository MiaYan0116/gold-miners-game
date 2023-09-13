import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Object {
  // location
  int x;
  int y;
  // width and height
  int width;
  int height;
  // pic
  Image img;
  // represent the object has been moved or not
  boolean flag = false;
  int weight;

  int points;
  //1: gold; 2: stone
  int type;

  void paintSelf (Graphics g){
      g.drawImage(img, x, y, null);
  }

  public int getWidth(){
    return width;
  }

  public Rectangle getRectangle(){
    return new Rectangle(x,y,width, height);
  }
}
