package EditorTexto;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Ventana extends JFrame{

  JPanel panel;
  JMenuBar barraMenus;
  JMenu menuArchivo;
  JMenuItem itemAbrir;
  JTextArea cajaTexto;
  
  public Ventana(String titulo){
    super.setTitle(titulo);
    iniciarContenedores();
    iniciarMenus();
    iniciarCajaTexto();
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
    panel.setLayout(null);// Para poder situar los componentes donde queramos
  }

  private void iniciarMenus() {
    // Creación de la barra de menús
    barraMenus = new JMenuBar();
    barraMenus.setSize(400, 20);
    barraMenus.setLocation(0, 0);
    barraMenus.setOpaque(true);
    barraMenus.setBackground(new Color(154, 165, 127));
    panel.add(barraMenus); // Añadimos la barra al panel
    
    // Creamos el menú Archivo
    menuArchivo = new JMenu("Archivo");
    barraMenus.add(menuArchivo); // Añadimos el menuArchivo a la barraMenus
    itemAbrir = new JMenuItem("Abrir"); // Creamos la primera entrada de menú
    menuArchivo.add(itemAbrir);
  }

  private void iniciarCajaTexto() {
    // Creación del área de Texto
    cajaTexto = new JTextArea();
    cajaTexto.setLocation(5,25);
    cajaTexto.setSize(630,450);
    panel.add(cajaTexto); // Añadimos la caja de texto al panel
  }

  private void añadirListeners() {
    // Creamos el objeto de la clase oyente 
    // para cuando se realice alguna acción
    
    OyenteAccion oyente = new OyenteAccion();
    
  }
  
  class OyenteAccion implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent evento) {
      
    }
    
  }
}