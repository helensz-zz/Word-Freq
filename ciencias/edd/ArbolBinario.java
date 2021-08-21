package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
            this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <tt>true</tt> si el vértice tiene padre,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayPadre() {
          if(padre == null)
            return false;
          return true;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <tt>true</tt> si el vértice tiene izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
          if(izquierdo == null)
           return false;
          return true;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <tt>true</tt> si el vértice tiene derecho,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayDerecho() {
          if(derecho == null)
            return false;
          return true;
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
          if(hayPadre())
            return padre;
          throw new NoSuchElementException("El vértice no tiene padre");
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
          if(hayIzquierdo())
            return izquierdo;
          throw new NoSuchElementException("No hay vértice izquierdo.");
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
          if(hayDerecho())
            return derecho;
          throw new NoSuchElementException("No hay vértice derecho.");
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
          int alti = -1;
          int altd = -1;
          if(izquierdo != null)
            alti = izquierdo.altura();
          if(derecho != null)
            altd = derecho.altura();
          return 1 + Math.max(alti, altd);
        }

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
          if(!hayPadre())
            return 0;
          return 1 + padre.profundidad();
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
          return elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)objeto;
            if(!elemento.equals(vertice.elemento))
              return false;
            if((!hayIzquierdo() && vertice.hayIzquierdo()) ||
            (hayIzquierdo() && !vertice.hayIzquierdo()))
              return false;
            if((!hayDerecho() && vertice.hayDerecho()) || (hayDerecho() &&
            !vertice.hayDerecho()))
              return false;
            if((!hayIzquierdo() && !vertice.hayIzquierdo()) &&
            (!hayDerecho() && !vertice.hayDerecho()))
              return true;
            boolean vi = true;
            boolean vd = true;
            if(hayIzquierdo())
              vi = izquierdo.equals(vertice.izquierdo);
            if(hayDerecho())
              vd = derecho.equals(vertice.derecho);
            return (vi && vd);
        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        public String toString() {
          return elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
      for(T e : coleccion){
        agrega(e);
      }
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
      if(raiz == null)
        return -1;
      return raiz.altura();
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
      return this.elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
      if(busca(elemento) != null)
       return true;
      return false;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <tt>null</tt>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <tt>null</tt> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
      if(raiz == null)
        return null;
      if(raiz.elemento.equals(elemento))
        return raiz;
      else
        return busca(elemento,raiz);
    }

    /**
     *Método auxiliar de busca().
     *@param elemento
     *@param v
     */
    private Vertice busca(T elemento, Vertice v){
      if(v == null)
        return null;
      if(elemento.equals(v.elemento))
        return v;
      Vertice aux = busca(elemento, v.izquierdo);
      if(aux != null)
        return aux;
      aux = busca(elemento, v.derecho);
        return aux;
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
      if(esVacia())
        throw new NoSuchElementException();
      return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
      return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
      raiz = null;
      elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
            if(raiz == null)
              return (arbol.raiz == null);
            return raiz.equals(arbol.raiz);
    }

    /**
     *Método auxiliar para crear la cadena antes de un vértice.
     *@param nivel niveles del árbol binario.
     *@param arreglo arreglo.
     *@return una representación en cadena antes de un vértice.
     */
    private String dibujaEspacios(int nivel, int[] arreglo){
      String s = "";
      for(int i = 0; i < nivel; i++){
        if(arreglo[i] == 1)
          s += "│  ";
        else
          s += "   ";
      }
      return s;
    }

    /**
     *Método auxiliar recursivo para crear la cadena que representa la línea de
     *vértices.
     *@param v vértice a representar.
     *@param nivel nivel del árbol en que nos encontramos.
     *@param arreglo arreglo binario.
     */
     private String toString(Vertice v, int nivel, int[] arreglo){
       String s = v.toString() + "\n";
       arreglo[nivel] = 1;
       if(v.hayIzquierdo() && v.hayDerecho()){
         s += dibujaEspacios(nivel,arreglo);
         s += "├─›";
         s += toString(v.izquierdo, nivel+1, arreglo);
         s += dibujaEspacios(nivel,arreglo);
         s += "└─»";
         arreglo[nivel] = 0;
         s += toString(v.derecho, nivel+1, arreglo);
       }else if(v.hayIzquierdo()){
         s += dibujaEspacios(nivel,arreglo);
         s += "└─›";
         arreglo[nivel] = 0;
         s += toString(v.izquierdo,nivel+1,arreglo);
       }else if(v.hayDerecho()){
         s += dibujaEspacios(nivel,arreglo);
         s += "└─»";
         arreglo[nivel] = 0;
         s += toString(v.derecho, nivel+1, arreglo);
       }
       return s;
     }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
      if(raiz == null)
        return "";
      int altura = altura() + 1;
      int[] a = new int[altura];
      for(int i = 0; i < altura; i++)
        a[i] = 0;
      return toString(raiz, 0, a);
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
}
