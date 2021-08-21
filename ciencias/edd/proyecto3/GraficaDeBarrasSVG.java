package mx.unam.ciencias.edd.proyecto3;

import java.util.Iterator;
import mx.unam.ciencias.edd.*;

/**
 * Clase que extiende a ConteoDePalabras que contiene los datos
 * de cuáles son las palabras más comunes en el archivo y qué porcentaje del
 * total de palabras ocupan.
 */
public class GraficaDeBarrasSVG extends GraficasSVG{

  /** Constructor vacío*/
  public GraficaDeBarrasSVG() {
  }

  /**
   * Se encarga de rotar el texto y colocarlo en la posición que debe ir.
   * @param x posición x del texto.
   * @param y posición y del texto.
   * @param llave que se va a imprimir.
   * @param porcentaje de las repeticiones de la llave recibida.
   * @return el código SVG que dibujará nuestras palabras con su porcentaje.
   */
  public String texto(int x, int y, String llave, float porcentaje){
    String svg = String.format("<path class=\"domain\"></path> \n" +
    "<text font-family=\"Courgette\" x=\"%s\" y=\"%s\" dy=\".71em\"" +
    "transform=\"rotate(-90)\" style=\"text-anchor: end;\">%s: %s%%</text> \n",
    x, y, llave, porcentaje);
    return svg;
  }

  /**
   * Dibuja una barra de la gráfica de Barras.
   * @param x posición x de la gráfica.
   * @param y posición y de la gráfica.
   * @param height determina la altura de la barra de acuerdo al porcentaje
   * recibido.
   * @return el código SVG que dibuja una barra.
   */
  public String barras(int x, int y, int height){
    String svg = String.format("" +
    " <rect class=\"bar\" x=\"%s\" y=\"%s\" width=\"13\" height=\"%s\"" +
    "style=\"fill:teal;\"></rect> \n",
     x, y, height);
    return svg;
  }

  /**
   * Genera todo el código SVG para dibujar nuestra gráfica de Barras.
   * @param diccionario del que generaremos la gráfica de barras.
   * @return el código SVG para la gráfica de Barras.
   */
  public String barrasSVG(Diccionario<String, Integer> diccionario){

    Iterator<String> it = diccionario.iteradorLlaves();

    int distancia = 0;
    int x = 0;
    int y = 200;
    if (diccionario.getElementos() > 20) {
      distancia = diccionario.getElementos()*15;
      x = distancia/diccionario.getElementos() * 2;
    } else {
      distancia = diccionario.getElementos()*100;
      x = distancia/diccionario.getElementos() * 2;
    }

    int total = getTotal(diccionario);

    String fl = setFirstLine(distancia, 400) + fuente() +
                title(distancia/2 - 40, "Gráfica de Barras");
    String b = "";

    for (int i = 0; i < diccionario.getElementos(); i++) {
      String s = it.next();
      int height = diccionario.get(s);
      int aux = y - (height*10);
      b += barras(x, aux , height*10);
      b += texto(-215, x, s, getPorcentaje(total, height));
      x += 15;
    }

    // int aux = y - (getTotal(otros)*10);
    // b += barras(x, aux, getTotal(otros)*10);
    // b += texto(-215, x, "otros", getPorcentaje(total, getTotal(otros)));

    return fl + b  + ultimaLinea();
  }

}
