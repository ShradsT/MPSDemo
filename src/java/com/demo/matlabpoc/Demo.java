/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.demo.matlabpoc;

import com.mathworks.mps.client.MATLABException;
import com.mathworks.mps.client.MWClient;
import com.mathworks.mps.client.MWHttpClient;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.*;
import jxl.read.biff.BiffException;
/**
 *
 * @author ShraddhaT
 */
public class Demo extends HttpServlet {
private MWClient client;
 public void init(ServletConfig config) throws ServletException{
       
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
        
        Workbook workbook;
    try {
        workbook = Workbook.getWorkbook(new File("C:\\Users\\ShraddhaT\\Desktop\\SmartSearch_Dataset.xls"));
        Sheet sheet = workbook.getSheet(0);
        
        /************************************
        BEGIN MPS Proxy creation code 
        *************************************/
        client = new MWHttpClient();
        CleanData newInstance =  client.createProxy(new URL("http://localhost:9910/DataCleansing"), CleanData.class);
        /************************************
        END MPS Proxy creation code
        *************************************/
        
        String htmlRespone = "<html><head>\n" +
"        <title>Newedge POC</title></head> <body> <br/><br/><br/><br/><table height=\"80%\"  bgcolor=\"#99B4D1\" align=\"center\" border=\"1\" style=\"width: 80%;height: 80%; \">"
                + "<tr><td align=\"center\"><h1><u>Input String</u></h1></td><td align=\"center\"><h1><u>Clean Strings</u></h1></td><tr>"; 
        PrintWriter out = response.getWriter();
          String userstring = request.getParameter("inputstring");	
          String firstOutput = null;
          String secondOutput =null;
          if(userstring == "")
          {              
            try {
                for(int i=1; i<274; i++)
                {
                Cell a1 = sheet.getCell(0,i);
                String cellContent = a1.getContents();
                Object[] output =  newInstance.DataCleansing(2,cellContent);
                firstOutput =  ((String[]) output[0])[0];
                secondOutput =  ((String[]) output[1])[0];
                 htmlRespone += "<tr><td align=\"center\"> <h2>" + cellContent + "</h2></td><td><h2>Clean string I:  " + firstOutput + "<br/>";      
                 htmlRespone += "Clean string II: " + secondOutput + "</h2><td></tr   >";    
                }
            } 
            catch (MATLABException ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
                }
       
      
          }
          else
          {
              htmlRespone = "<html><head>\n" +
"        <title>Newedge POC</title></head> <body> <br/><br/><br/><br/><table height=\"35%\"  bgcolor=\"#99B4D1\" align=\"center\" border=\"1\" style=\"width: 80%;height:35%; \">"
                + "<tr><td align=\"center\"><h1><u>Input String</u></h1></td><td align=\"center\"><h1><u>Clean Strings</u></h1></td><tr>"; 
              try
              {
                Object[] output =  newInstance.DataCleansing(2,userstring);
                firstOutput =  ((String[]) output[0])[0];
                secondOutput =  ((String[]) output[1])[0];
                 htmlRespone += "<tr><td align=\"center\"> <h2>" + userstring + "</h2></td><td><h2>Clean string I:  " + firstOutput + "<br/>";      
                 htmlRespone += "Clean string II: " + secondOutput + "</h2><td></tr   >";
              }
              catch (MATLABException ex) {
                Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
                }
          }
            htmlRespone += "</table></body></html>";
        // return response
        out.println(htmlRespone);   
        } catch (BiffException ex) {
        Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
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
            throws ServletException, IOException  {
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
public void destroy(){
        client.close();
    }
}
