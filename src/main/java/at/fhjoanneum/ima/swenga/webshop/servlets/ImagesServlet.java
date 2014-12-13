/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.fhjoanneum.ima.swenga.webshop.servlets;

import at.fhjoanneum.ima.swenga.webshop.controllers.ProductsController;
import at.fhjoanneum.ima.swenga.webshop.entities.Image;
import at.fhjoanneum.ima.swenga.webshop.facades.ProductsFacade;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "ImagesServlet", urlPatterns = {"/showImage", "/showThumbnail"})
public class ImagesServlet extends HttpServlet {

    @Inject
    private ProductsController controller;
    @Inject
    private ProductsFacade productFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int imgIndex;
        String name;
        Image img = null;
        try {
            try {
                imgIndex = Integer.parseInt(request.getParameter("index"));
                img = controller.getSelected().getImages().get(imgIndex);
            } catch (NumberFormatException | NullPointerException ex) {
                name = request.getParameter("name");
                if (name != null) {

                    for (Image image : controller.getSelected().getImages()) {
                        if (image.getName().equals(name)) {
                            img = image;
                        }
                    }
                } else {
                    Long id = Long.parseLong(request.getParameter("id"));
                    img = productFacade.findImageById(id);
                }
            }
            response.setContentType(img.getType());
            response.setHeader("Content-Disposition", "filename=\"" + img.getName() + "\"");
            ServletOutputStream out = response.getOutputStream();
            out.write(request.getRequestURI().endsWith("Thumbnail") ? img.getThumbnail() : img.getContent());
            out.close();
        } catch (Exception exception) {
            //You should send some default image here!
        }
    }
}
