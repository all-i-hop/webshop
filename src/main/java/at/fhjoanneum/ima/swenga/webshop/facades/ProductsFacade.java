/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.fhjoanneum.ima.swenga.webshop.facades;

import at.fhjoanneum.ima.swenga.webshop.dtos.ProductPreview;
import at.fhjoanneum.ima.swenga.webshop.entities.Image;
import at.fhjoanneum.ima.swenga.webshop.entities.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alex
 */
@Stateless
public class ProductsFacade extends AbstractFacade<Product> {
    @PersistenceContext(unitName = "webshop-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Product.class);
    }
    
    public List<ProductPreview> findPreviews(int start, int length)
    {
        return em.createQuery("select new at.fhjoanneum.ima.swenga.webshop.dtos.ProductPreview("
                                + "p.id, p.name, p.shortdescription, p.price, i.id) "
                                + "from Product p LEFT OUTER JOIN p.images i "
                                + "where i.id = (select min(i2.id) from p.images i2) "
                                + "or i.id is NULL", ProductPreview.class)
                                .setFirstResult(start)
                                .setMaxResults(length)
                                .getResultList();
    }
    
    public Image findImageById(Long id)
    {
        return em.find(Image.class, id);
    }
    
}
