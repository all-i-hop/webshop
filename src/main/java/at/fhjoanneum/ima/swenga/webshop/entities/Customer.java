/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.fhjoanneum.ima.swenga.webshop.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Alex
 */

@NamedQueries({
@NamedQuery(name = "Customer.findByEmail",
            query = "Select c from Customer c where c.email = :email")
})
@Entity
public class Customer implements Serializable {
    public enum UserRole {USER,ADMINISTRATOR};
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank @Size(max = 50)
    @Pattern(regexp = "\\D+",message = "{name_with_numbers}")
    @Column(nullable = false, length = 50)
    private String firstname;
    
    @NotBlank @Size(max = 50)
    @Pattern(regexp = "\\D+",message = "{name_with_numbers}")
    @Column(nullable = false, length = 50)
    private String lastname;
    
    @NotBlank @Size(max = 50) 
    @Email
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    
    @ElementCollection
    @Size(min = 1)
    private Set<Address> addresses = new HashSet<>();

    @ElementCollection
    @Basic
    @Enumerated(EnumType.STRING)
    @Size(min = 1)
    private List<UserRole> roles = new ArrayList<>();
    
    @NotBlank
    @Size(min=8)
    private String password;

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.lastname + ", " + this.firstname;
    }
    
}
