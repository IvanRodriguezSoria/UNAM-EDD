package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LectorEstructura {
    
    private String SVG;
    private String elementos;
    private String relaciones;
    private Estructura estructura;
    
    // Constructor por omision.
    public LectorEstructura() {}
    
    // Transforma la estructura recibida a SVG.
    public String toSVG(Estructura estructura, String elementos) {
        switch(estructura) { // TODO. Regresar SVG.
            case ARBOL_AVL:
                return "AVL";
            case ARBOL_BINARIO:
                return "Binario";
            case ARBOL_BINARIO_COMPLETO:
                return "Completo";
            case ARBOL_BINARIO_ORDENADO:
                return "Ordenado";
            case ARBOL_ROJINEGRO:
                return "Rojinegro";
            case COLA:
                return "Cola";
            case LISTA:
                return "Lista";
            case MONTICULO_MINIMO: 
                return "Heap";
            case PILA: 
                return "Pila";
            default:
                throw new IllegalArgumentException("Enumeracion invalida.");
        }
    }
    
    // Transforma la estructura recibida a SVG.
    public String toSVG(Estructura estructura, String elementos, String relaciones) {
        return "Grafica"; // TODO. Regresar SVG.
    }
    
    // Regresa el SVG de la estructura dada.
    public String getSVG(Lista<String> lista) {
        if (lista == null)
            throw new IllegalArgumentException("Lista invalida (null).");
            
        Iterator iterador = lista.iterator();
        
        try {
            String primeraLinea = iterador.next().toString();
            estructura = getEstructura(primeraLinea);
            elementos = iterador.next().toString();
        
            if (estructura == Estructura.GRAFICA) {
                relaciones = iterador.next().toString(); 
                SVG = toSVG(estructura, elementos, relaciones);
            } else {
                SVG = toSVG(estructura, elementos);
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
        
        return SVG;
    }
    
    // Regresa una enumeracion Estructura que representa la estructura con la que se trabajara.
    public Estructura getEstructura(String estructura) {
        String renglon = estructura.toLowerCase();
        
        if (renglon.equals("# arbolavl") )
            return Estructura.ARBOL_AVL;
        else if (renglon.equals("# arbolbinario") )
            return Estructura.ARBOL_BINARIO;
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
}