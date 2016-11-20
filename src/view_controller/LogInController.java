package view_controller;

import entities.Usuario;
import entities_controllers.UsuarioJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
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
        if (e.getSource().equals(currentView.getPostBillBtn()))
            postBillAction();
        if (e.getSource().equals(currentView.getTransactionButton()))
            transacionAction();
        if( e.getSource().equals(currentView.getManageGroupBtn()))
            manageGroupAction();
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
    
    // Temporal function for testing the posting a bill view
    private void postBillAction () {
        currentView.setVisible(false);
        MainView.getPostingBillView().setVisible(true);
        MainView.getPostingBillView().setCurrentGroupID(new BigDecimal("22"));
    }
    
    private void transacionAction () {
        currentView.setVisible(false);
        MainView.getTransactionView().setVisible(true);
        
    }
    
    private void manageGroupAction()
    {
        currentView.setVisible(false);
        MainView.getManageGroupView().setGroupID(new BigDecimal("121"));
        MainView.getManageGroupView().init();
        MainView.getManageGroupView().setVisible(true);
    }
    
    
}
