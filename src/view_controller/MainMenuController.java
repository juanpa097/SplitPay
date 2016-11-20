package view_controller;

import entities.UserXGroup_;
import entities_controllers.UserXGroupJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import vista.MainMenuView;
import vista.MainView;

public class MainMenuController implements ActionListener {
    private MainMenuView currentView;
    private String currentUser;
    
    public MainMenuController (MainMenuView currtent) {
        currentView = currtent;
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(MainView.getActual_user().getEmail());
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void loadTable (String currentEmail) {
        currentUser = currentEmail;
        UserXGroupJpaController userGrpCtrl = new UserXGroupJpaController(EntityFactorySingleton.getEMF());
        List<Object[]> usersGroups = userGrpCtrl.getUsersGroups(currentEmail);
        Object[][] tableModel = new Object[usersGroups.size()][3];
        for (int i = 0; i < usersGroups.size(); ++i) {
            Object[] tempArray = usersGroups.get(i);
            tableModel[i][0] = tempArray[0];
            tableModel[i][1] = tempArray[1];
            tableModel[i][2] = tempArray[2];
        }
        //String [] columNames =
        DefaultTableModel groupsModel = new DefaultTableModel(tableModel, tableModel);
    }
    
}
