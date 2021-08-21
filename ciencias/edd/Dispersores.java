package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
      int r = 0;
      int n = 0;
      int l = llave.length;

      while(l > 3) {
        r ^= combina(llave[n], llave[n+1], llave[n+2], llave[n+3]);
        n += 4;
        l -= 4;
      }

      switch(l) {
        case 1:
          r ^= combina(llave[n], (byte) 0, (byte) 0, (byte) 0);
          break;
        case 2:
          r ^= combina(llave[n], llave[n+1], (byte) 0, (byte) 0);
          break;
        case 3:
          r ^= combina(llave[n], llave[n+1], llave[n+2], (byte) 0);
          break;
      }

      return r;
    }

    /**
     *
     */
    private static int combina(byte a, byte b, byte c, byte d){
      return ( ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8)
            | ((d & 0xFF)) );
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
      int a = 0x9e3779b9;
      int b = 0x9e3779b9;
      int c = 0xffffffff;
      int i = 0;
      int available = llave.length;
      int n = llave.length;
      while(available >= 12) {
          a += (
              (llave[i] & 0xFF) |
              ((llave[i + 1] & 0xFF) << 8) |
              ((llave[i + 2] & 0xFF) << 16) |
              ((llave[i + 3] & 0xFF) << 24)
          );

          b += (
              (llave[i + 4] & 0xFF) |
              ((llave[i + 5] & 0xFF) << 8) |
              ((llave[i + 6] & 0xFF) << 16) |
              ((llave[i + 7] & 0xFF) << 24)
          );

          c += (
              (llave[i + 8] & 0xFF) |
              ((llave[i + 9] & 0xFF) << 8) |
              ((llave[i + 10] & 0xFF) << 16) |
              ((llave[i + 11] & 0xFF) << 24)
          );

          // Mezcla
          int[] r = mezcla(a, b, c);
          a = r[0]; b = r[1]; c = r[2];
          i += 12;
          available -= 12;
      }
      c += n;
      switch (available) {
          case 11:
              c += ((llave[i + 10] & 0xFF) << 24);
          case 10:
              c += ((llave[i + 9] & 0xFF) << 16);
          case 9:
              c += ((llave[i + 8] & 0xFF) << 8);
          case 8:
              b += ((llave[i + 7] & 0xFF) << 24);
          case 7:
              b += ((llave[i + 6] & 0xFF) << 16);
          case 6:
              b += ((llave[i + 5] & 0xFF) << 8);
          case 5:
              b += (llave[i + 4] & 0xFF);
          case 4:
              a += ((llave[i + 3] & 0xFF) << 24);
          case 3:
              a += ((llave[i + 2] & 0xFF) << 16);
          case 2:
              a += ((llave[i + 1] & 0xFF) << 8);
          case 1:
              a += (llave[i] & 0xFF);
      }

      // Uso del método Mezcla.
      int[] r = mezcla(a, b, c);
      return r[2];
    }

    /**
     *
     */
    private static int[] mezcla(int a, int b, int c) {
      int[] r = new int[3];
      a -= b; a -= c; a ^= (c >>> 13);
      b -= c; b -= a; b ^= (a << 8);
      c -= a; c -= b; c ^= (b >>> 13);
      a -= b; a -= c; a ^= (c >>> 12);
      b -= c; b -= a; b ^= (a << 16);
      c -= a; c -= b; c ^= (b >>> 5);
      a -= b; a -= c; a ^= (c >>> 3);
      b -= c; b -= a; b ^= (a << 10);
      c -= a; c -= b; c ^= (b >>> 15);
      r[0] = a;
      r[1] = b;
      r[2] = c;
      return r;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
      int h = 5381;
      for(int i = 0; i < llave.length; i++)
        h += (h << 5) + (llave[i] & 0xFF);
      return h;
    }
}
