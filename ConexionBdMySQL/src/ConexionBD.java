import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class ConexionBD {

    public String db = "flexometro";
    public String url = "jdbc:mysql://localhost/"+db
            + "?useUnicode=true"
            + "&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimeCode=false"
            + "&serverTimezone=UTC";
    public String user = "root";
    public String pass = "Xzdsaewq1";

    Connection link;

    public Connection Conectar(){
         link = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//version 8
            link = DriverManager.getConnection(this.url, this.user, this.pass);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return link;
    }

    //mostrar registros
    public ResultSet consultarMedida(){
        ResultSet numRegistros = null;
        try {
            PreparedStatement stConsultar = link.prepareStatement(
                    "SELECT Indicador,fecha, Medicion FROM bdflexometro.medida1 ORDER BY Indicador");

            numRegistros = stConsultar.executeQuery();

        } catch(SQLException error){
            error.printStackTrace();
        }
        return numRegistros;
    }

    //consultar un dato especifico
    public ResultSet consultarIndice(String indi){
        ResultSet numRegistros = null;
        try {
            PreparedStatement stConsultarNo = link.prepareStatement(
                    "SELECT Indicador, fecha, Medicion FROM bdflexometro.medida1  WHERE Indicador = ? ORDER BY Indicador ");

            stConsultarNo.setString(1, indi);
            numRegistros = stConsultarNo.executeQuery();

        } catch(SQLException error){
            error.printStackTrace();
        }
        return numRegistros;


    }

}


