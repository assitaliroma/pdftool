package com.servlet.pdf.fonts.embedded;


import org.apache.log4j.Logger;
import com.itextpdf.text.pdf.BaseFont;


/**
 * En esta clase se deben agregar todos ls tipos de fuentes embebidas
 * que se deseen tener disponibles. Una fuente embebida garantiza su uso
 * y que no se transforme en las fuentes por defecto en los readers.
 * @author anrivero
 *
 */
public class Fuentes {

	private static  BaseFont helvetica=null;	
	private static Logger log=Logger.getLogger(Fuentes.class);
	

	/**
	 * Obtiene la fuente helvetica con el archivo contenido dentro de la libreria
	 * @return
	 */
	public static BaseFont getHelvetica() {
		try {
			
			helvetica=BaseFont.createFont(BaseFont.HELVETICA,BaseFont.WINANSI,BaseFont.EMBEDDED);
			
        }catch (Exception e) {
		log.error("Fuente Helvetica no fue cargada exitosamente.",e);
		
		
		}
		return helvetica;
	}

}
