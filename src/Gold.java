import java.awt.Toolkit;

public class Gold extends Object{

  Gold(){
    this.x = (int)(Math.random() * 700);
    this.y = (int)(Math.random() * 550 + 300);
    this.width = 52;
    this.height = 52;
    this.weight = 30;
    this.points = 4;
    this.type = 1;
    this.img = Toolkit.getDefaultToolkit().getImage("imgs/gold1.gif");
  }
}

class GoldMini extends Gold{
  GoldMini(){
    this.width = 36;
    this.height = 36;
    this.weight = 15;
    this.points = 2;
    this.img = Toolkit.getDefaultToolkit().getImage("imgs/gold0.gif");
    }
}

class GoldPlus extends Gold{
  GoldPlus(){
    this.x = (int)(Math.random() * 650);
    this.width = 105;
    this.height = 105;
    this.weight = 60;
    this.points = 8;
    this.img = Toolkit.getDefaultToolkit().getImage("imgs/gold2.gif");
  }
}
