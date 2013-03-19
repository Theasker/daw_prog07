package EditorTexto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Ventana extends JFrame implements ActionListener{

  JPanel panel;
  JMenuBar barraMenus;
  JMenu menuArchivo;
  JMenuItem itemNuevo;
  JMenuItem itemAbrir;
  JMenuItem itemGuardar;
  JMenuItem itemGuardarComo;
  JMenuItem itemSalir;
  JTextArea cajaTexto;
  
  JFileChooser sel;
  FileNameExtensionFilter filtro;
  
  public Ventana(String titulo){
    super.setTitle(titulo);
    iniciarContenedores();
    iniciarMenus();
    iniciarCajaTexto();
    seleccionarFichero();
    añadirListeners();
    setVisible(true);
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
    itemAbrir = new JMenuItem("Abrir archivo..."); // Creamos la primera entrada de menú
    menuArchivo.add(itemAbrir);
    itemGuardar = new JMenuItem("Guardar");
    menuArchivo.add(itemGuardar);
    itemGuardarComo = new JMenuItem("Guardar Como...");
    menuArchivo.add(itemGuardarComo);
    itemSalir = new JMenuItem("Salir");
    menuArchivo.add(itemSalir);   
    
  }
  private void iniciarCajaTexto() {
    // Creación del área de Texto
    cajaTexto = new JTextArea();
    cajaTexto.setLocation(25,25);
    cajaTexto.setSize(630,450);
    cajaTexto.setVisible(false);
    panel.add(cajaTexto,BorderLayout.CENTER); // Añadimos la caja de texto al panel
  }
  private void seleccionarFichero(){
    String curDir = System.getProperty("user.dir"); // Directorio de trabajo actual
    sel = new JFileChooser(curDir); // Instanciamos la ventana de selección de archivos.
    filtro = new FileNameExtensionFilter("Texto plano", "txt"); // Instanciamos un filtro para que el JFileChooser
    //de archivo tenga la opción de sólo ver archivos de texto plano y todos los archivos.
    sel.setFileFilter(filtro); // Asignamos el filtro al JFileChooser
  }
  private void añadirListeners() {
    // añado los objetos listener a tods las opcionesy botones
    itemNuevo.addActionListener(this);
    itemAbrir.addActionListener(this);
    itemGuardar.addActionListener(this);
    itemGuardarComo.addActionListener(this);
    itemSalir.addActionListener(this);
    
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
    if (!"".equals(cajaTexto.getText())){
      final JOptionPane optionPane = new JOptionPane(
              "¿Quieres guardar los cambios?",
              JOptionPane.QUESTION_MESSAGE,
              JOptionPane.YES_NO_OPTION);
    }
    cajaTexto.setVisible(true);
    cajaTexto.setText("");
  }
  private void eventoGuardar() {
    JOptionPane.showMessageDialog(this, "Eggs are not supposed to be green.");
  }
  private void eventoGuardarComo() {
    int seleccion = sel.showSaveDialog(this);
    if (seleccion == JFileChooser.APPROVE_OPTION){
      File fich = sel.getSelectedFile(); // Guardamos el fichero seleccionado o escrito
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
    System.exit(0);
  }
  private void guardar(File fich){
    try{
      FileWriter fw = new FileWriter(fich);
      String temp = cajaTexto.getText();
      fw.write(temp);
    }catch (IOException ioe){
      System.err.println("Error de entrada/salida");
    }
  }
}
