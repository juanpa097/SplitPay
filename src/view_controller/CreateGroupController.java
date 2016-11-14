package view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.CreateGroupView;
import vista.MainView;

public class CreateGroupController implements ActionListener
{
    private CreateGroupView currentView;
    
    public CreateGroupController( CreateGroupView otherView )
    {
        this.currentView = otherView;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource().equals( currentView.getAddMemberBtn()) )
        {
            MainView.getAddMemberView().getEmail_textField().setText("");
            MainView.getCreateGroupView().setVisible(false);
            MainView.getAddMemberView().setVisible(true);
        }
        else if( e.getSource().equals( currentView.getAddGroupBtn() ) )
        {
            
        }
    }
    
}
