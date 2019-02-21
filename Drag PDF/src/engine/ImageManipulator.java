package engine;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;


import gui.Comprobar;
import gui.Main;

public class ImageManipulator {

	public static final int MAX_RESOLUTION = 0;
	public static final int MIN_SIZE = 1;
	
	public static int getResolutionFile() {
	    String[] salida = {    		
	            "Máxima resolución (Por defecto)",
	            "Tamaño mínimo",
	        };
	        
	        String sal = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de salida de fichero que desea", "¿Cómo desea el fichero?", JOptionPane.DEFAULT_OPTION, null, salida, salida[0]);	        
	        if(sal.equals((salida[0]))) {
	        	return MAX_RESOLUTION;
	        }
	        return MIN_SIZE;
	}
	
	/**
	 * Crea un documento PDF a partir de una imagen
	 * @param image La imagen que será convertida a pdf
	 * @param output El fichero PDF que se creará a la salida
	 */
	public static void createPDFromImage(File image, File output) {

		Document document = new Document();

		try {
			Image img = Image.getInstance(image.getAbsolutePath());
			// Agrega propiedades al documento
			document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
			document.setMargins(0, 0, 0, 0);
			document.addAuthor("Drag PDF");
			
			FileOutputStream fos = new FileOutputStream(output);
			PdfWriter writer = PdfWriter.getInstance(document, fos);
			writer.open();
			document.open();

			document.add(Image.getInstance(img));
			document.close();
			writer.close();

		} catch (Exception i1) {
			i1.printStackTrace();
		}
	}
	
	/**
	 * Crea un documento PDF a partir de una imagen reescalada a la mitad
	 * @param image La imagen que será previamente reescalada a la mitad y convertida a pdf
	 * @param output El fichero PDF que se creará a la salida
	 */
	public static void createPDFromImageResized(File image, File output) {

		Document document = new Document();

		try {
			Image img = Image.getInstance(image.getAbsolutePath());
			// Agrega propiedades al documento
			document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
			document.setMargins(0, 0, 0, 0);
			document.addAuthor("Drag PDF");
			
			FileOutputStream fos = new FileOutputStream(output);
			PdfWriter writer = PdfWriter.getInstance(document, fos);
			writer.open();
			document.open();

			document.add(Image.getInstance(img));
			document.close();
			writer.close();

		} catch (Exception i1) {
			i1.printStackTrace();
		}
	}
    
    /**
     * Comprueba si una lista de ficheros son imágenes
     * @param lista de ficheros a comprobar
     * @return Si contiene imágenes o no
     */
    public static boolean containsImages(List<File> listadoDeFicheros) {
    	boolean containImages = false;
    	for(File file : listadoDeFicheros) {
    		if(Comprobar.isJPG(file) || Comprobar.isPNG(file)) {
    			containImages = true;
    		}
    	}
    	return containImages;
    }
    
    /**
     * Recorre cada Archivo del la colección del Main (PRINCIPAL) y lo convierte a pdf si no era ya un pdf
     * @param listadoMezcladoConImagenes La lista vacía cual será creada con SOLO elementos PDF
     * @param listadoTemporalDeArchivos La lista de ficheros que serán borrados tras las operaciones 
     */
    public static void convertImagesToPDF (List<File> listadoMezcladoConImagenes, List<File> listadoTemporalDeArchivos) {
    	// Se recorre toda la colección original
    	for(File file : Main.getLista()) {
    		// Si no es pdf lo combierte
    		if(!Comprobar.isPDF(file)) {
    			// Crea un fichero temporal en la misma ruta agregando .pdf
				File output = new File(file.getAbsolutePath()+".pdf");
				// Crea el pdf de una imagen
				ImageManipulator.createPDFromImage(file, output);
				// Agrega output a la lista de ficheros final y la lista de ficheros temporal (para luego ser borrado)
				listadoMezcladoConImagenes.add(output);
				listadoTemporalDeArchivos.add(output);
    		} else {
    			listadoMezcladoConImagenes.add(file);
    		}
    	}
    }
    
    /**
     * Recorre cada Archivo del la colección del Main (PRINCIPAL) y lo convierte a pdf si no era ya un pdf
     * @param listadoMezcladoConImagenes La lista vacía cual será creada con SOLO elementos PDF
     * @param listadoTemporalDeArchivos La lista de ficheros que serán borrados tras las operaciones 
     */
    public static void convertImagesResizedToPDF (List<File> listadoMezcladoConImagenes, List<File> listadoTemporalDeArchivos) {
    	// Se recorre toda la colección original
    	for(File file : Main.getLista()) {
    		// Si no es pdf lo combierte
    		if(!Comprobar.isPDF(file)) {
    			// Reescala la imagen
    			File imageReescalated = new File(file.getAbsolutePath()+"-reescalated");
    			LeerImagen io = new LeerImagen(file.getAbsoluteFile());
    			io.resizeImage();
    			io.escribirImagenEnFichero(imageReescalated);
    			
    			// La agrega para listaTemporalDeArchivos
    			listadoTemporalDeArchivos.add(imageReescalated);
    			// Crea un fichero pdf temporal de la imagen reescalada temporal en la misma ruta agregando .pdf
				File output = new File(file.getAbsolutePath()+".pdf");
				// Crea el pdf de una imagen
				ImageManipulator.createPDFromImageResized(imageReescalated, output);
				// Agrega output a la lista de ficheros final y la lista de ficheros temporal (para luego ser borrado)
				listadoMezcladoConImagenes.add(output);
				listadoTemporalDeArchivos.add(output);
    		} else {
    			listadoMezcladoConImagenes.add(file);
    		}
    	}
    }  

}
