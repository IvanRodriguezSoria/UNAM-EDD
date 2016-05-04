package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Proyecto2 {

    public static void main(String[] args) throws IOException {
        
        LectorEntrada inputReader;
        
        if (args.length > 0)
            inputReader = new LectorEntrada(new BufferedReader(new FileReader(args[0]) ) );
        else
            inputReader = new LectorEntrada(new BufferedReader(new InputStreamReader(System.in) ) );
            
        inputReader.agregaLinea();
        
        for (String s : inputReader.lista)
            System.out.println(s);
    }
}