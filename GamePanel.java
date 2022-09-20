import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.util.Random;

import java.awt.*;
import java.awt.event.*;
public class GamePanel extends JPanel implements ActionListener {
    
      static final int  SCREEN_WTDTH = 600;
      static final int SCREEN_HEIGHT= 600;
      static final int UNIT_SIZE = 25;
      static final int GAME_UNIT = (SCREEN_WTDTH*SCREEN_HEIGHT)/UNIT_SIZE;
      static final int DELAY = 75;
      final int x[] = new int [GAME_UNIT];
      final int y[] = new int [GAME_UNIT];
      int bodyparts= 6;
      int applesEaten;
      int applex;
      int appley;
      char direction  ='R';
      boolean running =false;
      Timer timer;
      Random random;

    
    GamePanel(){
        random =new Random();
        this.setPreferredSize(new DimensionUIResource(SCREEN_WTDTH, SCREEN_HEIGHT));
        this.setBackground(Color.cyan);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    
    public void startGame(){

        newApple();
        running= true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){

       super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
      if(running){
        // for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
        //     g.drawLine(i*UNIT_SIZE , 0, i*UNIT_SIZE, SCREEN_HEIGHT);
        //     g.drawLine(0,i*UNIT_SIZE, SCREEN_WTDTH,i*UNIT_SIZE);
        // }
        g.setColor(Color.red);
        g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);
        

        for(int i=0;i<bodyparts;i++){
            if(i==0){
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            else {
                g.setColor(new Color(45,180,0));
                g.setColor(new Color(random.nextInt(40),random.nextInt(250),random.nextInt(60)));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("SCORE: "+applesEaten, (SCREEN_WTDTH-metrics.stringWidth("SCORE: "+applesEaten))/2, g.getFont().getSize());


    }
    else {
        gameOver(g);
    }
    }
    public void move(){

        for(int i=bodyparts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        switch(direction){
            case 'U':
            y[0] =y[0]-UNIT_SIZE;
            break;
            case 'D':
            y[0] =y[0]+UNIT_SIZE;
            break;
            case 'L':
            x[0] =x[0]-UNIT_SIZE;
            break;
            case 'R':
            x[0] =x[0]+UNIT_SIZE;
            break;
        }
    }
    public void newApple(){
      applex =random.nextInt((int)(SCREEN_WTDTH/UNIT_SIZE))*UNIT_SIZE;
      appley =random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
      
    }
    public void checkApple(){

        if((x[0]==applex)&& (y[0]==appley)){
            bodyparts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollision(){
         //cheks if head collide with body
        for(int i=bodyparts;i>0;i--){
            if((x[0]==x[i])&&(y[0] ==y[i])){
                running =false;
            }
        }
        //cheks if head touch left border
        if(x[0]<0) running =false;
        
        //checks if head touch right border
        if(x[0]> (SCREEN_WTDTH-UNIT_SIZE)) running = false;
        
        //checks if head touch top border
        if(y[0]<0) running =false;
        
        //checks if head touch bottom border
        if(y[0]>SCREEN_HEIGHT) running = false;

        //check if head touch apple 
        // if(x[0]== (applex-UNIT_SIZE) || y[0]==(appley-UNIT_SIZE))
        // running = false;

        if(!running) timer.stop();
    }
    public void gameOver(Graphics g){
        //game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WTDTH-metrics.stringWidth("GAMEOVER"))/2, SCREEN_HEIGHT/2);

        //score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("SCORE: "+applesEaten, (SCREEN_WTDTH-metrics1.stringWidth("SCORE: "+applesEaten))/2, g.getFont().getSize());


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
        
    }

    public class MyKeyAdapter extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                     if(direction!='R'){
                        direction ='L';
                     }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                       direction ='R';
                    }
                   break;
                case KeyEvent.VK_UP:
                   if(direction!='D'){
                      direction ='U';
                   }
                  break;
                case KeyEvent.VK_DOWN:
                  if(direction!='U'){
                     direction ='D';
                  }
                 break;
                
            } 

        }
    }
}
