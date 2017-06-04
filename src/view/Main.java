package view;

import controller.Controller;

import javax.swing.*;

/**
 * Created by Maria on 22.05.2017.
 */
public class Main {

    public static void main(String[] args) {

        try {
            String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
            // устанавливаем LookAndFeel
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel on this platform.");
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel, for some reason.");
        }

        Controller controller = new Controller();
        Interface program = new Interface(controller);
        program.runProgram();
    }
}
