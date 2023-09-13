import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class GameWin extends JFrame {

  // 0: not start; 1: in progress; 2: shopping; 3: failed; 4: win
  static int state;
  // store gold and stones
  List<Object> objectList = new ArrayList<>();
  Background bg = new Background();
  Line line = new Line(this);
  {
    boolean isPlace = true;
    for(int i = 0; i < 8; i++){
      double random = Math.random();
      Gold gold;// the current gold
      if(random < 0.3){
        gold = new GoldMini();
      }else if(random < 0.7){
        gold = new Gold();
      }else{
        gold = new GoldPlus();
      }
      for(Object obj: objectList){
        if(gold.getRectangle().intersects(obj.getRectangle())){
          // cannot be added into the list, we need to generate another one
          isPlace = false;
        }
      }
      if(isPlace){
        objectList.add(gold);
      }else{
        isPlace = true;
        i--;
      }
    }
    for(int i = 0; i < 3; i++){
      Stone stone = new Stone();
      for(Object obj: objectList){
        if(stone.getRectangle().intersects(obj.getRectangle())){
          isPlace = false;
        }
      }
      if(isPlace){
        objectList.add(stone);
      }else{
        isPlace = true;
        i--;
      }
    }
  }
  Image offScreenImage;

  void launch() throws InterruptedException {
    this.setVisible(true);
    this.setSize(768, 1000);
    this.setLocationRelativeTo(null);
    this.setTitle("Gold Miners");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    addMouseListener(new MouseAdapter() {
      @Override public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        switch(state) {
          case 0:
            if(e.getButton() == 3){
              state = 1;
              bg.startTime = System.currentTimeMillis();
            }
            break;
          case 1:
            // swing
            if (e.getButton() == 1 && line.state == 0) {
              line.state = 1;
            }
            // catch and go back,click the right part of the mouse
            if (e.getButton() == 3 && line.state == 3 && Background.waterNumber > 0) {
              Background.waterNumber--;
              Background.waterFlag = true;
            }
            break;
          case 2:
            if(e.getButton() == 1){
              bg.shop = true;
            }
            if(e.getButton() == 3){
              state = 1;
              bg.startTime = System.currentTimeMillis();
            }
            break;
          case 3:

          case 4:
            if(e.getButton() == 1){
              state = 0;
              bg.reGame();
              line.reGame();
            }
            break;
          default:
        }
      }
    });
    while(true){
      repaint();
      nextLevel();
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  // get into the next level
  public void nextLevel() throws InterruptedException {
    if(state == 1 && bg.gameTime()){
      if(Background.totalPoint > bg.targetPoints){
        if(Background.level >= 5){
          state = 4;
        }else {
          state = 2;
          Background.level++;
        }
      }else{
        state = 3;
      }
      dispose();
      GameWin game1 = new GameWin();
      game1.launch();
    }
  }

  @Override public void paint(Graphics g) {
    offScreenImage = this.createImage(768,1000);
    Graphics gImage = offScreenImage.getGraphics();
    bg.paintSelf(gImage);
    if(state == 1){
      for(Object obj: objectList){
        obj.paintSelf(gImage);
      }
      try {
        line.paintSelf(gImage);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    g.drawImage(offScreenImage,0, 0,null);
  }

  public static void main(String[] args) throws InterruptedException {
    GameWin gameWin = new GameWin();
    gameWin.launch();
  }
}
