/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
import phucln.tblbooking.TblBookingDTO;
import phucln.tblbookingdetail.FeedbackError;
import phucln.tblbookingdetail.TblBookingDetailDAO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SendFeedbackServlet", urlPatterns = {"/SendFeedbackServlet"})
public class SendFeedbackServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");

        String url = resourceBundle.getString(BOOKING_HISTORY);
        try {
            if (session != null) {
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("account");
                String role = (String) session.getAttribute("role");
                GoogleAccount googleAccount = (GoogleAccount) session.getAttribute("googleAccount");
                if (!role.equals("Admin")) {
                    if (accountDTO != null || googleAccount != null) {
                        String txtBookingID = request.getParameter("txtBookingID");
                        String txtRoomID = request.getParameter("txtRoomID");
                        String txtFeedBack = request.getParameter("txtFeedBack");

                        TblBookingDetailDAO bookingDetailDAO = new TblBookingDetailDAO();

                        TblBookingDAO bookingDAO = new TblBookingDAO();
                        TblBookingDTO bookingDTO = bookingDAO.getBookingByBookingID(txtBookingID);
                        String email;
                        if (accountDTO != null) {
                            email = accountDTO.getEmail();
                        } else {
                            email = googleAccount.getEmail();
                        }
                        if (bookingDTO.getUserID().equals(email)) {
                            Date today = new Date(System.currentTimeMillis());
                            if (bookingDTO.getCheckOutDate().before(today)) {
                                boolean result = bookingDetailDAO.insertFeedback(txtBookingID, txtRoomID, txtFeedBack);
                                if (!result) {
                                    url = resourceBundle.getString(ERROR_PAGE);
                                }
                            } else {
                                FeedbackError error = new FeedbackError();
                                error.setDateNotYetError("Can not feedback because you are not checkout yet!");
                                request.setAttribute("feedbackError", error);
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
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
