public class HiloEstudiante {
    private int id;
    private String nombre;
    private String telefono;
    private String carrera;
    private int semestre;
    private boolean gratuidad;

    public HiloEstudiante(int id, String nombre, String telefono, String carrera, int semestre, boolean gratuidad) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.carrera = carrera;
        this.semestre = semestre;
        this.gratuidad = gratuidad;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               "\nNombre: " + nombre +
               "\nTeléfono: " + telefono +
               "\nCarrera: " + carrera +
               "\nSemestre: " + semestre +
               "\nGratuidad: " + (gratuidad ? "Sí" : "No");
    }
}
