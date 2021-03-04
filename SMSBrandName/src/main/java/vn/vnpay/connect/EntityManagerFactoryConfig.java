package vn.vnpay.connect;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.vnpay.common.GsonCustom;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryConfig {

    private static final Logger LOG = LogManager.getLogger(GsonCustom.class);
    private static final String persistenceUnitName = "smsgw";
    private EntityManagerFactoryConfig() {

    }

    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory(){
        if(entityManagerFactory == null){
           synchronized ((EntityManagerFactoryConfig.class)){
               if(entityManagerFactory == null){
                    entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
                    LOG.info("Create entityManagerFactory success");
                    return entityManagerFactory;
               }
           }
        }
        return entityManagerFactory;
    }
}
