package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

/**
 * Clase que hereda a SVGArbolBinario para dibujar un ArbolRojinegro.
 */
public class SVGArbolRojinegro extends SVGArbolBinario{

  /** Contiene a los elementos que vamos a dibujar */
  private ArbolRojinegro<Integer> enteros;

  /** Vertice que queremos dibujar */
  private VerticeArbolBinario v;

  /** Guarda parte el código SVG para dibujar un ArbolRojinegro*/
  private String da = "";

  /**
   * Contructor que recibe la lista con los elementos a dibujar.
   * @param enteros de la estructura que vamos a representar.
   */
  public SVGArbolRojinegro(Lista<Integer> enteros){
    this.enteros = new ArbolRojinegro<Integer>(enteros);
  }

  /**
   * @return el código SVG correspondiente para dibujar el ArbolRojinegro
   * con los elementos recibidos del usuario.
   */
  public String cadenaSVG(){
    int width = enteros.getElementos() * 80;
    int height = enteros.altura() * 80 + 100;
    String svg = "";
    svg += setLongitud(width + 15, height);
    v = enteros.raiz();
    svg += title((width/2)-45, "Árbol \n Rojinegro");
    dibujaArbolRojinegro(v, 0, 0, width/2);
    svg += da;
    svg += cierre();
    return svg;
  }

  /**
   * Dado que en los Rojinegros tenemos colores, necesitamos saber de qué color
   * debe ir cada vértice. Genera el código SVG para dibujar el árbol con
   * todos los elementos requeridos y lo gace recursivamente sobre los hijos
   * izquierdo y derecho.
   * @param v vértice que dibujaremos.
   * @param i indice para dibujar los árboles.
   * @param y posición y para dibujar los árboles.
   * @param w posición x para dibujar los árboles.
   */
  public void dibujaArbolRojinegro(VerticeArbolBinario v, int i, int y,int w){
    if(v.toString().substring(0,1).equals("R"))
      da += verticeRojo(w, y + 25);
    if(v.toString().substring(0,1).equals("N"))
      da += verticeNegro(w, y + 25);
    da += texto(v, w , y+20);
    if(v.hayIzquierdo()){
      int l = (w - i)/2;
      da += dibujaAristaRojinegro(w - 20, y + 20, w - l, y + 55);
      dibujaArbolRojinegro(v.izquierdo(), i, y + 40, w - l);
    }
    if(v.hayDerecho()){
      int r = (w - i)/2;
      da += dibujaAristaRojinegro(w + 20, y + 20, w + r, y + 50);
      dibujaArbolRojinegro(v.derecho(), w, y + 40, w + r);
    }
  }

  /**
   * Genera el código SVG para dibujar un vértice de color Rojo.
   * @param cx posición x del vértice.
   * @param cy posición y del vértice.
   * @return código SVG que dibuja un vértice rojo.
   */
  public String verticeRojo(int cx, int cy){
    String dv = String.format("\n<circle cx=\'%s\' cy=\'%d\' r=\'20\' " +
    "stroke=\'khaki\' stroke-width=\'2\' fill=\'firebrick\' />",cx,cy);
    return dv;
  }

  /**
  * Genera el código SVG para dibujar un vértice de color Negro.
  * @param cx posición x del vértice.
  * @param cy posición y del vértice.
  * @return código SVG que dibuja un vértice negro.
  */
  public String verticeNegro(int cx, int cy){
    String dv = String.format("\n<circle cx=\'%s\' cy=\'%d\' r=\'20\' " +
    "stroke=\'khaki\' stroke-width=\'2\' fill=\'black\' />",cx,cy);
    return dv;
  }

  /**
   * Genera el código SVG para dibujar un arista de distinto color a las
   * aristas de SVGArbolBinario.
   * @param x1 posicion x del inicio de la arista.
   * @param y1 posicion y del inicio de la arista.
   * @param x2 posicion x del final de la arista.
   * @param y2 posicion y del final de la arista.
   * @return código SVG que dibuja la arista que une un vértice a otro.
   */
  public String dibujaAristaRojinegro(int x1, int y1, int x2, int y2){
    String line = String.format("\n<line x1=\'%d\' y1=\'%d\' x2=\'%d\' " +
    "y2=\'%d\' stroke=\'khaki\' stroke-width=\'2\' />",x1,y1,x2,y2);
    return line;
  }


}
