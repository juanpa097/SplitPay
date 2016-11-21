package vista;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import view_controller.TransactionController;

public class TransactionView extends javax.swing.JFrame {

    public TransactionView() {
        initComponents();
        transactionCrtl = new TransactionController(this);
        clearBebtCheckBox.addItemListener(transactionCrtl);
        singlePayCheckBox.addItemListener(transactionCrtl);
        doneButton.addActionListener(transactionCrtl);
        goBackBtn.addActionListener(transactionCrtl);
    }

    public TransactionController getTransactionCrtl() {
        return transactionCrtl;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tittleLabel = new javax.swing.JLabel();
        destinyLabel = new javax.swing.JLabel();
        destinyComboBox = new javax.swing.JComboBox<>();
        singlePayCheckBox = new javax.swing.JCheckBox();
        clearBebtCheckBox = new javax.swing.JCheckBox();
        amountField = new javax.swing.JTextField();
        doneButton = new javax.swing.JButton();
        goBackBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tittleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        tittleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tittleLabel.setText("Transaction");

        destinyLabel.setText("Transactio Destiny: ");

        destinyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        singlePayCheckBox.setSelected(true);
        singlePayCheckBox.setText("$");
        singlePayCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                singlePayCheckBoxActionPerformed(evt);
            }
        });

        clearBebtCheckBox.setText("Clear all debt");
        clearBebtCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBebtCheckBoxActionPerformed(evt);
            }
        });

        amountField.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        amountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountFieldActionPerformed(evt);
            }
        });

        doneButton.setText("Done");
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });

        goBackBtn.setText("Go Back");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(212, 212, 212)
                                .addComponent(destinyLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(destinyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(244, 244, 244)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(doneButton)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(clearBebtCheckBox)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(singlePayCheckBox)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(goBackBtn)))
                        .addGap(0, 230, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tittleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(goBackBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tittleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(destinyLabel)
                    .addComponent(destinyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(singlePayCheckBox)
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearBebtCheckBox)
                .addGap(28, 28, 28)
                .addComponent(doneButton)
                .addContainerGap(248, Short.MAX_VALUE))
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

    private void singlePayCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_singlePayCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_singlePayCheckBoxActionPerformed

    private void clearBebtCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBebtCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearBebtCheckBoxActionPerformed

    private void amountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountFieldActionPerformed

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_doneButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amountField;
    private javax.swing.JCheckBox clearBebtCheckBox;
    private javax.swing.JComboBox<String> destinyComboBox;
    private javax.swing.JLabel destinyLabel;
    private javax.swing.JButton doneButton;
    private javax.swing.JButton goBackBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox singlePayCheckBox;
    private javax.swing.JLabel tittleLabel;
    // End of variables declaration//GEN-END:variables
    
    private TransactionController transactionCrtl;

    public JTextField getAmountField() {
        return amountField;
    }

    public JCheckBox getClearBebtCheckBox() {
        return clearBebtCheckBox;
    }

    public JComboBox<String> getDestinyComboBox() {
        return destinyComboBox;
    }

    public JButton getDoneButton() {
        return doneButton;
    }

    public JCheckBox getSinglePayCheckBox() {
        return singlePayCheckBox;
    }

    public JButton getGoBackBtn() {
        return goBackBtn;
    }
    
    
}
