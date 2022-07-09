/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phucln.tblaccount.GoogleAccount;
import phucln.tblaccount.TblAccountDTO;
import phucln.tblbooking.TblBookingDAO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "DeleteBookingHistoryServlet", urlPatterns = {"/DeleteBookingHistoryServlet"})
public class DeleteBookingHistoryServlet extends HttpServlet {

    private Logger log = null;
    private final String ERROR_PAGE = "errorPage";
    private final String BOOKING_HISTORY = "bookingHistory";

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        log = Logger.getLogger(this.getClass().getName());
    }

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

        String url = ERROR_PAGE;
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                String role = (String) session.getAttribute("role");
                if (role != null) {
                    if (!role.equals("Admin")) {
                        TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("account");
                        GoogleAccount googleAccount = (GoogleAccount) session.getAttribute("googleAccount");
                        if (accountDTO != null || googleAccount != null) {
                            url = BOOKING_HISTORY;
                            String txtBookingID = request.getParameter("txtBookingID");
                            String userID = request.getParameter("txtUserID");
                            String email;
                            if (accountDTO != null) {
                                email = accountDTO.getEmail();
                            } else {
                                email = googleAccount.getEmail();
                            }
                            if (userID.equals(email)) {
                                TblBookingDAO bookingDAO = new TblBookingDAO();
                                boolean result = bookingDAO.deleteBookingHistory(txtBookingID);
                                if (!result) {
                                    url = ERROR_PAGE;
                                }
                            }
                        }
                    }
                }
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        } finally {
            response.sendRedirect(url);
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
