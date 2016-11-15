package view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.PostBillView;

public class PostBillController implements ActionListener {
    private PostBillView currentView;
    
    public PostBillController (PostBillView view) {
        currentView = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
