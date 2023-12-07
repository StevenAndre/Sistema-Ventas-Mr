import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
        public MenuPrincipal() {
            setTitle("Sistema de Ventas marañon");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ImageIcon icon = new ImageIcon("C:/Users/steve/OneDrive//Escritorio/Proyectos programcion//Sistema-Maranon-final//src//main//java//logo.jpeg");
            Image image = icon.getImage();
            setIconImage(image);
            setLocationRelativeTo(null);
            add(new PanelPrincipal());
            setBounds(200,100,1200,700);
            setResizable(false);
        }
}



class PanelPrincipal extends JPanel{
    public PanelPrincipal() {
        setLayout(new BorderLayout());


        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(90, 187, 177));

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("New"));
        fileMenu.add(new JMenuItem("Open"));
        fileMenu.add(new JMenuItem("Save"));
        fileMenu.add(new JMenuItem("Exit"));
        JMenu sMenu = new JMenu("Soporte");
        sMenu.add(new JMenuItem("contact"));
        sMenu.add(new JMenuItem("Open"));
        sMenu.add(new JMenuItem("Save"));
        sMenu.add(new JMenuItem("Exit"));

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(new JMenuItem("Cut"));
        editMenu.add(new JMenuItem("Copy"));
        editMenu.add(new JMenuItem("Paste"));
        JMenu cMenu = new JMenu("Configuracion");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(sMenu);
        menuBar.add(cMenu);

        // Panel para el encabezado
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(227, 211, 147, 236));

        // Agregar el menú a la izquierda del título
        headerPanel.add(menuBar, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Menu principal Sistema de Ventas");
        headerPanel.add(titleLabel, BorderLayout.CENTER);


        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 1, 0, 40)); // Una columna, separación vertical de 10 píxeles
        menuPanel.setBackground(new Color(220, 193, 206, 107));
        menuPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Espacios alrededor del panel


        JButton btnOpcion1 = createStyledButton("Ventas", Color.RED);
        JButton btnProveedores = createStyledButton("Proveedores", Color.GREEN);
        JButton btnProductos = createStyledButton("Productos", Color.BLUE);
        JButton btnClientes = createStyledButton("Clientes", Color.ORANGE);




        // Agregar los botones al panel con separación

        menuPanel.add(new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("C:\\Users\\steve\\OneDrive\\Escritorio\\Proyectos programcion\\Sistema-Maranon-final\\src\\main\\java\\logo.jpeg"); // Ruta de la imagen de fondo
                Image backgroundImage = image.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }


        });
        menuPanel.add(btnOpcion1);
        menuPanel.add(btnProveedores);
        menuPanel.add(btnProductos);
        menuPanel.add(btnClientes);


        JPanel viewPanel = new JPanel();
        viewPanel.setBackground(Color.WHITE);
        viewPanel.setLayout(new BorderLayout());
        viewPanel.add(new JLabel("Vista por defecto"), BorderLayout.CENTER);


        add(headerPanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.WEST);
        add(viewPanel, BorderLayout.CENTER);

        btnProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();
                viewPanel.add(new ProductosPanel(), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });
        
        btnClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();
                viewPanel.add(new ClientesPanel(), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });


        btnOpcion1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();
                viewPanel.add(new VentasPanel(), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });

        btnProveedores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();
                viewPanel.add(new ProveedorPanel(), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });

    }
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40)); // Ajustar el tamaño según sea necesario
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Agregar espacios internos al botón
        return button;
    }



}


