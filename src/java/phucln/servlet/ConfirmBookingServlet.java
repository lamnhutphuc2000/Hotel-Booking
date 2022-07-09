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
import java.util.Base64;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import phucln.tblbooking.TblBookingDAO;
import phucln.tblbooking.TblBookingDTO;
import phucln.tblbookingdetail.TblBookingDetailDAO;
import phucln.tblbookingdetail.TblBookingDetailDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ConfirmBookingServlet", urlPatterns = {"/ConfirmBookingServlet"})
public class ConfirmBookingServlet extends HttpServlet {

    private static Logger log = null;
    private final String ERROR_PAGE = "errorPage";
    private final String MAIN_PAGE = "";

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
    
    private static boolean checkParameter(String bookingID) {
        boolean flag = false;
          
        try {
            TblBookingDAO bookingDAO = new TblBookingDAO();
            TblBookingDTO bookingDTO = bookingDAO.getBookingByBookingID(bookingID);
            TblBookingDetailDAO bookingDetailDAO = new TblBookingDetailDAO();
            bookingDetailDAO.searchBookingDetailByID(bookingID);
            List<TblBookingDetailDTO> listRooms = bookingDetailDAO.getListBookingDetail();
            Date checkInDate = bookingDTO.getCheckInDate();
            Date checkOutDate = bookingDTO.getCheckOutDate();
            if (listRooms != null) {
                bookingDAO = new TblBookingDAO();
                bookingDAO.searchBookingByCheckInCheckOut(checkInDate, checkOutDate);
                List<TblBookingDTO> listBooking = bookingDAO.getListBooking();
                if (listBooking != null) {
                    TblBookingDetailDAO bookingdetailDAO = new TblBookingDetailDAO();

                    for (TblBookingDTO tblBookingDTO : listBooking) {
                        bookingdetailDAO.searchBookingDetailByID(tblBookingDTO.getBookingID());
                    }
                    List<TblBookingDetailDTO> listBookingDetail = bookingdetailDAO.getListBookingDetail();
                    if (listBookingDetail != null) {
                        for (TblBookingDetailDTO tblBookingDetailDTO : listBookingDetail) {
 
                            for (TblBookingDetailDTO listRoom : listRooms) {
                                if (listRoom.getRoomID().equals(tblBookingDetailDTO.getRoomID())) {
                                    return false;
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
        }

        return true;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = MAIN_PAGE;

        try {
            String txtBookingID = request.getParameter("txtBookingID");
            if (txtBookingID != null) {
                byte[] decodedBytes = Base64.getDecoder().decode(txtBookingID);
                String bookingID = new String(decodedBytes);
                boolean result = checkParameter(bookingID);
                if(result ) {
                    url = ERROR_PAGE;
                    TblBookingDAO bookingDAO = new TblBookingDAO();
                    result = bookingDAO.confirmBooking(bookingID);
                    if (result) {
                        url = MAIN_PAGE;
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
