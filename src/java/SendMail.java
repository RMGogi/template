/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

/**
 *
 * @author anema
 */
@WebServlet(urlPatterns = {"/SendMail"})
public class SendMail extends HttpServlet {

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
        try {
            String FROM = "anema@lenovo.com";   // Replace with your "From" address. This address must be verified.
//    static final String TO = "znadaf@lenovo.com";  // Replace with a "To" address. If your account is still in the
            String TO = request.getParameter("to_email");                                                      // sandbox, this address must be verified.
            
            String BODY = request.getParameter("descr");
            String SUBJECT = request.getParameter("sub");
            
            // Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
            String SMTP_USERNAME = "AKIAI5HVTHP7XTTRRA5A";  // Replace with your SMTP username.
            String SMTP_PASSWORD = "AgCCRIJjloKnKLo5jqfMZzYv2K6Fwgf1wIsKuZ5kSIB2";  // Replace with your SMTP password.
            
            // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
            String HOST = "email-smtp.us-east-1.amazonaws.com";
            
            // The port you will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because we will use
            // STARTTLS to encrypt the connection.
            int PORT = 587;
            //response.setContentType("text/html;charset=UTF-8");
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", PORT);
            
            // Set properties indicating that we want to use STARTTLS to encrypt the connection.
            // The SMTP session will begin on an unencrypted connection, and then the client
            // will issue a STARTTLS command to upgrade to an encrypted connection.
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            
            // Create a Session object to represent a mail session with the specified properties.
            Session session = Session.getDefaultInstance(props);
            
            // Create a message with the specified information.
            MimeMessage msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress(FROM));
            } catch (MessagingException ex) {
                Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
            }
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
            msg.setSubject(SUBJECT);
            msg.setContent(BODY,"text/html");
            
            // Create a transport.
            Transport transport = session.getTransport();
            
            // Send the message.
            for(int i=0;i<=0;i++)
            {
                try
                {
                    System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
                    
                    // Connect to Amazon SES using the SMTP username and password you specified above.
                    transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
                    
                    // Send the email.
                    
                    
                    transport.sendMessage(msg, msg.getAllRecipients());
                    
                    System.out.println(i+1+" Email sent!");
                    JOptionPane.showMessageDialog(null, "Sent");
                }
                catch (Exception ex) {
                  
                    System.out.println("The email was not sent.");
                    System.out.println("Error message: " + ex.getMessage());
                }
                finally
                {
                    // Close and terminate the connection.
                    transport.close();
                }
            }
            
            
            
            
            
        } catch (MessagingException ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
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
