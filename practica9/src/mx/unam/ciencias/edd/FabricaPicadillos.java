package mx.unam.ciencias.edd;

/**
 * Clase para fabricar picadillos.
 */
public class FabricaPicadillos {

    /**
     * Regresa una instancia de {@link Picadillo} para cadenas.
     * @param algoritmo el algoritmo de picadillo que se desea.
     * @return una instancia de {@link Picadillo} para cadenas.
     * @throws IllegalArgumentException si recibe un identificador no
     *         reconocido.
     */
    public static Picadillo<String> getInstancia(AlgoritmoPicadillo algoritmo) {
        Picadillo<String> bjString = (String objeto) -> {
            // TODO.
            return 1;
        };
        
        Picadillo<String> glibString = (String objeto) -> {
            // TODO.
            return 2;
        };
        
        Picadillo<String> xorString = (String objeto) -> {
            // TODO.
            return 3;
        };
        
        switch (algoritmo) {
            
            case BJ_STRING:
                return bjString;
                
            case GLIB_STRING:
                return glibString;
                
            case XOR_STRING:
                return xorString;
                
            default:
                throw new IllegalArgumentException();
        }
    }
}
