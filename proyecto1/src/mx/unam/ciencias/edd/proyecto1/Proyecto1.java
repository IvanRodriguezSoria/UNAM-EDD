package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import mx.unam.ciencias.edd.IteradorLista;
import java.io.IOException;

// Clase que con la cual se lee uno o varios archivos de la entrada estandar y se ordenan
// sus lineas en orden alfabetico.
public class Proyecto1 {

    public static void main(String[] args) throws IOException {
        
        BufferedReader[] in;
        Lista<StringProyecto1> listaRenglones = new Lista<>();
        Lista<StringProyecto1> listaOrdenada = new Lista<>();
        String linea = "";
        boolean reversa = false;
        
        // Lee los archivos o banderas que el usuario ingreso.
        if (args.length > 0) {
            in = new BufferedReader[args.length];
            for (int i = 0; i < args.length; ++i) {
                // Checa si el elemento de "args" es la bandera -r.
                if (args[i].equals("-r")) {
                    reversa = true;
                    continue;
                }
                in[i] = new BufferedReader(new FileReader(args[i]));
                agregaLinea(listaRenglones, linea, in[i]);
            }
        // Como el usuario no ingreso banderas o archivos se lee el texto de
        // la entrada estandar.
        } else {
            in = new BufferedReader[1];
            in[0] = new BufferedReader(new InputStreamReader(System.in));
            agregaLinea(listaRenglones, linea, in[0]);
        }
        
        // Ordena la lista acorde a la tabla ASCII.
        listaOrdenada = Lista.mergeSort(listaRenglones);
        
        // Si se ingreso la bandera "-r" reversa sera "true" y se imprimira la lista
        // de lineas del texto ingresado en reversa, esto es de Z-A.
        if (reversa) {
            listaOrdenada = listaOrdenada.reversa();
            for (StringProyecto1 s : listaOrdenada)
                System.out.println(s);
        // Como el usuario no ingreso ninguna bandera se imprimer en la salida la lista ordenada
        // esto es de A-Z.
        } else {
            for (StringProyecto1 s : listaOrdenada)
                System.out.println(s);
        }
    }
    
    // Agrega cada linea leida del BufferedReader "in" en la lista "lista" y convierte
    // la linea en minuscula para que pueda ser correctamente comparada.
    public static void agregaLinea(Lista<StringProyecto1> lista, String linea, BufferedReader in) throws IOException {
        linea = in.readLine();
        while (linea != null) {
            lista.agrega(new StringProyecto1(linea));
            linea = in.readLine();
        }
    }
}