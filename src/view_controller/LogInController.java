package view_controller;

import entities.Usuario;
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
        if (e.getSource().equals(currentView.getEmailField()))
            signInAction();
        if (e.getSource().equals(currentView.getRegisterBtn()))
            registerAction();
        // TEST
        if (e.getSource().equals(currentView.getReporteBtn()))
            reportAction();
    }
    
    private void registerAction() {
        currentView.setVisible(false);
        MainView.getRegisterView().init();
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
            //MainView.getCreateGroupView().setVisible(true);// Usado para testear la view de createGroupView
            MainView.setActualUser( found );
            MainView.getMainMenuView().getMenuCtrl().loadTable(email);
            MainView.getMainMenuView().setVisible(true); // Usado para testear la view de createGroupView
        }
        else
            new JOptionPane().showMessageDialog(currentView,
                    "El usuario no existe, por favor registrese primero.", 
                    "Usuario No Existe", 
                    JOptionPane.ERROR_MESSAGE);
    }
    
    private void reportAction() {
        currentView.setVisible(false);
        MainView.getReporteBillView().setVisible(true);
        MainView.getReporteBillView().getReportCtrl().loadTable();
    }
}
