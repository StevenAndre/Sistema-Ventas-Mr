import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
        public MenuPrincipal() {
            setTitle("Sistema de Ventas mara�on");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            add(new PanelPrincipal());


        }
}



class PanelPrincipal extends JPanel{
    public PanelPrincipal() {
        setLayout(new BorderLayout());


        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Menu princiapl Sistema de Ventas");


        headerPanel.add(titleLabel);


        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
        menuPanel.setBackground(new Color(220, 193, 206, 107));

        JButton btnOpcion1 = new JButton("Opci�nn 1");
        JButton btnOpcion2 = new JButton("Opci�n 2");
        JButton btnProductos = new JButton("Productos");
        JButton btnClientes = new JButton("Clientes");

        menuPanel.add(btnOpcion1);
        menuPanel.add(btnOpcion2);
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
                viewPanel.add(new JLabel("Vista de la Opci�n 1"), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });

        btnOpcion2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();
                viewPanel.add(new JLabel("Vista de la Opci�n 2"), BorderLayout.CENTER);
                viewPanel.revalidate();
                viewPanel.repaint();
            }
        });

    }



}


