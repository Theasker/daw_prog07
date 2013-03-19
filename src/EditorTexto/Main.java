package EditorTexto;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
   public static void main(String[] args) {
       MiVentana v = new MiVentana("Editor de texto muy cutre");
   }
}

class MiVentana extends JFrame implements ActionListener {
   JPanel panel;
   JTextArea cajaTexto;
   JMenuBar barraMenus;
   JMenu menuArchivo;
   JMenuItem itemAbrir;
   JFileChooser sel;
   FileNameExtensionFilter filtro;
   public MiVentana(String titulo) {
       super(titulo);
       iniciarComponentes();
       añadirListeners();
   }

   private void iniciarComponentes() {
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(600, 600);
       panel = new JPanel();//crear un JPanel para almacenar los componentes
       setContentPane(panel);//establecer nuestro JPanel como el panel por defecto de la ventana
       panel.setLayout(null);//así nosotros le decimos "donde" colocar cada componente

       barraMenus = new JMenuBar();//crear la barra de menus
       barraMenus.setSize(600, 20);//darle tamaño
       barraMenus.setLocation(new Point(0, 0));//establecer punto de inserción
       panel.add(barraMenus);//añadimos la barra de menus al panel
 
       cajaTexto = new JTextArea();//creamos el área de texto
       cajaTexto.setLocation(0, 20);//le decimos donde estará insertada
       cajaTexto.setSize(600, 540);//tamaño
       panel.add(cajaTexto);//añadimos el componente al panel
 
       menuArchivo = new JMenu("Archivo");//creamos el menu Archivo
       barraMenus.add(menuArchivo);//añadimos el menu Archivo a la barra de herramientas
       itemAbrir = new JMenuItem("Abrir");//creamos la primera opción de este menu
       menuArchivo.add(itemAbrir);//añadimos la opción Abrir al menu Archivo
       
       String curDir = System.getProperty("user.dir");//averiguar directorio de trabajo actual
       sel = new JFileChooser(curDir);//instanciamos la ventana de selección de archivos.
       filtro = new FileNameExtensionFilter("Texto plano", "txt");//instanciamos un filtro para que el JFileChooser
       //de archivo tenga la opción de sólo ver archivos de texto plano y todos los archivos.
       sel.setFileFilter(filtro);//asignamos el filtro al JFileChooser

       setVisible(true);
   }

   private void añadirListeners(){
       itemAbrir.addActionListener(this);//añado el objeto listener al itemAbrir
       //como hemos implementado la interfac ActionListener el objeto oyente será this.
       //aquí habría que ir añadiendo listeners para los distintos items que se vayan añadiendo
   }

   @Override
   public void actionPerformed(ActionEvent e) {
       if(e.getSource() == itemAbrir){//si el componente en el que se produjo el evento fué la opcion Abrir
           int userOption = sel.showDialog(this, "Abrir Fichero");
           if(userOption == JFileChooser.APPROVE_OPTION){//si la opcion que ha escogido el usuario es Abrir Fichero
               File f = sel.getSelectedFile();//obtengo el archivo que ha seleccionado el usuario
               try {
                   FileReader fr = new FileReader(f);
                   BufferedReader br = new BufferedReader(fr);
                   StringBuffer sb = new StringBuffer();//creo un objeto que puede contener dentro
                   //strings muy largos
                   String line = "";
                   while((line = br.readLine()) != null){
                       sb.append(line);
                       sb.append("\n");
                   }
                   cajaTexto.setText(sb.toString());//meto el contenido del StringBuffer en el cuadro de texto
               } catch (Exception exc) {
               }
           }
       }

       //aqui iria el código para más items del menu Archivo, del menu Editar etc
   }
}
