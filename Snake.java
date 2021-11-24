/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame;

import javax.swing.JFrame;

/**
 *
 * @author gurja
 */
public class Snake extends JFrame {
    
    Snake(){
        
        Board b=new Board();
         
        add(b);
         pack();
        
         setLocationRelativeTo(null);
          
         setTitle("Snake Game");
         setResizable(false);
         
         
    }
    
    public static void main(String[] args){
        
        new Snake().setVisible(true);
    }

  
}