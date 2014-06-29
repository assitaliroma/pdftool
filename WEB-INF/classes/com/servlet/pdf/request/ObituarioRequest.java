package com.servlet.pdf.request;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.servlet.pdf.body.ObituarioDraw;
import com.servlet.pdf.bundles.ModuloBundle;
import com.servlet.pdf.interfaces.RequestInterface;

/**
 * Clase que sabe como tratar un request tipo Obituario
 * @author anrivero
 *
 */
public class ObituarioRequest implements RequestInterface {

	private ArrayList<String> requestParams=null;
	private HttpServletRequest request;
	private ObituarioDraw obiDraw;
	public static ModuloBundle properties;
	private static Logger log=Logger.getLogger(ObituarioRequest.class);
	
	
	/**
	 * @param args
	 */
	public ObituarioRequest(HttpServletRequest req){
		this.request=req;
		this.requestParams=new ArrayList<String>();		
		this.requestParams.add(request.getParameter("avisoObituario.preEncabezado")); //0
		this.requestParams.add(request.getParameter("avisoObituario.encabezadoPrincipal")); //1
		this.requestParams.add(request.getParameter("avisoObituario.nombreFallecido")); //2		
		this.requestParams.add(request.getParameter("avisoObituario.encabezadoSecundario")); //3
		this.requestParams.add(request.getParameter("avisoObituario.texto"));//4
		this.requestParams.add(request.getParameter("avisoObituario.pie")); //5
		this.requestParams.add(request.getParameter("fecha")); //6
		this.requestParams.add(request.getParameter("pageSize")); //7
		this.requestParams.add(request.getParameter("orla")); //8		       
		this.requestParams.add(request.getParameter("imgHeader")); //9
		this.requestParams.add(request.getParameter("logo")); //10
		this.requestParams.add(request.getParameter("subtype")); //11
		this.requestParams.add(request.getParameter("preOrSave")); //12
		this.requestParams.add(request.getParameter("idAviso")); //13
		
		
		log.info("******Lectura de parametros del Request:******");
		log.info("razon: "+requestParams.get(0));
		log.info("header: "+requestParams.get(1));
		log.info("fallecido: "+requestParams.get(2));
		log.info("texto: "+requestParams.get(3));
		log.info("qepd: "+requestParams.get(4));
		log.info("pie: "+requestParams.get(5));
		log.info("fecha: "+requestParams.get(6));
		log.info("pageSize: "+requestParams.get(7));
		log.info("orla: "+requestParams.get(8));
		log.info("imgHeader: "+requestParams.get(9));
		log.info("logo: "+requestParams.get(10));
		log.info("subtype: "+requestParams.get(11));
		log.info("preOrSave: "+requestParams.get(12));
		
		//Bundle necesario para el Drawer
		properties=new ModuloBundle(Integer.parseInt(requestParams.get(7)));
		
	}	
	
	@Override
	public void ManageRequest(){		
		
		//Definir si se esta invocando para el Preview o el Save
		if (requestParams.get(12).equalsIgnoreCase("preview")){
			obiDraw.setPreview(true);
		}else if (requestParams.get(12).equalsIgnoreCase("save")){
			obiDraw.setPreview(false);
		}
		
		log.info("Invocando al Drawer para crear el PDF.");
		obiDraw.Draw();
		log.info("Invocando al Drawer para escribir el PDF.");		
		obiDraw.writeOutput();
		
	}
	
	public ObituarioDraw getObituarioDraw(){
		return obiDraw;	
	}
	
	public void setObituarioDraw(ObituarioDraw obi){
		this.obiDraw=obi;
		this.obiDraw.setReqParams(getRequestParams());
	}
	
	public ArrayList<String> getRequestParams() {
		return requestParams;
	}
	public void setRequestParams(ArrayList<String> requestParams) {
		this.requestParams = requestParams;
	}
	
	public HttpServletRequest getRequest(){
		return request;		
	}

}