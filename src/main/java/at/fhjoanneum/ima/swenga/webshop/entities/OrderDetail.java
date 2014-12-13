/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.fhjoanneum.ima.swenga.webshop.entities;

import at.fhjoanneum.ima.swenga.webshop.controllers.OrderController;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alex
 */
@Embeddable
public class OrderDetail implements Serializable {
    
    @Min(1)
    @Column(nullable = false)
    private int pos;
    @Min(1)
    @Column(nullable = false)
    private int quantity;
    @Min(0)
    @Column(nullable = false)
    private double unitprice;
    @Min(0)
    @Column(nullable = false)
    private double priceTotal;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "productID",nullable = false) // JoinColumn creates a foreignkey to Product class
    private Product product;

    public OrderDetail() {
    }

    public OrderDetail(int pos, int quantity, double unitprice, Product product) {
        this.pos = pos;
        this.quantity = quantity;
        this.unitprice = unitprice;
        this.product = product;
        this.priceTotal = quantity * unitprice;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    @Override
    public String toString() {
        return "Name: " + this.product.getName() + " Quantity: " + this.quantity;
    }

    
    
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
    @Named("orderDetailConverter")
    @FacesConverter(forClass = Address.class)
    public static class AddressConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrderController controller = (OrderController) context.getApplication().getELResolver().
                    getValue(context.getELContext(), null, "orderController");
            int hash = Integer.parseInt(value);
            for (Address address : controller.getSelected().getCustomer().getAddresses()) {
                if (hash == (address.getZipCode() + address.getCity() + address.getStreet()).hashCode()) {
                    return address;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Address) {
                Address address = (Address) object;
                return Integer.toString((address.getZipCode() + address.getCity() + address.getStreet()).hashCode());
            } else {
                return null;
            }
        }

    }
    
    
}
