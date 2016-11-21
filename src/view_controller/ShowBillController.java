package view_controller;

import entities_controllers.BillJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import vista.ShowBillView;

public class ShowBillController implements ActionListener {

    private ShowBillView currentView;
    private BigDecimal currentGroup;
    
    public ShowBillController(ShowBillView current) {
        currentView = current;
        currentGroup = new BigDecimal(221);
        loadTable();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame f = new JFrame();
        try {
            f.setContentPane(new JLabel(new ImageIcon(ImageIO.read(getBillImage()))));
        } catch (IOException ex) {
            Logger.getLogger(ShowBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        f.pack();
        f.setVisible(true);
        
    }
    
    private void loadTable() {
        BillJpaController billCtrl = new BillJpaController(EntityFactorySingleton.getEMF());
        String [] columnNames = {"ID", "FECHA", "TITTLE", "ID_RESPONSABLE", "AMOUNT"};
        Object[][] data = billCtrl.getGroupBill(currentGroup);
        DefaultTableModel billsModel = new DefaultTableModel(data, columnNames);
        currentView.getBillsTable().setModel(billsModel);
    }
    
    private File getBillImage() {
        int row = currentView.getBillsTable().getSelectedRow();
        BigDecimal groupId = new BigDecimal( (String) currentView.getBillsTable().getModel().getValueAt(row, 0));
        BillJpaController billCtrl = new BillJpaController(EntityFactorySingleton.getEMF());
        File toRet = null;
        try {
            toRet = billCtrl.getBillImage(groupId);
        } catch (SQLException ex) {
            Logger.getLogger(ShowBillController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShowBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toRet;
    }
    
}
