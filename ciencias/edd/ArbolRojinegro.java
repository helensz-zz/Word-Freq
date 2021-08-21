package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
          super(elemento);
          color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
          if (color == Color.ROJO)
            return "R{" + elemento.toString() + "}";
          return "N{" + elemento.toString() + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
                return (color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /* Convierte el vértice a VerticeRojinegro. */
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro)vertice;
        return v;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
      if(vertice.getClass() != VerticeRojinegro.class )
              	throw new ClassCastException();
      return verticeRojinegro(vertice).color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
      super.agrega(elemento);
      VerticeRojinegro v = verticeRojinegro(super.ultimoAgregado);
      v.color = Color.ROJO;
      rebalanceaAgrega(v);
    }

    /**
     * Método auxiliar que nos dice si el vértice es rojo.
     */
    private boolean esRojo(VerticeRojinegro v){
      return(v != null && v.color == Color.ROJO);
    }

    /**
     * Método auxiliar que nos dice si el vértice es negro.
     */
    private boolean esNegro(VerticeRojinegro v){
      return(v == null || v.color == Color.NEGRO);
    }

    /**
     *Método auxiliar para conseguir el abuelo del vértice.
     */
     private VerticeRojinegro getAbuelo(VerticeRojinegro v){
      VerticeRojinegro p = verticeRojinegro(v.padre);
      return verticeRojinegro(p.padre);
     }

     /**
      *Método auxiliar para conseguir el tío del vértice.
      */
     private VerticeRojinegro getTio(VerticeRojinegro v){
       VerticeRojinegro a = getAbuelo(v);
       if(v.padre == a.izquierdo)
        return verticeRojinegro(a.derecho);
      else
        return verticeRojinegro(a.izquierdo);
     }

   /**
    *Método auxiliar para conseguir el hermano del vértice.
    */
    private VerticeRojinegro getHermano(VerticeRojinegro v){
       VerticeRojinegro p = verticeRojinegro(v.padre);
       if(v == p.izquierdo)
         return verticeRojinegro(p.derecho);
       else
        return verticeRojinegro(p.izquierdo);
     }

     /**
      *Método auxiliar para buscar un elemento en el árbol.
      *@param vertice en el que vamos a buscar.
      *@param elemento que vamos a buscar en el árbol.
      *@return el vértice en el que se encuentra el elemento, si
      *es que se encuentra.
      */
    private VerticeRojinegro buscaElemento(VerticeRojinegro v,T elemento){
      if(v == null)
        return null;
      if(elemento.equals(v.elemento))
        return v;
      if(elemento.compareTo(v.elemento) < 0)
        return buscaElemento(verticeRojinegro(v.izquierdo), elemento);
      else
        return buscaElemento(verticeRojinegro(v.derecho), elemento);
    }

    /**
     *Método auxiliar para intercambiar un vértice con el máximo.
     */
    private VerticeRojinegro intercambiaVertice(VerticeRojinegro vertice){
      if(vertice.hayDerecho())
        return intercambiaVertice(verticeRojinegro(vertice.derecho));
      else
        return vertice;
    }

    /**
     *Método auxiliar privado para rebalancear el árbol siempre que se le agrega
     *un elemento.
     *@param v que es el vértice sobre el que se balanceará el árbol
     */
     private void rebalanceaAgrega(VerticeRojinegro v){
      //Caso 1:
      if(!v.hayPadre()){
        v.color = Color.NEGRO;
        return;
      }

      VerticeRojinegro p = verticeRojinegro(v.padre);
      //Caso 2:
      if(esNegro(p))
        return;

      VerticeRojinegro t = getTio(v);
      VerticeRojinegro a = getAbuelo(v);
      //Caso 3:
      if(esRojo(t)){
        t.color = Color.NEGRO;
        p.color = Color.NEGRO;
        a.color = Color.ROJO;
        rebalanceaAgrega(a);
        return;
      }

      VerticeRojinegro aux = p;
      //Caso 4:
      if(p == a.izquierdo && v == p.derecho){
        super.giraIzquierda(p);
        p = v;
        v = aux;
      }else if(p == a.derecho && v == p.izquierdo){
        super.giraDerecha(p);
        p = v;
        v = aux;
      }

      //Caso 5:
      p.color = Color.NEGRO;
      a.color = Color.ROJO;
      if(v == p.izquierdo)
        super.giraDerecha(a);
      else
        super.giraIzquierda(a);
     }


    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
      VerticeRojinegro v = buscaElemento(verticeRojinegro(super.raiz),elemento);
      //Buscamos el elemento a eliminar.
      if(v == null)
        return;

      VerticeRojinegro aux = null;
      if(v.hayIzquierdo()){
        aux = v;
        v = intercambiaVertice(verticeRojinegro(v.izquierdo));
        aux.elemento = v.elemento;
        aux = null;
      }

      if(!v.hayIzquierdo() && !v.hayDerecho()){
        v.izquierdo = nuevoVertice(null);
        aux = verticeRojinegro(v.izquierdo);
        aux.padre = v;
        aux.color = Color.NEGRO;
      }

      VerticeRojinegro h = null;
      if(v.hayIzquierdo())
        h = verticeRojinegro(v.izquierdo);
      else
        h = verticeRojinegro(v.derecho);

      desconecta(h,v);

      if(esRojo(h) || esRojo(v)){
        h.color = Color.NEGRO;
      }else{
        h.color = Color.NEGRO;
        rebalanceaElimina(h);
      }

      if(aux != null){
        if(!aux.hayPadre()){
          super.raiz = null;
          ultimoAgregado = null;
          aux = null;
        }else if(aux.padre.derecho == aux){
          aux.padre.derecho = null;
        }else{
          aux.padre.izquierdo = null;
        }
      }
      elementos--;
    }

    /**
     *Método auxiliar para reemplazar el vértice por uno de sus hijos.
     *@param hijo por el cuál será reemplazado el vértice.
     *@param v vértice que se eliminará.
     */
    private void desconecta(VerticeRojinegro hijo, VerticeRojinegro v){
      if(!v.hayPadre()){
        raiz = hijo;
        raiz.padre = null;
        return;
      }
      hijo.padre = v.padre;
      if(v == v.padre.izquierdo){
        if(v.izquierdo == hijo)
          v.padre.izquierdo = v.izquierdo;
        else
          v.padre.izquierdo = v.derecho;
      }
      if(v == v.padre.derecho){
        if(v.izquierdo == hijo)
          v.padre.derecho = v.izquierdo;
        else
          v.padre.derecho = v.derecho;
      }
    }

    /**
     *Método auxiliar privado para rebalancear el árbol siempre que se elimina
     *un elemento
     *@param v que es el vértice de color negro sobre el que se balanceará
     *el árbol.
     */
    private void rebalanceaElimina(VerticeRojinegro v){
      //Caso 1:
      if(!v.hayPadre()){
        v.color = Color.NEGRO;
        raiz = v;
        return;
      }
      VerticeRojinegro p = verticeRojinegro(v.padre);
      VerticeRojinegro h = getHermano(v);

      //Caso 2:
      if(esRojo(h) && esNegro(p)){
        p.color = Color.ROJO;
        h.color = Color.NEGRO;
        if(v == p.izquierdo)
          super.giraIzquierda(p);
        else
          super.giraDerecha(p);
        h = getHermano(v);
        p = verticeRojinegro(v.padre);
      }

      VerticeRojinegro hi = verticeRojinegro(h.izquierdo);
      VerticeRojinegro hd = verticeRojinegro(h.derecho);
      //Caso 3:
      if(esNegro(p) && esNegro(h) && esNegro(hi) && esNegro(hd)){
        h.color = Color.ROJO;
        rebalanceaElimina(p);
        return;
      }

      //Caso 4:
      if(esNegro(h) && esNegro(hi) && esNegro(hd) && esRojo(p)){
        h.color = Color.ROJO;
        p.color = Color.NEGRO;
        return;
      }

      //Caso 5:
      if(v == p.izquierdo && esRojo(hi) && esNegro(hd) && esNegro(h)){
        h.color = Color.ROJO;
        hi.color = Color.NEGRO;
        super.giraDerecha(h);
      }else if(v == p.derecho && esNegro(hi) && esRojo(hd) && esNegro(h)){
        h.color = Color.ROJO;
        hd.color = Color.NEGRO;
        super.giraIzquierda(h);
      }

      h = getHermano(v);
      hi = verticeRojinegro(h.izquierdo);
      hd = verticeRojinegro(h.derecho);

      //Caso 6:
      h.color = p.color;
      p.color = Color.NEGRO;
      if(v == p.izquierdo){
        hd.color = Color.NEGRO;
        super.giraIzquierda(p);
      }else{
        hi.color = Color.NEGRO;
        super.giraDerecha(p);
      }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
