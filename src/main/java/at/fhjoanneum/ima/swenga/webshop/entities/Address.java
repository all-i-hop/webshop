/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.fhjoanneum.ima.swenga.webshop.entities;

import at.fhjoanneum.ima.swenga.webshop.controllers.OrderController;
import java.io.Serializable;
import java.util.Objects;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Alex
 */
@Embeddable  // In order to make it a weak entity
public class Address implements Serializable {

    @NotBlank
    @Size(max = 8)
    @Column(nullable = false, length = 8)
    private String zipCode;
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String city;
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String street;

    public Address(String zipCode, String city, String street) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
    }

    @Override
    public String toString() {
        return this.street + ", " + this.city + ", " + this.zipCode; //To change body of generated methods, choose Tools | Templates.
    }
    
    

    public Address() {
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.zipCode);
        hash = 23 * hash + Objects.hashCode(this.city);
        hash = 23 * hash + Objects.hashCode(this.street);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        return true;
    }

    
    @Named("addressConverter")
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
