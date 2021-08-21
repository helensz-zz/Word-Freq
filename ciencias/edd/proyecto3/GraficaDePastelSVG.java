package mx.unam.ciencias.edd.proyecto3;

import java.util.Iterator;
import java.util.Random;
import mx.unam.ciencias.edd.*;

/**
 * Clase que extiende a ConteoDePalabras que contiene los datos
 * de cuáles son las palabras más comunes en el archivo y qué porcentaje del
 * total de palabras ocupan.
 */
public class GraficaDePastelSVG extends GraficasSVG{

  /**
   * Se encarga de dibujar un rectángulo con el color correspondiente a
   * la rebanada de la gráfica y también de escribir a que palabra
   * corresponde dicho color.
   * @param y posición y del rectángulo.
   * @param y_text posición y del texto.
   * @param color del que será el rectángulo.
   * @param llave que será la palabra a la que corresponde dicho color.
   * @param valor correspondiente a la llave.
   */
  public String texto(int y, int y_text, String color, String llave, int valor){
    String svg = String.format("<rect x=\"540\" y=\"%s\" width=\"30\" height=\"20\" \n" +
	   "style=\"stroke-linejoin: mitre; \n" +
  		"stroke-width: 2; stroke: black; fill: %s\"/> \n" +
    "<text font-family=\"Courgette\" x=\"580\" y=\"%s\" dy=\".71em\" \n" +
    "font-size: 14; fill: black; stroke: none\"> \n" + "%s: %s repeticiones" +
    "</text>", y, color, y_text, llave, valor);
    return svg;
  }

  /**
   * Genera la cadena SVG correspondiente para dibujarle sombra a la
   * gráfica de pastel.
   * @return la cadena SVG que dibuja una elipse.
   */
  public String elipse(){
    String cad = "<defs> \n" +
    "<filter id=\"dropshadow\" width=\"120%\" height=\"120%\"> \n" +
    "<feGaussianBlur stdDeviation=\"4\"/> \n" +
    "</filter> \n" +
    "</defs> \n" +
    "<ellipse cx=\"205\" cy=\"245\" rx=\"180\" ry=\"120\" \n" +
    	"style=\"fill: black; \n" +
    		"fill-opacity:0.6; \n" +
    		"stroke:none; \n" +
    	"filter:url(#dropshadow)\"/> \n";
    return cad;
  }

  /**
   * Genera la cadena correspondiente para dibujar una rebanada de la
   * gráfica de pastel.
   * @param l1 parámetro para generar la rebanada.
   * @param l2 parámetro para generar la rebanada.
   * @param a parámetro para generar la rebanada.
   * @param b parámetro para generar la rebanada.
   * @param color del que será dicha rebanada.
   * @return la cadena SVG que dibujará la rebanada.
   */
  public String rebanadas(double l1, double l2, double a, double b, String color) {
    String svg = String.format("<path d=\"M200,240 L%s,%s A180,120 0 0,1 %s,%s Z\" \n" +
  	 "style=\"fill: %s; stroke: black; stroke-width: 2\"/>", l1, l2, a, b ,color);
    return svg;
  }

  /**
   * Nos regresa un diccionario nuevo con las palabras frecuentes del
   * diccionario original.
   * @param diccionario del archivo completo.
   * @return el diccionario con las palabras más repetidas.
   */
  public Diccionario<String,Integer> getDiccionario(Diccionario<String,Integer>
                                                    diccionario) {
    Diccionario<String,Integer> diccionario_aux = new Diccionario<>();

    Iterator<String> it = diccionario.iteradorLlaves();

    int total = getTotal(diccionario);
    float media = getMedia(total, diccionario.getElementos());

    for (int i = 0; i < diccionario.getElementos(); i++) {
      String s = it.next();
      int palabras = diccionario.get(s);
      if (palabras > media)
        diccionario_aux.agrega(s, palabras);
    }

    return diccionario_aux;
  }

  /**
   * Genera colores aleatorios para la gráfica de Pastel mediante una
   * función Random.
   * @return una cadena con <símbolo>#</símbolo> y el número hexadecimal
   * que genera un color.
   */
  public String getRandomColor() {
    final Random random = new Random();
    final String[] letters = "0123456789ABCDEF".split("");
    String color = "#";
    for (int i = 0; i < 6; i++) {
        color += letters[Math.round(random.nextFloat() * 15)];
    }
    return color;
  }

  /**
   * Nos regresa un diccionario nuevo con las palabras menos frecuentes del
   * diccionario original.
   * @param diccionario del archivo completo.
   * @return el diccionario con las palabras menos repetidas.
   */
  public Diccionario<String,Integer> getDiccionarioOtros(Diccionario<String,Integer>
                                                    diccionario) {
    Diccionario<String,Integer> diccionario_otros = new Diccionario<>();

    Iterator<String> it = diccionario.iteradorLlaves();

    int total = getTotal(diccionario);
    float media = getMedia(total, diccionario.getElementos());

    for (int i = 0; i < diccionario.getElementos(); i++) {
      String s = it.next();
      int palabras = diccionario.get(s);
      if (palabras <= media)
        diccionario_otros.agrega(s, palabras);
    }

    return diccionario_otros;
  }

  /**
   * Genera todo el código SVG para dibujar nuestra gráfica de Pastel.
   * @param diccionario del que generaremos la gráfica de pastel.
   * @return el código SVG para la gráfica de Pastel.
   */
  public String pastelSVG(Diccionario<String, Integer> diccionario) {
    diccionario = getDiccionario(diccionario);

    Diccionario<String,Integer> otros = getDiccionarioOtros(diccionario);

    Iterator<String> it = diccionario.iteradorLlaves();

    int distancia = 800;
    String fl = setFirstLine(distancia, 600) + fuente() +
                  title(distancia/2 - 40, "Gráfica de Pastel");

    String svg = "";
    String color = getRandomColor();

    int y = 90;
    int y_text = 93;
    double l1 = 40.0, l2 = 290.0;
    double a = 290.0, b = 136.08;

    svg += elipse();

    for (int i = 0; i < diccionario.getElementos(); i++) {
      String c = getRandomColor();
      String s = it.next();
      int v = diccionario.get(s);
      svg += texto(y, y_text, c, s, v);

      svg += rebanadas(l1,l2,a,b,color);

      y_text += 23;
      y += 23;
    }

    svg += texto(y, y_text, color, "Otros", getTotal(otros));

    return fl + svg + ultimaLinea();

  }

}
