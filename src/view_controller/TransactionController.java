package view_controller;

import entities_controllers.DeudaJpaController;
import entities_controllers.GrupoJpaController;
import entities_controllers.UserXGroupJpaController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.swing.DefaultComboBoxModel;
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
        DefaultComboBoxModel comboModel = new DefaultComboBoxModel(listEmailsGroup());
        currentView.getDestinyComboBox().setModel(comboModel);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(currentView.getClearBebtCheckBox())) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                onSelectedClearDebtAction();
            } else {
                onSelectedSinglePayAction();
            }
        }
        if (e.getSource().equals(currentView.getSinglePayCheckBox())) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                onSelectedSinglePayAction();
            } else {
                onSelectedClearDebtAction();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentView.getClearBebtCheckBox().isSelected()) {
            //payListUsers();
        }
        if (currentView.getSinglePayCheckBox().isSelected()) {
            seeDebts();
        }
    }

    private void onSelectedClearDebtAction() {
        currentView.getSinglePayCheckBox().setSelected(false);
        currentView.getClearBebtCheckBox().setSelected(true);
        currentView.getAmountField().setEditable(false);
        currentView.getAmountField().setBackground(Color.LIGHT_GRAY);
        currentView.getAmountField().setText("");
        currentView.getDestinyComboBox().setEnabled(false);
    }

    private void onSelectedSinglePayAction() {
        currentView.getSinglePayCheckBox().setSelected(true);
        currentView.getClearBebtCheckBox().setSelected(false);
        currentView.getAmountField().setEditable(true);
        currentView.getAmountField().setText("");
        currentView.getAmountField().setBackground(Color.WHITE);
        currentView.getDestinyComboBox().setEnabled(true);

    }

    private void payListUsers() {
        DeudaJpaController deudaCtrl = new DeudaJpaController(EntityFactorySingleton.getEMF());
        UserXGroupJpaController userGrpCtrl = new UserXGroupJpaController(EntityFactorySingleton.getEMF());
        HashMap<String, BigDecimal> listDeudas = deudaCtrl.getListDeudas(currentUser);
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
            BigDecimal val = entry.getValue();
            userGrpCtrl.addUserBalance(email, groupID, val);
        }
        deudaCtrl.deleteAllDebts(currentUser);
    }

    private String[] listEmailsGroup() {
        GrupoJpaController grpCtrl = new GrupoJpaController(EntityFactorySingleton.getEMF());
        Object[][] group = grpCtrl.getGroupMembers(groupID);
        if( group.length > 0 )
        {
            String[] emailList = new String[group.length - 1];
            int idx = 0;
            for (int i = 0; i < group.length; ++i) {
                String em = (String) group[i][1];
                if (!em.equals(currentUser)) {
                    emailList[idx++] = em;
                }
            }
            return emailList;
        }
        else
        {
            String []abc = new String[0];
            return abc;
        }
    }

    private void seeDebts() {
        DeudaJpaController deudaCtrl = new DeudaJpaController(EntityFactorySingleton.getEMF());
        UserXGroupJpaController userGrpCtrl = new UserXGroupJpaController(EntityFactorySingleton.getEMF());
        String toPay = currentView.getDestinyComboBox().getItemAt(currentView.getDestinyComboBox().getSelectedIndex());
        System.out.println("To Pay: " + toPay);
        List<Object[]> deptsToUser = deudaCtrl.deptsToUser(currentUser, toPay);
        if (deptsToUser.isEmpty()) {
            new JOptionPane().showMessageDialog(currentView,
                    "No se le debe nada a " + toPay + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArrayList <Pair <String, BigDecimal> > listDeudas = new ArrayList<>();
        ArrayList<BigDecimal> billsId = new ArrayList<>();
        for (Object[] objects : deptsToUser) {
            String deud = (String) objects[0];
            String acreed = (String) objects[1];
            BigDecimal amount = (BigDecimal) objects[2];
            Pair newPair = new Pair(deud, amount);
            listDeudas.add(newPair);
            BigDecimal id = (BigDecimal) objects[3];
            billsId.add(id);
        }
        
        BigDecimal amountToTransfer = new BigDecimal(currentView.getAmountField().getText());
        userGrpCtrl.addUserBalance(currentUser, groupID, amountToTransfer.multiply(new BigDecimal(-1)));
        userGrpCtrl.addUserBalance(toPay, groupID, amountToTransfer);
        System.err.println("HEre");
        int idx = 0;
        while (amountToTransfer.compareTo(BigDecimal.ZERO) == 1 && idx < listDeudas.size()) {
            Pair <String, BigDecimal> dept = listDeudas.get(idx++);
            System.out.println("Deuda: " + dept.getValue() + " Acreedor: " + dept.getKey());
            if (amountToTransfer.compareTo(dept.getValue()) == 1) {
                Double res = amountToTransfer.doubleValue() - dept.getValue().doubleValue();
                amountToTransfer = new BigDecimal(res);
                Pair newDebt = new Pair(dept.getKey(), BigDecimal.ZERO);
                listDeudas.set(idx - 1, newDebt);
            } else if (amountToTransfer.compareTo(dept.getValue()) == -1) {
                System.err.println(amountToTransfer);
                Double res = dept.getValue().doubleValue() - amountToTransfer.doubleValue();
                Pair newDebt = new Pair(dept.getKey(), new BigDecimal(res));
                listDeudas.set(idx - 1, newDebt);
                break;
            } else if (amountToTransfer.compareTo(dept.getValue()) == 0) {
                Pair newDebt = new Pair(dept.getKey(), BigDecimal.ZERO);
                listDeudas.set(idx - 1, newDebt);
                break;
            }
        }
        deudaCtrl.updateDebts(listDeudas, billsId, currentUser);
        
        
        
    }
    
    private boolean validateAmountField () {
        String text = currentView.getAmountField().getText();
        if (text.equals("")) {
            new JOptionPane().showMessageDialog(currentView,
                    "Ingrese un monto a transferir.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isInteger(text)) {
            new JOptionPane().showMessageDialog(currentView,
                    "Ingrese un valor valido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
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
