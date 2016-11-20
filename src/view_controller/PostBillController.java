package view_controller;

import entities.Bill;
import entities_controllers.BillJpaController;
import entities_controllers.DeudaJpaController;
import entities_controllers.GrupoJpaController;
import entities_controllers.UserXGroupJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.PostBillView;

public class PostBillController implements ActionListener {

    private PostBillView currentView;
    private File imageFile;
    private BigDecimal idGroup;
    private String currentUser;
    
    
    public PostBillController(PostBillView view) {
        currentView = view;
        idGroup = new BigDecimal(22);
        currentUser = "juanpenaloza@gmail.com";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(currentView.getConfirmBtn())) {
            if (validateFields()) {
                increaseUsersBalance();
                insertBill();
                generateOwes();
            }
        }
        if (e.getSource().equals(currentView.getSelectImageBtn())) {
            JFileChooser jFile = new JFileChooser();
            jFile.showOpenDialog(null);
            imageFile = jFile.getSelectedFile();
        }
    }
    
    private void increaseUsersBalance() {
        UserXGroupJpaController usrXgrpCntrl = new UserXGroupJpaController(EntityFactorySingleton.getEMF());
        Double amount = new Double(currentView.getAmountField().getText());
        usrXgrpCntrl.addUserBalance(currentUser, idGroup, new BigDecimal(amount * -1));
        Double div = countPayers();
        amount = amount / div;
        Math.round(amount);
        BigDecimal toAdd = new BigDecimal(amount);
        try {
            usrXgrpCntrl.encreaseUsersBalance(payersEmails(), currentView.getCurrentGroupID(), toAdd);
        } catch (Exception ex) {
            new JOptionPane().showMessageDialog(currentView,
                    "El usuario no existe.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(PostBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean validateFields() {
        if (!isInteger(currentView.getAmountField().getText())) {
            new JOptionPane().showMessageDialog(currentView,
                    "Ingrese un amount valido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (isAmountEmpty()) {
            new JOptionPane().showMessageDialog(currentView,
                    "Ingrese un amount.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (isTittleEmpty()) {
            new JOptionPane().showMessageDialog(currentView,
                    "Ingrese unmbre para el Bill.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (countPayers() == 0.0) {
            new JOptionPane().showMessageDialog(currentView,
                    "Seleccione usuarios para pagar..",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
        
    }

    private boolean isAmountEmpty() {
        return currentView.getAmountField().getText().equals("");
    }
    
    private boolean isTittleEmpty() {
        return currentView.getTittleBillField().getText().equals("");
    }

    private ArrayList<String> payersEmails() {
        ArrayList<String> emails = new ArrayList<>();
        for (int i = 0; i < currentView.getGroupMembersTable().getRowCount(); ++i) {
            Boolean checked = Boolean.valueOf(currentView.getGroupMembersTable().getValueAt(i, 0).toString());
            if (checked) {
                String email = currentView.getGroupMembersTable().getValueAt(i, 2).toString();
                emails.add(email);
            }
        }
        return emails;
    }
    
    private double countPayers() {
        double cont = 0.0;
        for (int i = 0; i < currentView.getGroupMembersTable().getRowCount(); ++i) {
            Boolean checked = Boolean.valueOf(currentView.getGroupMembersTable().getValueAt(i, 0).toString());
            if (checked) {
                cont+= 1.0;
            }
        }
        return cont;
    }

    public void loadTable() {
        GrupoJpaController groupJpaCtrl = new GrupoJpaController(EntityFactorySingleton.getEMF());
        Object[][] usersInGroup = groupJpaCtrl.getGroupMembers(currentView.getCurrentGroupID());

        DefaultTableModel groupMembersModel = (DefaultTableModel) currentView.getGroupMembersTable().getModel();
        for (int i = 0; i < usersInGroup.length; ++i) {
            Object[] row = {false, usersInGroup[i][0], usersInGroup[i][1]};
            groupMembersModel.addRow(row);
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
    
    private double getAmount() {
        Double amount = new Double(currentView.getAmountField().getText());
        return amount;
    }
    
    private void insertBill() {
        BillJpaController billCtrl = new BillJpaController(EntityFactorySingleton.getEMF());
        BigDecimal amoun = new BigDecimal(currentView.getAmountField().getText());
        String tittle = currentView.getTittleBillField().getText();
        String responsable = currentUser;
        //BigDecimal groupID = currentView.getCurrentGroupID();
        // TODO: Estos valores no son correctors! 
        BigDecimal groupID = idGroup;

        try {
            billCtrl.insertBill( amoun, tittle, responsable, groupID);
        } catch (SQLException ex) {
            Logger.getLogger(PostBillController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PostBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void generateOwes () {
        ArrayList<String> payersEmails = payersEmails();
        double amount = getAmount();
        double numPayer = countPayers();
        double incrEach = amount / numPayer;
        Math.round(incrEach);
        BigDecimal toAdd = new BigDecimal(incrEach);
        BillJpaController billCtrl = new BillJpaController(EntityFactorySingleton.getEMF());
        DeudaJpaController deudaCtrl = new DeudaJpaController(EntityFactorySingleton.getEMF());
        try {
            Bill bill = billCtrl.findBill(billCtrl.getLastBillID());
            deudaCtrl.addOwers(payersEmails, toAdd, bill);
        } catch (Exception ex) {
            Logger.getLogger(PostBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
