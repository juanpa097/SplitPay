package view_controller;

import entities.Usuario;
import entities_controllers.UsuarioJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
        if (e.getSource().equals(currentView.getPostBillBtn()))
            postBillAction();
        
    }
    
    private void registerAction() {
        currentView.setVisible(false);
        MainView.getRegisterView().setVisible(true);
    }
    
    private void signInAction () {
        String email = currentView.getEmailText();
        UsuarioJpaController contro = new UsuarioJpaController( EntityFactorySingleton.getEMF() );
        Usuario found = contro.findUsuario( email );
        if( found != null )
        {
            System.err.println("Existe");
            currentView.setVisible(false);
            MainView.getCreateGroupView().setVisible(true); // Usado para testear la view de createGroupView
            MainView.setActualUser( found );
        }
        else
            new JOptionPane().showMessageDialog(currentView, 
                    "El usuario no existe, por favor registrese primero.", 
                    "Usuario No Existe", 
                    JOptionPane.ERROR_MESSAGE);
    }
    
    // Temporal function for testing the posting a bill view
    private void postBillAction () {
        currentView.setVisible(false);
        MainView.getPostingBillView().setVisible(true);
        MainView.getPostingBillView().setCurrentGroupID(new BigDecimal("22"));
    }
    
    
    
}
