import java.awt.Toolkit;

public class Stone extends Object{
  Stone(){
    this.x = (int)(Math.random() * 700);
    this.y = (int)(Math.random() * 550 + 300);
    this.width = 71;
    this.height = 71;
    this.weight = 50;
    this.points = 1;
    this.type = 2;
    this.img = Toolkit.getDefaultToolkit().getImage("imgs/rock1.png");
  }
}
