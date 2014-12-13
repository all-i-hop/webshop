/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.fhjoanneum.ima.swenga.webshop.beans;
import at.fhjoanneum.ima.swenga.webshop.entities.Product;

/**
 *
 * @author Alex
 */

public  class LineItem {

        private Product product;
        private int quantity = 1;

        public LineItem(Product product) {
            this.product = product;
        }

        public void increaseQuantity(int number){
            quantity += number;
        }
        public int getQuantity() {
            return quantity;
        }

        public Product getProduct() {
            return product;
        }

        public Double getLinePrice() {
            return product.getPrice() * quantity;
        }

    }