import java.net.*;
import java.util.*;

public class ServidorUDP {
    private static List<HiloEstudiante> estudiantes = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9876);
        inicializarEstudiantes();
        System.out.println("Servidor UDP iniciado...");

        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
            socket.receive(peticion);

            new Thread(() -> {
                try {
                    String idRecibido = new String(peticion.getData(), 0, peticion.getLength());
                    int id = Integer.parseInt(idRecibido.trim());
                    String respuesta = buscarEstudiante(id);

                    byte[] datosRespuesta = respuesta.getBytes();
                    DatagramPacket paqueteRespuesta = new DatagramPacket(
                        datosRespuesta,
                        datosRespuesta.length,
                        peticion.getAddress(),
                        peticion.getPort()
                    );

                    socket.send(paqueteRespuesta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static String buscarEstudiante(int id) {
        for (HiloEstudiante e : estudiantes) {
            if (e.getId() == id) {
                return e.toString();
            }
        }
        return "Estudiante no encontrado";
    }

    private static void inicializarEstudiantes() {
        estudiantes.add(new HiloEstudiante(1, "Darwin cachimil", "099111222", "Software", 3, true));
        estudiantes.add(new HiloEstudiante(2, "wilmer vargas", "098111223", "Redes", 2, false));
        estudiantes.add(new HiloEstudiante(3, "Anderson vilatuña", "097111224", "Software", 5, true));
        estudiantes.add(new HiloEstudiante(4, "andres tufiño", "096111225", "Electrónica", 1, true));
        estudiantes.add(new HiloEstudiante(5, "Laura Vaca", "095111226", "Software", 4, false));
        estudiantes.add(new HiloEstudiante(6, "Pedro León", "094111227", "Redes", 2, true));
        estudiantes.add(new HiloEstudiante(7, "Diana Mora", "093111228", "Software", 3, false));
        estudiantes.add(new HiloEstudiante(8, "Jorge Cedeño", "092111229", "Electrónica", 6, true));
        estudiantes.add(new HiloEstudiante(9, "Mónica Ríos", "091111230", "Software", 7, false));
        estudiantes.add(new HiloEstudiante(10, "Esteban Rojas", "090111231", "Redes", 1, true));
    }
}
