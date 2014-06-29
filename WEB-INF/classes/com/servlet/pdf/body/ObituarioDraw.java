package com.servlet.pdf.body;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.CMYKColor;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.servlet.pdf.fonts.embedded.Fuentes;
import com.servlet.pdf.interfaces.DrawerInterface;
import com.servlet.pdf.request.ObituarioRequest;

public class ObituarioDraw implements DrawerInterface{ 

	private HttpServletResponse response;
	private boolean preview;
	private ByteArrayOutputStream outputStream;
	private ArrayList<String> reqParams=null;
	private boolean hasImages=false;
	
	private static final float FACTOR=0.127f;
	private static Logger log=Logger.getLogger(ObituarioDraw.class);
	

	public ObituarioDraw(HttpServletResponse resp){
		this.response=resp;		
	}
	
	@Override
	public void Draw(){		
	    log.info("Creando el Documento PDF.");
		log.debug("Paso 1: Delimitando la pagina del PDF.");
         // Paso 1: Pintar el rectangulo que delimita la Página
         Rectangle pagesize = new Rectangle(Utilities.millimetersToPoints(ObituarioRequest.properties.getPageWidth()),Utilities.millimetersToPoints(ObituarioRequest.properties.getPageHeight()));
         pagesize.setBackgroundColor(new CMYKColor(0f, 0f, 0f, 0f));
         
         //Veo si tiene imagenes para definir las configuraciones posteriores
         if ((reqParams.get(9)!=null && !reqParams.get(9).equals(""))||(reqParams.get(10)!=null && !reqParams.get(10).equals("")))
             hasImages=true;
         
         Document document = new Document(pagesize,ObituarioRequest.properties.getMarginContentLeft(reqParams.get(8),hasImages),ObituarioRequest.properties.getMarginContentRight(reqParams.get(8),hasImages),ObituarioRequest.properties.getMarginContentTop(reqParams.get(8),hasImages),ObituarioRequest.properties.getMarginContentBottom(reqParams.get(8),hasImages));
    
         
         log.debug("Paso 2: Definir el OutputStream del PDF.");
         
         //Paso 2: Definir el OutputStream donde escribirá iText
         outputStream= new ByteArrayOutputStream();
         PdfWriter writer=null;
		try {
			writer = PdfWriter.getInstance(document, outputStream);
		} catch (DocumentException e3) {
			log.error("No se puede obtener la instancia del Writer.",e3);
		}
                 
		log.debug("Paso 3: Abriendo el PDF para escritura.");
        
         //Paso 3: Abrir el Document para comenzar la escritura
         document.open();         
         
        log.debug("Paso 4: Añadiendo contenido al PDF.");
         
         //Paso 4: Añadir el contenido en el Document  
         
                 
         //El contenido esta representado en una tabla de 1 sola col
         PdfPTable content = new PdfPTable (1);
         content.setWidthPercentage (100.0f);
         
         
         //El texto va agrupado en una celda de la tabla
         PdfPCell cell = new PdfPCell ();
         cell.setMinimumHeight (document.getPageSize ().getHeight () - ObituarioRequest.properties.getCellSize(reqParams.get(8),hasImages) - ObituarioRequest.properties.getCellSize(reqParams.get(8),hasImages));
         cell.setVerticalAlignment (Element.ALIGN_MIDDLE);
         cell.setBorder(Rectangle.NO_BORDER);
         //Componentes del texto que se añadiran al documento
         
         
         //Coloca la orla del documento
         
         log.debug("Paso 4.1: Añadiendo orla al documento.");         
          
        try {
			document.add(addImagen(getImagen(reqParams.get(8),0), ObituarioRequest.properties.getPageWidth(), ObituarioRequest.properties.getPageHeight(), 0, 0));
		} catch (DocumentException e1) {
			log.error("No se pudo agregar la orla al documento.",e1);
		}
        
         //Las imagenes predefinidas y el logo se colocan en otra capa
         PdfContentByte over = writer.getDirectContent();
                
         log.debug("Paso 4.2: Añadiendo imagen predefinida al documento (Solo si se selecciona).");         
         
         //Añade la imagen predefinida en caso de ser seleccionada
         if (reqParams.get(9)!=null && !reqParams.get(9).equals("")){
        	try {
        		addImageOverLayer(reqParams.get(9),0, over, ObituarioRequest.properties.getContainerW("header"), ObituarioRequest.properties.getContainerH("header"), ObituarioRequest.properties.getContainerX("header",reqParams.get(8)), ObituarioRequest.properties.getContainerY("header",reqParams.get(8)));
        		log.debug("Paso 4.3: Imagen Cargada - "+reqParams.get(9));
			} catch (Exception e) {
				log.error("No se pudo agregar la imagen predefinida al documento.", e);
			}
         }
         log.debug("Paso 4.3: Añadiendo foto/logo al documento (Solo si se cargo alguna).");         
         
        	if (reqParams.get(10)!=null && !reqParams.get(10).equals("")){
            	try {
            		addImageOverLayer(reqParams.get(10),1, over,ObituarioRequest.properties.getContainerW("logo"), ObituarioRequest.properties.getContainerH("logo"), ObituarioRequest.properties.getContainerX("logo",reqParams.get(8)), ObituarioRequest.properties.getContainerY("logo",reqParams.get(8)));
            		log.debug("Paso 4.4: Foto/Logo Cargado - "+reqParams.get(10));
            	} catch (Exception e) {
              		log.error("No se pudo agregar el foto/logo al documento.", e);
    			}
         
          }
        	
        log.debug("Paso 4.4: Añadiendo texto a la celda contenedora.");         
            addText(reqParams,cell);
            content.addCell (cell);
            try {
   			document.add (content);
   		} catch (DocumentException e2) {
   			log.error("No se pudo agregar el texto al documento.",e2);
   		}
         
        log.debug("Paso 5: Cerrando el PDF.");         
            
         //Paso 5: Cerrar el Document
         document.close();
	}
	
	
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public boolean isPreview() {
		return preview;
	}

	public void setPreview(boolean preview) {
		this.preview = preview;
	}
	
	public ByteArrayOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(ByteArrayOutputStream outputStream) {
		this.outputStream = outputStream;
	}
	public ArrayList<String> getReqParams() {
		return reqParams;
	}

	public void setReqParams(ArrayList<String> reqParams) {
		this.reqParams = reqParams;
	}
	
	@Override
	public Image getImagen(String nombre,int type) {
		Image img=null;
		try {
			if (type==0){
			img = Image.getInstance(ObituarioRequest.properties.getImagePath()+nombre);
			}else if (type==1){
				img = Image.getInstance(nombre);
				
			}
			
			} catch (Exception e) {
			log.error("No se pudo obtener la instancia de la imagen solicitada.",e);
		} 

        return img;
	}
	
	@Override
	public void addText(ArrayList<String> reqParams,PdfPCell cell){		
		Paragraph componente = null;
		String compDesc=null;
		
		/*
		 * Seleccionar que configuracion de texto usar
		 * segun los criterios:
		 * Imagenes y Caracteres en el Texto.
		 */
		
		String conf=getConfig(hasImages,reqParams.get(4));
		
        for (int comp=0; comp<reqParams.size()-6;comp++){
        	
       	 //Si omitio algun componente no se añade al documento
       	 if (reqParams.get(comp)!=null && !reqParams.get(comp).equals("")){
       	 
       		 switch (comp) {
				case 0:compDesc="preEncabezado";break;
				case 1:compDesc="encabezadoPrincipal";break;
				case 2:compDesc="nombreFallecido";break;
				case 3:compDesc="encabezadoSecundario";break;
				case 4:compDesc="texto";break;
				case 5:compDesc="pie";break;
				case 6:compDesc="fecha";break;
				default: break;
			}
       		 
       		 componente=new Paragraph(reqParams.get(comp),ObituarioRequest.properties.getFont(Fuentes.getHelvetica(), compDesc, conf));
       		 componente.setAlignment(ObituarioRequest.properties.getAlignment(compDesc, conf));
       		 componente.setSpacingAfter(ObituarioRequest.properties.getSpacingAf(compDesc, conf));
       		 componente.setSpacingBefore(ObituarioRequest.properties.getSpacingBe(compDesc, conf));
      		 
       		 if (comp==2){
       			 componente.setMultipliedLeading(1);	       
	         }else{
	        	 componente.setLeading(ObituarioRequest.properties.getLeading(compDesc, conf));
	         }	        	 
       	 	 cell.addElement (componente);
	        	 
       	 }
       	 
        }
		
	}
	
	private String getConfig(boolean hasImages2, String texto) {
		
		log.info("Se esta obteniendo la configuracion con los valores: ");
		log.info("HasImages: "+hasImages2);
		log.info("Longitud de texto: "+texto.length());
		
		
		
		if (hasImages2&&texto.length()>ObituarioRequest.properties.getMinRango(1)&&texto.length()<=ObituarioRequest.properties.getMaxRango(1))
			return ObituarioRequest.properties.getNombreRango()+1;
		if (hasImages2&&texto.length()>ObituarioRequest.properties.getMinRango(2)&&texto.length()<=ObituarioRequest.properties.getMaxRango(2))
			return ObituarioRequest.properties.getNombreRango()+2;
		if (hasImages2&&texto.length()>ObituarioRequest.properties.getMinRango(3)&&texto.length()<=ObituarioRequest.properties.getMaxRango(3))
			return ObituarioRequest.properties.getNombreRango()+3;
		if (!hasImages2&&texto.length()>ObituarioRequest.properties.getMinRango(1)&&texto.length()<=ObituarioRequest.properties.getMaxRango(1))
			return ObituarioRequest.properties.getNombreRango()+4;
		if (!hasImages2&&texto.length()>ObituarioRequest.properties.getMinRango(2)&&texto.length()<=ObituarioRequest.properties.getMaxRango(2))
			return ObituarioRequest.properties.getNombreRango()+5;
		if (!hasImages2&&texto.length()>ObituarioRequest.properties.getMinRango(3)&&texto.length()<=ObituarioRequest.properties.getMaxRango(3))
			return ObituarioRequest.properties.getNombreRango()+6;
		
		return null;
	}

	@Override
	public void writeOutput(){
		
		log.info("Escribiendo Documento PDF en el Response.");
		  response.setHeader("Expires", "0");
          response.setHeader("Cache-Control",
              "must-revalidate, post-check=0, pre-check=0");
          response.setHeader("Pragma", "public");
          
		 if (preview){
			 response.setContentType("application/pdf");
	         
			 log.info("Se previsualizara el documento PDF.");
				
			 try {
				 OutputStream os=response.getOutputStream();
				 outputStream.writeTo(os);
				 os.flush();
		         os.close();
			} catch (Exception e) {
				log.error("No se pudo escribir en el Response.",e);
				
			}
			
			 
		 }else{
			 log.info("Se Guardara el documento PDF.");
				
			try {
				FileOutputStream file = new FileOutputStream(ObituarioRequest.properties.getOutputPath()+reqParams.get(13)+".pdf");  
				 outputStream.writeTo(file);
		         file.flush();
		         file.close();
			} catch (Exception e) {
				log.error("No se pudo escribir en el Response.",e);
				
			}
			
		 }
         
		 
         
        
	}
	
	@Override
	public Image addImagen(Image img,float scaleW,float scaleH,float positionX,float positionY){
		img.scaleToFit(Utilities.millimetersToPoints(scaleW),Utilities.millimetersToPoints(scaleH+1));
        img.setAbsolutePosition(positionX,positionY);
       	return img;
	}

	
	
	@Override
	public void addImageOverLayer(String ImgName,int typeImg,PdfContentByte layer,float containerW,float containerH,float containerX, float containerY){
		
		float razonW=0;
		float razonH=0;
		float yCentrado=0; 
		float xCentrado=0;
		Image img=getImagen(ImgName,typeImg);	
		 
		razonH=Math.abs((containerH-(img.getPlainHeight()*FACTOR))/2); //rate de Altura
		razonW=Math.abs((containerW-(img.getPlainWidth()*FACTOR))/2); //rate de Anchura
		yCentrado=containerY+razonH*3;
		xCentrado=containerX+razonW*3;
		
		img.scaleToFit(Utilities.millimetersToPoints((img.getPlainWidth()*FACTOR)), Utilities.millimetersToPoints((img.getPlainHeight()*FACTOR)));
		img.setAbsolutePosition(xCentrado,yCentrado);
		
        try {
			layer.addImage(img);
		} catch (Exception e) {
			log.error("No se pudo agregar la imagen sobre la capa.",e);
		}

	}
}