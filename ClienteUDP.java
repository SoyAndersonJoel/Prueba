import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class ClienteUDP extends JFrame {
    private JTextField campoID;
    private JTextArea areaRespuesta;
    private JButton botonConsultar;

    public ClienteUDP() {
        setTitle("Consulta de Estudiantes - UDP");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255)); 

        // Panel superior para la entrada de datos
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSuperior.setBackground(new Color(173, 216, 230)); 
        panelSuperior.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));

        JLabel label = new JLabel("ID del estudiante:");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(25, 25, 112)); 

        campoID = new JTextField(15);
        campoID.setFont(new Font("Arial", Font.PLAIN, 14));
        campoID.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));

        botonConsultar = new JButton("Consultar");
        botonConsultar.setFont(new Font("Arial", Font.BOLD, 14));
        botonConsultar.setBackground(new Color(34, 139, 34)); 
        botonConsultar.setForeground(Color.WHITE);
        botonConsultar.setFocusPainted(false);

        panelSuperior.add(label);
        panelSuperior.add(campoID);
        panelSuperior.add(botonConsultar);

        // Panel central para la respuesta
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout(10, 10));
        panelCentral.setBackground(new Color(240, 248, 255)); // Fondo azul claro
        panelCentral.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            "Respuesta",
            0,
            0,
            new Font("Arial", Font.BOLD, 14),
            new Color(25, 25, 112) // Azul oscuro
        ));

        areaRespuesta = new JTextArea();
        areaRespuesta.setEditable(false);
        areaRespuesta.setLineWrap(true);
        areaRespuesta.setWrapStyleWord(true);
        areaRespuesta.setFont(new Font("Arial", Font.PLAIN, 14));
        areaRespuesta.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));

        JScrollPane scrollPane = new JScrollPane(areaRespuesta);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        // Agregar paneles al marco principal
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        // Acción del botón
        botonConsultar.addActionListener(e -> consultarEstudiante());
    }

    private void consultarEstudiante() {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName("localhost");
            byte[] enviarDatos = campoID.getText().trim().getBytes();

            DatagramPacket paqueteEnvio = new DatagramPacket(enviarDatos, enviarDatos.length, ip, 9876);
            socket.send(paqueteEnvio);

            byte[] recibirDatos = new byte[1024];
            DatagramPacket paqueteRespuesta = new DatagramPacket(recibirDatos, recibirDatos.length);
            socket.receive(paqueteRespuesta);

            String respuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());
            areaRespuesta.setText(respuesta);

            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            areaRespuesta.setText("Error en la conexión");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteUDP().setVisible(true));
    }
}