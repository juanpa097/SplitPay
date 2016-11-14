package view_controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityFactorySingleton {    
    public static EntityManagerFactory emf = null;
    public static EntityManagerFactory getEMF() {
        if (emf == null)
            emf = Persistence.createEntityManagerFactory("SplitPayPU");
        return emf;
    }
}
