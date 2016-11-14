package view_controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import vista.LogInView;

public class Controller {
    
    private LogInController logInContrl;
    private RegisterController registerContrl;
    
    public static EntityManagerFactory emf = null;
    
    public Controller (LogInView currentView) {
        logInContrl = new LogInController(currentView);
        registerContrl = new  RegisterController(currentView);
    }
    
    public LogInController getLogInController() {
        return logInContrl;
    }

    public RegisterController getRegisterContrl() {
        return registerContrl;
    }
    
    public static EntityManagerFactory getEMF() {
        if (emf == null)
            emf = Persistence.createEntityManagerFactory("SplitPayPU");
        return emf;
    }
    
 
    
}
