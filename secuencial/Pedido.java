import java.util.LinkedList;
import java.util.Random;

public class Pedido {
    public int numItems, numPedido;
    public LinkedList<Integer> listaItems = new LinkedList<>();
    
    public Pedido(int numItems, int numPedido) {
        this.numItems = numItems;
        this.numPedido = numPedido;
    }

    public LinkedList<Integer> generarListaItems()   {
        Random rand = new Random();
        int randMax = 200;
        int item;
        
        // Evita repetición de items en pedido
        while (this.listaItems.size() < this.numItems) { 
            item = rand.nextInt(randMax);
            if (!this.listaItems.contains(item)) {
                this.listaItems.add(item);
            }
        }

        return this.listaItems;
    }

    public int getNumItems() {
        return this.numItems;
    }

    public int getNumPedido() {
        return this.numPedido;
    }

    public LinkedList<Integer> getListaItems() {
        return this.listaItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public void setListaItems(LinkedList<Integer> listaItems) {
        this.listaItems = listaItems;
    }
}
