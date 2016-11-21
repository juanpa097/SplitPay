/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.JButton;
import javax.swing.JTextField;
import view_controller.LogInController;

/**
 *
 * @author JuanPablo
 */
public class LogInView extends javax.swing.JFrame {

    /**
     * Creates new form LogInView
     */
    public LogInView() {
        initComponents();
        logInCtrl = new LogInController(this);
        signInBtn.addActionListener(logInCtrl);
        registerBtn.addActionListener(logInCtrl);
        emailField.addActionListener(logInCtrl);
        reporteBtn.addActionListener(logInCtrl);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        logInLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        registerLabel = new javax.swing.JLabel();
        registerBtn = new javax.swing.JButton();
        signInBtn = new javax.swing.JButton();
        reporteBtn = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logInLabel.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        logInLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logInLabel.setText("Log In");

        emailLabel.setText("Email:");

        registerLabel.setForeground(new java.awt.Color(51, 51, 255));
        registerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        registerLabel.setText("¿No tienes Cuenta? Haz Click En Registrarse.");

        registerBtn.setText("¡Registrarse!");

        signInBtn.setText("Sign In");
        signInBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInBtnActionPerformed(evt);
            }
        });

        reporteBtn.setText("Ver Reporte");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(logInLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 251, Short.MAX_VALUE)
                                .addComponent(emailLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(77, 77, 77)
                                        .addComponent(signInBtn)))
                                .addGap(238, 238, 238))
                            .addComponent(registerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(registerBtn)
                                .addGap(348, 348, 348))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(reporteBtn)
                                .addGap(285, 285, 285))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(logInLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(signInBtn)
                .addGap(61, 61, 61)
                .addComponent(registerLabel)
                .addGap(18, 18, 18)
                .addComponent(registerBtn)
                .addGap(45, 45, 45)
                .addComponent(reporteBtn)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void signInBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signInBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_signInBtnActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel logInLabel;
    private javax.swing.JButton registerBtn;
    private javax.swing.JLabel registerLabel;
    private javax.swing.JButton reporteBtn;
    private javax.swing.JButton signInBtn;
    // End of variables declaration//GEN-END:variables

    private LogInController logInCtrl;
    
    public String getEmailText() {
        return emailField.getText();
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JButton getSignInBtn () {
        return signInBtn;
    }
    
    public JButton getRegisterBtn () {
        return registerBtn;
    }    

    public JButton getReporteBtn() {
        return reporteBtn;
    }
    
}
