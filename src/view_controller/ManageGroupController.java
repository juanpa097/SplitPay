package view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ManageGroupView;

public class ManageGroupController implements ActionListener
{
    private ManageGroupView currentView;
    
    ManageGroupController(ManageGroupView view )
    {
        currentView = view;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        
    }
    
}
