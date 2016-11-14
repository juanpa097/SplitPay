package view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.AddMemberView;

public class AddMemberController implements ActionListener
{
    private AddMemberView currentView;
    
    public AddMemberController( AddMemberView vista )
    {
        currentView = vista;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
