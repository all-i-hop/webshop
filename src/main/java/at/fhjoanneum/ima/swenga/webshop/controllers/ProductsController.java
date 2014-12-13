package at.fhjoanneum.ima.swenga.webshop.controllers;

import at.fhjoanneum.ima.swenga.webshop.controllers.util.ImageUtils.MaxType;
import static at.fhjoanneum.ima.swenga.webshop.controllers.util.ImageUtils.resizeImage;
import at.fhjoanneum.ima.swenga.webshop.controllers.util.JsfUtil;
import at.fhjoanneum.ima.swenga.webshop.controllers.util.JsfUtil.PersistAction;
import static at.fhjoanneum.ima.swenga.webshop.controllers.util.JsfUtil.addErrorMessage;
import static at.fhjoanneum.ima.swenga.webshop.controllers.util.JsfUtil.addErrorMessage;
import static at.fhjoanneum.ima.swenga.webshop.controllers.util.JsfUtil.addSuccessMessage;
import at.fhjoanneum.ima.swenga.webshop.dtos.ProductPreview;
import at.fhjoanneum.ima.swenga.webshop.entities.Image;
import at.fhjoanneum.ima.swenga.webshop.entities.Product;
import at.fhjoanneum.ima.swenga.webshop.facades.ProductsFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


@Named("productsController")
@SessionScoped
public class ProductsController implements Serializable {

    @Inject
    private at.fhjoanneum.ima.swenga.webshop.facades.ProductsFacade ejbFacade;
    private List<Product> items = null;
    private LazyDataModel<ProductPreview> products = null;
    private Product selected;
    private ProductPreview selectedPreview;

    public ProductsController() {
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            Image img = new Image();
            img.setName(event.getFile().getFileName());
            img.setType(event.getFile().getContentType());
            String fileType = img.getType().split("/")[1];
            img.setContent(resizeImage(event.getFile().getInputstream(), fileType, 600, MaxType.MAX_WIDTH));
            img.setThumbnail(resizeImage(event.getFile().getInputstream(), fileType, 80, MaxType.MAX_HEIGHT));
            img.setCreatedAt(new Date());
            getSelected().getImages().add(img);
            addSuccessMessage(JsfUtil.getMessage(FacesContext.getCurrentInstance(), "fileupload_successful", event.getFile().getFileName()));
 
        } catch (IOException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(JsfUtil.getMessage(FacesContext.getCurrentInstance(), "bundle.fileupload_error"));
        }
    }

    public LazyDataModel<ProductPreview> getProducts() {
        if (products == null)
        {
            products = new LazyDataModel<ProductPreview>()
            {
                @Override
                public List<ProductPreview> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    return ejbFacade.findPreviews(first, pageSize); //everytime load a new page it will ask for the data 0..10, 11..20
                }
            };
            products.setRowCount(ejbFacade.count()); //sets size of records
        }
        return products;
    }

    public void setProducts(LazyDataModel<ProductPreview> products) {
        this.products = products;
    }

    public ProductPreview getSelectedPreview() {
        return selectedPreview;
    }

    public void setSelectedPreview(ProductPreview selectedPreview) {
        this.selectedPreview = selectedPreview;
        setSelected(ejbFacade.find(selectedPreview.getId()));
    }
    
   
    public Product getSelected() {
        return selected;
    }

    public void setSelected(Product selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProductsFacade getFacade() {
        return ejbFacade;
    }

    public Product prepareCreate() {
        selected = new Product();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, JsfUtil.getMessage(FacesContext.getCurrentInstance(),"ProductsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null; 
            products = null; 
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, JsfUtil.getMessage(FacesContext.getCurrentInstance(),"ProductsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, JsfUtil.getMessage(FacesContext.getCurrentInstance(),"ProductsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null;  //remove selection
            items = null;    //invalidate list of items to trigger re-query 
            products = null;
        }
    }

    public List<Product> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(JsfUtil.getMessage(FacesContext.getCurrentInstance(),"PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(JsfUtil.getMessage(FacesContext.getCurrentInstance(),"PersistenceErrorOccured"));
            }
        }
    }

    public Product getProducts(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Product> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Product> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Product.class)
    public static class ProductsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductsController controller = (ProductsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productsController");
            return controller.getProducts(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Product) {
                Product o = (Product) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Product.class.getName()});
                return null;
            }
        }

    }

}
