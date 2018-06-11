import java.awt.Graphics;
public class Puntos {
    private int x, y;
    public static final int d = 10;

    public Puntos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void pintar(Graphics g){
        g.drawOval(this.x - d/2, this.y - d/2, d, d );
        g.fillOval(this.x - d/2, this.y - d/2, d, d);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

