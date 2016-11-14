package view_controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import vista.LogInView;
import vista.MainView;

public class Controller {
    
    private MainView mainView;
    private RegisterController registerContrl;
    private LogInController logInContrl;
    
    private LogInView logInView;
    
    public static EntityManagerFactory emf = null;
    
    public Controller (LogInView logInView) {
        logInContrl = new LogInController(logInView);
        registerContrl = new  RegisterController(MainView.getRegisterView());
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
