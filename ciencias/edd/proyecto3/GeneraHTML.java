package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import mx.unam.ciencias.edd.*;

/**
 * Clase que genera los archivos HTML por cada archivo recibido en los
 * argumentos.
 */
public class GeneraHTML extends CuerpoHTML{

  /** Acceso para la cadena que genera las gráficas de barras en SVG */
  GraficaDeBarrasSVG barras = new GraficaDeBarrasSVG();

  /** Acceso para la cadena que genera las gráficas de pastel en SVG */
  GraficaDePastelSVG pastel = new GraficaDePastelSVG();

  /** Acceso para la cadena que genera los árboles binarios en SVG */
  ArbolesSVG a_svg = new ArbolesSVG();

  /**
   * Constructo vacío.
   */
  public GeneraHTML() {}

  /**
   * Crea el contenido que irá dentro del archivo HTML.
   * @param d el diccionario con las palabras y el número de veces que se
   * repite.
   * @param archivo el nombre del archivo recibido.
   * @return la cadena que genera el HTML.
   */
  public String cadenaHTML(Diccionario<String,Integer> d, String archivo){
    return cadenaPrincipal(archivo) + "<p align=\"center\">" +
     barras.barrasSVG(d) + "</p></br>" + "<p align=\"center\">" +
     pastel.pastelSVG(d) + "</p></br>" + a_svg.cadenaArboles(d) + cadenaFinal();
  }

  /**
   * Método que genera los archivos HTML con el contenido obtenido de
   * <método>cadenaHTML</método>.
   * @param d el diccionario del cual se debe generar el archivo.
   * @param archivo nombre del archivo recibido.
   * @param directorio donde se generarás los archivos HTML.
   * @param x número del archivo que se está generando.
   */
  public void generaArchivo(Diccionario<String,Integer> d, String archivo,
                            String directorio, int x) {
    try {
      File dir = new File(String.format("%sarchivo%s.html", directorio, x));
      FileWriter fw = new FileWriter(dir);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(cadenaHTML(d, archivo));
      bw.close();
      fw.close();
    } catch(IOException ioe) {
      ioe.printStackTrace();
    }
  }


}
