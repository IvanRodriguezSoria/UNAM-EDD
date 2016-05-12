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
    private Lista<Integer> listaElementos;
    private Lista<Integer> listaRelaciones;
    
    // Constructor que inicializa la lista de elementos, relaciones y obtiene la estructura 
    // que se desea dibujar en SVG.
    public LectorEstructura(Lista<String> lista) {
        if (lista == null)
            throw new IllegalArgumentException();
        
        this.lista = lista;
        Iterator iterador = lista.iterator();
        listaElementos = new Lista<Integer>();
        listaRelaciones = new Lista<Integer>();
        
        String primeraLinea = iterador.next().toString();
        estructura = getEstructura(primeraLinea);
        elementos = iterador.next().toString();
        getNumeros(elementos, listaElementos);
            
        if (estructura == Estructura.GRAFICA) {
            relaciones = iterador.next().toString();
            getNumeros(relaciones, listaRelaciones);
        }
    }
    
    // Obtiene todos los numeros enteros de la cadena y los agrega a una lista.
    // Parametros: La cadena de donde se obtendran los numeros, la lista a donde se agregaran.
    private void getNumeros(String cadena, Lista<Integer> lista) {
        String auxString = "";
        int auxInt = cadena.length();
        char auxChar;
        for (int i = 0; i < auxInt; ++i) {
            auxChar = cadena.charAt(i);
            switch (auxChar) {
                case ',':
                case ';':
                    lista.agregaFinal(Integer.parseInt(auxString) );
                    auxString = "";
                    break;
                case ' ':
                    break;
                default:
                    auxString += auxChar;    
            }
            
            if (i == auxInt - 1)
                lista.agregaFinal(Integer.parseInt(auxString) );
        }
    }
    
    // Regresa el SVG de la estructura deseada.
    public String getSVG() {
        SVG = toSVG(estructura);
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
            throw new NoSuchElementException();
    }
    
    // Transforma la estructura recibida a SVG.
    public String toSVG(Estructura estructura) {
        DibujadorSVG dibujador = new DibujadorSVG();
        switch(estructura) {
            
            case ARBOL_AVL:
                ArbolAVL<Integer> avl = new ArbolAVL<Integer>();
                return dibujador.dibujarAVL(avl, listaElementos);
            
            case ARBOL_BINARIO_COMPLETO:
                ArbolBinarioCompleto<Integer> completo = new ArbolBinarioCompleto<Integer>();
                return dibujador.dibujarCompleto(completo, listaElementos);
            
            case ARBOL_BINARIO_ORDENADO:
                ArbolBinarioOrdenado<Integer> ordenado = new ArbolBinarioOrdenado<Integer>();
                return dibujador.dibujarOrdenado(ordenado, listaElementos);
            
            case ARBOL_ROJINEGRO:
                ArbolRojinegro<Integer> rojinegro = new ArbolRojinegro<Integer>();
                return dibujador.dibujarRojinegro(rojinegro, listaElementos);
            
            case COLA:
                Cola<Integer> cola = new Cola<Integer>();
                return dibujador.dibujarCola(cola, listaElementos);
            
            case LISTA:
                Lista<Integer> lista = new Lista<Integer>();
                return dibujador.dibujarLista(lista, listaElementos);
            
            case MONTICULO_MINIMO: 
                // TODO.
                return dibujador.dibujarMonticulo(listaElementos);
            
            case PILA: 
                Pila<Integer> pila = new Pila<Integer>();
                return dibujador.dibujarPila(pila, listaElementos);
            
            case GRAFICA:
                // TODO.
                return dibujador.dibujarGrafica(listaElementos, listaRelaciones);
            
            default:
                throw new IllegalArgumentException();
        }
    }
}