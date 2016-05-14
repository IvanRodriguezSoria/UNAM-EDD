package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Proyecto2 {

    public static void main(String[] args) throws IOException {
        
        LectorEntrada in = null;
        Lista<String> datos = null;
        LectorEstructura inEDD = null;
        
        try {
            // Checo si la entrada es un archivo o texto ingresado en el teclado.
            if (args.length > 0)
                in = new LectorEntrada(new BufferedReader(new FileReader(args[0]) ) );
            else
                in = new LectorEntrada(new BufferedReader(new InputStreamReader(System.in) ) );
            
            in.leerEntrada();
            datos = in.getDatos();
            inEDD = new LectorEstructura(datos);
            System.out.println(inEDD.getSVG() );
            
        } catch (IOException e) {
            System.err.println("\n************************************************"
                + "\n\nError al leer el archivo, verifique que el " 
                + "\narchivo y su direccion esten bien escritos."
                + "\n\n\tEjemplo: Downloads/archivo.txt o" 
                + "\n\ten Windows Downloads\\archivo.txt"
                + "\n\n************************************************");
        } catch (NoSuchElementException e) {
            System.err.println("\n************************************************"
                + "\n\nSe ingreso un formato invalido."
                + "\n\tFormato valido:"
                + "\n\t\t# <NombreDeClase>"
                + "\n\t\t<Elementos>"
                + "\n\t\t<Relaciones>"
                + "\n(Ultimo parametro exclusivo de Graficas)"
                + "\n\n************************************************");
        } catch (NumberFormatException e) {
            System.err.println("\n************************************************"
                + "\n\nSe ingreso un elemento invalido, asegurese " 
                + "\nque ingreso un numero entero."
                + "\n\n************************************************");
        } catch (IllegalArgumentException e) {
            System.err.println("\n************************************************"
                + "\n\nError(Enumeracion invalida o lista null). "
                + "\nContacte al proveedor del sofware."
                + "\n\n************************************************");
        }
    }
}
