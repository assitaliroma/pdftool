package com.servlet.pdf.interfaces;

import java.util.ArrayList;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;

public interface DrawerInterface {
	
	/**
	 * M�todo que permite a�adir una imagen con posicionamiento directo
	 * en el documento PDF sin usar capas (Direct Content)
	 * @param img Imagen a agregar
	 * @param scaleW Ancho de la imagen para escalar
	 * @param scaleH Alto de la imagen para escalar
	 * @param positionX Posicion X de la imagen
	 * @param positionY Posicion Y de la imagen
	 * @return
	 */
	public Image addImagen(Image img,float scaleW,float scaleH,float positionX,float positionY);

	/**
	 * M�todo para escribir en el OutputStream del response
	 */
	public void writeOutput();
	
	/**
	 * M�todo para a�adir texto a una celda de una PDFTable
	 * @param reqParams Componentes del texto
	 * @param cell Celda donde se escribir� el texto
	 */
	public void addText(ArrayList<String> reqParams,PdfPCell cell);
	
	/**
	 * M�todo para obtener una instancia de una imagen
	 * @param nombre Nombre de la imagen
	 * @return
	 */
	public Image getImagen(String nombre,int type);
	
	/**
	 * M�todo que contiene la l�gica de como se estructura el PDF
	 */
	public void Draw();

	/**
	 * M�todo para agregar una imagen a una capa del documento 
	 * con posicionamiento absoluto
	 * 
	 * @param ImgName nombre de la imagen para a�adir
	 * @param scaleW  ancho de la iamgen para escalar
	 * @param scaleH alto de imagen para escalar
	 * @param layer  capa del PDFWriter donde escribir
	 * @param containerW ancho del contenedor
	 * @param containerH alto dle contenedor
	 * @param containerX posicion X del contenedor
	 * @param containerY Posicion Y del contenedor
	 */
	public void addImageOverLayer(String ImgName,int typeImg,PdfContentByte layer, float containerW, float containerH,
			float containerX, float containerY);


}
