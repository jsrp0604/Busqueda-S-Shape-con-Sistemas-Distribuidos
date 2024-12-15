public class Bloque {
    public int numItem;
    public boolean enPedido;

    public Bloque(int numItem) {
        this.numItem = numItem;
    }

    public int getNumItem() {
        return numItem;
    }

    public void setNumItem(int numItem) {
        this.numItem = numItem;
    }

    public boolean isEnPedido() {
        return enPedido;
    }

    public void setEnPedido(boolean enPedido) {
        this.enPedido = enPedido;
    }

    @Override
    public String toString() {
        String s = String.format("%03d", numItem);
        return s;
    }
}
