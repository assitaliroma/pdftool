package com.servlet.main;
  
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.servlet.pdf.body.*;
import com.servlet.pdf.request.*;

public class PdfServlet extends HttpServlet {
	
	private static Logger log=Logger.getLogger(PdfServlet.class);
	private ObituarioRequest obiRequest;
	 
    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            //Depending on the type of document you must create an object

            String type = request.getParameter("type");
            log.info("It has been received a type: "+type);
            if (type.equalsIgnoreCase("obituario")){
            	
            	obiRequest=new ObituarioRequest(request);
            	obiRequest.setObituarioDraw(new ObituarioDraw(response));
            	obiRequest.ManageRequest();
            	
            }
    
        }
        catch(Exception e) {
        	log.error("AN ERROR HAS OCCURRED WHILE CREATING PDF",e);
        }
    }

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 6067021675155015602L;

}
