package engine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LeerImagen {
	private Image img;
	private BufferedImage bimg;
	
	public LeerImagen(File file) {
		// TODO Auto-generated constructor stub
		 try {
	          bimg = ImageIO.read(file);
	          img = ImageIO.read(file);;
	          
	       } catch (IOException e) {
	       }
	}
	
	public void resizeImage() {
		img = img.getScaledInstance(bimg.getWidth()/2, bimg.getHeight()/2, java.awt.Image.SCALE_SMOOTH);
	}
	
	public Image getImage() {
		return img;
	}
	
	public void escribirImagenEnFichero(File fileToSave) {
		try {
		    // retrieve image
		    BufferedImage bi = imageToBufferedImage(getImage());
		    ImageIO.write(bi, "png", fileToSave);
		} catch (IOException e) {
		}
	}
	
	 private BufferedImage imageToBufferedImage(Image im) {
	     BufferedImage bi = new BufferedImage
	        (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
	     Graphics bg = bi.getGraphics();
	     bg.drawImage(im, 0, 0, null);
	     bg.dispose();
	     return bi;
	  }
}
