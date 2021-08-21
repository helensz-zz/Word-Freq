package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     *Método auxiliar Intercambia
     *@param arreglo el arreglo en el que estamos ordenando.
     *@param i,j índices a intercambiar.
     */
    private static <T> void intercambia(T[] arreglo, int i, int j){
      T elemento = arreglo[i];
      arreglo[i] = arreglo[j];
      arreglo[j] = elemento;
    }

    /**
     *Método auxiliar para quickSort
     *@param arreglo el arreglo a ordenar.
     *@param ini índice al inicio del arreglo.
     *@param fin índice en el final del arreglo.
     *@param comparador compara los elementos del arreglo.
     */
    public static <T> void quickSort(T[] arreglo, int ini, int fin,
    Comparator<T> comparador){
      if(ini >= fin)
        return;
      int i = ini + 1;
      int j = fin;
      while(i < j){
        if(comparador.compare(arreglo[i], arreglo[ini]) > 0 &&
        comparador.compare(arreglo[j], arreglo[ini]) <= 0){
          intercambia(arreglo,i,j);
          i++;
          j--;
        }else if(comparador.compare(arreglo[i], arreglo[ini]) <= 0){
          i++;
        }else{
          j--;
        }
      }
      if(comparador.compare(arreglo[i], arreglo[ini]) > 0)
        i--;
      Arreglos.intercambia(arreglo,ini,i);
      Arreglos.quickSort(arreglo,ini,i-1,comparador);
      Arreglos.quickSort(arreglo,i+1,fin,comparador);

    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
      int ini = 0;
      int i = 0;
      int j = arreglo.length-1;
      quickSort(arreglo,i,j,comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
      for(int i = 0; i < arreglo.length; i++){
        int m = i;
        for(int j = i+1; j < arreglo.length; j++){
          if(comparador.compare(arreglo[j],arreglo[m]) < 0){
            m = j;
          }
        }
        intercambia(arreglo,i,m);
      }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
      int ini = 0;
      int fin = arreglo.length - 1;
      while(ini <= fin){
        int m = ini + (fin-ini) / 2;
        if(comparador.compare(arreglo[m], elemento) < 0)
          ini = m + 1;
        else if(comparador.compare(arreglo[m], elemento) > 0)
          fin = m - 1;
        else
          return m;
      }
      return -1;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
