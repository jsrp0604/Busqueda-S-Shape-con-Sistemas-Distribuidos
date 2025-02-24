import java.util.ArrayList;
import java.util.List;


public class Lote   {
    public int espacioDisponible;
    public List<Pedido> listaPedidos = new ArrayList<>();

    public Lote(int espacioDisponible) {
        this.espacioDisponible = espacioDisponible;
    }

    public int getEspacioDisponible() {
        return espacioDisponible;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setEspacioDisponible(int espacioDisponible) {
        this.espacioDisponible = espacioDisponible;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }  
}