package view_controller;

import entities_controllers.BillJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import vista.ReporteBillView;

public class ReporteBillController implements ActionListener {
    
    private ReporteBillView currentView;
    
    public ReporteBillController (ReporteBillView vista) {
        currentView = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        loadTable();
    }
    
    public void loadTable() {
        BillJpaController billCtrl = new BillJpaController(EntityFactorySingleton.getEMF());
        Object[][] data = billCtrl.billReport();
        String[] columnNames = billCtrl.getArrayGroupNames();
        DefaultTableModel reporModel = new DefaultTableModel(data, columnNames);
        currentView.getReportTable().setModel(reporModel);
    }
    
}
