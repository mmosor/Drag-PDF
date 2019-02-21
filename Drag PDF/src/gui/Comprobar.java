package gui;
import java.io.File;

public class Comprobar {
		
		/**
		 * Comprueba si una fichero es jpg
		 * @param file El archivo a comprobar
		 * @return Si es una imagen JPG o no
		 */
		public static boolean isJPG(File file) {
		        if(file.toString().endsWith(".jpg")||file.toString().endsWith(".JPG")){
		        	return true;
		        }
		        return false;
		}
		
		/**
		 * Comprueba si una fichero es PNG
		 * @param file El archivo a comprobar
		 * @return Si es una imagen PNG o no
		 */
		public static boolean isPNG(File file) {
		        if(file.toString().endsWith(".png")||file.toString().endsWith(".PNG")){
		        	return true;
		        }
		        return false;
		}
		
		/**
		 * Comprueba si una fichero es pdf
		 * @param file El archivo a comprobar
		 * @return Si es una imagen PDF o no
		 */
		public static boolean isPDF(File file) {
		        if(file.toString().endsWith(".pdf")||file.toString().endsWith(".PDF")){
		        	return true;
		        }
		        return false;
		}
		
		/**
		 * Comprueba si un fichero es válido
		 * @param file El archivo a evaluar
		 * @return Si es válido o no
		 */
		public static boolean isValidFile(File file) {
			if(isJPG(file) || isPNG(file) || isPDF(file)) {
				return true;
			}
			return false;
		}
}
