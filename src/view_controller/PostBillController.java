package view_controller;

import entities_controllers.GrupoJpaController;
import entities_controllers.UserXGroupJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.PostBillView;

public class PostBillController implements ActionListener {

    private PostBillView currentView;

    public PostBillController(PostBillView view) {
        currentView = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(currentView.getConfirmBtn())) {
            if (validateAmountField()) {
                increaseUsersBalance();
            }
        }
    }
    
    private void increaseUsersBalance() {
        UserXGroupJpaController usrXgrpCntrl = new UserXGroupJpaController(EntityFactorySingleton.getEMF());
        Double amount = new Double(currentView.getAmountField().getText());
        Double div = new Double(countPayers());
        amount = amount / div;
        Math.round(amount);
        BigDecimal toAdd = new BigDecimal(amount);
        System.out.println("Amount For Each: " + amount);
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
    
    private boolean validateAmountField() {
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

    private ArrayList<String> payersEmails() {
        ArrayList<String> emails = new ArrayList<>();
        for (int i = 0; i < currentView.getGroupMembersTable().getRowCount(); ++i) {
            Boolean checked = Boolean.valueOf(currentView.getGroupMembersTable().getValueAt(i, 0).toString());
            if (checked) {
                emails.add(currentView.getGroupMembersTable().getValueAt(i, 2).toString());
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

}
