package view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vista.LogInView;

public class LogInController implements ActionListener {
    private LogInView currentView;

    public LogInController (JFrame current) {
        currentView = (LogInView) current;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = currentView.getEmailText();
        System.out.println(text);
    }
    
    
    
}
