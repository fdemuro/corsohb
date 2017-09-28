package it.consoftinformatica.corsoHibernate.lezione1;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import it.consoftinformatica.corsoHibernate.domain.Instrument;


public class ContainerManagedJPATest {

    @Resource
    private EntityManager manager = null;

    public void persistNewInstrument() {
        Instrument instrument = new Instrument();
        instrument.setIssue("IBM");
        manager.persist(instrument);
    }
    
    public void findInstrument() {
        manager.getReference(Instrument.class,1);
    }

    public void removeInstrument() {
        manager.remove(1);
    }
    public static void main(String[] args) {
        ContainerManagedJPATest test = new ContainerManagedJPATest();
    }
}

