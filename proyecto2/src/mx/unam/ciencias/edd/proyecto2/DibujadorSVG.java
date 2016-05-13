package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
import java.util.Iterator;

// Clase que contiene metodos para dibujar en SVG las estructuras de datos vistas en el curso.
public class DibujadorSVG {
    
    // Constructor por omision.
    public DibujadorSVG() {}
    
    private String dibujarMarco(int ancho, int largo, String relleno) {
        return "<svg width=\"" + ancho + "\" height=\"" + largo + "\"><g>" + relleno + "</g></svg>";
    }
    
    // Regresa una cadena que representa un rectangulo en SVG.
    // Parametros: coordenada x, coordenada y, ancho y alto.
    private String dibujarRectangulo(int x, int y, int ancho, int alto) {
        return "<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + ancho + "\" height=\"" + alto 
        + "\" style=\"fill:white;stroke:black;stroke-width:2\" />";
    }
    
    // Regresa una cadena que representa un circulo en SVG.
    // Parametros: coordenada del centro x, coordenada del centro y, raio y color.
    private String dibujarCirculo(int cx, int cy, int r, String color) {
        return "<circle cx=\"" + cx + "\" cy=\"" + cy + "\" r=\"" + r
        + "\" stroke=\"black\" stroke-width=\"2\" fill=\"" + color + "\" />";
    }
    
    // Regresa una cadena que representa una linea en SVG.
    // Parametros: coordenada x1, coordenada y1, coordenada x2, coordenada y2 (Extremos).
    private String dibujarLinea(int x1, int y1, int x2, int y2) {
        return "<line x1=\"" + x1 + "\" y1=\"" + y1 + "\" x2=\"" + x2 
        + "\" y2=\"" + y2 + "\" style=\"stroke:black;stroke-width:2\" />";
    }
    // Regresa una cadena que representa texto en SVG.
    // Parametros: coordenada x, coordenada y y texto a dibujar..
    private String dibujarTexto(int x, int y, String color, String texto) {
        return "<text x=\"" + x + "\" y=\"" + y + "\" fill=\"" + color 
        + "\">" + texto + "</text>";
    }
    
    // Regresa una cadena que representa un arbol avl en SVG.
    public String dibujarAVL(ArbolAVL<Integer> avl, Lista<Integer> elementos) {
        return "AVL";
    }
    
    // Regresa una cadena que representa un arbol binario completo en SVG.
    public String dibujarCompleto(ArbolBinarioCompleto<Integer> completo, Lista<Integer> elementos) {
        return "Arbol Binario Completo";
    }
    
    // Regresa una cadena que representa un arbol binario ordenado en SVG.
    public String dibujarOrdenado(ArbolBinarioOrdenado<Integer> ordenado, Lista<Integer> elementos) {
        return "Arbol Binario Ordenado";
    }
    
    // Regresa una cadena que representa un arbol rojinegro en SVG.
    public String dibujarRojinegro(ArbolRojinegro<Integer> rojinegro, Lista<Integer> elementos) {
        return "Arbol Rojinegro";
    }
    
    // Regresa una cadena que representa una cola en SVG.
    public String dibujarCola(Cola<Integer> cola, Lista<Integer> elementos) {
        
        for (Integer elemento : elementos)
            cola.mete(elemento);
        
        String aux = "";
        int xRec = 20;
        int xText = 40;
        while(!cola.esVacia() ) {
            aux += dibujarRectangulo(xRec, 20, 50, 30);
            aux += dibujarTexto(xText, 45, "black", cola.saca().toString() );
            xRec += 90;
            xText += 90;
            
            if (!cola.esVacia() ) {
                aux += dibujarLinea(xRec - 90 + 50, 35, xRec, 35);
                
                // Flechas derecha.
                aux += dibujarLinea(xRec - 10, 25, xRec, 35);
                aux += dibujarLinea(xRec - 10, 45, xRec, 35);
            }
        }
        
        return dibujarMarco(xRec + 20, 100,  aux);
    }
    
    // Regresa una cadena que representa una grafica en SVG.
    public String dibujarGrafica(Lista<Integer> elementos, Lista<Integer> relaciones) {
        return "Grafica";
    }
    
    // Regresa una cadena que representa una lista en SVG.
    public String dibujarLista(Lista<Integer> lista, Lista<Integer> elementos) {
        String aux = "";
        int xRec = 20;
        int xText = 40;
        Iterator<Integer> iter = elementos.iterator();
        while(iter.hasNext() ) {
            aux += dibujarRectangulo(xRec, 20, 50, 30);
            aux += dibujarTexto(xText, 45, "black", iter.next().toString() );
            xRec += 90;
            xText += 90;
            
            if (iter.hasNext() ) {
                aux += dibujarLinea(xRec - 90 + 50, 35, xRec, 35);
                
                // Flechas izquierda.
                aux += dibujarLinea(xRec - 90 + 50, 35, xRec - 90 + 60, 25);
                aux += dibujarLinea(xRec - 90 + 50, 35, xRec - 90 + 60, 45);
                
                // Flechas derecha.
                aux += dibujarLinea(xRec - 10, 25, xRec, 35);
                aux += dibujarLinea(xRec - 10, 45, xRec, 35);
            }
        }
        
        return dibujarMarco(xRec + 20, 100,  aux);
    }
    
    // Regresa una cadena que representa una pila en SVG.
    public String dibujarPila(Pila<Integer> pila, Lista<Integer> elementos) {
        for (Integer elemento : elementos)
            pila.mete(elemento);
        
        String aux = "";
        int yRec = 20;
        int yText = 40;
        while(!pila.esVacia() ) {
            aux += dibujarRectangulo(20, yRec, 50, 30);
            aux += dibujarTexto(40, yText, "black", pila.saca().toString() );
            yRec += 90;
            yText += 90;
            
            if (!pila.esVacia() ) {
                aux += dibujarLinea(45, yRec - 90 + 30, 45, yRec);
                
                // Flechas derecha.
                aux += dibujarLinea(35, yRec - 10, 45, yRec);
                aux += dibujarLinea(55, yRec - 10, 45, yRec);
            }
        }
        
        return dibujarMarco(100, yRec + 20,  aux);
    }
    
    // Regresa una cadena que representa un monticulo en SVG.
    public String dibujarMonticulo(Lista<Integer> elementos) {
        return "Monticulo";
    }
}