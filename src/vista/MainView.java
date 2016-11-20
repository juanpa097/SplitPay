/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import entities.Usuario;
import view_controller.EntityFactorySingleton;

/**
 *
 * @author JuanPablo
 */
public class MainView extends javax.swing.JFrame {

    /**
     * Creates new form MainView
     */
    public MainView() {
        initComponents();
        initializeView();
        logInView.setVisible(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private static LogInView logInView;
    private static CreateGroupView createGroupView;
    private static RegisterView registerView;
    private static AddMemberView addMemberView;
    private static PostBillView postingBillView;
    private static TransactionView transactionView;
    private static MainMenuView mainMenuView;
    
    private static EntityFactorySingleton contro;
    private static Usuario actual_user;
    
    private void initializeView () {
        logInView = new LogInView();
        registerView = new RegisterView();
        createGroupView = new CreateGroupView();
        addMemberView = new AddMemberView();
        postingBillView = new PostBillView();
        transactionView = new TransactionView();
        mainMenuView = new MainMenuView();
    }

    public static LogInView getLogInView() {
        return logInView;
    }
    
    public static Usuario getActual_user() {
        return actual_user;
    }
    
    public static RegisterView getRegisterView() {
        return registerView;
    }
    public static CreateGroupView getCreateGroupView() {
        return createGroupView;
    }
    
    public static AddMemberView getAddMemberView() {
        return addMemberView;
    }

    public static PostBillView getPostingBillView() {
        return postingBillView;
    }

    public static TransactionView getTransactionView() {
        return transactionView;
    }

    public static MainMenuView getMainMenuView() {
        return mainMenuView;
    }
    
    public static void setActualUser( Usuario nuevo_user ) {
        actual_user = nuevo_user;
    }
}
