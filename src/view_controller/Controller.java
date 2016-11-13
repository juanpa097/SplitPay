package view_controller;

import javax.swing.JFrame;

public class Controller {
    
    private LogInController logInContrl;
    
    public Controller (JFrame currentView) {
        logInContrl = new LogInController(currentView);
    }
    
    public LogInController getLogInController() {
        return logInContrl;
    }
    
}
