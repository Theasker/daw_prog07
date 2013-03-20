package EditorTexto;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;

/*
 * @author Carlos Rodríguez Fernández
 * @version 1.0 18/03/2013
 */
public class EditorTexto2 {

    public static void main(String[] args) {
        MiVentana v = new MiVentana("Editor de texto CarlosPad v 1.0");
    }
}

/*
 * Clase que desarrolla la aplicación de un pequeño editor de textos con funcionalidades básicas.
 */
class MiVentana extends JFrame implements ActionListener {

    JPanel panelPrincipal, panelSuperior, panelInferior;
    JScrollPane scroll;
    JTextArea cajaTexto;
    JComboBox comboTamano, comboFuente, comboTipo;
    JMenuBar barraMenus;
    JMenu menuArchivo, menuEditar, menuVer;
    JMenuItem itemNuevo, itemAbrir, itemGuardar, itemGuardarComo, itemCerrar, itemSalir;
    JMenuItem itemCortar, itemCopiar, itemPegar;
    JToolBar barraHerramientas;
    JButton botonNuevo, botonAbrir, botonGuardar, botonGuardarComo, botonCerrar, botonSalir;
    JButton botonCortar, botonCopiar, botonPegar;
    JFileChooser sel;
    Integer[] arrayTamano = {14, 16, 18, 20, 22, 24, 26, 28, 30};
    String[] arrayTiposLetra = {"Normal", "Negrita", "Cursiva"};
    String[] arrayFuentes = {"Arial", "Times New Roman", "Courier New"};
    FileNameExtensionFilter filtro;
    File f;
    FileReader fr;
    BufferedReader br;
    FileWriter fw;
    Action cortarAccion, copiarAccion, pegarAccion;
    Font fuente;

    public MiVentana(String titulo) {
        super(titulo);
        iniciarComponentes();
        añadirListeners();
    }

    /*
     * Método privado que llama a los configuradores de todos los componentes y elementos de la aplicación.
     */
    private void iniciarComponentes() {
        configurarFuente();
        configurarAcciones();
        configurarVentana();
        configurarPanelSuperior();
        configurarPanelInferior();
        configurarFileChooser();
        configurarNemotecnicosAceleradores();
        setVisible(true);
    }

    /*
     * Método privado que añade los listeners a cada uno de los componentes origen de eventos.
     */
    private void añadirListeners() {
        itemNuevo.addActionListener(this);
        itemAbrir.addActionListener(this);
        itemGuardar.addActionListener(this);
        itemGuardarComo.addActionListener(this);
        itemCerrar.addActionListener(this);
        itemSalir.addActionListener(this);
        botonNuevo.addActionListener(this);
        botonAbrir.addActionListener(this);
        botonGuardar.addActionListener(this);
        botonGuardarComo.addActionListener(this);
        botonCerrar.addActionListener(this);
        botonSalir.addActionListener(this);
        comboFuente.addActionListener(this);
        comboTamano.addActionListener(this);
        comboTipo.addActionListener(this);
    }

    /*
     * Método privado que configura los nemotécnicos y aceleradores de los menús.
     */
    private void configurarNemotecnicosAceleradores() {
        menuArchivo.setMnemonic(KeyEvent.VK_A);
        menuEditar.setMnemonic(KeyEvent.VK_E);
        itemNuevo.setMnemonic(KeyEvent.VK_N);
        itemNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        itemAbrir.setMnemonic(KeyEvent.VK_A);
        itemAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        itemGuardar.setMnemonic(KeyEvent.VK_G);
        itemGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        itemGuardarComo.setMnemonic(KeyEvent.VK_Q);
        itemGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        itemCerrar.setMnemonic(KeyEvent.VK_C);
        itemCerrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        itemSalir.setMnemonic(KeyEvent.VK_C);
        itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        itemCortar.setMnemonic(KeyEvent.VK_C);
        itemCortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        itemCopiar.setMnemonic(KeyEvent.VK_O);
        itemCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        itemPegar.setMnemonic(KeyEvent.VK_P);
        itemPegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuVer.setMnemonic(KeyEvent.VK_V);

    }

    /*
     * Método que implementa la lógica de la aplicación mediante la gestión de los distintos tipos
     * de eventos que se pueden generar en la aplicación.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == itemNuevo) || (e.getSource() == botonNuevo)) {
            nuevoArchivo();
        }

        if ((e.getSource() == itemAbrir) || (e.getSource() == botonAbrir)) {
            abrirArchivo();
        }

        if ((e.getSource() == itemGuardar) || (e.getSource() == botonGuardar)) {
            guardarCambios();
        }

        if ((e.getSource() == itemGuardarComo) || (e.getSource() == botonGuardarComo)) {
            guardarComo();
        }

        if ((e.getSource() == itemCerrar) || (e.getSource() == botonCerrar)) {
            cerrarArchivo();
        }

        if ((e.getSource() == itemSalir) || (e.getSource() == botonSalir)) {
            salir();
        }

        if ((e.getSource() == comboFuente) || (e.getSource() == comboTamano) || (e.getSource() == comboTipo)) {
            actualizarLetra();
        }

    }

    /*
     * Método privado al que se llamará cuando el usuario quiera abrir un nuevo archivo.
     */
    private void nuevoArchivo() {
        //habilito el textbox para poder escribir en el.
        cajaTexto.setEditable(true);
        cajaTexto.setBackground(Color.WHITE);
        cajaTexto.setText("");
        //hago visibles los comboBox.
        visibilizarComponentesFuente(true);
    }

    /*
     * Método privado al que se llamará cuando el usuario quiera abrir un archivo existente.
     */
    private void abrirArchivo() {
        //Si hay un archivo abierto, se preguntará al usuario si quiere guardar los cambios
        //antes de mostrarle el filechooser para abrir otro archivo.
        if (cajaTexto.isEditable()) {
            guardarCambios();
        }
        int opcionAbrir = sel.showDialog(this, "Abrir Fichero");
        if (opcionAbrir == JFileChooser.APPROVE_OPTION) {//si la opcion que ha escogido el usuario es Abrir Fichero
            f = sel.getSelectedFile();//obtengo el nombre del archivo que ha seleccionado el usuario
            try {
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                StringBuffer sb = new StringBuffer();//creo un objeto que puede contener dentro
                //strings muy largos
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                br.close();
                fr.close();
                cajaTexto.setText(sb.toString());//meto el contenido del StringBuffer en el cuadro de texto
            } catch (Exception exc) {
            }
            cajaTexto.setEditable(true);
            cajaTexto.setBackground(Color.WHITE);
            visibilizarComponentesFuente(true);
        }
    }

    /*
     * Método que se llamará cuando el usuario quiera guardar los cambios del archivo activo.
     */
    private void guardarCambios() {
        //Si la caja de texto no es editable, no se ejecutará el método, puesto que no hay ningún
        //archivo abierto.
        if (!cajaTexto.isEditable()) {
            return;
        }
        int opcionGuardar = JOptionPane.showConfirmDialog(this, "¿Quiere guardar los cambios?", "Guardar", JOptionPane.YES_NO_OPTION);
        if (opcionGuardar == JOptionPane.OK_OPTION) {
            if (f == null) {//si f no apunta a ningun archivo, es la primera vez que se guarda y por tanto es necesario hacer guardarComo()
                guardarComo();
            } else {
                String texto = cajaTexto.getText();
                try {
                    fw = new FileWriter(f);
                    fw.write(texto);
                    fw.close();
                } catch (Exception exc) {
                }
            }
        }
    }

    /*
     * Método privado que realiza la acción de guardado por primera vez de un archivo.
     */
    private void guardarComo() {
        //Si la caja de texto no es editable, no se ejecutará el método, puesto que no hay ningún archivo abierto.
        if (!cajaTexto.isEditable()) {
            return;
        }
        int saveOption = sel.showSaveDialog(this);
        if (saveOption == 0) {
            String texto = cajaTexto.getText();
            try {
                f = sel.getSelectedFile();
                fw = new FileWriter(f);
                fw.write(texto);
                fw.close();
            } catch (Exception exc) {
            }
        }
    }

    /*
     * Método privado que se ejecuta cuando se quiere cerrar un archivo.
     */
    private void cerrarArchivo() {
        //primero se guardan los cambios.
        guardarCambios();
        //se inhabilita el textBox para que no se pueda escribir en el.
        cajaTexto.setEditable(false);
        cajaTexto.setText("");
        cajaTexto.setBackground(Color.LIGHT_GRAY);
        //se ocultan los comboBox
        visibilizarComponentesFuente(false);
    }

    /*
     * Método privado que se ejecuta cuando el usario decide salir de la aplicación
     */
    private void salir() {
        //primero se guardan los cambios
        guardarCambios();
        System.exit(0);
    }

    /*
     * Método privado que sirve para visualizar, ocultar y configurar los comboBox y los valores que muestran por defecto.
     */
    private void visibilizarComponentesFuente(boolean b) {
        comboFuente.setVisible(b);
        comboFuente.setSelectedIndex(0);
        comboTipo.setVisible(b);
        comboTipo.setSelectedIndex(0);
        comboTamano.setVisible(b);
        comboTamano.setSelectedIndex(2);
    }

    /*
     * Método privado al que se llama cuando se actualiza alguno de los comboBox
     */
    private void actualizarLetra() {
        //primero se obtienen los valores actuales seleccionados en cada comboBox
        int tipo = comboTipo.getSelectedIndex();
        String fte = (String) comboFuente.getSelectedItem();
        Integer tam = (Integer) comboTamano.getSelectedItem();
        //se crea una nueva fuente
        fuente = new Font(fte, tipo, tam);
        //se ajusta la fuente del textBox
        cajaTexto.setFont(fuente);
    }

    /*
     * Método privado que ajusta los parámetros de la ventana y sus componentes.
     */
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        panelPrincipal = new JPanel();//crear un JPanel para almacenar los componentes
        setContentPane(panelPrincipal);//establecer nuestro JPanel como el panel por defecto de la ventana
        panelPrincipal.setLayout(new BorderLayout());
        panelSuperior = new JPanel();
        panelInferior = new JPanel();
        panelPrincipal.add(panelSuperior, BorderLayout.PAGE_START);
        panelPrincipal.add(panelInferior, BorderLayout.CENTER);
        panelSuperior.setLayout(new GridLayout(2, 1));
        panelInferior.setLayout(new BorderLayout());
    }

    /*
     * Método privado que ajusta los parámetros del panel inferior y sus componentes.
     */
    private void configurarPanelInferior() {
        scroll = new JScrollPane();
        panelInferior.add(scroll);
        cajaTexto = new JTextArea();//creamos el área de texto
        cajaTexto.setEditable(false);
        cajaTexto.setBackground(Color.LIGHT_GRAY);
        cajaTexto.setFont(fuente);
        scroll.setViewportView(cajaTexto);//añadimos el componente al scroll
    }

    /*
     * Método privado que sirve para configurar las acciones de cortar, copiar y pegar.
     */
    private void configurarAcciones() {
        cortarAccion = new DefaultEditorKit.CutAction();
        copiarAccion = new DefaultEditorKit.CopyAction();
        pegarAccion = new DefaultEditorKit.PasteAction();
    }

    /*
     * Metodo privado que permite configurar la fuente con la que arranca la aplicación.
     */
    private void configurarFuente() {
        fuente = new Font("Arial", Font.PLAIN, 18);
        comboFuente = new JComboBox(arrayFuentes);
        comboTipo = new JComboBox(arrayTiposLetra);
        comboTamano = new JComboBox(arrayTamano);
        comboFuente.setSelectedIndex(0);
        comboTipo.setSelectedIndex(0);
        comboTamano.setSelectedIndex(2);
        visibilizarComponentesFuente(false);
    }

    /*
     * Método que ajusta los parámetros del panel superior y sus componentes.
     */
    private void configurarPanelSuperior() {
        barraMenus = new JMenuBar();//crear la barra de menus     
        panelSuperior.add(barraMenus);//añadimos la barra de menus al panel
        barraHerramientas = new JToolBar();
        barraHerramientas.setFloatable(false);
        botonNuevo = new JButton();
        botonNuevo.setIcon(new ImageIcon("images/new_document.png"));
        botonNuevo.setToolTipText("Nuevo Archivo.");
        botonAbrir = new JButton();
        botonAbrir.setIcon(new ImageIcon("images/open_document.png"));
        botonAbrir.setToolTipText("Abrir Archivo.");
        botonGuardar = new JButton();
        botonGuardar.setIcon(new ImageIcon("images/save_document.png"));
        botonGuardar.setToolTipText("Guardar Archivo.");
        botonGuardarComo = new JButton();
        botonGuardarComo.setIcon(new ImageIcon("images/save_as_document.png"));
        botonGuardarComo.setToolTipText("Guardar Archivo Como...");
        botonCerrar = new JButton();
        botonCerrar.setIcon(new ImageIcon("images/close_document.png"));
        botonCerrar.setToolTipText("Cerrar documento.");
        botonSalir = new JButton();
        botonSalir.setIcon(new ImageIcon("images/exit.png"));
        botonSalir.setToolTipText("Salir de la aplicación.");
        botonCortar = new JButton(cortarAccion);
        botonCortar.setIcon(new ImageIcon("images/cut_icon.png"));
        botonCortar.setToolTipText("Cortar");
        botonCortar.setText(null);
        botonCopiar = new JButton(copiarAccion);
        botonCopiar.setIcon(new ImageIcon("images/copy_icon.png"));
        botonCopiar.setToolTipText("Copiar");
        botonCopiar.setText(null);
        botonPegar = new JButton(pegarAccion);
        botonPegar.setIcon(new ImageIcon("images/paste_icon.png"));
        botonPegar.setToolTipText("Pegar");
        botonPegar.setText(null);
        barraHerramientas.add(botonNuevo);
        barraHerramientas.add(botonAbrir);
        barraHerramientas.add(botonGuardar);
        barraHerramientas.add(botonGuardarComo);
        barraHerramientas.add(botonCerrar);
        barraHerramientas.add(botonSalir);
        barraHerramientas.addSeparator();
        barraHerramientas.add(botonCortar);
        barraHerramientas.add(botonCopiar);
        barraHerramientas.add(botonPegar);
        barraHerramientas.addSeparator();
        barraHerramientas.add(comboFuente);
        barraHerramientas.add(comboTipo);
        barraHerramientas.add(comboTamano);
        panelSuperior.add(barraHerramientas);
        menuArchivo = new JMenu("Archivo");//creamos el menu Archivo
        barraMenus.add(menuArchivo);//añadimos el menu Archivo a la barra de herramientas
        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir");//creamos la primera opción de este menu
        itemGuardar = new JMenuItem("Guardar");
        itemGuardarComo = new JMenuItem("Guardar como...");
        itemCerrar = new JMenuItem("Cerrar");
        itemSalir = new JMenuItem("Salir");
        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);
        menuArchivo.add(itemGuardarComo);
        menuArchivo.add(itemCerrar);
        menuArchivo.add(itemSalir);
        menuEditar = new JMenu("Editar");
        barraMenus.add(menuEditar);
        itemCortar = new JMenuItem(cortarAccion);
        itemCortar.setText("Cortar");
        itemCopiar = new JMenuItem(copiarAccion);
        itemCopiar.setText("Copiar");
        itemPegar = new JMenuItem(pegarAccion);
        itemPegar.setText("Pegar");
        menuEditar.add(itemCortar);
        menuEditar.add(itemCopiar);
        menuEditar.add(itemPegar);
        menuVer = new JMenu("Ver");
        barraMenus.add(menuVer);
    }

    /*
     * Método privado que sirve para configurar la forma de trabajar del fileChooser
     */
    private void configurarFileChooser() {
        String curDir = System.getProperty("user.dir");//averiguar directorio de trabajo actual
        sel = new JFileChooser(curDir);//instanciamos la ventana de selección de archivos.
        filtro = new FileNameExtensionFilter("Texto plano", "txt");
        sel.setFileFilter(filtro);//asignamos el filtro al JFileChooser
    }
}
