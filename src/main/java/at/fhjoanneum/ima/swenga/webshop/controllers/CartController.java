/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.fhjoanneum.ima.swenga.webshop.controllers;

import at.fhjoanneum.ima.swenga.webshop.beans.LineItem;
import at.fhjoanneum.ima.swenga.webshop.beans.ShoppingCart;
import at.fhjoanneum.ima.swenga.webshop.entities.Product;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Alex
 */

@Named("cartController")
@SessionScoped
public class CartController implements Serializable {
    
    private ShoppingCart cart = new ShoppingCart();
    public CartController() {
        
    }
    
    public ShoppingCart getCart() {
        return cart;
    }

    public void add(Product product) {
        cart.addProduct(new LineItem(product));
    }
    
}
