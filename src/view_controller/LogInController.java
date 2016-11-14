package view_controller;

import entities_controllers.UsuarioJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import vista.LogInView;
import vista.MainView;

public class LogInController implements ActionListener {
    private LogInView currentView;
    
    public LogInController (LogInView logInView) {
        currentView = logInView;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {        
        if (e.getSource().equals(currentView.getSignInBtn()))
            signInAction();
        if (e.getSource().equals(currentView.getRegisterBtn()))
            registerAction();
        
    }
    
    private void registerAction() {
        currentView.setVisible(false);
        MainView.getRegisterView().setVisible(true);
    }
    
    private void signInAction () {
        String email = currentView.getEmailText();
        UsuarioJpaController contro = new UsuarioJpaController( EntityFactorySingleton.getEMF() );
        if( contro.findUsuario( email ) != null )
            System.err.println("Existe");
        else
            new JOptionPane().showMessageDialog(currentView, 
                    "El usuario no existe, por favor registrese primero.", 
                    "Usuario No Existe", 
                    JOptionPane.ERROR_MESSAGE);
    }
    
    
    
}
