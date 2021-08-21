package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
          this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
          this.anterior = null;
          this.siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
          if(this.siguiente == null)
            return false;
          return true;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
          if(hasNext()){
            this.anterior = this.siguiente;
            this.siguiente = this.siguiente.siguiente;
            return this.anterior.elemento;
          }else{
              throw new NoSuchElementException("No hay elemento siguiente.");
          }
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
          if(this.anterior == null)
            return false;
          return true;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
          if(hasPrevious()){
            this.siguiente = this.anterior;
            this.anterior = anterior.anterior;
            return this.siguiente.elemento;
          }else{
            throw new NoSuchElementException("No hay elemento anterior");
          }
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
          this.siguiente = cabeza;
          this.anterior = null;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
          this.anterior = rabo;
          this.siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
      return this.longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
      return this.longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
      if(cabeza == null)
        return true;
      return false;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
      if(elemento == null){
        throw new IllegalArgumentException();
      }else{
        Nodo n = new Nodo(elemento);
        if(this.cabeza == null){
          this.cabeza = n;
          this.rabo = n;
          longitud++;
        }else{
          agregaFinal(elemento);
        }
      }
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
      if(elemento == null){
        throw new IllegalArgumentException();
      }else{
        Nodo n = new Nodo(elemento);
        longitud++;
        if(cabeza == null){
          this.cabeza = n;
          this.rabo = n;
        }else{
          this.rabo.siguiente = n;
          n.anterior = this.rabo;
          this.rabo = n;
        }
      }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
      if(elemento == null){
        throw new IllegalArgumentException();
      }else{
        Nodo n = new Nodo(elemento);
        longitud++;
        if(cabeza == null){
          cabeza = n;
          rabo = n;
        }else{
          this.cabeza.anterior = n;
          n.siguiente = this.cabeza;
          this.cabeza = n;
        }
      }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
      if(elemento == null)
        throw new IllegalArgumentException();
      if(i <= 0){
        agregaInicio(elemento);
      }else if(i >= longitud){
        agrega(elemento);
      }else{
        Nodo aux = cabeza;
        for(int j = 0; j < i; j++){
          aux = aux.siguiente;
        }
        Nodo nuevo = new Nodo(elemento);
        aux.anterior.siguiente = nuevo;
        nuevo.siguiente = aux;
        nuevo.anterior = aux.anterior;
        aux.anterior = nuevo;
        longitud++;
      }
    }

    /**
    *Busca un elemento en la lista.
    * @param elemento elemento a buscar.
    */
    private Nodo buscaNodo(T elemento){
      Nodo aux = cabeza;
      while(aux != null){
        if(aux.elemento.equals(elemento)){
          return aux;
        }else{
          aux = aux.siguiente;
        }
      }
      return null;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
      Nodo n = buscaNodo(elemento);
      if(n == null){
        return;
      }else if(cabeza == n){
        eliminaPrimero();
      }else if(rabo != n){
        n.anterior.siguiente = n.siguiente;
        n.siguiente.anterior = n.anterior;
        longitud--;
      }else{
        eliminaUltimo();
      }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
      if(esVacia()){
        throw new NoSuchElementException();
      }else if(cabeza == rabo){
        T elemento = cabeza.elemento;
        limpia();
        return elemento;
      }else{
        T elemento = cabeza.elemento;
        cabeza = cabeza.siguiente;
        cabeza.anterior = null;
        longitud--;
        return elemento;
      }
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
      if(esVacia()){
        throw new NoSuchElementException();
      }else if(cabeza == rabo){
        T elemento = rabo.elemento;
        limpia();
        return elemento;
      }else{
        T elemento = rabo.elemento;
        rabo = rabo.anterior;
        rabo.siguiente = null;
        longitud--;
        return elemento;
      }
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
      if(buscaNodo(elemento) == null)
        return false;
      return true;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
      Lista<T> newlist = new Lista<T>();
      Nodo aux = cabeza;
      while(aux != null){
        T elemento = aux.elemento;
        newlist.agregaInicio(elemento);
        aux = aux.siguiente;
      }
      return newlist;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
      Lista<T> newlist = new Lista<T>();
      Nodo aux = cabeza;
      T elemento;
      while(aux != null){
        elemento = aux.elemento;
        newlist.agrega(elemento);
        aux = aux.siguiente;
      }
      return newlist;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
      cabeza = null;
      rabo = null;
      longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
      if(cabeza == null)
        throw new NoSuchElementException();
      return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
      if(cabeza == null)
        throw new NoSuchElementException();
      return rabo.elemento;
    }

    /**
     *Regresa el nodo en la <em>i</em>-ésima posición de la lista.
     *@param i índice de la posición del elemento que queremos.
     *@return nodo en la <em>i</em>-ésima posición.
     */
    private Nodo iNodo(int i){
      Nodo nodo = this.cabeza;
      for(int j = 0; j < i; j++){
           nodo = nodo.siguiente;
      }
      return nodo;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
      if(i < 0 || i >= this.longitud){
        throw new ExcepcionIndiceInvalido();
      }else{
        Nodo aux = iNodo(i);
        return aux.elemento;
      }
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
      Nodo aux = cabeza;
      int i = 0;
      while(aux != null){
        if (aux.elemento.equals(elemento)){
          return i;
        }else{
          aux = aux.siguiente;
          i++;
        }
      }
      return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
      if (this.esVacia())
        return "[]";
      String c = "[";
      Nodo aux = this.cabeza;
      while(aux != this.rabo){
        c += aux.elemento.toString() + ", ";
        aux = aux.siguiente;
      }
      c+= this.rabo.elemento.toString() + "]";
      return c;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        if(this.longitud != lista.getLongitud()){
        return false;
        }else{
          Nodo aux = this.cabeza;
          Nodo auxo = lista.cabeza;
          while(aux != null){
            if(!aux.elemento.equals(auxo.elemento)){
              return false;
            }else{
              aux = aux.siguiente;
              auxo = auxo.siguiente;
            }
          }
          return true;
        }
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    private Lista<T> mezcla(Lista<T> l1, Lista<T> l2, Comparator<T>
    comparador){
      Lista<T> newlist = new Lista<T>();
      Nodo aux1 = l1.cabeza;
      Nodo aux2 = l2.cabeza;
      T elemento;
      while(aux1 != null && aux2 != null){
        if(comparador.compare(aux1.elemento, aux2.elemento) <= 0){
          newlist.agrega(aux1.elemento);
          aux1 = aux1.siguiente;
        }else{
          newlist.agrega(aux2.elemento);
          aux2 = aux2.siguiente;
        }
      }
      while(aux1 != null){
        newlist.agrega(aux1.elemento);
        aux1 = aux1.siguiente;
      }
      while(aux2 != null){
        newlist.agrega(aux2.elemento);
        aux2 = aux2.siguiente;
      }
      return newlist;
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
      Lista<T> newlist = copia();
      if(this.longitud <= 1)
        return newlist;
      Lista<T> l1 = new Lista<T>();
      Lista<T> l2 = new Lista<T>();
      int n = newlist.getLongitud()/2;
      for(int i = 0; i < n; i++){
        l1.agrega(newlist.get(i));
      }
      for(int j = n; j < newlist.getLongitud(); j++){
        l2.agrega(newlist.get(j));
      }
      l1 = l1.mergeSort(comparador);
      l2 = l2.mergeSort(comparador);
      newlist = mezcla(l1,l2,comparador);
      return newlist;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <tt>true</tt> si el elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
      Nodo aux = cabeza;
      while(aux != null){
        if(comparador.compare(elemento, aux.elemento) == 0)
          return true;
          aux = aux.siguiente;
      }
      return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <tt>true</tt> si el elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
