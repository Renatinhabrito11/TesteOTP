import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
  
  private static final String SECRET_KEY = "SECRET_KEY"; // Substitua pela sua chave secreta
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    String otp = req.getParameter("otp");
    if (authenticate(username, password) && verifyOTP(otp)) {
      // Autenticação bem-sucedida
      resp.sendRedirect("dashboard.html");
    } else {
      // Autenticação mal-sucedida
      resp.sendRedirect("login.html");
    }
  }
  
  private boolean authenticate(String username, String password) {
    // Implemente sua lógica de autenticação aqui
    return true;
  }
  
  private boolean verifyOTP(String otp) {
    try {
      long timeInMillis = System.currentTimeMillis();
      String expectedOTP = OTPGenerator.generateOTP(SECRET_KEY, timeInMillis);
      return otp.equals(expectedOTP);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
      return false;
    }
  }
  
}
