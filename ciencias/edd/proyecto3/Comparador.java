package mx.unam.ciencias.edd.proyecto3;

import java.util.Comparator;

/**
 *Clase Comparador que implementa a Comparator para enteros.
 */
public class Comparador implements Comparator<Integer> {

  /**
   * Compara dos enteros.
   */
  @Override public int compare(Integer x, Integer y) {
    if (x > y)
      return -1;
    else if(x < y)
      return 1;
    else
     return 0;
  }

}
