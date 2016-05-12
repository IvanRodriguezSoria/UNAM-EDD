package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Clase que contiene metodospara leer los datos de la estructura que se desea 
// dibujar en SVG.
public class LectorEstructura {
    
    private String SVG;
    private String elementos;
    private String relaciones;
    private Estructura estructura;
    private Lista<String> lista;
    public Lista<Integer> listaElementos; // TODO quitar public.
    private Lista<Integer> listaRelaciones;
    
    // Constructor que inicializa la lista de elementos, relaciones y obtiene la estructura 
    // que se desea dibujar en SVG.
    public LectorEstructura(Lista<String> lista) {
        if (lista == null)
            throw new IllegalArgumentException("Lista invalida (null).");
        
        this.lista = lista;
        Iterator iterador = lista.iterator();
        listaElementos = new Lista<Integer>();
        listaRelaciones = new Lista<Integer>();
        
        try {
            String primeraLinea = iterador.next().toString();
            estructura = getEstructura(primeraLinea);
            elementos = iterador.next().toString();
            elementos = elementos.replace(", ", "");
            elementos = elementos.trim();
            poblarListaInt(elementos);
        
            if (estructura == Estructura.GRAFICA) {
                relaciones = iterador.next().toString();
                relaciones = relaciones.replace(", ", "");
                relaciones = relaciones.replace("; ", "");
                relaciones = relaciones.trim();
                poblarListaInt(relaciones);
            }
            
        } catch (NoSuchElementException e) {
            System.out.println("\nSe ingreso un formato invalido.\n"
                + "\tFormato valido:\n"
                + "\t\t# <NombreDeClase>\n"
                + "\t\t<Elementos>\n"
                + "\t\t<Relaciones>\n"
                + "\n(Ultimo parametro exclusivo de Graficas)\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Error(Enumeracion invalida). "
            + "Contacte al proveedor del sofware.");
        }
    }
    
    // Llena una lista con los enteros en una cadena.
    // Use un truco visto en StackOverflow para convertir char a int.
    // http://stackoverflow.com/questions/4968323/java-parse-int-value-from-a-char
    private void poblarListaInt(String cadena) {
        for (int i = 0; i < elementos.length(); ++i)
            listaElementos.agregaFinal(cadena.charAt(i) - '0' );
    }
    
    // Regresa el SVG de la estructura deseada.
    public String getSVG() {
        if (estructura == Estructura.GRAFICA)
            SVG = toSVG(estructura, listaElementos, listaRelaciones);
        else 
            SVG = toSVG(estructura, listaElementos);
        
        return SVG;
    }
    
    // Regresa una enumeracion Estructura que representa la estructura con la que se trabajara.
    public Estructura getEstructura(String estructura) {
        String renglon = estructura.toLowerCase();
        
        if (renglon.equals("# arbolavl") )
            return Estructura.ARBOL_AVL;
        else if (renglon.equals("# arbolbinariocompleto") )
            return Estructura.ARBOL_BINARIO_COMPLETO;
        else if (renglon.equals("# arbolbinarioordenado") )
            return Estructura.ARBOL_BINARIO_ORDENADO;
        else if (renglon.equals("# arbolrojinegro") )
            return Estructura.ARBOL_ROJINEGRO;
        else if (renglon.equals("# cola") )
            return Estructura.COLA;
        else if (renglon.equals("# grafica") )
            return Estructura.GRAFICA;
        else if (renglon.equals("# lista") )
            return Estructura.LISTA;
        else if (renglon.equals("# monticulominimo") )
            return Estructura.MONTICULO_MINIMO;
        else if (renglon.equals("# pila") )
            return Estructura.PILA;
        else 
            throw new NoSuchElementException("Estructura invalida.");
    }
    
    // Transforma la estructura recibida a SVG.
    public String toSVG(Estructura estructura, Lista<Integer> elementos) {
        switch(estructura) { // TODO. Regresar SVG.
            case ARBOL_AVL:
                ArbolAVL<Integer> avl = new ArbolAVL<Integer>();
                return "AVL";
            case ARBOL_BINARIO_COMPLETO:
                ArbolBinarioCompleto<Integer> completo = new ArbolBinarioCompleto<Integer>();
                return "Completo";
            case ARBOL_BINARIO_ORDENADO:
                ArbolBinarioOrdenado<Integer> ordenado = new ArbolBinarioOrdenado<Integer>();
                return "Ordenado";
            case ARBOL_ROJINEGRO:
                ArbolRojinegro<Integer> rojinegro = new ArbolRojinegro<Integer>();
                return "Rojinegro";
            case COLA:
                Cola<Integer> cola = new Cola<Integer>();
                return "Cola";
            case LISTA:
                Lista<Integer> lista = new Lista<Integer>();
                return "Lista";
            case MONTICULO_MINIMO: 
                // TODO.
                return "Heap";
            case PILA: 
                Pila<Integer> pila = new Pila<Integer>();
                return "Pila";
            default:
                throw new IllegalArgumentException("Enumeracion invalida.");
        }
    }
    
    // Transforma la estructura recibida a SVG.
    public String toSVG(Estructura estructura, Lista<Integer> elementos, Lista<Integer> relaciones) {
        return "Grafica"; // TODO. Regresar SVG.
    }
}