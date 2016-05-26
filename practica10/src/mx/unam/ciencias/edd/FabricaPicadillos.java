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
            byte[] k = objeto.getBytes();
            int n = k.length;
            
            // Arreglo que representa a las variables a, b, c.
            // abc[0] = a / abc[1] = b / abc[2] = c.
            int [] abc = {0x9e3779b9, 0x9e3779b9, 0xffffffff};
            
            // Variables que representan los indices del arreglo.
            int a = 0; 
            int b = 1; 
            int c = 2;
            
            int l = n;
            int i = 0;
        
            while (l >= 12) {
                abc[a] += (k[i] + (k[i + 1] << 8) + (k[i + 2]  << 16) + (k[i + 3]  << 24));
                abc[b] += (k[i + 4] + (k[i + 5] << 8) + (k[i + 6]  << 16) + (k[i + 7]  << 24));
                abc[c] += (k[i + 8] + (k[i + 9] << 8) + (k[i + 10] << 16) + (k[i + 11] << 24));
                
                abc = permutacionesBJ(abc);
                
                i += 12;
                l -= 12;
            }
            
            abc[c] += n;
            switch (l) {
                case 11: abc[c] += (k[i + 10] << 24);
                case 10: abc[c] += (k[i + 9]  << 16);
                case  9: abc[c] += (k[i + 8]  <<  8);
            
                case  8: abc[b] += (k[i + 7]  << 24);
                case  7: abc[b] += (k[i + 6]  << 16);
                case  6: abc[b] += (k[i + 5]  <<  8);
                case  5: abc[b] += k[i + 4];
            
                case  4: abc[a] += (k[i + 3]  << 24);
                case  3: abc[a] += (k[i + 2]  << 16);
                case  2: abc[a] += (k[i + 1]  <<  8);
                case  1: abc[a] += k[i];
            }
            
            abc = permutacionesBJ(abc);
        
            return (int) abc[c];
        };
        
        Picadillo<String> glibString = (String objeto) -> {
            byte[] k = objeto.getBytes(); 
            int n = k.length; 
            int h = 5381; 
            for (int i = 0; i < n; ++i) { 
                char c = (char) k[i]; 
                h = 33 * h + c; 
            }
            
            return h; 

        };
        
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
    
    // Metodo auxiliar que permite evitar repetir codigo.
    // abc[0] = a / abc[1] = b / abc[2] = c.
    private static int[] permutacionesBJ(int[] abc) {
        int a = 0;
        int b = 1;
        int c = 2;
        
        abc[a] -= abc[b]; abc[a] -= abc[c]; abc[a] ^= (abc[c] >>>  13);
        abc[b] -= abc[c]; abc[b] -= abc[a]; abc[b] ^= (abc[a] << 8);
        abc[c] -= abc[a]; abc[c] -= abc[b]; abc[c] ^= (abc[b] >>> 13);
        abc[a] -= abc[b]; abc[a] -= abc[c]; abc[a] ^= (abc[c] >>> 12);
        abc[b] -= abc[c]; abc[b] -= abc[a]; abc[b] ^= (abc[a] << 16);
        abc[c] -= abc[a]; abc[c] -= abc[b]; abc[c] ^= (abc[b] >>> 5);
        abc[a] -= abc[b]; abc[a] -= abc[c]; abc[a] ^= (abc[c] >>> 3);
        abc[b] -= abc[c]; abc[b] -= abc[a]; abc[b] ^= (abc[a] << 10);
        abc[c] -= abc[a]; abc[c] -= abc[b]; abc[c] ^= (abc[b] >>> 15);
        
        return abc;
    }
}
