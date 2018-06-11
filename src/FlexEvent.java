import java.util.EventObject;
public class FlexEvent extends EventObject {

    private int distancia;
    protected Object objFuente;

    public FlexEvent(Object fuente, int distancia) {
        super(fuente);
        this.distancia = distancia;
    }

    public int getDistancia(){
       return distancia;
    }

    public Object getSource(){
        return objFuente;
    }
}
