package vista;

import javax.swing.JFrame;

public class View extends JFrame {
    //private RegisterView registerView;
    //private LogInView logInView;
    //private static View instance = null;
    
    public View() {
        registerView = new RegisterView();
        logInView = new LogInView();
    }

    public RegisterView getRegisterView() {
        return registerView;
    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    public LogInView getLogInView() {
        return logInView;
    }

    public void setLogInView(LogInView logInView) {
        this.logInView = logInView;
    }
    /*
    public static View getInstanceOfView() {
        if (instance == null)
            instance = new View();
        return instance;
    }
*/
    
}
