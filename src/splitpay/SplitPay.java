package splitpay;

import entities.Usuario;
import entities_controllers.UsuarioJpaController;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SplitPay {
    public static void main(String[] args) {
        System.out.println("Hello");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SplitPayPU");
        UsuarioJpaController userContrl = new UsuarioJpaController(emf);
        Usuario newUsr = new Usuario("julio@gmail.com", "Julio", new BigDecimal("3122582685"));
        
        try {
            userContrl.create(newUsr);
        } catch (Exception ex) {
            Logger.getLogger(SplitPay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}   
