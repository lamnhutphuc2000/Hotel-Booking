/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phucln.tblaccount.GoogleAccount;
import phucln.tblaccount.TblAccountDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SendConfirmBookingServlet", urlPatterns = {"/SendConfirmBookingServlet"})
public class SendConfirmBookingServlet extends HttpServlet {

    private Logger log = null;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession thisSession = request.getSession(false);
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = resourceBundle.getString(ERROR_PAGE);

        try {
            if (thisSession != null) {
                TblAccountDTO accountDTO = (TblAccountDTO) thisSession.getAttribute("account");
                String role = (String) thisSession.getAttribute("role");
                GoogleAccount googleAcccount = (GoogleAccount) thisSession.getAttribute("googleAccount");
                if (role != null) {
                    if (!role.equals("Admin")) {
                        if (accountDTO != null || googleAcccount != null) {
                            String email;
                            if(accountDTO != null) {
                                email = accountDTO.getEmail();
                            } else {
                                email = googleAcccount.getEmail();
                            }
                            String host = "smtp.gmail.com";
                            final String user = "toluffy432a5@gmail.com";
                            final String password = "Izayoiprono1";

                            String to = email;

                            Properties props = new Properties();
                            props.put("mail.smtp.host", host);
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.port", "25");
                            props.put("mail.smtp.socketFactory.port", "465");
                            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                            Session session = Session.getDefaultInstance(props,
                                    new javax.mail.Authenticator() {
                                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                                    return new javax.mail.PasswordAuthentication(user, password);
                                }
                            });

                            MimeMessage message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(user));
                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                            message.setSubject("Confirm your booking");
                            String txtBookingID = (String) request.getAttribute("txtBookingID");
                            String encodedString = Base64.getEncoder().encodeToString(txtBookingID.getBytes());
                            String location = "http://localhost:8080/SE140202-J3LP0003/confirmBooking?txtBookingID=" + encodedString;
                            message.setText(location);

                            Transport.send(message);
                            url = resourceBundle.getString(MAIN_PAGE);
                        }
                    }
                }

            }
        } catch (MessagingException e) {
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
