/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phucln.cart.Cart;
import phucln.tblaccount.GoogleAccount;
import phucln.tblaccount.TblAccountDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "AddRoomToCartServlet", urlPatterns = {"/AddRoomToCartServlet"})
public class AddRoomToCartServlet extends HttpServlet {

    private final String ERROR_PAGE = "errorPage";
    private final String SEARCH_CONTROLLER = "searchRoom";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");

        String url = resourceBundle.getString(ERROR_PAGE);
        try {
            if (session != null) {
                String role = (String) session.getAttribute("role");
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("account");
                GoogleAccount googleAccount = (GoogleAccount) session.getAttribute("googleAccount");
                if (!role.equals("Admin")) {
                    if (accountDTO != null || googleAccount != null) {
                        String roomID = request.getParameter("txtRoomID");
                        String roomPrice = request.getParameter("txtRoomprice");
                        String typeOfRoomName = request.getParameter("txtTypeOfRoomName");
                        Cart cart = (Cart) session.getAttribute("cart");
                        if (cart == null) {
                            cart = new Cart();
                        }
                        float price = Float.parseFloat(roomPrice);
                        cart.addRoomToCart(roomID, typeOfRoomName, price);
                        session.setAttribute("cart", cart);

                        url = resourceBundle.getString(SEARCH_CONTROLLER);
                    }
                }
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
