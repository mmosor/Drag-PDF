package gui;

import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class PantallaCargandoMain {

  //PantallaCargando screen;
	InitDialog screen;
	
  public PantallaCargandoMain() {
    inicioPantalla();
	screen.velocidadDeCarga();
  }

  private void inicioPantalla() {
    ImageIcon myImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/init-icon.png")));
    //screen = new PantallaCargando(myImage);
    screen = new InitDialog(myImage);
    screen.setProgresoMax(100);
    screen.setVisible(true);
  }
  
  
}