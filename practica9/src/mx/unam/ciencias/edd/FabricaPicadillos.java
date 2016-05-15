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
        
        // No tengo idea de que hace este metodo. Lo puse tal cual lo dio el profesor.
        Picadillo<String> xorString = (String objeto) -> {
            byte[] k = objeto.getBytes();
            int l = k.length;
            int r = 0;
            int i = 0;
            
            while (l >= 4) {
                r ^= (k[i] << 24) | (k[i + 1] << 16) | (k[i + 2] << 8) | k[i + 3];
                i += 4;
                l -= 4;
            }
            
            int t = 0;
            switch (l) {
                case 3: t |= k[i+2] << 8;
                case 2: t |= k[i+1] << 16;
                case 1: t |= k[i]   << 24;
            }
            
            r ^= t;
            return r;
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
