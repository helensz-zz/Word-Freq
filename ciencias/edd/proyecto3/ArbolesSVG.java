package mx.unam.ciencias.edd.proyecto3;

import java.util.Iterator;
import java.util.Comparator;

import mx.unam.ciencias.edd.*;

/**
 * Clase abstracta que se encargará de hacer el conteo de las palabras más
 * comunes en el archivo recibido y darles un porcentaje que será
 * representado en una gráfica de pastel y en una gráfica de barras.
 */
public class ArbolesSVG {

  /** Comparador para enteros*/
  private Comparador compara = new Comparador();

  /** Constructor vacío. */
  public ArbolesSVG() {}

  /**
   * Recibe el diccionario con los valores que van dentro de los
   * árboles binarios.
   * @param diccionario diccionario del que vamos a sacar los valores de cada llave.
   * @return una lista con los valores de las palabras más repetidas en
   * el diccionario.
   */
  public Lista<Integer> creaLista(Diccionario<String, Integer> diccionario){

    Lista<Integer> list = new Lista<>();

    Iterator<String> it = diccionario.iteradorLlaves();

    int total = getTotal(diccionario);
    float media = getMedia(total, diccionario.getElementos());

    if (diccionario.getElementos() <= 15) {
      for (int i = 0; i < diccionario.getElementos(); i++) {
        String s = it.next();
        int palabras = diccionario.get(s);
        list.agrega(palabras);
      }
    } else {
      for (int i = 0; i < diccionario.getElementos(); i++) {
        String s = it.next();
        int palabras = diccionario.get(s);
        if (palabras > media)
          list.agrega(palabras);
      }
    }

    list = getMayores(list);

    return list;
  }

  /**
   * Crea una lista con los quince elementos más repetidos de una lista.
   * @param lista de valores del diccionario.
   * @return una lista con los quince elementos más repetidos de la lista de
   * valores.
   */
  public Lista<Integer> getMayores(Lista<Integer> lista) {
    Lista<Integer> mayores = new Lista<>();

    lista = lista.mergeSort(compara);

    for (int x : lista)
      mayores.agrega(x);

    while(mayores.getElementos() > 15)
      mayores.eliminaUltimo();

    return mayores;
  }

  /**
   * Obtiene el total de palabras en diccionario.
   * @param diccionario el diccionario del que queremos sacar el total de palabras.
   * @return el entero con el total de palabras.
   */
  public int getTotal(Diccionario<String,Integer> diccionario) {
    Iterator<String> it = diccionario.iteradorLlaves();

    int total = 0;
    int p = 0;
    for (int j = 0; j < diccionario.getElementos(); j++) {
      String s = it.next();
      p = diccionario.get(s);
      total += p;
    }
    return total;
  }

  /**
   * Obtiene el valor de la media de las palabras en un diccionario.
   * @param total el número total de palabras en el diccionario.
   * @param elementos contenidos en el diccionario.
   * @return el total dividido entre el número de elementos.
   */
  public float getMedia(int total, int elementos) {
    return total/elementos;
  }

  /**
   * Genera la cadena correspondiente para nuestros árboles binarios
   * que contienen los valores de las palabras más repetidas.
   * @param almacen el diccionario del que crearemos los árboles.
   * @return la cadena con el código SVG para árboles binarios AVL y Rojinegros.
   */
  public String cadenaArboles(Diccionario<String,Integer> almacen){
    SVGArbolRojinegro ar = new SVGArbolRojinegro(creaLista(almacen));
    SVGArbolAVL avl = new SVGArbolAVL(creaLista(almacen));
    return "<p align=\"center\">" + ar.cadenaSVG() + "</p></br>" +
    "<p align=\"center\">" + avl.cadenaSVGAVL() + "</p></br>";
  }

}
