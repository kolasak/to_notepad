import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ApplicationController {

    Log log = new Log();



    public ApplicationController()
    {
        JFrame guiFrame = new JFrame();

//make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Notepad");
        guiFrame.setSize(400,400);
//This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);



//The first JPanel contains a JLabel and JCombobox
        JTextArea textField = new JTextArea("");
        textField.setPreferredSize(new Dimension(400, 400));



// TODO: trzeba się będzie zameldować w osobnej klasie która będzie to wszystko sprawdzać
        Timer t = new Timer(1000, actionEvent -> {

            String text = textField.getText();
            if (!text.equals(log.getLatestState())) {
                System.out.println(text);
                log.updateStatus(text);
            }
        });
        t.start();



        textField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_Z)) {
                    System.out.println("Backward!");

                    textField.setText(log.undoStatus());

                } else if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_Y)) {
                    System.out.println("Forward!");

                    textField.setText(log.redoStatus());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });


//The JFrame uses the BorderLayout layout manager.
//Put the two JPanels and JButton in different areas.
        guiFrame.add(textField, BorderLayout.NORTH);

//make sure the JFrame is visible
        guiFrame.setVisible(true);
    }
}