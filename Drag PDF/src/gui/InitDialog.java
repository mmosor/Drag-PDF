package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class InitDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private BorderLayout borderLayout1 = new BorderLayout();
	private final JPanel contentPanel = new JPanel();
	private JLabel imageLabel = new JLabel();
	private ImageIcon imageIcon;

	/**
	 * Constructor 
	 * @param imageIcon
	 */
	public InitDialog() {
		imageIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/init-icon.png")));
		//InitDialog screen = new InitDialog(imageIcon);
		//screen.setVisible(true);
		draWindow();
	}

	/**
	 * Draw the window
	 */
	public void draWindow() {
		imageLabel.setIcon(imageIcon);
		this.getContentPane().setLayout(borderLayout1);
		this.getContentPane().add(imageLabel, BorderLayout.CENTER);
		setUndecorated(true);
		setResizable(false);
	    setBackground(new Color(0,0,0,0));
	    contentPanel.setBackground(new Color(0,0,0,0));
		// Centra la ventana
	    setBounds(100, 100, 498, 417);
	    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension ventana = getSize();
		setLocation((pantalla.width-ventana.width)/2, (pantalla.height-ventana.height)/2);
		imageLabel.setIcon(imageIcon);
		contentPanel.add(imageLabel, BorderLayout.CENTER);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		velocidadDeCarga();
	}

	/**
	 * Set loading speed
	 */
	public void velocidadDeCarga() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Error al dormir el proceso", "Advertence", JOptionPane.ERROR);
		}
		dispose();
	}

}