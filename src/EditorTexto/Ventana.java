package EditorTexto;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;

public class Ventana extends JFrame implements ActionListener{

  File fich;
  
  JTextArea cajaTexto;
  
  JPanel panel;
  JMenuBar barraMenus;
  
  JMenu menuArchivo,menuEdicion;
  JMenuItem itemNuevo, itemAbrir,itemGuardar,itemGuardarComo,itemSalir;
  JMenuItem itemCortar,itemCopiar,itemPegar;
  
  JFileChooser sel;
  FileNameExtensionFilter filtro;
  
  public Ventana(String titulo){
    super.setTitle(titulo);
    iniciarLookAndFeel();
    iniciarContenedores();
    iniciarMenus();
    iniciarMnemotecnicos();
    iniciarCajaTexto();
    seleccionarFichero();
    aniadirListeners();
    setVisible(true);
  }
  private void iniciarLookAndFeel(){
    try{
      String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
      UIManager.setLookAndFeel(lookAndFeel);
    } catch (Exception e) {
      System.err.println("Error");
    }
  }
  private void iniciarContenedores() {
    // Configuraciones generales
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Elimina la aplicación de memoria y de CPU
    setSize(640, 480);
    // Creamos un JPanel para agrupar los objetos
    panel = new JPanel();
    setContentPane(panel);// Establecemos panel como panel por defecto.
    panel.setLayout(new BorderLayout());// Para poder situar los componentes donde queramos
  }
  private void iniciarMenus() {
    // Creación de la barra de menús
    barraMenus = new JMenuBar();
    barraMenus.setSize(640, 20);
    barraMenus.setLocation(0, 0);
    panel.add(barraMenus,BorderLayout.NORTH); // Añadimos la barra al panel
    
    // Creamos el menú Archivo
    menuArchivo = new JMenu("Archivo");
    barraMenus.add(menuArchivo); // Añadimos el menuArchivo a la barraMenus
    
    itemNuevo = new JMenuItem("Nuevo");
    menuArchivo.add(itemNuevo);
    menuArchivo.add(new JSeparator());
    itemAbrir = new JMenuItem("Abrir archivo..."); // Creamos la primera entrada de menú
    menuArchivo.add(itemAbrir);
    itemGuardar = new JMenuItem("Guardar");
    menuArchivo.add(itemGuardar);
    itemGuardarComo = new JMenuItem("Guardar Como...");
    menuArchivo.add(itemGuardarComo);
    menuArchivo.add(new JSeparator());
    itemSalir = new JMenuItem("Salir");
    menuArchivo.add(itemSalir);   
    
    menuEdicion = new JMenu("Edicion");
    barraMenus.add(menuEdicion); // Añadimos el menuEdicion a la barraMenus
    itemCortar = new JMenuItem(new DefaultEditorKit.CutAction());
    itemCortar.setText("Cortar");
    menuEdicion.add(itemCortar);
    itemCopiar = new JMenuItem(new DefaultEditorKit.CopyAction());
    itemCopiar.setText("Copiar");
    menuEdicion.add(itemCopiar);
    itemPegar = new JMenuItem(new DefaultEditorKit.PasteAction());
    itemPegar.setText("Pegar");
    menuEdicion.add(itemPegar);
  }
  private void iniciarMnemotecnicos(){
        menuArchivo.setMnemonic(KeyEvent.VK_A);
        menuEdicion.setMnemonic(KeyEvent.VK_E);
        itemNuevo.setMnemonic(KeyEvent.VK_N);
        itemNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        itemAbrir.setMnemonic(KeyEvent.VK_A);
        itemAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        itemGuardar.setMnemonic(KeyEvent.VK_G);
        itemGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        itemGuardarComo.setMnemonic(KeyEvent.VK_Q);
        itemGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        itemSalir.setMnemonic(KeyEvent.VK_C);
        itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        itemCortar.setMnemonic(KeyEvent.VK_C);
        itemCortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        itemCopiar.setMnemonic(KeyEvent.VK_O);
        itemCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        itemPegar.setMnemonic(KeyEvent.VK_P);
        itemPegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
  }
  private void iniciarCajaTexto() {
    // Creación del área de Texto
    cajaTexto = new JTextArea();
    cajaTexto.setLocation(125,125);
    cajaTexto.setSize(100,100);
    cajaTexto.setVisible(false);
    // Asigno un tipo de fuente al cuadro de texto
    cajaTexto.setFont(new Font("Courier New", Font.PLAIN,12));
    panel.add(cajaTexto,BorderLayout.CENTER); // Añadimos la caja de texto al panel
  }
  private void seleccionarFichero(){
    String curDir = System.getProperty("user.dir"); // Directorio de trabajo actual
    sel = new JFileChooser(curDir); // Instanciamos la ventana de selección de archivos.
    filtro = new FileNameExtensionFilter("Texto plano", "txt"); // Instanciamos un filtro para que el JFileChooser
    //de archivo tenga la opción de sólo ver archivos de texto plano y todos los archivos.
    sel.setFileFilter(filtro); // Asignamos el filtro al JFileChooser
  }
  private void aniadirListeners() {
    // añado los objetos listener a tods las opcionesy botones
    itemNuevo.addActionListener(this);
    itemAbrir.addActionListener(this);
    itemGuardar.addActionListener(this);
    itemGuardarComo.addActionListener(this);
    itemSalir.addActionListener(this);
    itemCortar.addActionListener(this);
    itemCopiar.addActionListener(this);
    itemPegar.addActionListener(this);
  }
  
  @Override
  public void actionPerformed(ActionEvent evento) {
    if(evento.getSource() == itemNuevo){// se ha seleccionado Nuevo en el menú
      eventoNuevo();
    }
    if(evento.getSource() == itemAbrir){// se ha seleccionado Abrir en el menú
      eventoAbrir();
    }
    if(evento.getSource() == itemGuardar){// se ha seleccionado Guardar en el menú
      eventoGuardar();
    }
    if(evento.getSource() == itemGuardarComo){// se ha seleccionado Guardar Como en el menú
      eventoGuardarComo();
    }
    if(evento.getSource() == itemSalir){// se ha seleccionado Salir en el menú
      eventoSalir();
    }    
  }
  
  private void eventoAbrir(){
    int seleccion = sel.showDialog(this, "Abrir Fichero");
    if(seleccion == JFileChooser.APPROVE_OPTION){//si la opcion que ha escogido el usuario es Abrir Fichero
      File f = sel.getSelectedFile();//obtengo el archivo que ha seleccionado el usuario
      try {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer sb = new StringBuffer();//creo un objeto que puede contener dentro strings muy largos
        String linea = "";
        while((linea = br.readLine()) != null){
          sb.append(linea);
          sb.append("\n");
        }
        cajaTexto.setText(sb.toString());//meto el contenido del StringBuffer en el cuadro de texto
        cajaTexto.setVisible(true);
      } catch (Exception e) {
        System.out.println("Error de entrada / salida");
      }
    }
  }
  private void eventoNuevo() {
    // Si la caja de texto es visible, significa que ya se había activado 
    // de alguna manera la cajaTexto por lo que ya se puede guardar, se haya
    // seleccionado un fichero o no.
    if (cajaTexto.isVisible()){ 
      eventoGuardar();
    }
    fich = null;// Al hacer nuevo aun no hemos elegido fichero para guardar.
    cajaTexto.setVisible(true);
    cajaTexto.setText("");
  }
  private void eventoGuardar() {
    // Significa que no se ha guardado aún el fichero
    // y no hay asignado ningún fichero
    if (fich == null) 
      eventoGuardarComo();
    else guardar(fich);
  }
  private void eventoGuardarComo() {
    int seleccion = sel.showSaveDialog(this);
    if (seleccion == JFileChooser.APPROVE_OPTION){
      fich = sel.getSelectedFile(); // Guardamos el fichero seleccionado o escrito
      if (fich.exists()){
        int n = JOptionPane.showConfirmDialog(this,
                "¿Te gustaría sobreescribir el fichero?",
                "Sobreescribir",JOptionPane.YES_NO_OPTION);  
        if (n == 0){
          guardar(fich);
        }
      }else{
        guardar(fich);
      }
    }
  }
  private void eventoSalir() {
    int n = JOptionPane.showConfirmDialog(this,
                "¿Te gustaría guardar el fichero?",
                "Guardar",JOptionPane.YES_NO_OPTION);  
    if (n == 0){
      eventoGuardar();
    }
    System.exit(0);
  }
  private void guardar(File fich){
    String texto = cajaTexto.getText();
    try{
      FileWriter fw = new FileWriter(fich);
      fw.write(texto);
      fw.close();
    }catch (IOException ioe){
      System.err.println("Error de entrada/salida");
    }
  }
}
