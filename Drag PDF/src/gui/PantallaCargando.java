package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class PantallaCargando extends JDialog {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout1 = new BorderLayout();
  JLabel imageLabel = new JLabel();
  JPanel southPanel = new JPanel();
  FlowLayout southPanelFlowLayout = new FlowLayout();
  JProgressBar progressBar = new JProgressBar();
  ImageIcon imageIcon;

  public PantallaCargando(ImageIcon imageIcon) {
    this.imageIcon = imageIcon;
    dibujaVentana();
  }

  public void dibujaVentana() {
    imageLabel.setIcon(imageIcon);
    this.getContentPane().setLayout(borderLayout1);
    
    southPanel.setLayout(southPanelFlowLayout);
    southPanel.setBackground(new Color(0,0,0,0));
    southPanel.setOpaque(true);
    this.getContentPane().add(imageLabel, BorderLayout.CENTER);
    this.getContentPane().add(southPanel, BorderLayout.SOUTH);
    this.setResizable(false);
    this.setUndecorated(true);
    this.setBackground(new Color(0,0,0,0));
    progressBar.setVisible(false);
    southPanel.add(progressBar, BorderLayout.CENTER);
    this.pack();
  }

  public void setProgresoMax(int maxProgress)
  {
    progressBar.setMaximum(maxProgress);}

  public void setProgreso(int progress)
  {
    final int progreso = progress;
        progressBar.setValue(progreso);}

  public void setProgreso(String message, int progress)
  {
    final int progreso = progress;
    final String theMessage = message;
    setProgreso(progress);
    progressBar.setValue(progreso);
    setMessage(theMessage);  }

  private void setMessage(String message)
 {
    if (message==null){
      message = "";
      progressBar.setStringPainted(false);}
    else{
      progressBar.setStringPainted(true);}

    progressBar.setString(message); }

public void velocidadDeCarga(){
    for (int i = 0; i <= 100; i++)
    {
      for (long j=0; j<1000000; ++j)//modifica el numero segun la velidad q desees
      {
        @SuppressWarnings("unused")
		String poop = " " + (j + i);
      }
     setProgreso("" + i, i);  // si quieres q muestre los numeros y un mensaje
     //setProgreso(i);        //si no quieres q muestre nada, solo la barra
   }
    dispose();}

}