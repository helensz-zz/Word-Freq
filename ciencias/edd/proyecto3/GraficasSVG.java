package mx.unam.ciencias.edd.proyecto3;

import java.util.Iterator;
import mx.unam.ciencias.edd.*;

/**
 * Clase que contiene las líneas que comparten GraficaDeBarrasSVG y
 * GraficaDePastelSVG.
 */
public abstract class GraficasSVG {

  /** Constructor vacío. */
  public GraficasSVG() {}

  /**
   * Crea la primer línea necesaria en los códigos SVG.
   * @param x será la medida para nuestras gráficas.
   * @return la cadena correspondiente a la primer línea de SVG.
   */
  public String setFirstLine(int width, int height){
    return String.format("<svg width=\"%s\" height=\"%s\"" +
    "xmlns=\"http://www.w3.org/2000/svg\">\n", width, height);
  }

  /**
   * Genera la cadena que nos da acceso al tipo de fuente elegida para escribir
   * las llaves y sus respectivos valores .
   * @return la cadena con el código SVG para acceder a la fuente elegida.
   */
  public String fuente(){
    String svg = "<g class> \n" +
    "<defs> \n" +
    " <style> \n" +
    " @import url(\"https://fonts.googleapis.com/css?family=Courgette:400,400i,700,700i\"); \n" +
    "</style> \n" +
    "</defs> \n" +
    "<style><![CDATA[svg text{stroke:none}]]></style> \n";
    return svg;
  }

  /**
   * Cadena SVG para escribir el título de la gráfica correspondiente.
   * @param x posición x del título.
   * @param titulo correspondiente a la gráfica utilizada.
   * return código SVG para dibujar el título.
   */
  public String title(int x, String titulo){
    String svg = String.format("<text x=\"%s\" y=\"50\" \n" +
    	"style=\"font-family:Courgette; \n" +
    			"font-size:100; \n" +
    			"font-weight: bold; \n" +
    			"fill: #FFD700; \n" +
    			"stroke: #DAA520; \n" +
    		"	stroke-width: 1\"> \n" +
    "%s\n" +
    "</text>\n", x, titulo);
    return svg;
  }

  /**
   * Nos devuelve el porcentaje que corresponde a cada palabra en los
   * diccionarios.
   * @param total de palabras en el diccionario.
   * @param p número de repeticiones de una palabra en específico.
   * @return el float que corresponde al porcentaje de las repeticiones de la
   * palabra.
   */
  public float getPorcentaje(int total, int p) {
    return (p * 100)/total;
  }

  /**
   * Suma las repeticiones las palabras dentro de un diccionario para sumarlas
   * y sacar así el total de palabras en un diccionario.
   * @param dicc diccionario del cual queremos el total de palabras.
   * @return el número total de palabras.
   */
  public int getTotal(Diccionario<String,Integer> dicc) {
    Iterator<String> it = dicc.iteradorLlaves();

    int total = 0;
    int p = 0;
    for (int j = 0; j < dicc.getElementos(); j++) {
      String s = it.next();
      p = dicc.get(s);
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
   * Nos da el código SVG correspondiente a las líneas de cierre.
   * @return las dos últimas líneas del código SVG.
   */
  public String ultimaLinea() {
    return "</g> \n" + "</svg>";
  }


}
