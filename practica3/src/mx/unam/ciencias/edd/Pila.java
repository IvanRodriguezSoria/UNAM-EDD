package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 * Autor: ivan rodriguez soria.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     */
    @Override public void mete(T elemento) {
        Nodo n = new Nodo(elemento);
        if(this.esVacia()) {
            cabeza = n;
            rabo = cabeza;
        }
        else {
            Nodo auxiliar = cabeza;
            cabeza = n;
            n.siguiente = auxiliar;
        }
    }
}
