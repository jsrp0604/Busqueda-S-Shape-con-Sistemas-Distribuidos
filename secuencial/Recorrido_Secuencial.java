import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Recorrido_Secuencial
 */
public class Recorrido_Secuencial {
    
    public static Bloque[][] crearMatriz(int filas, int columnas) {

        int[][] matriz = new int[filas][columnas];
        Bloque[][] matrizBloques = new Bloque[filas][columnas];
        
        int x = 0;  // Numero del primer item (en orden de creacion de matriz, de arriba a abajo)
        int nSum = 10;   // Numero que se irá sumando 

        for (int i = 0; i < filas; i++) {
            // Se crea una matriz de enteros como referencia para la matriz de bloques
            // Desde la fila 2 hasta la fila 11 (revisar si se cambian dimensiones del almacén)
            if (i >= 2 && i <= filas - 4)   {
                matriz[i] = new int[]{x, 0, 0, x + nSum, x + nSum * 2, 0, 0, x + nSum * 3, x + nSum * 4, 0, 0, x + nSum * 5, 
                                        x + nSum * 6, 0, 0, x + nSum * 7, x + nSum * 8, 0, 0, x + nSum * 9, x + nSum * 10, 
                                        0, 0, x + nSum * 11, x + nSum * 12,  0, 0, x + nSum * 13, x + nSum * 14, 0, 0, 
                                        x + nSum * 15, x + nSum * 16, 0, 0, x + nSum * 17, x + nSum * 18, 0, 0, x + nSum * 19};   // Llega hasta 190
                x = x + 1;
            }

            for (int j = 0; j < columnas; j++)  
                matrizBloques[i][j] = new Bloque(matriz[i][j]);
        }

        // Posicion de entrada y salida al supermercado
        matrizBloques[filas - 1][1].setNumItem(111);
        matrizBloques[filas - 1][2].setNumItem(111);

        return matrizBloques;

    }
    
    public static void imprimirMatriz(Bloque[][] matrizBloques)    {
        for (Bloque[] fila : matrizBloques) {
            System.out.println(Arrays.toString(fila));
        }
    }

    public static Pedido[] generarPedidos(int numPedidos) {
        Random rand = new Random();
        Pedido[] listaPedidos = new Pedido[numPedidos];
        
        // Rango de items permitidos en el carrito
        int minItems = 5, maxItems = 30;

        for (int i = 0; i < numPedidos; i++) {
            int numItems = rand.nextInt(maxItems - minItems + 1) + minItems;
            Pedido pedido = new Pedido(numItems, i + 1); // i + 1 representa el número del pedido
            listaPedidos[i] = pedido;
            pedido.generarListaItems();
        }   
        
        return listaPedidos;
    }

    public static void crearArchivos(Pedido[] listaPedidos) {
        for (Pedido pedido : listaPedidos) {
            try {
                String nombreArchivo = String.format("Pedido #%d.txt", pedido.getNumPedido());
                File archivo = new File(nombreArchivo);
                if (archivo.createNewFile()) {
                    // Se escribe en cada archivo creado con un Buffered Writer
                    FileWriter writer = new FileWriter(nombreArchivo);
                    BufferedWriter buffWriter = new BufferedWriter(writer);
                    
                    // Se accede a la listaItems de cada pedido para generar el archivo
                    for (int item : pedido.getListaItems()) {
                        buffWriter.write("Item #" + item + "\n");
                    }                    
                    System.out.println("Archivo creado: " + archivo.getName());

                    buffWriter.close();
                    writer.close();
                } else {
                    System.out.println("El archivo ya existe");
                }

            } catch (IOException e) {
            System.out.println("Error en la creación del archivo");
            e.printStackTrace();
            }

        }
    }

    public static void sShape(Pedido p, Bloque[][] matriz) {
        // Mejorar variables para escalabilidad
        int colIzq = 1, colDer = 2;
        int limite = 20; // Num max de item que se puede encontrar en un pasillo 
        int ultItem;
        boolean arribaPasillo = false;
        
        LinkedList<Integer> listaItems = p.getListaLinked();

        // Se recorre el camino inicial (la entrada al almacen)
        // TODO: Agregar variable para caminos iniciales
        matriz[12][1].setNumItem(777);
        matriz[13][1].setNumItem(777);
        
        while (!listaItems.isEmpty())  {
            ultItem = listaItems.pop();
            
            if (ultItem <= limite)   {
                if (listaItems.isEmpty())  // Previene un null pointer exception
                    listaItems.add(ultItem);
             
                if (listaItems.peek() > limite) {
                    // Está en pasillo
                    // No hay más items en pasillo 
                    
                    // Camino hacia arriba/abajo 
                    for (int i = 1; i <= 12; i++) {
                        matriz[i][colIzq].setNumItem(777);
                    }

                    // Se movió arriba/abajo
                    arribaPasillo = !arribaPasillo;
                }
                
                else if (listaItems.getLast() <= limite)    {
                    // Todos los items restantes se encuentran dentro del pasillo 
                    // Mover hasta ult item y regresar
                    
                    // arribaPasillo => Línea continúa hacia abajo y regresa
                    // !arribaPasillo => Línea gira en u y regresa 

                    if (arribaPasillo)  {
                        for (int i = 1; i <= 13; i++) {
                            matriz[i][colIzq].setNumItem(777);
                        }
                        listaItems.clear();
                    } 
                    
                    else  {
                        boolean flag = true;
                        int i = 2;  // Se comienza en la fila más arriba del pasillo
                        listaItems.addFirst(ultItem); // Se añade de vuelta el ult item
                        
                        // Se buscará el item más alejado (más arriba en pasillo)
                        // Se dibujará desde la posición del item más alejado
                        
                        while (flag) {
                            if (listaItems.contains(matriz[i][colIzq - 1].getNumItem())) {
                                for (int x = i; x <= 13; x++) {
                                    matriz[x][colIzq].setNumItem(777);
                                    matriz[x][colDer].setNumItem(777);
                                }
                                listaItems.clear();
                                flag = false;
                            }
                            
                            else if (listaItems.contains(matriz[i][colDer + 1].getNumItem())) {
                                for (int x = i; x <= 13; x++) {
                                    matriz[x][colIzq].setNumItem(777);
                                    matriz[x][colDer].setNumItem(777);
                                }
                                listaItems.clear();
                                flag = false;
                            }

                            i++;
                        }
                    }
                    
                    for (int j = 2; j <= colIzq; j++) { // Se regresa al punto inicial 
                        matriz[13][j].setNumItem(777);
                    }
                }
            } 
            
            else if (ultItem > limite)  {
                // Mover derecha (arriba/abajo)
                if (arribaPasillo)  {
                    for (int i = colIzq; i <= colIzq + 4; i++) {
                        matriz[1][i].setNumItem(777);
                    }
                } else  {
                    for (int i = colIzq; i <= colIzq + 4; i++) {
                        matriz[12][i].setNumItem(777);
                    }
                }
                
                // Devuelvo item a lista
                listaItems.addFirst(ultItem);
                                
                colIzq = colIzq + 4;
                colDer = colDer + 4;
                limite = limite + 20;
            } 
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Definición tamaño matriz del almacén
        int filas = 15, columnas = 40; // Cambiar filas a 14 cuando termine prueba
        Bloque[][] matriz = crearMatriz(filas, columnas);

        Pedido[] listaPedidos = generarPedidos(5);

        crearArchivos(listaPedidos);
        
        // Prueba sShape
        ///////////////////////////////////////////////////////////////////
        
        String listaString = listaPedidos[0].getListaLinked().toString();
        
        sShape(listaPedidos[0], matriz);

        System.out.println("\n");

        System.out.println(listaString + "\n");
        
        imprimirMatriz(matriz);

        ///////////////////////////////////////////////////////////////////

    }
}