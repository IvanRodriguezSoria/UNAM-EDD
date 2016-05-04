package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.IOException;

public class LectorEntrada {
    
    public String linea;
    public BufferedReader in;
    public Lista<String> lista;
    
    public LectorEntrada(BufferedReader in) {
        this.in = in;
        lista = new Lista<String>();
    }
    
    public void agregaLinea() throws IOException {
        while ((linea = in.readLine() ) != null && linea.length() != 0)
            lista.agrega(linea);
    }
}