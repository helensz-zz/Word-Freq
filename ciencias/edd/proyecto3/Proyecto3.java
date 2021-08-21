package mx.unam.ciencias.edd.proyecto3;

/**
 * Este programa se encarga de leer archivos, genera un HTML por cada
 * archivo recibido, dentro del cuál hallarás una gráfica de barras, una
 * gráfica de pastel, un árbol binario rojinegro y un árbol binario AVL
 * que contiene los datos de cuántas veces se repite una misma palabra dentro
 * del archivo. Tomaremos como una misma palabra a todas aquellas palabras
 * que se escriban exactamente igual aunque tengan acentos o las mayúsculas y
 * minúsculas sean inconsistentes. Además se generará un index HTML con
 * una gráfica que nos indique qué archivos comparten palabras en común y
 * las ligas a los archivos generados.
 *
 * Clase que contiene al main.
 *
 * Proyecto 3 de la Asignatura Estructuras de Datos.
 * @author Helen Michelle Salazar Zaragoza.
 */
public class Proyecto3{

  public static void main(String[] args) {

    Entrada app = new Entrada();

    app.recibe(args);

  }

}
