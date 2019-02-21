package engine;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

import gui.Main;

public class PDFManipulator {
	
	
	/**
	 * Genera una vista previa del documento pdf que se va a generar
	 */
	public static void vistaPreviaDocumento() {
		// Si contiene imágenes las combierte en pdf
					if (ImageManipulator.containsImages(Main.getLista())) {
						List<File> listadoMezcladoConImagenes = new ArrayList<>();
						List<File> listadoTemporalDeArchivos = new ArrayList<>();
						// Genera un fichero de salida pdf
						File ficheroSalida = new File(Main.getLista().get(0)+"fnltemp.pdf");
						// Combierte el listado original en pdf y guarda dicho listado de ficheros en listadoConImagenes
						ImageManipulator.convertImagesToPDF(listadoMezcladoConImagenes, listadoTemporalDeArchivos);
						// Combina el listadoMezcladoConImagenes (ahora solo contiene PDFs) y genera a la salida un fichero que sobreescribe el ficheroSalida del parámetro
						combinarMultiplesPDF(listadoMezcladoConImagenes, ficheroSalida);
						// Elimina los temporales
						openDocumentPDF(ficheroSalida);
						for(File file : listadoTemporalDeArchivos) {
							file.delete();
						}
						ficheroSalida.delete();
					} else {
						// Comprueba si el tamaño del fichero es mayor a 1 ó si necesita bajar la resolución a la imagen (Ya que si no, no debería modificar el fichero pdf cargado en el programa)
						if(Main.getLista().size() > 1) {
							File ficheroSalida = new File(Main.getLista().get(0)+"fnltemp.pdf");
							// Combina los ficheros y genera a la salida el ficheroSalida por parámetro
							combinarMultiplesPDF(Main.getLista(), ficheroSalida);
							openDocumentPDF(ficheroSalida);
							ficheroSalida.delete();
						}
					}
	}
	
	/**
	 * Genera un Documento PeDF de la lista de Main.getLIST y sobre escribe el fichero de salida
	 * @param ficheroSalida El fichero de salida al que debe ir la salida
	 */
	public static void generarDocumentPDF(File ficheroSalida) {
		if (ImageManipulator.getResolutionFile() == ImageManipulator.MAX_RESOLUTION) {
			// Si contiene imágenes las combierte en pdf
			if (ImageManipulator.containsImages(Main.getLista())) {
				List<File> listadoMezcladoConImagenes = new ArrayList<>();
				List<File> listadoTemporalDeArchivos = new ArrayList<>();
				// Combierte el listado original en pdf y guarda dicho listado de ficheros en listadoConImagenes
				ImageManipulator.convertImagesToPDF(listadoMezcladoConImagenes, listadoTemporalDeArchivos);
				// Combina el listadoMezcladoConImagenes (ahora solo contiene PDFs) y genera a la salida un fichero que sobreescribe el ficheroSalida del parámetro
				combinarMultiplesPDF(listadoMezcladoConImagenes, ficheroSalida);
				// Elimina los temporales
				for(File file : listadoTemporalDeArchivos) {
					file.delete();
				}
			} else {
				// Comprueba si el tamaño del fichero es mayor a 1 ó si necesita bajar la resolución a la imagen (Ya que si no, no debería modificar el fichero pdf cargado en el programa)
				if(Main.getLista().size() > 1) {
					// Combina los ficheros y genera a la salida el ficheroSalida por parámetro
					combinarMultiplesPDF(Main.getLista(), ficheroSalida);
				}
			}
			
		} else {
			if (ImageManipulator.containsImages(Main.getLista())) {
				List<File> listadoMezcladoConImagenes = new ArrayList<>();
				List<File> listadoTemporalDeArchivos = new ArrayList<>();
				// Combierte el listado original en pdf y guarda dicho listado de ficheros en listadoConImagenes
				ImageManipulator.convertImagesResizedToPDF(listadoMezcladoConImagenes, listadoTemporalDeArchivos);
				// Combina el listadoMezcladoConImagenes (ahora solo contiene PDFs) y genera a la salida un fichero que sobreescribe el ficheroSalida del parámetro
				combinarMultiplesPDF(listadoMezcladoConImagenes, ficheroSalida);
				// Elimina los temporales
				for(File file : listadoTemporalDeArchivos) {
					file.delete();
				}
			} else {
				// Comprueba si el tamaño del fichero es mayor a 1 ó si necesita bajar la resolución a la imagen (Ya que si no, no debería modificar el fichero pdf cargado en el programa)
				if(Main.getLista().size() > 1) {
					// Combina los ficheros y genera a la salida el ficheroSalida por parámetro
					combinarMultiplesPDF(Main.getLista(), ficheroSalida);
				}
			}

		}
	}
	
	
	/**
	 * Visualiza un documento en un Frame
	 * @param file El documento PDF a visualizar
	 */
	public static void openDocumentPDF(File file) {
		// Se abre el documento
		
	        // build a controller
		 SwingController controller = new SwingController();

	        // Build a SwingViewFactory configured with the controller
	        SwingViewBuilder factory = new SwingViewBuilder(controller);
	        PropertiesManager properties = new PropertiesManager( System.getProperties(), ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
	           
	        properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.75");

	        // Use the factory to build a JPanel that is pre-configured
	        //with a complete, active Viewer UI.
	       JPanel viewerComponentPanel = factory.buildViewerPanel();
	       JFrame fr = new JFrame();
	       fr.setTitle("Vista previa de: " + file.getName());
	       fr.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/aplication-icon.png")));
	       fr.setLayout(new BorderLayout());
	       fr.add(viewerComponentPanel, BorderLayout.CENTER);
	       fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
	       fr.setVisible(true);
	        controller.openDocument(file.getAbsolutePath());
	        
	}
	
	/**
	 * Combina múltiples ficheros de la lista de ficheros PDF y lo escribe en el fichero de salida pasado
	 * por parámetro
	 * @param listadoDeFicherosPDF La lista de ficheros PDF que deben ser combinados
	 * @param ficheroSalida El fichero de salida donde se escribirá el PDF
	 */
	public static void combinarMultiplesPDF(List<File> listadoDeFicherosPDF , File ficheroSalida) {
		try {
			// Combina el fichero
			PDFMergerUtility pdfMerger = new PDFMergerUtility(); 
			pdfMerger.setDestinationFileName(ficheroSalida.getAbsolutePath());
			for(File file : listadoDeFicherosPDF) {
				pdfMerger.addSource(file);
			}
			pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido crear el fichero " + ficheroSalida.getAbsolutePath(), "Ups! Algo ha salido mal", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Main.class.getResource("/images/error-icon.png")));
		}
	}	
  
}
