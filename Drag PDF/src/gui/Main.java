package gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import engine.PDFManipulator;
import engine.SO;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.DropMode;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JPanel panel_Central;
	private static JPanel panelInferior;
	private static List<File> listaFicheros;
	private static DefaultTableModel tableModel;
	private static JTable listView;
	private static JScrollPane scrollPane;
	private JMenuItem menuItemMove;
	private JMenuItem menuItemRemove;
	public static JPanel panelRight;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { 
		 try {
			 if(SO.isWindows()) {
				 UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			 } /*else if(SO.isUnix()){
				 UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			 }*/
	    } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	       // handle exception
	    } 
		
		new InitDialog();
		 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public Main() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/aplication-icon.png")));
		setTitle("Drag PDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 357);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Documento");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmCrearPdf = new JMenuItem("Añadir archivos");
		
		listaFicheros = new ArrayList<>();
		
		mntmCrearPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				//Creamos el objeto JFileChooser
				JFileChooser fc=new JFileChooser();
				fc.setDialogTitle("Selecciona los archivos para crear el pdf");
				 
				//Indicamos que podemos seleccionar varios ficheros
				fc.setMultiSelectionEnabled(true);
				 
				//Indicamos que podemos seleccionar solo ficheros
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				 
				//Abrimos la ventana, guardamos la opcion seleccionada por el usuario
				int seleccion=fc.showOpenDialog(contentPane);
				 
				//Si el usuario, pincha en aceptar
				if(seleccion==JFileChooser.APPROVE_OPTION){
				 
				    //Seleccionamos el fichero
				    File[] ficheros=fc.getSelectedFiles();
				    
				    List<File> listaTemp = new ArrayList<File>();
				   
				    // Copiamos los ficheros a la lista
				    for(int i=0; i<ficheros.length; i++) {
				    	listaTemp.add(ficheros[i]);
				    	
				    }
				    
				    addFilesToList(listaTemp);
				    
				    // Habilitamos la barra de herramientas
				    panelRight.setVisible(true);
				    panelInferior.setVisible(true);
				    
				}
				
			}
		});
		mnArchivo.add(mntmCrearPdf);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JSeparator separator = new JSeparator();
		mnArchivo.add(separator);
		mnArchivo.add(mntmSalir);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About acercaDe = new About();
				acercaDe.setVisible(true);
			}
		});
		
		JMenuItem mntmAtajosDeTeclado = new JMenuItem("Atajos de teclado");
		mntmAtajosDeTeclado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Pronto estará disponible esta función");
			}
		});
		mnAyuda.add(mntmAtajosDeTeclado);
		mnAyuda.add(mntmAcercaDe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel_Central = new JPanel();
		contentPane.add(panel_Central, BorderLayout.CENTER);
		
		   // Create the drag and drop listener
	    MyDragDropListener myDragDropListener = new MyDragDropListener();
	    panel_Central.setLayout(new BoxLayout(panel_Central, BoxLayout.X_AXIS));
		
	    String col[] = {"Posición","Tipo","Nombre", "Ver"};

	    tableModel = new DefaultTableModel(col, 0){
	        @Override
	        public Class<?> getColumnClass(int column) {
	            switch(column) {
	                case 0: return Integer.class;
	                case 1: return ImageIcon.class;
	                case 2: return Object.class;
	                case 3: return ImageIcon.class;
	                default: return Object.class;
	            }
	        }
	    };
		
		JPanel panelLeft = new JPanel();
		panel_Central.add(panelLeft);
		
		JLabel btnDragAndDrop = new JLabel(new ImageIcon(((new ImageIcon(Main.class.getResource("/images/file-2127833_640.png"))).getImage()).getScaledInstance(230, 230, java.awt.Image.SCALE_SMOOTH)));
		panelLeft.add(btnDragAndDrop);
		btnDragAndDrop.setText("Arrastrar archivos aquí");
		btnDragAndDrop.setHorizontalTextPosition( SwingConstants.CENTER );
		btnDragAndDrop.setVerticalTextPosition( SwingConstants.BOTTOM );
		
			    // Connect the label with a drag and drop listener
			    new DropTarget(btnDragAndDrop, (DropTargetListener) myDragDropListener);
		
		panelRight = new JPanel();
		panel_Central.add(panelRight);
		panelRight.setLayout(new BorderLayout(0, 0));
		panelRight.setVisible(false);
		listView = new JTable (tableModel){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return colIndex != 0 && colIndex != 1 && colIndex != 2 && colIndex != 3;
				}
		};
		listView.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				 if(e.getKeyCode()== KeyEvent.VK_DELETE) {
					 if(listView.getSelectedRow()!=-1) {
							removeFileToList(listView.getSelectedRow());
								if(listView.getRowCount()==0) {
									panelRight.setVisible(false);
									panelInferior.setVisible(false);
								}
							} else {
								JOptionPane.showMessageDialog(null, "Debes seleccionar un fichero para quitarlo de la lista", "Selecciona una fila", JOptionPane.ERROR_MESSAGE);
							}
						}
			}
		});
		
		listView.setDragEnabled(true);
		listView.setDropMode(DropMode.INSERT_ROWS);
		listView.setTransferHandler(new TableRowTransferHandler(listView)); 
		
		listView.addMouseListener(new MouseAdapter() {
			
			@Override
		    public void mousePressed(MouseEvent event) {
		        // selects the row at which point the mouse is clicked
		        Point point = event.getPoint();
		        int currentRow = listView.rowAtPoint(point);
		        listView.setRowSelectionInterval(currentRow, currentRow);
		    }
	       
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listView.isColumnSelected(3)) {
					try {

			            File objetofile = listaFicheros.get(listView.getSelectedRow());
			            Desktop.getDesktop().open(objetofile);

			     }catch (IOException ex) {
			            JOptionPane.showMessageDialog(null, "Error al abrir el documento: " + ex, "Ups, algo ha ido mal", JOptionPane.ERROR_MESSAGE);

			     }
				}
			}
		});
		
		listView.setShowVerticalLines(false);
		// Cambia el ancho de las columnas de la tabla
		listView.getColumnModel().getColumn(0).setMinWidth(44);
		listView.getColumnModel().getColumn(0).setMaxWidth(150);
		listView.getColumnModel().getColumn(1).setPreferredWidth(64);
		listView.getColumnModel().getColumn(1).setMaxWidth(64);
		listView.getColumnModel().getColumn(1).setMinWidth(64);
		listView.getColumnModel().getColumn(2).setPreferredWidth(150);
		listView.getColumnModel().getColumn(3).setPreferredWidth(64);
		listView.getColumnModel().getColumn(3).setMaxWidth(64);
		listView.getColumnModel().getColumn(3).setMinWidth(64);
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER); //RIGHT o LEFT
		listView.getColumnModel().getColumn(0).setCellRenderer(tcr);
		
		listView.setRowHeight(64); 
		
		JPopupMenu popupMenu = new JPopupMenu();
		menuItemMove = new JMenuItem("Mover");
		menuItemMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Puedes mover cualquier fichero simplemente seleccionándolo y arrastrándolo a la fila que quieras", "Ayuda", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		menuItemRemove = new JMenuItem("Remover fichero");
		menuItemRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listView.getSelectedRow()!=-1) {
				removeFileToList(listView.getSelectedRow());
					if(listView.getRowCount()==0) {
						panelRight.setVisible(false);
						panelInferior.setVisible(false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debes seleccionar un fichero para quitarlo de la lista", "Selecciona una fila", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		 
		popupMenu.add(menuItemMove);
		popupMenu.add(menuItemRemove);
		
		scrollPane = new JScrollPane(listView);
		panelRight.add(scrollPane);
		
		// sets the popup menu for the table
		listView.setComponentPopupMenu(popupMenu);
		
				listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				JLabel lblListadoDeFicheros = new JLabel("Listado de ficheros");
				panelRight.add(lblListadoDeFicheros, BorderLayout.NORTH);
		
		panelInferior = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		panelInferior.setVisible(false);
		contentPane.add(panelInferior, BorderLayout.SOUTH);
		
		JButton btnVistaPrevia = new JButton("Vista Previa");
		btnVistaPrevia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isIncorrectFile = false;
				Integer[] listadoDePos = new Integer [listaFicheros.size()];
				// Comprueba que todos los ficheros son válidos
				for(int i = 0; i<listaFicheros.size(); i++) {
					File archivo = listaFicheros.get(i);
					if(!Comprobar.isValidFile(archivo)) {
						listadoDePos[i]=i+1;
						isIncorrectFile = true;
						listView.getSelectionModel().setSelectionInterval (i, i); 
					}
				}
				
				if(!isIncorrectFile) {
					// Genera la vista previa del documento
					PDFManipulator.vistaPreviaDocumento();
					
				} else {
					String documentos = new String("\n");
					for(int i=0; i<listadoDePos.length; i++) {
						if(listadoDePos[i]!= null) {
							documentos += "- Posición del fichero: " + listadoDePos[i] + "\n"; 
						}
					}
					if(listadoDePos.length>1) {
						JOptionPane.showMessageDialog(null, "No se puede generar el fichero PDF a partir de los siguientes ficheros:\n" +  documentos + "\nSon ficheros válidos (JPG, PNG o PDF) \n", "No se puede crear el fichero PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Main.class.getResource("/images/error-icon.png")));
					}else {
						JOptionPane.showMessageDialog(null, "No se puede generar el fichero PDF a partir del siguiente fichero:\n" +  documentos + "\nEs un fichero válido (JPG, PNG o PDF) \n", "No se puede crear el fichero PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Main.class.getResource("/images/error-icon.png")));

					}
				}
			}
		});
		panelInferior.add(btnVistaPrevia);
		
		JButton btnCrear = new JButton("Crear PDF");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Creamos el objeto JFileChooser
				JFileChooser fc=new JFileChooser();
				fc.setDialogTitle("Selecciona el fichero PDF de salida");
				 
				//Indicamos que podemos seleccionar varios ficheros
				fc.setMultiSelectionEnabled(true);
				 
				//Indicamos que podemos seleccionar solo ficheros
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				//Creamos el filtro
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PDF", "pdf");
				 
				//Le indicamos el filtro
				fc.setFileFilter(filtro);
				 
				//Abrimos la ventana, guardamos la opcion seleccionada por el usuario
				int seleccion=fc.showSaveDialog(contentPane);
				
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					PDFManipulator.generarDocumentPDF(fc.getSelectedFile());
				}
					
			}
		});
		panelInferior.add(btnCrear);
		
	}
	
	/**
	 * Cambia la lista de archivos que deben ser impresos
	 */
	public static void addFilesToList(List<File> lista) {
		// Agrega los elementos a la lista principal
		for(File archivo : lista) {
			listaFicheros.add(archivo);
		}
		
		System.out.println("La lista actual es:");
		for(File archivo:listaFicheros) {
			System.out.println("Archivo: " + archivo);
		}
		
		changePanelView();
	}
	
	/**
	 * Cambia la lista de archivos que deben ser impresos
	 */
	public static void insertFilesinPosToList(File file, int pos) {
		listaFicheros.add(pos, file);
		
		changePanelView();
	} 
	
	
	/**
	 * Elimina el fichero en una posición dada
	 * @pre pos debe ser una posción válida
	 * @param pos La posición a eliminar
	 */
	public static void removeFileToList(int pos){
		listaFicheros.remove(pos);
		changePanelView();
	}
	
	/**
	 * Modifica el Panel para insertar la lista de ficheros que serán combinados
	 */
	public static void changePanelView() {
		// Vacía la tabla
		for(int i= 0; i<tableModel.getRowCount(); ) {
			tableModel.removeRow(i);
		}
		
		// LLena la tabla basándose en la lista
		Object rowData[] = new Object[4];
		Integer pos = 1;
		ImageIcon iconFile = new ImageIcon(Main.class.getResource("/images/unknown-file.png"));
		ImageIcon iconSearch = new ImageIcon(Main.class.getResource("/images/search-icon.png"));
		for(File archivo : listaFicheros) {
			rowData[0] = pos;
			if (Comprobar.isJPG(archivo)){
				rowData[1] = new ImageIcon(Main.class.getResource("/images/jpg-file.png"));
			} else if( Comprobar.isPNG(archivo)) {
				rowData[1] = new ImageIcon(Main.class.getResource("/images/png-file.png"));
			} else if (Comprobar.isPDF(archivo)) {
				rowData[1] = new ImageIcon(Main.class.getResource("/images/pdf-file.png"));
			} else {
				rowData[1] = iconFile;
			}
			rowData[2] = archivo.getName();
			rowData[3] = iconSearch;
			tableModel.addRow(rowData);
			pos++;
		}
		
		// Pone visible la lista abajo
		panelInferior.setVisible(true);
		
	}
	
	/**
	 * Devuelve la lista de ficheros 
	 * @return la lista de ficheros
	 */
	public static List<File> getLista(){
		return listaFicheros;
	}
	
}
