package view_controller;

import entities_controllers.DeudaJpaController;
import entities_controllers.UserXGroupJpaController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import vista.TransactionView;

public class TransactionController implements ItemListener, ActionListener {

    private TransactionView currentView;
    private String currentUser;
    private BigDecimal groupID;

    public TransactionController(TransactionView current) {
        currentView = current;
        currentUser = "julio@gmail.com";
        groupID = new BigDecimal("22");
    }
    
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(currentView.getClearBebtCheckBox()))
            if (e.getStateChange() == ItemEvent.SELECTED) 
                onSelectedClearDebtAction();
            else
                onSelectedSinglePayAction();
        if (e.getSource().equals(currentView.getSinglePayCheckBox())) 
            if (e.getStateChange() == ItemEvent.SELECTED) 
                onSelectedSinglePayAction();
            else 
                onSelectedClearDebtAction();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        doneAction();
    }
    
    private void onSelectedClearDebtAction () {
        currentView.getSinglePayCheckBox().setSelected(false);
        currentView.getClearBebtCheckBox().setSelected(true);
        currentView.getAmountField().setEditable(false);
        currentView.getAmountField().setBackground(Color.LIGHT_GRAY);
        currentView.getAmountField().setText("");
        currentView.getDestinyComboBox().setEnabled(false);
    }
    private void onSelectedSinglePayAction () {
        currentView.getSinglePayCheckBox().setSelected(true);
        currentView.getClearBebtCheckBox().setSelected(false);
        currentView.getAmountField().setEditable(true);
        currentView.getAmountField().setText("");
        currentView.getAmountField().setBackground(Color.WHITE);
        currentView.getDestinyComboBox().setEnabled(true);
        
    }
    
    private void doneAction () {
        payListUsers();
    }
    
    private void payListUsers() {
        DeudaJpaController deudaCtrl = new DeudaJpaController(EntityFactorySingleton.getEMF());
        UserXGroupJpaController userGrpCtrl = new UserXGroupJpaController(EntityFactorySingleton.getEMF());
        HashMap <String, BigDecimal> listDeudas = deudaCtrl.getListDeudas(currentUser);
        BigDecimal totalToPay = deudaCtrl.getSumDeudas(currentUser);
        if (totalToPay == null) {
            new JOptionPane().showMessageDialog(currentView,
                    "El usuario no tiene deudas por pagar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println("Total to Pay: " + totalToPay);
        // Descount myself
        userGrpCtrl.addUserBalance(currentUser, groupID, totalToPay.multiply(new BigDecimal("-1")));
        
        for (Map.Entry<String, BigDecimal> entry : listDeudas.entrySet()) {
            String email = entry.getKey();
            System.out.println("Entry: " + email);
            BigDecimal val = entry.getValue();
            System.out.println("Amount: " + val);
            userGrpCtrl.addUserBalance(email, groupID, val);
        }
        deudaCtrl.deleteAllDebts(currentUser);
        
        
    }
    
    
}
