package view_controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityFactorySingleton {    
    private static EntityManagerFactory emf = null;
    public static EntityManagerFactory getEMF() {
        if (emf == null)
            emf = Persistence.createEntityManagerFactory("SplitPayPU");
        return emf;
    }
}
