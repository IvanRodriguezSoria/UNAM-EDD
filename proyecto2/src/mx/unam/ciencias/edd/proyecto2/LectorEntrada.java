package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.IOException;


// Clase LectorEntrada para leer archivos o texto de la entrada estandard y
// guardar las lineas de texto leidas en una lista. Esto permite trabajar con
// el texto posteriormente. 
public class LectorEntrada {
    
    private String linea;
    private BufferedReader in;
    private Lista<String> lista;
    
    public LectorEntrada(BufferedReader in) {
        this.in = in;
        lista = new Lista<String>();
    }
    
    public void leerEntrada() throws IOException {
        while ((linea = in.readLine() ) != null && linea.length() != 0)
            lista.agrega(linea);
    }
    
    public Lista<String> getLista() {
        return lista;
    }
}