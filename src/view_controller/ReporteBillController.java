package view_controller;

import entities_controllers.BillJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ReporteBillView;

public class ReporteBillController implements ActionListener {
    
    private ReporteBillView currentView;
    
    public ReporteBillController (ReporteBillView vista) {
        currentView = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        printBillGrp();
    }
    
    private void printBillGrp() {
        BillJpaController billCtrl = new BillJpaController(EntityFactorySingleton.getEMF());
        billCtrl.getListGroups();
    }
    
}
