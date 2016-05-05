package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Proyecto2 {

    public static void main(String[] args) throws IOException {
        
        LectorEntrada in;
        
        if (args.length > 0)
            in = new LectorEntrada(new BufferedReader(new FileReader(args[0]) ) );
        else
            in = new LectorEntrada(new BufferedReader(new InputStreamReader(System.in) ) );
            
        in.leerEntrada();
        
        for (String s : in.getLista() )
            System.out.println(s); 
    }
}