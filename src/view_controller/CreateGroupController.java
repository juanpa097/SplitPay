package view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.CreateGroupView;

public class CreateGroupController implements ActionListener
{
    private CreateGroupView currentView;
    
    public CreateGroupController( CreateGroupView otherView )
    {
        this.currentView = otherView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
