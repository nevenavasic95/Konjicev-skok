package konjicevskok;

import javax.swing.*;
import java.util.Stack;

public class Frame {

    public static void createGUI(){
        Board frame = new Board("Konjicev skok");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
