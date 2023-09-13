
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Line {
  // start point
  int x = 380;
  int y = 180;

  // end point
  int endX = 500;
  int endY = 500;

  // set the length of the line
  double length = 100;
  double min_length = 100;
  double max_length = 750;
  double n = 0;
  // direction
  int dir = 1;

  // status: 0: swing; 1: extend; 2: back; 3: catch and back
  int state;
  //The hook
  Image hook = Toolkit.getDefaultToolkit().getImage("imgs/hook.png");


  GameWin frame;
  Line(GameWin frame){
    this.frame = frame;
  }

  // check catch condition
  void logic(){
    for(Object obj: this.frame.objectList){
      if(endX > obj.x && endX < obj.x + obj.width
          && endY > obj.y && endY < obj.y + obj.height){
        state = 3;
        obj.flag = true;
      }
    }
  }

  void lines(Graphics g){
    endX = (int) (x + length * Math.cos(n * Math.PI));
    endY = (int) (y + length * Math.sin(n * Math.PI));
    g.setColor(Color.red);
    g.drawLine(x - 1,y,endX - 1, endY);
    g.drawLine(x,y,endX, endY);
    g.drawLine(x + 1,y,endX + 1, endY);
    g.drawImage(hook, endX - 36, endY - 2, null);
  }
  void paintSelf(Graphics g) throws InterruptedException {
    logic();
    // change the state of the line
    switch (state){
      case(0):
        if(n < 0.1){
          dir = 1;
        }else if(n > 0.9){
          dir = -1;
        }
        n = n + 0.005 * dir;
        lines(g);
        break;
      case 1:
        if(length < max_length){
          length = length + 5;
          lines(g);
        }else{
          state = 2;
        }
        break;
      case 2:
        if(length > min_length){
          length = length - 5;
          lines(g);
        }else{
          state = 0;
        }
        break;
      case 3:
        int m = 1;
        if(length > min_length){
          length = length - 10;
          lines(g);
          for(Object obj: this.frame.objectList){
            if(obj.flag == true) {
              m = obj.weight;
              obj.x = endX - obj.getWidth() / 2;
              obj.y = endY;
              if (length <= min_length) {
                obj.x = -150;
                obj.y = -150;
                this.frame.bg.waterFlag = false;
                obj.flag = false;
                this.frame.bg.totalPoint += obj.points;
                state = 0;
              }
              if(this.frame.bg.waterFlag == true){
                if(obj.type == 1){
                  m = 1;
                }
                if(obj.type == 2){
                  obj.x = -150;
                  obj.y = -150;
                  this.frame.bg.waterFlag = false;
                  state = 2;
                }
              }
            }
          }
        }
        try {
          Thread.sleep(m);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        break;
      default:
      }
    }

  // reset the line
  void reGame(){
    n = 0;
    length = 100;
  }

}
