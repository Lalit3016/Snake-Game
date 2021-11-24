/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author gurja
 */

public class Board extends JPanel implements ActionListener{
    private Image apple;
    private Image dot;
   private Image head;

 private final int DOT_SIZE = 10;
 private final int ALL_DOT = 900;    //   300*300 -> 90000             10*10    - >  90000/100=900
    
 private final int x[]=new int[ALL_DOT];
  private final int y[]=new int[ALL_DOT];
  
  private int dots;
  
    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;
    
    private Timer timer;
    
    
    private Boolean leftDirection = false;
    private Boolean rightDirection = true;
    private Boolean upDirection = false;
    private Boolean downDirection = false;
    
    private Boolean inGame = true;
    Board(){
        
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));
        
        setFocusable(true);
        iconImages();
        
       initGame();
    }

     public void iconImages(){
         
         ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("javagame/img/apple.png"));
         apple = i1.getImage();
         
         ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("javagame/img/dot.png"));
         dot = i2.getImage();
         
         ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("javagame/img/head.png"));
        head = i3.getImage();
     
     }
 
     public void initGame(){
       
        dots = 3;
        
        for(int z = 0 ;  z < dots; z++){
                                                       // DOT_SIZE = 10 initial hai
            x[z] = 50 - z * DOT_SIZE;       //  1.dot -> 50 - 0*10==>50    ,  2.dot -> 50 - 1 *10==>40  , 3.dot->50 - 2 *10==>30
            y[z] = 50;
        }
        
        locateApple();
        timer = new Timer(140,this);
       timer.start();
     }
     
     public void locateApple(){
    
       int r = (int)(Math.random() * RANDOM_POSITION);    // 0 and 1 -> 0.1  0.2  0.3  0.4  ........ 0.9     ==>  29 * 0.1=290
       apple_x = (r * DOT_SIZE);
      
         r = (int)(Math.random() * RANDOM_POSITION);    // 0 and 1 -> 0.1  0.2  0.3  0.4  ........ 0.9     ==>  29 * 0.1=290
       apple_y = (r * DOT_SIZE);
    }
    
     public void actionPerformed(ActionEvent ae){
         
         if(inGame){
            checkApple();   
            checkCollision();
            move();
         }
         
         repaint();
     }
     
     public void paintComponent(Graphics g){
         
         super.paintComponent(g);
         draw(g);
     }
     
     public void draw(Graphics g){
         if(inGame){
             g.drawImage(apple, apple_x, apple_y, this);
             
             for(int z = 0; z < dots ; z++){
                 if(z == 0){
                     g.drawImage(head, x[z], y[z], this);
                 }
                 else{
                     g.drawImage(dot, x[z], y[z], this);
                 }
             }
             Toolkit.getDefaultToolkit().sync();
         }else{
             gameOver(g);
         }
     }
     
     public void gameOver(Graphics g){
         String msg = "Game Over";
         Font font = new Font("SAN SERIF",Font.BOLD,24);
         FontMetrics metrices = getFontMetrics(font);
         
         g.setColor(Color.WHITE);
         g.setFont(font);
         g.drawString(msg, (300 - metrices.stringWidth(msg)) /2,300/2);
         
     }
     public void checkApple(){
         if(x[0] == apple_x && y[0] == apple_y){
             dots++;
             locateApple();
         }
     }
     
     public void checkCollision(){
     
         for(int z = dots ; z > 0 ; z--){
             if((z > 4)  && (x[0] == x[z]) && (y[0] == y[z])){
                 inGame = false;
             }
         }
       
         if(x[0] >= 300){
             inGame = false;
         }
         
         if(y[0] >= 300){
             inGame = false;
        }
     
         if(x[0] < 0){
             inGame = false;
         }
         
         if(y[0] < 0){
             inGame = false;
         }
         
         if(!inGame){
             timer.stop();
         }
         
     }
     
     public void move(){
         
         for(int z = dots ; z > 0 ;z--){
             x[z] = x[z - 1];
             y[z] = y[z - 1];
         }
         
         if(leftDirection){
             x[0] = x[0] - DOT_SIZE;
         }
         
         if(rightDirection){
             x[0] = x[0] + DOT_SIZE;
         }
         
          if(upDirection){
             y[0] = y[0] - DOT_SIZE;
         }
         
         if(downDirection){
             y[0] = y[0] + DOT_SIZE;
         }
         
         
     }
     
     private class TAdapter extends KeyAdapter{
         
         public void keyPressed(KeyEvent e) 
         {
            int key =  e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                
                leftDirection = true;
                upDirection = false;
                downDirection = false;
             }
                
              if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                
                rightDirection = true;
                upDirection = false;
                downDirection = false;
             }
                
                if(key == KeyEvent.VK_UP && (!downDirection)){
                
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
             }
                
                  if(key == KeyEvent.VK_DOWN && (!upDirection)){
                
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
             }
                
         }
     }
     
     
     public static void main(String[] args) {
        new Board().setVisible(true);
    }
}