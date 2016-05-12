package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Proyecto2 {

    public static void main(String[] args) throws IOException {
        
        LectorEntrada in;
        Lista<String> datos;
        LectorEstructura inEDD;
        
        // Checo si la entrada es un archivo o texto ingresado en el teclado.
        if (args.length > 0)
            in = new LectorEntrada(new BufferedReader(new FileReader(args[0]) ) );
        else
            in = new LectorEntrada(new BufferedReader(new InputStreamReader(System.in) ) );
            
        in.leerEntrada();
        datos = in.getDatos();
        inEDD = new LectorEstructura(datos);
        System.out.println(inEDD.getSVG() ); // TODO. Enviar a archivo SVG.
    }
}