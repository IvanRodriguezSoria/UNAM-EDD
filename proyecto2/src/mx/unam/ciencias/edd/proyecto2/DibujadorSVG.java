package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

// Clase que contiene metodos para dibujar en SVG las estructuras de datos vistas en el curso.
public class DibujadorSVG {
    
    // Constructor por omision.
    public DibujadorSVG() {}
    
    // Regresa una cadena que representa un rectangulo en SVG.
    // Parametros: coordenada x, coordenada y, ancho y alto.
    private String dibujarRectangulo(int x, int y, int ancho, int alto) {
        return "<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + ancho + "\" height=\"" + alto + "\" style=\"fill:white;stroke:black;stroke-width:2\" />";
    }
    
    // Regresa una cadena que representa un circulo en SVG.
    // Parametros: coordenada del centro x, coordenada del centro y, raio y color.
    private String dibujarCirculo(int cx, int cy, int r, Color color) {
        return null;
    }
    
    // Regresa una cadena que representa una linea en SVG.
    // Parametros: coordenada x1, coordenada y1, coordenada x2, coordenada y2 (Extremos).
    private String dibujarLinea(int x1, int y1, int x2, int y2) {
        return null;
    }
    
    // Regresa una cadena que representa texto en SVG.
    // Parametros: coordenada x, coordenada y y texto a dibujar..
    private String dibujarTexto(int x, int y, String texto) {
        return null;
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
        return "Cola";
    }
    
    // Regresa una cadena que representa una grafica en SVG.
    public String dibujarGrafica(Lista<Integer> elementos, Lista<Integer> relaciones) {
        return "Grafica";
    }
    
    // Regresa una cadena que representa una lista en SVG.
    public String dibujarLista(Lista<Integer> lista, Lista<Integer> elementos) {
        return "Lista";
    }
    
    // Regresa una cadena que representa una pila en SVG.
    public String dibujarPila(Pila<Integer> pila, Lista<Integer> elementos) {
        return "Pila";
    }
    
    // Regresa una cadena que representa un monticulo en SVG.
    public String dibujarMonticulo(Lista<Integer> elementos) {
        return "Monticulo";
    }
}