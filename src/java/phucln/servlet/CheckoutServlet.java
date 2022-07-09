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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
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
import phucln.cart.Cart;
import phucln.cart.RoomInCart;
import phucln.tblaccount.GoogleAccount;
import phucln.tblaccount.TblAccountDTO;
import phucln.tblbooking.CheckoutError;
import phucln.tblbooking.TblBookingDAO;
import phucln.tblbooking.TblBookingDTO;
import phucln.tblbookingdetail.TblBookingDetailDAO;
import phucln.tblbookingdetail.TblBookingDetailDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private static Logger log;
    private final String SEND_CONFIRM_MESSAGE = "sendConfirm";
    private final String ERROR_PAGE = "errorPage";
    private final String CART_PAGE = "cartPage";

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
    private static CheckoutError checkParameter(String txtCheckInDate, String txtCheckOutDate, Cart cart) {
        boolean flag = false;
        CheckoutError error = new CheckoutError();

        SimpleDateFormat sdfm = new SimpleDateFormat("MM-dd-yyyy");
        sdfm.setLenient(false);
        Date checkInDate = null;
        Date checkOutDate = null;
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
            checkOutDate = new Date(sdfm.parse(txtCheckOutDate).getTime());
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

        

        try {
            List<RoomInCart> listRoomInCart = cart.getCart();
            if (checkInDate != null && checkOutDate != null) {
                if (listRoomInCart != null) {
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

                                for (RoomInCart roomInCart : listRoomInCart) {
                                    if (roomInCart.getRoomID().equals(tblBookingDetailDTO.getRoomID())) {
                                        flag = true;
                                         
                                        error.setRoomOutOfStockError("Room is already booked");
                                    }
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
        String totalPrice = request.getParameter("txtTotalPrice");
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");

        String url = resourceBundle.getString(ERROR_PAGE);
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                String role = (String) session.getAttribute("role");
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("account");
                GoogleAccount googleAccount = (GoogleAccount) session.getAttribute("googleAccount");
                if (!role.equals("Admin") ) {
                    if (accountDTO != null || googleAccount != null) {
                        Cart cart = (Cart) session.getAttribute("cart");
                        if (cart != null) {
                            CheckoutError checkoutError
                                    = checkParameter(txtCheckInDate, txtCheckOutDate, cart);
                            if (checkoutError == null) {
                                boolean flag = true;
                                TblBookingDAO bookingDAO = new TblBookingDAO();
                                TblBookingDetailDAO bookingDetailDAO = new TblBookingDetailDAO();
                                List<RoomInCart> listRoomInCart = cart.getCart();
                                Date checkInDate = new Date(sdfm.parse(txtCheckInDate).getTime());
                                Date checkOutDate = new Date(sdfm.parse(txtCheckOutDate).getTime());
                                
                                String userID;
                                if(accountDTO != null) {
                                    userID = accountDTO.getEmail();
                                } else {
                                    userID = googleAccount.getEmail();
                                }
                                Timestamp time = new Timestamp(System.currentTimeMillis());
                                String bookingID = userID + time;
                                long days = checkOutDate.getTime() - checkInDate.getTime();
                                long day = TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS);
                                float price = Float.parseFloat(totalPrice) * day;
                                if(price < 0){
                                    price = -price;
                                }
                                boolean result = bookingDAO.insertNewBooking(bookingID, userID, price, checkInDate, checkOutDate);
                                 
                                if (result == true) {
                                    for (int i = 0; i < listRoomInCart.size(); i++) {
                                        String roomID = listRoomInCart.get(i).getRoomID();
                                        String typeOfRoom = listRoomInCart.get(i).getTypeOfRoom();
                                        float roomPrice = listRoomInCart.get(i).getPrice();
                                        result = bookingDetailDAO.insertNewBookingDetail(bookingID, roomID, typeOfRoom, roomPrice, null);
                                        if (result == false) {
                                            flag = false;
                                        }
                                    }
                                }

                                if (flag == false) {
                                    url = resourceBundle.getString(ERROR_PAGE);
                                } else {
                                    session.removeAttribute("cart");
                                    request.setAttribute("txtBookingID", bookingID);
                                    url = resourceBundle.getString(SEND_CONFIRM_MESSAGE);
                                }
                            } else {
                                request.setAttribute("checkoutError", checkoutError);
                                url = resourceBundle.getString(CART_PAGE);
                            }
                        }
                    }
                }
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (NumberFormatException e) {
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
