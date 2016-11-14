package view_controller;

import entities.Usuario;
import entities_controllers.UsuarioJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.MainView;
import vista.RegisterView;

public class RegisterController implements ActionListener {

    private RegisterView registerView;

    public RegisterController(RegisterView current) {
        registerView = current;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (checkFieldsComplete() && insertUsuario(constructUser())) {
            registerView.setVisible(false);
            MainView.getLogInView().setVisible(true);
        }
    }

    private Usuario constructUser() {
        String email = registerView.getEmailField().getText();
        String name = registerView.getNameField().getText();
        String mvlStr = registerView.getMobileField().getText();
        BigDecimal movile = new BigDecimal(mvlStr);
        Usuario newUser = new Usuario(email, name, movile);
        return newUser;
    }
    
    private boolean insertUsuario (Usuario toInsert) {
        UsuarioJpaController contro = new UsuarioJpaController( EntityFactorySingleton.getEMF() );
        try {
            contro.create(toInsert);
        } catch (Exception ex) {
            new JOptionPane().showMessageDialog(registerView,
                    "Usuario ya Exixte.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
            // Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private boolean checkFieldsComplete() {
        if (registerView.getEmailField().getText().equals("")) {
            new JOptionPane().showMessageDialog(registerView,
                    "Ingrese un correo electronico.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (registerView.getNameField().getText().equals("")) {
            new JOptionPane().showMessageDialog(registerView,
                    "Ingrese un display name.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (registerView.getMobileField().getText().equals("")) {
            new JOptionPane().showMessageDialog(registerView,
                    "Ingrese un numero telefonico.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isInteger(registerView.getMobileField().getText())) {
            new JOptionPane().showMessageDialog(registerView,
                    "Ingrese un numero telefonico valido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

}
