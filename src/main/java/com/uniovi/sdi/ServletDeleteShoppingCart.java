package com.uniovi.sdi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ServletDeleteShoppingCart", value = "/deleteFromShoppingCart")
public class ServletDeleteShoppingCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        HttpSession session = request.getSession();

        HashMap<String, Integer> cart =
                (HashMap<String, Integer>) session.getAttribute("cart");

        // No hay carrito, creamos uno y lo insertamos en sesión
        if (cart == null) {
            cart = new HashMap<String, Integer>();
            session.setAttribute("cart", cart);
        }

        String product = request.getParameter("product");
        if (product != null) {
            deleteFromShoppingCart(cart, product);
        }

        // Retornar la vista con parámetro "selectedItems"
        request.setAttribute("selectedItems", cart);
        getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    private void deleteFromShoppingCart(Map<String, Integer> cart, String productKey){

        if(cart.get(productKey) != null)
        {
            int productCount = cart.get(productKey);
            if(productCount == 1)
                cart.remove(productKey);
            else
                cart.put(productKey, productCount -1);
        }
    }

    private String shoppingCartToHtml(Map<String, Integer> cart){
        StringBuilder shoppingCartToHtml = new StringBuilder();

        for (String key: cart.keySet())
            shoppingCartToHtml.append("<p>[").append(key).append("], ").append(cart.get(key)).append(" unidades</p>");

        return shoppingCartToHtml.toString();
    }

}
