package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.text.Normalizer;

import mx.unam.ciencias.edd.*;

/**
 * Clase que se encarga de solicitar las instrucciones correspondientes
 * para la elaboración del proyecto. En esta clase recae todo el
 * trabajo realizado.
 */
public class Entrada{

  /** Directorio donde se generarán los archivos HTML */
  private static String directorio;

  /** Lista que guarda los archivos recibidos en los argumentos. */
  private static Lista<String> archivosRecibidos = new Lista<>();

  /** */
  private ArbolesSVG arboles_svg = new ArbolesSVG();

  /** Constructor vacío */
  public Entrada() {}

  /**
   * Método que se encarga de leer la entrada de argumentos. Arroja un
   * error si no se encuentra la bandera <flag> -o </flag> o si no recibe
   * un directorio después.
   * @param args
   */
  public void recibe(String[] args) {
    boolean o = false;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-o")) {
        o = true;
        if (i + 1 < args.length) {
          directorio = args[i + 1];
          i += 1;
        } else {
          System.err.println("Necesita ingresar un directorio seguido de la " +
          "bandera -o");
          System.exit(1);
        }
      } else {
        archivosRecibidos.agrega(args[i]);
      }
    }
    if (!o) {
      System.err.println("Necesita ingresar un directorio seguido de la " +
      "bandera -o. -> \"-o /path/\"");
      System.exit(1);
    }

    lectura(archivosRecibidos);
    
  }

  /**
   * Lee los archivos que están en la lista de archivosRecibidos y se encarga
   * de crear un diccionario por cada archivo.
   * @param l lista de archivosRecibidos.
   */
  public void lectura(Lista<String> l) {
    int x = 1;

    GeneraIndex gi = new GeneraIndex();

    for (String archivo : l) {

      Diccionario<String, Integer> almacen = new Diccionario<>();

      GeneraHTML body = new GeneraHTML();

      try {

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String input;

        while ((input = br.readLine()) != null) {

          input = transformaCadena(input);

          String[] arr = input.split("\\p{P}");

          for (String s : arr)
            if (!s.equals("")) {
              if (almacen.contiene(s)) {
                int i = (Integer)almacen.get(s);
                almacen.agrega(s, ++i);
              } else {
                int j = 0;
                almacen.agrega(s, ++j);
              }
            }
        }

        br.close();
      } catch(FileNotFoundException e) {
        System.err.println("No se encontró alguno de los archivos ingresados.");
        System.exit(1);
      } catch(IOException ioe) {
        System.err.println(String.format("Error al leer %s", archivo));
        System.exit(1);
      }

      body.generaArchivo(almacen, archivo, directorio, x++);

    }

    gi.generaArchivo(l, directorio);

  }

  /**
   * Transforma las palabras de los archivos para quitarles acentos, pasarlas
   * todas a minúsculas, y en cada espacio se agrega un punto y coma para
   * indicar la separación de palaras.
   * @param cad la cadena a transformar.
   * @return la cadena transformada.
   */
  public String transformaCadena(String cad) {
    cad = cad.trim().toLowerCase().replace(" ", ";").replaceAll("\\p{P}",";");
    cad = Normalizer.normalize(cad, Normalizer.Form.NFD);
    cad = cad.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    return cad;
  }



}
