import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        // Configuración de la ventana principal
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new GridLayout(1,2));




        // Creación del panel con la imagen de fondo
        JPanel panel = new JPanel();
        JPanel panelBg = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon image = new ImageIcon("C:\\Users\\steve\\OneDrive\\Escritorio\\Proyectos programcion\\Sistema-Maranon-final\\src\\main\\java\\bglog.jpg"); // Ruta de la imagen de fondo
                Image backgroundImage = image.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Campos de texto para nombre de usuario y contraseña
        usernameField = new JTextField();
        usernameField.setBounds(120, 80, 150, 25);
        panel.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 120, 150, 25);
        panel.add(passwordField);

        // Etiquetas para los campos
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(40, 80, 80, 25);
        panel.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(40, 120, 80, 25);
        panel.add(passwordLabel);

        // Botón de login
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 160, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la lógica para validar el usuario y contraseña
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                // Ejemplo básico de validación
                RepositorioUsuario rp= new RepositorioUsuario();
                if (rp.login(username,password)) {
                    dispose();
                    openMainFrame();
                    JOptionPane.showMessageDialog(null, "Bievenido "+RepositorioUsuario.usuarioActual.getNombre());
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }
            }
        });
        setLocationRelativeTo(null);

        panel.add(loginButton);


        add(panel);
        add(panelBg);
        setVisible(true);
    }

    private void openMainFrame() {
        MenuPrincipal mainFrame = new MenuPrincipal();
        mainFrame.setVisible(true);
    }



}
