package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class Hello extends HttpServlet {
   public Hello() {
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      response.setContentType("text/html");
      PrintWriter writer = response.getWriter();
      writer.println("hello world!");
   }
}