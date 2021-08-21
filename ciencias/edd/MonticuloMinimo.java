package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
          return (indice < arbol.length && arbol[indice] != null);
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
          if(indice >= arbol.length)
            throw new NoSuchElementException();
          return arbol[indice++];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
          this.elemento = elemento;
          this.indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
          return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
          this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
          return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        this.arbol = nuevoArreglo(100); /* 100 es arbitrario. */
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
      this.arbol = nuevoArreglo(n);
      int i = 0;
      elementos = n;
      for(T elem : iterable){
        arbol[i] = elem;
        elem.setIndice(i);
        i+=1;
      }
      int ind = (n-1)/2 ;
      for(i = ind; i >= 0; i--){
        heapifyDown(arbol[i]);
      }
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
      if(elementos == arbol.length){
        T[] aux = nuevoArreglo(elementos * 2);
        for(int i = 0; i < elementos; i++)
          aux[i] = arbol[i];
        arbol = aux;
      }
      arbol[elementos] = elemento;
      elemento.setIndice(elementos);
      elementos++;
      heapifyUp(elemento);
    }

    /**
     * Método auxiliar para intercambiar dos elementos.
     * @param i uno de los elementos que va a participar en el intercambio.
     * @param j uno de los elementos que va a participar en el intercambio.
     */
    private void intercambia(T i, T j){
      int aux = j.getIndice();
      arbol[i.getIndice()] = j;
      arbol[j.getIndice()] = i;
      j.setIndice(i.getIndice());
      i.setIndice(aux);
    }

    /**
     * Método auxiliar que simula el algoritmo heapify-down.
     * @param elemento que vamos a acomodar hacia abajo.
     */
    private void heapifyDown(T elemento){
      if(elemento == null)
        return;
      int izq = elemento.getIndice() * 2 + 1;
      int der = elemento.getIndice() * 2 + 2;
      if(!iValido(izq) && !iValido(der))
        return;

      int minimo = der;
      if(iValido(izq)){
        if(iValido(der)) {
          if(arbol[izq].compareTo(arbol[der]) < 0)
            minimo = izq;
          }else
            minimo = izq;
      }
      if(elemento.compareTo(arbol[minimo]) > 0){
        intercambia(elemento, arbol[minimo]);
        heapifyDown(elemento);
      }
    }

    private boolean iValido(int i){
      return !(i < 0 ||  i >= elementos);
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
      if(esVacia())
        throw new IllegalStateException();
      T raiz = arbol[0];
      intercambia(arbol[0],arbol[elementos-1]);
      elementos-=1;
      arbol[elementos].setIndice(-1);
      arbol[elementos] = null;
      heapifyDown(arbol[0]);
      return raiz;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
      int i = elemento.getIndice();
      if(!iValido(i))
        return;
      intercambia(arbol[i], arbol[elementos-1]);
      elementos-=1;
      arbol[elementos] = null;
      elemento.setIndice(-1);
      reordena(arbol[i]);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
      int i = elemento.getIndice();
      if(!iValido(i))
        return false;
      return (arbol[i].compareTo(elemento) == 0);
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacia() {
      return (elementos == 0);
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
      for(int i = 0; i < elementos; i++)
        arbol[i] = null;
      elementos = 0;
    }

    /**
     * Método auxiliar que simula el algoritmo heapify-up.
     * @param elemento que vamos a acomodar hacia arriba.
     */
    private void heapifyUp(T elemento){
      int padre = elemento.getIndice()-1;
      padre = padre == -1 ? -1 : padre / 2;
      if(!iValido(padre) || arbol[padre].compareTo(elemento) < 0)
        return;
      intercambia(arbol[padre], elemento);
      heapifyUp(elemento);
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
      if(elemento == null)
        return ;
      int padre = elemento.getIndice() - 1;
      padre = padre == -1 ? -1 : padre/2;
      if(!iValido(padre) || arbol[padre].compareTo(elemento) <= 0)
        heapifyDown(elemento);
      else
        heapifyUp(elemento);
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
      return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
      if(!iValido(i))
        throw new NoSuchElementException();
      return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
      String s = "";
      for(int i = 0; i < arbol.length; i++)
        s += arbol[i] + ", ";
      return s;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        if(monticulo.getElementos() != getElementos())
          return false;
        for(int i = 0; i < getElementos(); i++)
          if(!arbol[i].equals(monticulo.get(i)))
            return false;
        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
      Lista<Adaptador<T>> l1 = new Lista<Adaptador<T>>();
      for(T elem : coleccion){
        l1.agrega(new Adaptador<T>(elem));
      }
      Lista<T> l2 = new Lista<>();
      MonticuloMinimo<Adaptador<T>> m = new MonticuloMinimo<Adaptador<T>>(l1);
      while(!m.esVacia())
        l2.agrega(m.elimina().elemento);
      return l2;
    }
}
