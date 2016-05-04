package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Proyecto2 {

    public static void main(String[] args) throws IOException {
        
        BufferedReader in;
        String linea = "";
        Lista<String> lista = new Lista<String>();
        
        if (args.length > 0)
            in = new BufferedReader(new FileReader(args[0]));
        else
            in = new BufferedReader(new InputStreamReader(System.in));
            
        agregaLinea(lista, linea, in);
        
        for (String s : lista)
            System.out.println(s);
    }
    
    public static void agregaLinea(Lista<String> lista, String linea, BufferedReader in) throws IOException {
        while ((linea = in.readLine() ) != null && linea.length() != 0)
            lista.agrega(linea);
    }
}