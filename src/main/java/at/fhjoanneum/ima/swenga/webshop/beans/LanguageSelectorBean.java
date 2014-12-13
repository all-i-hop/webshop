/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.fhjoanneum.ima.swenga.webshop.beans;

import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
/**
 *
 * @author Alex
 */
@Named("languageSelectorBean") @SessionScoped
public class LanguageSelectorBean implements Serializable {

    private Locale locale;

    public LanguageSelectorBean() {
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public void changeLocale(String loc) {
        locale = new Locale(loc);
    }

    public String getLocale() {
        return locale.toString();
    }
}
