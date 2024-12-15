import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Pedido {
    public int numItems, numPedido;
    public int[] listaItems;
    public LinkedList<Integer> listaLinked = new LinkedList<>();
    
    public Pedido(int numItems, int numPedido) {
        this.numItems = numItems;
        this.numPedido = numPedido;
    }

    public int[] generarListaItems()   {
        Random rand = new Random();
        int randMax = 199; 
        int[] lista = new int[this.numItems];

        for (int i = 0; i < this.numItems; i++) {
            int item = rand.nextInt(randMax);
            lista[i] = item;
        }

        Arrays.sort(lista);
        this.listaItems = lista;

        for (int i : lista) {
            listaLinked.add(i);
        }

        return lista;
    }

    public int getNumItems() {
        return this.numItems;
    }

    public int getNumPedido() {
        return this.numPedido;
    }

    public int[] getListaItems() {
        return this.listaItems;
    }

    public LinkedList<Integer> getListaLinked() {
        return this.listaLinked;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public void setListaItems(int[] listaItems) {
        this.listaItems = listaItems;
    }

    public void setListaLinked(LinkedList<Integer> listaLinked) {
        this.listaLinked = listaLinked;
    }
}
