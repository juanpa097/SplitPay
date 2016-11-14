package view_controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import vista.View;

public class Controller {
    
    private LogInController logInContrl;
    private RegisterController registerContrl;
    public static EntityManagerFactory emf = null;

    
    public Controller (View currentView) {
        logInContrl = new LogInController(currentView.getLogInView());
        registerContrl = new  RegisterController(currentView.getRegisterView());
    }
    
    public LogInController getLogInController() {
        return logInContrl;
    }
    
    public static EntityManagerFactory getEMF() {
        if (emf == null)
            emf = Persistence.createEntityManagerFactory("SplitPayPU");
        return emf;
    }
}
