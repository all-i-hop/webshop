/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.fhjoanneum.ima.swenga.webshop.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alex
 */


public class ShoppingCart {

    
    private Map<Long, LineItem> products = new HashMap<>();

    public Collection<LineItem> getProducts() {
        return new ArrayList<>(products.values());
    }

    public boolean isEmpty() {
        return (products.values().isEmpty());
    }

    public void addProduct(LineItem newLineItem) {
        if (products.containsKey(newLineItem.getProduct().getId())) {
            products.get(newLineItem.getProduct().getId()).increaseQuantity(1);
            return;
        }
        products.put(newLineItem.getProduct().getId(), newLineItem);
    }

    public Double getTotal() {
        Double total = 0D;
        for (LineItem myLineItem : products.values()) {
            total += myLineItem.getLinePrice();
        }
        return total;
    }

    public int getNumberOfItems() {
        int numberOfItems = 0;
        for (LineItem myItem : products.values()) {
            numberOfItems = numberOfItems + myItem.getQuantity();
        }
        return numberOfItems;
    }

    public void remove(Long id) {
        products.remove(id);
    }

}
