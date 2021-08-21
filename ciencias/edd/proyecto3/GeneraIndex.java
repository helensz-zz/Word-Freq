package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import mx.unam.ciencias.edd.*;

public class GeneraIndex extends CuerpoHTML{

  /** Constructor vacío. */
  public GeneraIndex() {}

  /**
   *
   */
  public String ligas(String link) {
    String svg = String.format("<p align=\"center\"> <text x=\"100\" " +
    "y=\"200\" dy=\".71em\" \n" +
    "font-size: 14; fill: black; stroke: none\">🦔</text> \n " +
    "<a href=\"%s\"> \n" +
    "<img src=\"🦔\" alt=\"%s\" " +
    "style=\"width:42px;height:42px;border:0\">" +
    "</a></p> </br> \n", link, link);
    return svg;
  }

  /**
   * Crea el contenido que irá dentro del archivo HTML.
   * @param d el diccionario con las palabras y el número de veces que se
   * repite.
   * @param archivo el nombre del archivo recibido.
   * @return la cadena que genera el HTML.
   */
  public String cadenaHTML(Lista<String> archivos){
    String svg = cadenaPrincipal("Index");
    int x = 1;
    for (int i = 0; i < archivos.getElementos(); i++) {
      String liga = String.format("archivo%s.html", x++);
      svg += ligas(liga);
    }
    return svg + cadenaFinal();
  }

  /**
   * Método que genera los archivos HTML con el contenido obtenido de
   * <método>cadenaHTML</método>.
   * @param d el diccionario del cual se debe generar el archivo.
   * @param archivo nombre del archivo recibido.
   * @param directorio donde se generarás los archivos HTML.
   * @param x número del archivo que se está generando.
   */
  public void generaArchivo(Lista<String> archivo, String directorio) {
    try {
      File dir = new File(String.format("%sindex.html", directorio));
      FileWriter fw = new FileWriter(dir);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(cadenaHTML(archivo));
      bw.close();
      fw.close();
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }


}
