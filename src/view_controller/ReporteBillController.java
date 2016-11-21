package view_controller;

import entities_controllers.BillJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import vista.MainView;
import vista.ReporteBillView;

public class ReporteBillController implements ActionListener {
    
    private ReporteBillView currentView;
    
    public ReporteBillController (ReporteBillView vista) {
        currentView = vista;
    }

    private void goBack()
    {
        currentView.setVisible(false);
        MainView.getLogInView().setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource().equals( currentView.getGoBackBtn() ) )
            goBack();
    }
    
    public void loadTable() {
        BillJpaController billCtrl = new BillJpaController(EntityFactorySingleton.getEMF());
        Object[][] data = billCtrl.billReport();
        String[] columnNames = billCtrl.getArrayGroupNames();
        DefaultTableModel reporModel = new DefaultTableModel(data, columnNames);
        currentView.getReportTable().setModel(reporModel);
    }
    
}
