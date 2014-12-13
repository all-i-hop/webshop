/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.fhjoanneum.ima.swenga.webshop.facades;

import at.fhjoanneum.ima.swenga.webshop.entities.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Alex
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "webshop-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    @Override
    public void edit(Customer entity) {
        if (entity.getId() == null) {
            entity.setPassword(DigestUtils.sha256Hex(entity.getPassword()));
        } else {
            Customer old = this.find(entity.getId());
            if (!old.getPassword().equals(entity.getPassword())) {
                entity.setPassword(DigestUtils.sha256Hex(entity.getPassword()));
            }
        }
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Customer entity) {
        entity.setPassword(DigestUtils.sha256Hex(entity.getPassword()));
        super.create(entity); //To change body of generated methods, choose Tools | Templates. 
    }
    
    public Customer findByEmail(String email){
        return em.createQuery("Select c from Customer c Where c.email= :email",Customer.class)
                .setParameter("email", email).getSingleResult();
    }

}
