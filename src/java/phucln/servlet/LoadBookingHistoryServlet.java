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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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
import phucln.tblbooking.BookingHistoryError;
import phucln.tblbooking.TblBookingDAO;
import phucln.tblbooking.TblBookingDTO;
import phucln.tblbookingdetail.TblBookingDetailDAO;
import phucln.tblbookingdetail.TblBookingDetailDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "LoadBookingHistoryServlet", urlPatterns = {"/LoadBookingHistoryServlet"})
public class LoadBookingHistoryServlet extends HttpServlet {

    private Logger log;
    private final String VIEW_HISTORY_PAGE = "bookingHistoryPage";
    private final String ERROR_PAGE = "errorPage";

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
    private static BookingHistoryError checkParameter(String searchDate) {
        boolean flag = false;
        BookingHistoryError error = new BookingHistoryError();

        if (!searchDate.equals("")) {
            SimpleDateFormat sdfm = new SimpleDateFormat("MM-dd-yyyy");
            sdfm.setLenient(false);
            try {
                Date date = new Date(sdfm.parse(searchDate).getTime());
            } catch (ParseException e) {
                flag = true;
                error.setDateFormatError("Date format is MM-dd-yyyy");
            }
        }

        if (flag == false) {
            return null;
        }
        return error;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        String searchDate = request.getParameter("txtBookingHistoryDate");
        if (searchDate == null) {
            searchDate = "";
        }
        searchDate = searchDate.trim();
        String searchName = request.getParameter("txtBookingHistoryName");
        if (searchName == null) {
            searchName = "";
        }
        searchName = searchName.trim();
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");

        String url = resourceBundle.getString(ERROR_PAGE);
        try {
            if (session != null) {
                String role = (String) session.getAttribute("role");
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("account");
                GoogleAccount googleAccount = (GoogleAccount) session.getAttribute("googleAccount");
                if (role != null) {
                    if (!role.equals("Admin")) {
                        if (accountDTO != null || googleAccount != null) {
                            BookingHistoryError error = checkParameter(searchDate);
                            if (error == null) {
                                TblBookingDetailDAO bookingDetailDAO = new TblBookingDetailDAO();
                                TblBookingDAO bookingDAO = new TblBookingDAO();
                                List<TblBookingDTO> listBooking;
                                List<TblBookingDetailDTO> listBookingDetail;
                                if (searchDate.equals("")) {
                                    String email;
                                    if (accountDTO != null) {
                                        email = accountDTO.getEmail();
                                    } else {
                                        email = googleAccount.getEmail();
                                    }
                                    bookingDAO.getBookingByUserID(email);
                                    listBooking = bookingDAO.getListBooking();
                                    if (listBooking != null) {
                                        for (TblBookingDTO tblBookingDTO : listBooking) {
                                            bookingDetailDAO.searchBookingDetailByIDName(tblBookingDTO.getBookingID(), searchName);
                                        }
                                    }
                                    listBookingDetail = bookingDetailDAO.getListBookingDetail();
                                } else {
                                    SimpleDateFormat sdfm = new SimpleDateFormat("MM-dd-yyyy");
                                    sdfm.setLenient(false);
                                    Date date = new Date(sdfm.parse(searchDate).getTime());
                                    String email;
                                    if (accountDTO != null) {
                                        email = accountDTO.getEmail();
                                    } else {
                                        email = googleAccount.getEmail();
                                    }
                                    bookingDAO.getBookingByUserIDAndDate(email, date.toString());
                                    listBooking = bookingDAO.getListBooking();
                                    if (listBooking != null) {
                                        for (TblBookingDTO tblBookingDTO : listBooking) {
                                            bookingDetailDAO.searchBookingDetailByIDName(tblBookingDTO.getBookingID(), searchName);
                                        }
                                    }

                                    listBookingDetail = bookingDetailDAO.getListBookingDetail();
                                }

                                request.setAttribute("listBooking", listBooking);
                                request.setAttribute("listBookingDetail", listBookingDetail);

                                url = resourceBundle.getString(VIEW_HISTORY_PAGE);
                            } else {
                                request.setAttribute("bookingHistoryError", error);
                                url = resourceBundle.getString(VIEW_HISTORY_PAGE);
                            }
                        }
                    }
                }
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (ParseException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
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
