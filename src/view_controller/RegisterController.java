package view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vista.MainView;
import vista.RegisterView;

public class RegisterController implements ActionListener{
    
    private RegisterView registerView;

    public RegisterController (RegisterView current) {
        registerView = current;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println(registerView.getEmailField().getText());
        checkFieldsComplete();
        registerView.setVisible(false);
        MainView.getLogInView().setVisible(true);
    }
    
    private void checkFieldsComplete() {
        if (registerView.getEmailField().getText() == "") 
            new JOptionPane().showMessageDialog(registerView, 
                    "Ingrese un correo electronico.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        if (registerView.getNameField().getText() == "") 
            new JOptionPane().showMessageDialog(registerView, 
                    "Ingrese un display name.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        if (registerView.getMobileField().getText() == "") 
            new JOptionPane().showMessageDialog(registerView, 
                    "Ingrese un numero telefonico.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        
    }
    
}
