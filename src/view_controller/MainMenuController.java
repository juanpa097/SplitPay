package view_controller;

import entities_controllers.UserXGroupJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import vista.MainMenuView;
import vista.MainView;

public class MainMenuController extends MouseAdapter implements ActionListener {

    private MainMenuView currentView;
    private String currentUser;

    public MainMenuController(MainMenuView currtent) {
        currentView = currtent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        createGroupAction();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getSource().equals(currentView.getGroupsTable())) {
            int row = currentView.getGroupsTable().getSelectedRow();
            BigDecimal groupId = (BigDecimal) currentView.getGroupsTable().getModel().getValueAt(row, 0);
            selectGroupAction(groupId);
        }
    }
    
    
    
    private void selectGroupAction(BigDecimal groupId)
    {
        currentView.setVisible(false);
        MainView.getViewGroupView().setGroupID(groupId);
        MainView.getViewGroupView().setVisible(true);
        MainView.getViewGroupView().init();
    }
    
    private void createGroupAction() {
        MainView.getMainMenuView().setVisible(false);
        MainView.getCreateGroupView().setVisible(true);
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void loadTable(String currentEmail) {
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
        String[] columNames = {"ID", "Group Name", "My Balance"};
        DefaultTableModel groupsModel = new DefaultTableModel(tableModel, columNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        currentView.getGroupsTable().setModel(groupsModel);
        currentView.getGroupsTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }



}
