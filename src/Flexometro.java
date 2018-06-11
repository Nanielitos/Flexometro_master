import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

public class Flexometro extends JComponent implements Runnable,
        MouseListener, Serializable {

    private Vector<Puntos> vectorPuntos;
    private Vector<Enlace> vectorEnlace;
    //vector para los listeners
    private Vector flexListeners=new Vector();
    private Point p1, p2;
    private Thread unHilo;
    private int medida;
    private boolean mouseClicked;
    private int distancia;
    public ConexionBD interfazMysql;
    Connection bdCon;

    public synchronized void addFlexListener(FlexListener listener){
        flexListeners.addElement(listener);
    }

    public synchronized void removFlexListener(FlexListener listener){
        flexListeners.removeElement(listener);
    }

    public Flexometro(){
        this.vectorPuntos = new Vector<>();
        this.vectorEnlace = new Vector<>();
        enableInputMethods(true);
        addMouseListener(this);
        mouseClicked=false;
    }


    public void calcularDistancia(int medida) {
        this.medida = medida;

        //Crear el evento
        FlexEvent evento = new FlexEvent(this, medida);

        //Llamar a método que ejecuta los métodos controladores de los listeners del vector
        ejecutarMetodosControladoresHacerMedida(evento);
    }

    private void ejecutarMetodosControladoresHacerMedida(FlexEvent evt){
        Vector lista;
        synchronized(this){
            lista = (Vector) flexListeners.clone();
        }

        for(int i=0; i<lista.size(); i++){
            FlexListener listener=(FlexListener) lista.elementAt(i);
            listener.HacerMedida(evt);
        }

    }

    public void HacerMedida(){

        //Activar la bandera del Flexometro
        mouseClicked = true;

        //Crear evento
        FlexEvent evento = new FlexEvent(this, this.distancia);

        //Llamar a métodos controladores de Hacer medida
        ejecutarMetodosControladoresHacerMedida(evento);

    }


    @Override
    public void  paint(Graphics g){
        for (Puntos npuntos : vectorPuntos){
            npuntos.pintar(g);
        }
        for (Enlace enlace : vectorEnlace) {
            enlace.pintar(g);
        }

    }
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){ //click izquierdo

            //agrega a un vector la posición del primer click
            this.vectorPuntos.add(new Puntos(e.getX(), e.getY()));
            for (Puntos npuntos : vectorPuntos){
                if(new Rectangle(npuntos.getX() - npuntos.d/2,npuntos.getY() - npuntos.d/2, npuntos.d,npuntos.d).contains(e.getPoint())){
                    if(p1 == null)
                        p1 = new Point(npuntos.getX(),npuntos.getY());

                    else{//segundo clik

                        //agrega a un vector la posición del primer click
                        p2 = new Point(npuntos.getX(), npuntos.getY());
                        this.vectorEnlace.add(new Enlace(p1.x, p1.y, p2.x, p2.y));
                        repaint();

                        //calcula la distancia con la formula de la pendiente
                         distancia = (int) Math.sqrt(Math.pow(p2.x - p1.x,2) +
                                Math.pow(p2.y - p1.y,2));

                        //para poder seguir pintando
                        p1 = null;
                        p2 = null;

                        this.HacerMedida();
                        /*ConexionBD cnx = new ConexionBD();
                        bdCon = cnx.Conectar();

                        String sql = "INSERT INTO bdflexometro.medida1 (Medicion) VALUES (?)";

                        try {
                            PreparedStatement psInsertar = (PreparedStatement) bdCon.prepareStatement(sql);
                            psInsertar.setInt(1, distancia);
                            int medida = psInsertar.executeUpdate();

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }*/

                    }
                }
            }

        }
        repaint();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Cursor cursor;
        ImageIcon imagenes = new ImageIcon("src\\Imagenes\\tape.png");
        Toolkit t=Toolkit.getDefaultToolkit();
        cursor=t.createCustomCursor(imagenes.getImage(),new Point(30,30), "Cursor");
        setCursor(cursor);
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    public void start ()
    {
        if (unHilo == null) {
            unHilo = new Thread((Runnable) this, "Flex");
            unHilo.start();//Método start de la clase Thread
        }
    }

    public void run () {
        while (true) {
            this.repaint();
            //Dispara evento del Flexometro
            //Hace la medida
            //Llamar a método que ejecuta métodos de listeners

            try {
                unHilo.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

    public void stop () {
        unHilo.stop(); //Para el hilo
        unHilo = null;
    }
}
