package view_controller;

import entities_controllers.UsuarioJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vista.LogInView;
import vista.RegisterView;

public class LogInController implements ActionListener {
    private LogInView currentView;

    public LogInController (JFrame current) {
        currentView = (LogInView) current;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.equals(currentView.getSignInBtn()))
            signInAction();
        if (e.equals(currentView.getRegisterBtn()))
            registerAction();
        
    }
    
    private void registerAction() {
        currentView.setVisible(false);
        RegisterView regView = new RegisterView();
        regView.setVisible(true);
    }
    
    private void signInAction () {
        String email = currentView.getEmailText();
        UsuarioJpaController contro = new UsuarioJpaController( Controller.getEMF() );
        if( contro.findUsuario( email ) != null )
            System.err.println("Existe");
        else
            new JOptionPane().showMessageDialog(currentView, 
                    "El usuario no existe, por favor registrese primero.", 
                    "Usuario No Existe", 
                    JOptionPane.ERROR_MESSAGE);
    }
    
    
    
}
