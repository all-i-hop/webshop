/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.fhjoanneum.ima.swenga.webshop.dtos;

/**
 *
 * @author Alex
 */
public class ProductPreview {
    private Long id;
    private String name;
    private String shortdescription;
    private Double price;
    private Long thumbnailId;

    public ProductPreview() {
    }
  
    public ProductPreview(Long id, String name, String shortdescription, Double price, Long thumbnailId) {
        this.id = id;
        this.name = name;
        this.shortdescription = shortdescription;
        this.price = price;
        this.thumbnailId = thumbnailId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(Long thumbnailId) {
        this.thumbnailId = thumbnailId;
    }
    
    
}
