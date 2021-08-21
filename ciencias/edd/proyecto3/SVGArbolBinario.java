package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

/**
 * Clase abstracta para dibujar árboles binarios.
 */
public abstract class SVGArbolBinario{

  /** Lista con los elementos a dibujar */
  private Lista<Integer> enteros = new Lista<>();

  /** Primer línea de nuestro código SVG */
  private String firstline = "<?xml version = \'1.0\' encoding = \'utf-8\' ?>\n";

  /** Variable estática que guarda parte del código SVG */
  private static String da = "";

  /**
   * Constructor vacío.
   */
  public SVGArbolBinario(){}

  /**
   * Define de qué tamaño será nuestra representación de la estructura.
   * @param width tamaño horizontal total.
   * @param height tamaño vertical total.
   * @return la línea que define la longitud en código SVG y "<g>".
   */
  public String setLongitud(int width, int height){
    String sndline = String.format("<svg width=\'%d\'" +
    " height=\'%d\'>", width, height);
    return sndline + "\n <g>";
  }

  /**
   * Genera el código SVG para dibujar nuestros vértices para árboles binarios.
   * @param cx posición x del vértice.
   * @param cy posición y del vértice.
   * @return código SVG que dibuja un vértice.
   */
  public String dibujaVertice(int cx, int cy){
    String dv = String.format("\n<circle cx=\'%s\' cy=\'%d\' r=\'20\' " +
    "stroke=\'teal\' stroke-width=\'2\' fill=\'lightseagreen\' />",cx,cy);
    return dv;
  }

  /**
   * Genera el código SVG para dibujar el texto sobre nuestros vértices.
   * @param v vértice que queremos dibujar.
   * @param x posicion x del texto.
   * @param y posicion y del texto.
   * @return código SVG que dibuja el texto sobre el vértice correspondiente.
   */
  public String texto(VerticeArbolBinario v, int x, int y){
    if(v.get().toString().length() == 1)
      x = x - 5;
    else if(v.get().toString().length() == 2)
      x = x - 9;
    else
      x = x - 14;
    String text = String.format("\n<text x=\'%s\' y=\'%s\'" +
    " font-family=\'Verdana\' font-size=\'16\' fill=\'wheat\'>" +
    "%d</text>", x, y + 12, v.get());
    return text;
  }

  /**
   * Genera el código SVG para dibujar las aristas que unen a los vértices
   * de nuestros árboles.
   * @param x1 posicion x del inicio de la arista.
   * @param y1 posicion y del inicio de la arista.
   * @param x2 posicion x del final de la arista.
   * @param y2 posicion y del final de la arista.
   * @return código SVG que dibuja la arista que une un vértice a otro.
   */
  public String dibujaArista(int x1, int y1, int x2, int y2){
    String line = String.format("\n<line x1=\'%d\' y1=\'%d\' x2=\'%d\' " +
    "y2=\'%d\' stroke=\'teal\' stroke-width=\'2\' />",x1,y1,x2,y2);
    return line;
  }

  /**
   * Genera el código SVG para dibujar el árbol con todos los elementos
   * requeridos, y lo hace recursivamente sobre los hijos izquierdo y derecho.
   * @param v vértice que dibujaremos.
   * @param i indice para dibujar los árboles
   * @param y posición y para dibujar los árboles.
   * @param w posición x para dibujar los árboles.
   */
  public void dibujaArbol(VerticeArbolBinario v, int i, int y,int w){
    da += dibujaVertice(w, y+25);
    da += texto(v, w , y+20);
    if(v.hayIzquierdo()){
      int l = (w - i)/2;
      da += dibujaArista(w - 20, y + 20, w - l, y + 55);
      dibujaArbol(v.izquierdo(), i, y + 40, w - l);
    }
    if(v.hayDerecho()){
      int r = (w - i)/2;
      da += dibujaArista(w + 20, y + 20, w + r, y + 50);
      dibujaArbol(v.derecho(), w, y + 40, w + r);
    }
  }

  /**
   * Cadena SVG para escribir el título de la gráfica correspondiente.
   * @param x posición x del título.
   * @param titulo correspondiente a la gráfica utilizada.
   * return código SVG para dibujar el título.
   */
  public String title(int x, String titulo){
    String svg = String.format("<text x=\"%s\" y=\"100\" \n" +
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
   * @return el código SVG que dibuja el árbol con todos sus elementos.
   */
  public String getDibujaArbol(){
    return da;
  }

  /**
   * @return la última línea que requiere el código SVG.
   */
  public String cierre(){
    return "\n </g> \n</svg> \n";
  }

}
