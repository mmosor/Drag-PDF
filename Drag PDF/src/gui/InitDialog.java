package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class InitDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel imageLabel = new JLabel();
	private JProgressBar progressBar;

	/**
	 * Create the dialog.
	 */
	public InitDialog(ImageIcon imageIcon) {
		setUndecorated(true);
		setResizable(false);
		setUndecorated(true);
	    setBackground(new Color(0,0,0,0));
	    contentPanel.setBackground(new Color(0,0,0,0));
	    // Centra la ventana
	    setBounds(100, 100, 498, 417);
	    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension ventana = getSize();
		setLocation((pantalla.width-ventana.width)/2, (pantalla.height-ventana.height)/2);
		imageLabel.setIcon(imageIcon);
		contentPanel.add(imageLabel, BorderLayout.CENTER);
		progressBar = new JProgressBar();
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
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
