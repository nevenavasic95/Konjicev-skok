/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package konjicevskok;

import javax.swing.SwingUtilities;

/**
 *
 * @author Nevena
 */
public class StartGame {
    
      public static void main(String[] args) {
        // TODO code application logic here
    	SwingUtilities.invokeLater(new Runnable(){
			public void run(){
                            Frame.createGUI();
			}
		});    
    }
  
}
