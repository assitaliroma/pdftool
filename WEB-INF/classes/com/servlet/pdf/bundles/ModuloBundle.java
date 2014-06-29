package com.servlet.pdf.bundles;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;


public class ModuloBundle {

	public static ResourceBundle bundle;
	
	private static Logger log=Logger.getLogger(ModuloBundle.class);
	
 
	 /*
	  * Caracteristicas de imagenes
	  */


	 private float pageHeight;
	 private float pageWidth;
	 private float containerH;
	 private float containerW;
	 private float containerX;
	 private float containerY;

	 /*
	  * Paths
	  */
	
	 private String imagePath;
	 private String outputPath;
	 
	 /*
	  * Rangos
	  */

	 private String nombreRango;


	public  ModuloBundle(int modulo){
		
		log.info("Se va a limpiar la cache de los Bundles");
		ResourceBundle.clearCache();
		log.info("Obteniendo Bundle del .properties Modulo"+modulo);
		bundle=ResourceBundle.getBundle("com.servlet.properties.Modulo"+modulo);
	
		
		pageWidth= Float.parseFloat(bundle.getString("page_w"));
		pageHeight= Float.parseFloat(bundle.getString("page_h"));
       
		imagePath=bundle.getString("image_path");
		outputPath=bundle.getString("output_path");
		
		nombreRango=bundle.getString("nombre_rango");
		
		 
		log.info("******Lectura de propiedades (Tamaño de Pagina y Paths) :******");
		log.info("Ancho de la Pagina: "+bundle.getString("page_w"));
		log.info("Alto de la Pagina: "+bundle.getString("page_h"));
		log.info("Path de Imagenes: "+bundle.getString("image_path"));
		log.info("Path de Salida: "+bundle.getString("output_path"));
		
			
	}

	public  float getMarginContentTop(String orla,boolean hasImage) {
		
		return Float.parseFloat(bundle.getString("margin-top_"+orla+"_"+hasImage));
	}

	public float getMarginContentBottom(String orla,boolean hasImage) {
		return Float.parseFloat(bundle.getString("margin-bottom_"+orla+"_"+hasImage));
		
	}

	public float getMarginContentLeft(String orla,boolean hasImage) {
		return Float.parseFloat(bundle.getString("margin-left_"+orla+"_"+hasImage));
		
	}

	public float getMarginContentRight(String orla,boolean hasImage) {
		return Float.parseFloat(bundle.getString("margin-right_"+orla+"_"+hasImage));
		
	}

	public float getPageHeight() {
		return pageHeight;
	}

	public float getPageWidth() {
		return pageWidth;
	}

	public float getCellSize(String orla,boolean hasImage) {
		return Float.parseFloat(bundle.getString("cell_"+orla+"_"+hasImage));
	}


	public String getImagePath() {
		return imagePath;
	}


	public String getOutputPath() {
		return outputPath;
	}

	
	public float getContainerH(String type) {
		containerH=Float.parseFloat(bundle.getString(type+"_container_h"));
		return containerH;
	}

	public float getContainerW(String type) {
		containerW=Float.parseFloat(bundle.getString(type+"_container_w"));
		return containerW;
	}



	public float getContainerX(String type,String orla) {
		containerX=Float.parseFloat(bundle.getString(type+"_container_x_"+orla));
		return containerX;
	}


	public float getContainerY(String type,String orla) {
		containerY=Float.parseFloat(bundle.getString(type+"_container_y_"+orla));
		return containerY;
	}

	/*
	 * Metodos Getters por componentes
	 */
	
	public float getSpacingAf(String componente,String fontConfig) {
		return Float.parseFloat(bundle.getString(fontConfig+"_"+componente+"_spacing_after"));
		
	}

	public float getSpacingBe(String componente,String fontConfig) {
		return Float.parseFloat(bundle.getString(fontConfig+"_"+componente+"_spacing_before"));
		
	}
	
	public Font getFont(BaseFont font,String componente,String fontConfig) {
		return new Font(font, Float.parseFloat(bundle.getString(fontConfig+"_"+componente+"_size")), Integer.parseInt(bundle.getString(fontConfig+"_"+componente+"_bold")));
		
	}
	
	public float getLeading(String componente,String fontConfig) {
		return Float.parseFloat(bundle.getString(fontConfig+"_"+componente+"_leading"));
		
	}
	
	public int getAlignment(String componente,String fontConfig) {
		return Integer.parseInt(bundle.getString(fontConfig+"_"+componente+"_align"));
		
	}

	public int getMinRango(int rango) {
		return Integer.parseInt(bundle.getString("min_rango_"+rango));
	}

	public int getMaxRango(int rango) {
		return Integer.parseInt(bundle.getString("max_rango_"+rango));
	}

	public String getNombreRango() {
		return nombreRango;
	}


	
}
