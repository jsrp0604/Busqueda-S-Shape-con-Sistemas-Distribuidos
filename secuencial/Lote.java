import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Lote   {
    public int espacioDisponible;
    public List<Pedido> listaPedidos = new ArrayList<>();
    public LinkedList<Integer> listaItems = new LinkedList<>();
    

    public Lote(int espacioDisponible) {
        this.espacioDisponible = espacioDisponible;
    }

    public void unificarListaItems() {
        for (Pedido pedido : this.listaPedidos) {
            for (int item: pedido.getListaItems())  {
                if (!this.listaItems.contains(item)) {
                    this.listaItems.add(item);
                }
            }
        }

        Collections.sort(this.listaItems);
    }

    public int getEspacioDisponible() {
        return espacioDisponible;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public LinkedList<Integer> getListaItems() {
        return listaItems;
    }

    public void setEspacioDisponible(int espacioDisponible) {
        this.espacioDisponible = espacioDisponible;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }
    
    public void setListaItems(LinkedList<Integer> listaItems) {
        this.listaItems = listaItems;
    }
}