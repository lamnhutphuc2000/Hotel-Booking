/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import phucln.tblaccount.CreateNewAccountError;
import phucln.tblaccount.TblAccountDAO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SignupServlet", urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {

    private static Logger log;
    private final String LOGIN_PAGE = "loginPage";
    private final String ERROR_PAGE = "signupErrorPage";

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
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private static CreateNewAccountError checkParameter(String email, String password, String confirmPassword, String name, String phone, String address) {
        boolean flag = false;
        CreateNewAccountError error = new CreateNewAccountError();

        if (email.equals("") ) {
            flag = true;
            error.setEmailEmptyError("Email must not empty");
        } else {
            if (email.length() > 30) {
                flag = true;
                error.setEmailEmptyError("Email must not > 30 characters");
            }
            if (!validate(email)) {
                flag = true;
                error.setEmailFormatError("Email wrong format");
            } else {
                try {
                    TblAccountDAO accountDAO = new TblAccountDAO();
                    boolean result = accountDAO.checkAccountExisted(email);
                    if (result) {
                        flag = true;
                        error.setEmailDuplicateError("Email is duplicated");
                    }
                } catch (NamingException e) {
                    log.error(e);
                } catch (SQLException e) {
                    log.error(e);
                }

            }
        }

        if (password.length() == 0 || password.equals("")) {
            flag = true;
            error.setPasswordLengthError("Password must not be empty");
        } else {
            if (password.length() < 6 || password.length() > 20) {
                flag = true;
                error.setPasswordLengthError("Password must be 6 to 20 characters");
            }
        }

        if (confirmPassword.length() == 0 || confirmPassword.equals("")) {
            flag = true;
            error.setConfirmPasswordLengthError("Confirm password must not be empty");
        } else {
            if (confirmPassword.length() < 6 || confirmPassword.length() > 20) {
                flag = true;
                error.setConfirmPasswordLengthError("Confirm password must be 6 to 20 characters");
            } else {
                if (!password.equals(confirmPassword)) {
                    flag = true;
                    error.setConfirmPasswordNotMatchedError("Confirm password must matched password");
                }
            }
        }

        if (name.length() == 0 || name.equals("")) {
            flag = true;
            error.setNameLengthError("Name must not be empty");
        } else {
            if (name.length() < 3 || name.length() > 30) {
                flag = true;
                error.setNameLengthError("Name must be 6 to 30 characters");
            }
        }

        if (address.length() == 0 || address.equals("")) {
            flag = true;
            error.setAddressLengthError("Address must not be empty");
        } else {
            if (address.length() < 3 || address.length() > 30) {
                flag = true;
                error.setAddressLengthError("Address must be 3 to 30 characters");
            }
        }

        if (phone.length() == 0 || phone.equals("")) {
            flag = true;
            error.setPhoneLengthError("Phone must not be empty");
        } else {
            if (phone.charAt(0) != '0') {
                flag = true;
                error.setPhoneFormatError("Phone must begin with character 0");
            } else if (phone.length() != 10) {
                flag = true;
                error.setPhoneFormatError("Phone must be 10 digits");
            } else {
                Pattern pattern = Pattern.compile("^\\d{10}$");
                Matcher matcher = pattern.matcher(phone);
                if (!matcher.matches()) {
                    flag = true;
                    error.setPhoneFormatError("Phone must be number only");
                }
            }
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
        String email = request.getParameter("txtEmail");
        email = email.trim();
        String phone = request.getParameter("txtPhone");
        phone = phone.trim();
        String name = request.getParameter("txtName");
        name = name.trim();
        String address = request.getParameter("txtAddress");
        address = address.trim();
        String password = request.getParameter("txtPassword");
        password = password.trim();
        String confirmPassword = request.getParameter("txtConfirmPassword");
        confirmPassword = confirmPassword.trim();
        
         
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");

        String url = resourceBundle.getString(ERROR_PAGE);
        try {
            CreateNewAccountError createNewError 
                    = checkParameter(email, password, confirmPassword, name, phone, address);
            if(createNewError == null) {
                TblAccountDAO accountDAO = new TblAccountDAO();
                boolean result = accountDAO.createNewAccount(email, password, name, phone, address);
                if(result) {
                    url = resourceBundle.getString(LOGIN_PAGE);
                }
            } else {
                request.setAttribute("createNewError", createNewError);
            }
        }catch(NamingException e) {
            log.error(e);
        }catch(SQLException e) {
            log.error(e);
        }
        finally {
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
