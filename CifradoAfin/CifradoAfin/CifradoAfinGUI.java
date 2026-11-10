import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CifradoAfinGUI extends JFrame {

    private CifradoAfin logica;

    private JTextArea txtEntrada, txtSalida;
    private JTextField txtClaveA, txtClaveB;
    private JButton btnCifrar, btnDescifrar, btnLimpiar, btnUsarResultado;

    // Paleta de colores MODO OSCURO (Suave a la vista)
    private final Color FONDO_PRINCIPAL = new Color(30, 32, 36);     // Gris muy oscuro
    private final Color FONDO_PANELES = new Color(43, 47, 54);       // Gris un poco más claro para "tarjetas"
    private final Color COLOR_TEXTO = new Color(230, 234, 240);      // Blanco hueso/azulado
    private final Color COLOR_TEXTO_SECUNDARIO = new Color(170, 175, 185); // Gris claro
    private final Color COLOR_BORDE = new Color(64, 68, 75);         // Borde sutil
    
    // Colores de botones vibrantes para contrastar
    private final Color BTN_PRIMARIO = new Color(88, 101, 242);      // Azul moderno (estilo Discord)
    private final Color BTN_SECUNDARIO = new Color(79, 84, 92);      // Gris para limpiar
    private final Color BTN_EXITO = new Color(46, 204, 113);         // Verde esmeralda para descifrar

    // Fuentes modernas y escaladas
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    private final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FUENTE_BASE = new Font("Segoe UI", Font.PLAIN, 15);
    private final Font FUENTE_CLAVES = new Font("Segoe UI", Font.BOLD, 22); // Claves más grandes

    public CifradoAfinGUI() {
        logica = new CifradoAfin();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Cifrado Afín - Workspace");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(FONDO_PRINCIPAL);
    }

    private void inicializarComponentes() {
        // --- PANEL SUPERIOR (TÍTULO) ---
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(FONDO_PRINCIPAL);
        panelTitulo.setBorder(new EmptyBorder(20, 20, 10, 20));
        JLabel lblTitulo = new JLabel("Sistema de Cifrado Afín");
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setFont(FUENTE_TITULO);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // --- PANEL CENTRAL (DIVIDIDO EN DOS) ---
        // Usamos GridLayout(1, 2) para dividir la pantalla a la mitad
        JPanel panelSplit = new JPanel(new GridLayout(1, 2, 20, 0));
        panelSplit.setBackground(FONDO_PRINCIPAL);
        panelSplit.setBorder(new EmptyBorder(10, 25, 25, 25));

        // 1. CONSTRUIR LADO IZQUIERDO (Claves y Botones)
        panelSplit.add(construirPanelIzquierdo());

        // 2. CONSTRUIR LADO DERECHO (Áreas de Texto)
        panelSplit.add(construirPanelDerecho());

        add(panelSplit, BorderLayout.CENTER);

        // --- EVENTOS ---
        btnCifrar.addActionListener(e -> procesarTexto(true));
        btnDescifrar.addActionListener(e -> procesarTexto(false));
        
        btnUsarResultado.addActionListener(e -> {
            if (!txtSalida.getText().isEmpty()) {
                txtEntrada.setText(txtSalida.getText());
                txtSalida.setText("");
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtEntrada.setText("");
            txtSalida.setText("");
        });
    }

    // --- CONSTRUCTOR DEL LADO IZQUIERDO ---
    private JPanel construirPanelIzquierdo() {
        JPanel panelIzq = new JPanel(new BorderLayout(0, 20));
        panelIzq.setBackground(FONDO_PANELES);
        panelIzq.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            new EmptyBorder(25, 25, 25, 25)
        ));

        // Título de la sección
        JLabel lblConfig = new JLabel("Configuración y Controles", SwingConstants.CENTER);
        lblConfig.setFont(FUENTE_SUBTITULO);
        lblConfig.setForeground(COLOR_TEXTO);
        panelIzq.add(lblConfig, BorderLayout.NORTH);

        // Zona de Claves (GridBagLayout para centrarlas y hacerlas grandes)
        JPanel panelClaves = new JPanel(new GridLayout(2, 2, 15, 20));
        panelClaves.setBackground(FONDO_PANELES);
        panelClaves.setBorder(new EmptyBorder(20, 20, 30, 20));

        JLabel lblA = new JLabel("Clave 'a':", SwingConstants.RIGHT);
        lblA.setFont(FUENTE_SUBTITULO);
        lblA.setForeground(COLOR_TEXTO_SECUNDARIO);
        txtClaveA = crearCampoClave();

        JLabel lblB = new JLabel("Clave 'b':", SwingConstants.RIGHT);
        lblB.setFont(FUENTE_SUBTITULO);
        lblB.setForeground(COLOR_TEXTO_SECUNDARIO);
        txtClaveB = crearCampoClave();

        panelClaves.add(lblA);
        panelClaves.add(txtClaveA);
        panelClaves.add(lblB);
        panelClaves.add(txtClaveB);
        
        panelIzq.add(panelClaves, BorderLayout.CENTER);

        // Zona de Botones (Cuadrícula 2x2)
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 15, 15));
        panelBotones.setBackground(FONDO_PANELES);

        btnCifrar = crearBoton("Cifrar", BTN_PRIMARIO, Color.WHITE);
        btnDescifrar = crearBoton("Descifrar", BTN_EXITO, Color.WHITE);
        btnUsarResultado = crearBoton("Usar Resultado", BTN_PRIMARIO, Color.WHITE);
        btnLimpiar = crearBoton("Limpiar", BTN_SECUNDARIO, Color.WHITE);

        panelBotones.add(btnCifrar);
        panelBotones.add(btnDescifrar);
        panelBotones.add(btnUsarResultado);
        panelBotones.add(btnLimpiar);

        panelIzq.add(panelBotones, BorderLayout.SOUTH);

        return panelIzq;
    }

    // --- CONSTRUCTOR DEL LADO DERECHO ---
    private JPanel construirPanelDerecho() {
        JPanel panelDer = new JPanel(new GridLayout(2, 1, 0, 15));
        panelDer.setBackground(FONDO_PRINCIPAL);

        txtEntrada = crearAreaTexto("Escribe aquí el texto...");
        txtSalida = crearAreaTexto("");
        txtSalida.setEditable(false);
        txtSalida.setBackground(new Color(36, 40, 46)); // Un poco más oscuro para modo lectura

        panelDer.add(crearContenedorTexto("Texto de Entrada:", txtEntrada));
        panelDer.add(crearContenedorTexto("Resultado Generado:", txtSalida));

        return panelDer;
    }

    // --- MÉTODOS AUXILIARES DE DISEÑO ---

    private JTextArea crearAreaTexto(String placeholder) {
        JTextArea area = new JTextArea();
        area.setFont(FUENTE_BASE);
        area.setForeground(COLOR_TEXTO);
        area.setBackground(FONDO_PANELES);
        area.setCaretColor(Color.WHITE); // Cursor visible en modo oscuro
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(12, 12, 12, 12));
        return area;
    }

    private JTextField crearCampoClave() {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE_CLAVES); // Fuente mucho más grande
        campo.setForeground(Color.WHITE);
        campo.setBackground(FONDO_PRINCIPAL);
        campo.setCaretColor(Color.WHITE);
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 2), 
            new EmptyBorder(10, 10, 10, 10)
        ));
        return campo;
    }

    private JPanel crearContenedorTexto(String titulo, JTextArea areaTexto) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(FONDO_PRINCIPAL);
        
        JLabel label = new JLabel(titulo);
        label.setFont(FUENTE_SUBTITULO);
        label.setForeground(COLOR_TEXTO_SECUNDARIO);
        
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(new LineBorder(COLOR_BORDE, 1, true));
        scroll.getVerticalScrollBar().setBackground(FONDO_PRINCIPAL);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JButton crearBoton(String texto, Color fondoNormal, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondoNormal);
        boton.setForeground(textoColor);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(new EmptyBorder(12, 15, 12, 15));
        
        // Evita que Windows sobreescriba el diseño
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);

        // Colores para las animaciones del botón
        Color fondoHover = fondoNormal.brighter();
        Color fondoPresionado = fondoNormal.darker();

        // EFECTOS DE INTERACCIÓN (Hover y Click)
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(fondoHover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(fondoNormal);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                boton.setBackground(fondoPresionado);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                boton.setBackground(fondoHover);
            }
        });

        return boton;
    }

    // --- LÓGICA ---
    private void procesarTexto(boolean esCifrado) {
        try {
            //se borra el resultado anterior para no mostrarlo como actual si la operacion falla
            txtSalida.setText("");
            String texto = txtEntrada.getText();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El área de entrada está vacía.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int a = Integer.parseInt(txtClaveA.getText().trim());
            int b = Integer.parseInt(txtClaveB.getText().trim());

            String resultado = esCifrado ? logica.cifrar(texto, a, b) : logica.descifrar(texto, a, b);
            txtSalida.setText(resultado);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Las claves 'a' y 'b' deben ser números enteros.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Clave Inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            // Aplicar estilo base del sistema para heredar ciertas configuraciones nativas
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // sirve para ejecutar los componentes de la interfaz uno por uno y no todos al mismo tiempo, evitando errores de renderizado
        SwingUtilities.invokeLater(() -> {
            CifradoAfinGUI ventana = new CifradoAfinGUI();
            ventana.setVisible(true);
        });
    }
}