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
import org.apache.log4j.Logger;
import phucln.tblbooking.TblBookingDAO;
import phucln.tblbooking.TblBookingDTO;
import phucln.tblbookingdetail.TblBookingDetailDAO;
import phucln.tblbookingdetail.TblBookingDetailDTO;
import phucln.tblroom.SearchRoomError;
import phucln.tblroom.TblRoomDAO;
import phucln.tblroom.TblRoomDTO;
import phucln.tbltypeofroom.TblTypeOfRoomDAO;
import phucln.tbltypeofroom.TblTypeOfRoomDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SearchRoomServlet", urlPatterns = {"/SearchRoomServlet"})
public class SearchRoomServlet extends HttpServlet {

    private static Logger log;
    private final String SEARCH_PAGE = "searchPage"; 

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
    private static SearchRoomError checkParameter(String txtCheckInDate, String txtCheckOutDate) {
        boolean flag = false;
        SearchRoomError error = new SearchRoomError();

        SimpleDateFormat sdfm = new SimpleDateFormat("MM-dd-yyyy");
        sdfm.setLenient(false);
        Date checkInDate = null;
        try {
            checkInDate = new Date(sdfm.parse(txtCheckInDate).getTime());
            if (checkInDate.before(new Date(System.currentTimeMillis()))) {
                flag = true;
                error.setCheckInDateBeforeNowError("Checkin date must after now");
            }
        } catch (ParseException e) {
            flag = true;
            error.setCheckInDateFormatError("Date format is MM-dd-yyyy");
        }

        try {
            Date checkOutDate = new Date(sdfm.parse(txtCheckOutDate).getTime());
            if (checkInDate != null) {
                if (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
                    flag = true;
                    error.setCheckOutBeforeCheckInError("Checkout date must after checkin date");
                }
            }
            if (checkOutDate.before(new Date(System.currentTimeMillis()))) {
                flag = true;
                error.setCheckOutDateBeforeNowtError("Checkout date must after now");
            }

        } catch (ParseException e) {
            flag = true;
            error.setCheckOutDateFormatError("Date format is MM-dd-yyyy");
        }

        if (flag == false) {
            return null;
        }
        return error;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        SimpleDateFormat sdfm = new SimpleDateFormat("MM-dd-yyyy");
        sdfm.setLenient(false);
        String txtCheckInDate = request.getParameter("txtCheckInDate");
        txtCheckInDate = txtCheckInDate.trim();
        String txtCheckOutDate = request.getParameter("txtCheckoutDate");
        txtCheckOutDate = txtCheckOutDate.trim();
        String txtTypeOfRoom = request.getParameter("txtTypeOfRoom");
        txtTypeOfRoom = txtTypeOfRoom.trim();
        if (txtTypeOfRoom.equals("All")) {
            txtTypeOfRoom = "";
        }
        String txtTypeOfRoomID = "";
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");

        String url = resourceBundle.getString(SEARCH_PAGE);
        try {
            SearchRoomError searchError
                    = checkParameter(txtCheckInDate, txtCheckOutDate);
            if (searchError == null) {
                if (!txtTypeOfRoom.equals("")) {
                    TblTypeOfRoomDAO typeOfRoomDAO = new TblTypeOfRoomDAO();
                    txtTypeOfRoomID = typeOfRoomDAO.getTypeOfRoomID(txtTypeOfRoom);
                }
                TblRoomDAO roomDAO = new TblRoomDAO();
                roomDAO.searchRoomByType(txtTypeOfRoomID);
                List<TblRoomDTO> listRoom = roomDAO.getListRoom();
                if (listRoom != null) {
                    Date checkInDate = new Date(sdfm.parse(txtCheckInDate).getTime());
                    Date checkOutDate = new Date(sdfm.parse(txtCheckOutDate).getTime());
                    TblBookingDAO bookingDAO = new TblBookingDAO();
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

                                for (int i = 0; i < listRoom.size(); i++) {
                                    if (listRoom.get(i).getRoomID().equals(tblBookingDetailDTO.getRoomID())) {
                                        listRoom.remove(i);

                                    }
                                }
                            }
                        }
                    }
                }
                request.setAttribute("listRoom", listRoom); 
            } else {
                request.setAttribute("searchError", searchError);
            }

            TblTypeOfRoomDAO typeOfRoomDAO = new TblTypeOfRoomDAO();
            typeOfRoomDAO.loadAllTypeOfRoom();
            List<TblTypeOfRoomDTO> typeOfRooms = typeOfRoomDAO.getListTypeOfRoom();
            if (typeOfRooms != null) {
                request.setAttribute("typeOfRooms", typeOfRooms);
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        } catch (ParseException e) {
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
