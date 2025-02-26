import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
// import com.opencsv.CSVWriter;

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

    public static List<Pedido> generarPedidos(int numPedidos) {
        Random rand = new Random();
        List<Pedido> listaPedidos = new ArrayList<>();
        
        // Rango de items permitidos en el carrito
        int minItems = 5, maxItems = 30;

        for (int i = 0; i < numPedidos; i++) {
            int numItems = rand.nextInt(maxItems - minItems + 1) + minItems;
            Pedido pedido = new Pedido(numItems, i + 1); // i + 1 representa el número del pedido
            listaPedidos.add(pedido);
            pedido.generarListaItems();
        }   
        
        return listaPedidos;
    }

    public static List<Lote> generarLotes(List<Pedido> listaPedidos, int numMaxItems)    {
    
        List<Lote> lotesDisponibles = new ArrayList<>();
        List<Lote> lotesFinales = new ArrayList<>();       
        
        for (Pedido pedido : listaPedidos) {

            // Revisa si hay un lote con espacio para el pedido
            // De ser asi se agrega el pedido al lote y se disminuye el espacio disponible
            for (Lote lote : lotesDisponibles) {
                if (pedido.getNumItems() <= lote.getEspacioDisponible()) {
                    lote.getListaPedidos().add(pedido);
                    pedido.setEnLote(true);
                    
                    lote.setEspacioDisponible(lote.getEspacioDisponible() - pedido.getNumItems());
                    
                    if (lote.getEspacioDisponible() < 5)  { // 5 es el numero minimo de items en un pedido
                        lotesDisponibles.remove(lote);
                        lotesFinales.add(lote);
                        
                        // Se unifica la lista de items en el lote 
                        lote.unificarListaItems(); 
                    }

                    break;
                }
            }

            if (!pedido.isEnLote()) {
                // Crea un lote con el num max items dado previamente
                // Se agrega el pedido y reduce el espacio disponible del lote
                Lote lote = new Lote(numMaxItems);
                lote.getListaPedidos().add(pedido);
                pedido.setEnLote(true);

                lote.setEspacioDisponible(lote.getEspacioDisponible() - pedido.getNumItems());
                lotesDisponibles.add(lote);
            }
        }

        // Se agregan los lotes con espacio disponible a los lotes finales
        for (Lote loteDisponible : lotesDisponibles) {
            lotesFinales.add(loteDisponible);
            
            // Se unifica la lista de items en el lote 
            loteDisponible.unificarListaItems();
        }

        return lotesFinales;

    }

    public static void imprimirLotes(List<Lote> listaLotes) {
        System.out.println("Lotes:\n\n");
        int i = 1;
        for (Lote lote : listaLotes) {
            System.out.println("Lote #" + i);
            
            for (Pedido pedido : lote.getListaPedidos()) {
                System.out.println(pedido);
            }
            
            System.out.println("Espacio Disponible: " + lote.getEspacioDisponible());
            System.out.println("Lista Items: " + lote.getListaItems().toString());
            System.out.println("\n");
            
            i++;
        }
    }

    public static void crearArchivos(List<Pedido> listaPedidos) {
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

    public static Object[] sShape(LinkedList<Integer> listaItems, Bloque[][] matriz) {
        // Mejorar variables para escalabilidad
        int colIzq = 1, colDer = 2;
        int limite = 19; // Num max de item que se puede encontrar en un pasillo 
        int ultItem;
        boolean arribaPasillo = false;
        int distanciaRecorrida = 0;
        
        // Se elimina esta linea; se recibe directamente la listaitems como argumento
        // LinkedList<Integer> listaItems = p.getListaItems();
        

        // Se recorre el camino inicial (la entrada al almacen)
        // TODO: Agregar variable para caminos iniciales (por definir si se agrega a distancia)
        // matriz[12][1].setNumItem(777); Se comenta para no afectar a distancia
        matriz[13][1].setNumItem(777);
        distanciaRecorrida++;

        // Inicia tiempo 
        long tiempoInicio = System.nanoTime();
        
        while (!listaItems.isEmpty())  {
            ultItem = listaItems.pop();
            
            if (ultItem <= limite)   {
                if (listaItems.isEmpty())  // Previene un null pointer exception
                    listaItems.add(ultItem);
             
                if (listaItems.peek() > limite) {
                    // Está en pasillo
                    // No hay más items en pasillo 
                    
                    if (colIzq == 1)    {
                        matriz[12][1].setNumItem(777); // Si es la primera columna se marca el paso inicial
                        distanciaRecorrida++;
                    } 
                    

                    // Camino hacia arriba/abajo 
                    for (int i = 2; i <= 11; i++) {                 // Se empieza en fila 2 y se termina en la 11 para
                        matriz[i][colIzq].setNumItem(777);          // no afectar la distancia recorrida
                        distanciaRecorrida++;
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
                        for (int i = 2; i <= 13; i++) {              // Se empieza en fila 2 pero se termina en la 13 
                            matriz[i][colIzq].setNumItem(777);       // dado que es el ultimo pasillo
                            distanciaRecorrida++;
                        }
                        listaItems.clear();
                    } 
                    
                    else  {
                        boolean flag = true;
                        int i = 2;  // Se comienza en la fila más arriba del pasillo
                        listaItems.addFirst(ultItem); // Se añade de vuelta el ult item

                        // Se elimina un paso del camino avanzado horizontalmente (para no afectar distancia recorrida)
                        distanciaRecorrida--;
                        
                        // Se buscará el item más alejado (más arriba en pasillo)
                        // Se dibujará desde la posición del item más alejado
                        
                        while (flag) {
                            if (listaItems.contains(matriz[i][colIzq - 1].getNumItem())) {
                                for (int x = i; x <= 13; x++) {                     // A diferencia de los otros pasillos, en el ultimo,
                                    matriz[x][colIzq].setNumItem(777);              // se recorrera hasta la fila 13 (la ultima),
                                    matriz[x][colDer].setNumItem(777);              // ya que se recorren 2 columnas
                                    distanciaRecorrida = distanciaRecorrida + 2;
                                }
                                listaItems.clear();
                                flag = false;
                            }
                            
                            else if (listaItems.contains(matriz[i][colDer + 1].getNumItem())) {
                                for (int x = i; x <= 13; x++) {
                                    matriz[x][colIzq].setNumItem(777);
                                    matriz[x][colDer].setNumItem(777);
                                    distanciaRecorrida = distanciaRecorrida + 2;
                                }
                                listaItems.clear();
                                flag = false;
                            }

                            i++;
                        }
                    }
                    
                    for (int j = 2; j <= colIzq - 1; j++) {       
                        if (matriz[13][j].getNumItem() != 777)  {       // Si está recorrido, no se marca ni se aumenta distancia
                            matriz[13][j].setNumItem(777);          
                            distanciaRecorrida++;
                        }                        
                    }

                }
            } 
            
            else if (ultItem > limite)  {
                // Mover derecha (arriba/abajo)
                
                if (arribaPasillo)  {
                    for (int i = colIzq; i <= colIzq + 4; i++) { 
                        if (matriz[1][i].getNumItem() != 777)    {              // Si está recorrido, no se marca ni se aumenta distancia
                            matriz[1][i].setNumItem(777);                
                            distanciaRecorrida++;   
                        }
                    }

                } else  {
                    for (int i = colIzq; i <= colIzq + 4; i++) { 
                        if (matriz[12][i].getNumItem() != 777)    {             // Si está recorrido, no se marca ni se aumenta distancia
                            matriz[12][i].setNumItem(777);                
                            distanciaRecorrida++;   
                        }
                    }
                }
                
                // Devuelvo item a lista
                listaItems.addFirst(ultItem);
                                
                colIzq = colIzq + 4;
                colDer = colDer + 4;
                limite = limite + 20;
            } 
        }

        // Tiempo final y cálculo tiempo total de ejecución
        long tiempoFin = System.nanoTime();
        long tiempoTotal = tiempoFin - tiempoInicio;

        // Se retorna un Array de Distancia y Tiempo total 
        Object[] resultados = new Object[]{distanciaRecorrida, tiempoTotal};

        return resultados;
    }

    public static void main(String[] args) throws InterruptedException {
        // Definición tamaño matriz del almacén
        int filas = 15, columnas = 40; // Cambiar filas a 14 cuando termine prueba
        int numPedidos = 1000;
        int espacioLote = 70;

        List<Pedido> listaPedidos = generarPedidos(numPedidos);

        // Prueba creacion de Lotes

        List<Lote> listaLotes = generarLotes(listaPedidos, espacioLote);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Recorrido Lotes

        int counterLote = 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("lotes.csv"))) {
            // String[] header = { "Número Lote", "Capacidad Carrito", "Distancia Recorrida", "Tiempo Total" };
            
            writer.append("Numero_Lote,Capacidad_Restante,Distancia_Recorrida,Tiempo_Total\n"); 

            
            for (Lote lote : listaLotes) {
                // Se crea una nueva matriz por cada lote
                Bloque[][] matriz = crearMatriz(filas, columnas);
                String listaString = lote.getListaItems().toString();

                Object[] resultadosLote = sShape(lote.getListaItems(), matriz);

                String strNumLote = String.valueOf(counterLote);
                String strEspacio = String.valueOf(lote.getEspacioDisponible());
                String strDistancia = resultadosLote[0].toString();
                String strTiempo = resultadosLote[1].toString();

                writer.append(strNumLote + "," + strEspacio + "," + strDistancia + "," + strTiempo + "\n");

                System.out.println("\n" + listaString + "\n");
                imprimirMatriz(matriz);

                System.out.println("\nResultados Finales:\n");
                System.out.println("Array Conjunto: " + Arrays.toString(resultadosLote));
                System.out.println("Distancia Recorrida: " + resultadosLote[0].toString());
                System.out.println("Tiempo de Ejecución: " + resultadosLote[1].toString());
                
                counterLote++;
            }
        }

        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        }

        ///////////////////////////////////////////////////////////////////
        
        // Prueba sShape

        
        // String listaString = listaLotes.get(1).getListaItems().toString();
        
        // Object[] resultados = sShape(listaLotes.get(1).getListaItems(), matriz);

        // System.out.println("\n" + listaString + "\n");
        // imprimirMatriz(matriz);

        // System.out.println("\nResultados Finales:\n");
        // System.out.println("Array Conjunto: " + Arrays.toString(resultados));
        // System.out.println("Distancia Recorrida: " + resultados[0].toString());
        // System.out.println("Tiempo de Ejecución: " + resultados[1].toString());
        
        ///////////////////////////////////////////////////////////////////
    }
}