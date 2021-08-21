package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

/**
 * Clase que hereda a SVGArbolBinario para dibujar un ArbolAVL.
 */
public class SVGArbolAVL extends SVGArbolBinario{

  /** Contiene a los elementos que vamos a dibujar */
  private ArbolAVL<Integer> enteros;

  /** Vertice que queremos dibujar */
  private VerticeArbolBinario v;

  /** Guarda parte el código SVG para dibujar un ArbolAVL*/
  private String s = "";

  /**
   * Contructor que recibe la lista con los elementos a dibujar.
   * @param enteros de la estructura que vamos a representar.
   */
  public SVGArbolAVL(Lista<Integer> enteros){
    this.enteros = new ArbolAVL<Integer>(enteros);
  }

  /**
   * @return el código SVG correspondiente para dibujar el ArbolAVL
   * con los elementos recibidos del usuario.
   */
  public String cadenaSVGAVL(){
    int width = enteros.getElementos() * 80;
    int height = enteros.altura() * 80 + 100;
    String svg = "";
    svg += setLongitud(width + 18, height);
    v = enteros.raiz();
    svg += title((width/2)-45, "Árbol \n AVL");
    dibujaArbolAVL(v, 0,0, width/2);
    svg += s;
    svg += cierre();
    return svg;
  }

  /**
   * Empezamos por dibujar la raíz del árbol y su balance.
   * Generamos el código SVG si tiene hijo izquiero o si tiene hijo derecho
   * recursivamente.
   */
  public void dibujaArbolAVL(VerticeArbolBinario v, int i, int y, int w){
    s += dibujaVertice(w, y + 25);
    s += alturaBalance(v, w, y + 20);
    if(v.hayIzquierdo()){
        int wi = (w - i)/2;
        s += dibujaArista(w-20,y+20,w-wi,y+55);
        dibujaArbolAVL(v.izquierdo(), i, y+40, w-wi);
    }
    if(v.hayDerecho()){
        int wd = (w - i)/2;
        s += dibujaArista(w+20,y+20,w+wd,y+50);
        dibujaArbolAVL(v.derecho(), w, y+40, w+wd);
    }
  }

  /**
   * @param v vértice que queremos dibujar.
   * @param x coordenada x del texto
   * @param y coordenada y del texto
   * @return el código SVG para dibujar el elemento sobre el vértice
   * y el balance del vértice a un costado.
   */
  public String alturaBalance(VerticeArbolBinario v, int x, int y){
    if(v.get().toString().length() == 1)
      x = x - 5;
    else if(v.get().toString().length() == 2)
      x = x - 9;
    else
      x = x - 14;

    String ab = String.format("<text x=\"%d\" y=\"%d\" font-family=\"sans-serif\""
    + " font-size=\"18\" fill=\"wheat\"" +
    ">%s</text>\n",x,y + 12,v.get().toString());

    int i = 0;
    if(v.get().toString().length() <= 2)
      i = 2;
    else if(v.get().toString().length() >= 3)
      i = 3;

    ab += String.format("<text x=\"%d\" y=\"%d\" font-family=\"sans-serif\""
    + " font-size=\"12\" fill=\"teal\"" +
    ">%s</text>\n",x + 35, y, v.toString().substring(i,v.toString().length()));

    return ab;
  }

}
