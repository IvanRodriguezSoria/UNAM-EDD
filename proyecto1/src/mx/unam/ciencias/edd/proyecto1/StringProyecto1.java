package mx.unam.ciencias.edd.proyecto1;

// Clase que permite sobreescribir el metodo compareTo() de un String.
public class StringProyecto1 implements Comparable<StringProyecto1> {
    
    // El String al cual se le sobrecargara el metodo compareTo().
    private String s;
    
    // Constructor.
    public StringProyecto1(String s) {
        this.s = s; 
    }
    
    // Regresa una representacion en String de un String...
    @Override public String toString() {
        return s;
    }
    
    // Compara dos Strings ignorando si son mayusculas o minusculas.
    @Override public int compareTo(StringProyecto1 string) {
        return s.compareToIgnoreCase(string.toString());
    }
}