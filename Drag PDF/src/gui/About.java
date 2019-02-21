package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextArea;

public class About extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public About() {
		setUndecorated(true);
		setTitle("Acerca de Drag PDF");
		setBounds(100, 100, 847, 347);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel_Left = new JPanel();
			contentPanel.add(panel_Left, BorderLayout.WEST);
			panel_Left.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JLabel lblImagen = new JLabel(new ImageIcon(((new ImageIcon(Main.class.getResource("/images/about-icon.png"))).getImage())));
				panel_Left.add(lblImagen);
			}
		}
		{
			JPanel panel_Right = new JPanel();
			contentPanel.add(panel_Right, BorderLayout.CENTER);
			panel_Right.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblDragPdf = new JLabel("Drag PDF");
				lblDragPdf.setFont(new Font("FreeSans", Font.BOLD, 30));
				panel_Right.add(lblDragPdf, BorderLayout.NORTH);
			}
			{
				JPanel panel_Top = new JPanel();
				panel_Right.add(panel_Top, BorderLayout.CENTER);
				panel_Top.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JTextArea txtrVersionn = new JTextArea();
					txtrVersionn.setLineWrap(true);
					txtrVersionn.setText("Versión 1.0 (Versión Java 1.8.0_161)\n\n\nDrag PDF está diseñado por Víctor Carreras y Laura Calvo.\n\n- Diseño de UI por Laura Calvo.\n- Programado por Víctor Carreras.\n"
							+ "\n\nAgradecimientos:\nEste proyecto ha sido desarrollado con el empleo de librerías Open Source, ICEpdf y OpenPDF.");
					txtrVersionn.setOpaque(false);
					txtrVersionn.setBackground(new Color(0,0,0,0));
					JScrollPane scrollPane = new JScrollPane(txtrVersionn);
					scrollPane.getViewport().setOpaque(false);
					scrollPane.setOpaque(false);
					scrollPane.setBorder(null);
					txtrVersionn.setWrapStyleWord(true);
					txtrVersionn.setEditable(false);
					panel_Top.add(scrollPane);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Salir");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
