package view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vista.RegisterView;

public class RegisterController implements ActionListener{
    
    private RegisterView registerView;

    public RegisterController (JFrame current) {
        registerView = (RegisterView) current;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
