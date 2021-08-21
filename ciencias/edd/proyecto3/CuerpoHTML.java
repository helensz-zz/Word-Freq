package mx.unam.ciencias.edd.proyecto3;


/**
 * Clase abstracta que genera el encabezado y la cadena final de los
 * archivos HTML.
 */
public abstract class CuerpoHTML{

  /** Nombre del archivo recibido. */
  private String archivoRecibido;

  /**
   * Constructor vacío.
   */
  public CuerpoHTML() {}

  /**
   * Genera el encabezado de los archivos HTML.
   * @param archivoRecibido nombre del archivo recibido.
   * @return la cadena que corresponde al encabezado de los archivos HTML.
   */
  public String cadenaPrincipal(String archivoRecibido){
    String cadena = String.format("<!DOCTYPE html> \n" +
    "<html> \n" +
    "   <head> \n" +
    "     <meta charset=\"utf-8\"> \n" +
    "      <title> \n" +
    "           %s \n" +
    "      </title> \n" +
    "   </head> \n" +
    "   <body bgcolor=\"#FFDAB9\"> \n", archivoRecibido);
    return cadena;
  }

  /**
   * Genera las líneas que cierran los archivos HTML.
   * @return la cadena que cierra los archivos HTML.
   */
  public String cadenaFinal(){
    String cadena = "   </body> \n" + "</html>";
    return cadena;
  }

}
