/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter; 
import java.sql.SQLException;
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
import phucln.tbltypeofroom.TblTypeOfRoomDAO;
import phucln.tbltypeofroom.TblTypeOfRoomDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "LoadTypeOfRoomServlet", urlPatterns = {"/LoadTypeOfRoomServlet"})
public class LoadTypeOfRoomServlet extends HttpServlet {

    private Logger log;
    private final String SEARCH_PAGE = "searchPage";
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");

        String url = resourceBundle.getString(ERROR_PAGE); 
        try  {
             TblTypeOfRoomDAO typeOfRoomDAO = new TblTypeOfRoomDAO();
             typeOfRoomDAO.loadAllTypeOfRoom();
             List<TblTypeOfRoomDTO> typeOfRooms = typeOfRoomDAO.getListTypeOfRoom();
             if(typeOfRooms != null) {
                 url = resourceBundle.getString(SEARCH_PAGE);
                 request.setAttribute("typeOfRooms", typeOfRooms); 
             }
        }catch(NamingException e ) {
            log.error(e);
        } catch(SQLException e) {
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
