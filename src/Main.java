import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends JFrame implements FlexListener,  ActionListener {
    private String[] Registros;
    private DefaultTableModel modelo;
    private JTable tabla;
    public Connection bdCon;
    public ConexionBD interfazMysql;
    private JTextField indice;

    public void HacerMedida(FlexEvent e) {
        //insertar la medida a la base de datos
        ConexionBD cnx = new ConexionBD();
        bdCon = cnx.Conectar();

        String sql = "INSERT INTO bdflexometro.medida1 (Medicion) VALUES (?)";

        try {
            PreparedStatement psInsertar = (PreparedStatement) bdCon.prepareStatement(sql);
            psInsertar.setInt(1, e.getDistancia());
            int medida = psInsertar.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }



    public Main(){
        super("Flexómetro");
        setLayout(new GridBagLayout());
        GridBagConstraints restricciones = new GridBagConstraints();
        Container contenedorPrincipal = this.getContentPane();
        contenedorPrincipal.setLayout(new GridBagLayout());
        contenedorPrincipal.setBackground(Color.lightGray);

        //Panel para usar el componente
        JPanel flex = new JPanel();
        flex.setLayout(new GridBagLayout());

        //panel de botones para BDD
        TitledBorder border2 = new TitledBorder(" Botones BDD ");
        border2.setTitleJustification(TitledBorder.LEFT);
        border2.setTitlePosition(TitledBorder.TOP);
        JPanel BDD = new JPanel();
        BDD.setBorder(border2);
        BDD.setLayout(new GridBagLayout());

        //panel para tabla
        TitledBorder border3 = new TitledBorder(" Tabla contenido BDD ");
        border3.setTitleJustification(TitledBorder.LEFT);
        border3.setTitlePosition(TitledBorder.TOP);
        JPanel panelTabla = new JPanel();
        panelTabla.setBorder(border3);
        panelTabla.setLayout(new GridBagLayout());

        //Panel tabla contenido
        modelo = new DefaultTableModel();
        String [] Titulos = {"Indicador","fecha","Medicion"};
        Registros = new String[3];
        modelo = new DefaultTableModel(null,Titulos);
        tabla = new JTable(modelo);

        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.gridheight = 0;
        restricciones.gridwidth = 4;
        restricciones.weightx = 4;
        restricciones.weighty = 0;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        restricciones.anchor = GridBagConstraints.NORTH;
        restricciones.insets.set(0,0,0,0);
        panelTabla.add(tabla, restricciones);

        //panel tabla medidas
        restricciones.gridx = 3;
        restricciones.gridy = 0;
        restricciones.gridheight = 0;
        restricciones.gridwidth = 4;
        restricciones.weightx = 4;
        restricciones.weighty = 0;
        restricciones.fill = GridBagConstraints.BOTH;
        restricciones.anchor = GridBagConstraints.NORTHEAST;
        restricciones.insets.set(0,0,0,0);
        contenedorPrincipal.add(panelTabla, restricciones);

        //panel flexometro medidas
        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.gridheight = 1;
        restricciones.gridwidth = 1;
        restricciones.weightx = 1;
        restricciones.weighty = 1;
        restricciones.fill = GridBagConstraints.BOTH;
        restricciones.anchor = GridBagConstraints.NORTH;
        restricciones.insets.set(0,0,0,0);
        contenedorPrincipal.add(new Flexometro(), restricciones);

        //Panel Botones contenido
        JButton mostrarTabla = new JButton("Mostrar Datos");
        mostrarTabla.setActionCommand("acMostrar");
        mostrarTabla.addActionListener(this);
        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.gridheight = 0;
        restricciones.gridwidth = 1;
        restricciones.weightx = 0;
        restricciones.weighty = 0;
        restricciones.fill = GridBagConstraints.BOTH;
        restricciones.anchor = GridBagConstraints.CENTER;
        restricciones.insets.set(0,0,0,10);
        BDD.add(mostrarTabla, restricciones);

        JButton probarConex = new JButton("Probar conexión");
        probarConex.setActionCommand("acProbar");
        probarConex.addActionListener(this);
        restricciones.gridx = 1;
        restricciones.gridy = 0;
        restricciones.gridheight = 0;
        restricciones.gridwidth = 1;
        restricciones.weighty = 0;
        restricciones.weightx = 0;
        restricciones.fill = GridBagConstraints.BOTH;
        restricciones.anchor = GridBagConstraints.CENTER;
        restricciones.insets.set(0,0,0,0);
        BDD.add(probarConex, restricciones);

        JButton BuscarId = new JButton("Buscar por Número");
        BuscarId.setActionCommand("acBuscar");
        BuscarId.addActionListener(this);
        restricciones.gridx = 2;
        restricciones.gridy = 0;
        restricciones.gridheight = 0;
        restricciones.gridwidth = 1;
        restricciones.weighty = 0;
        restricciones.weightx = 0;
        restricciones.fill = GridBagConstraints.BOTH;
        restricciones.anchor = GridBagConstraints.CENTER;
        restricciones.insets.set(0,10,0,0);
        BDD.add(BuscarId, restricciones);

        indice = new JTextField("Indice");
        BuscarId.setActionCommand("acBuscar");
        BuscarId.addActionListener(this);
        restricciones.gridx = 3;
        restricciones.gridy = 0;
        restricciones.gridheight = 0;
        restricciones.gridwidth = 1;
        restricciones.weighty = 0;
        restricciones.weightx = 0;
        restricciones.fill = GridBagConstraints.BOTH;
        restricciones.anchor = GridBagConstraints.CENTER;
        restricciones.insets.set(0,10,0,0);
        BDD.add(indice, restricciones);

        //panel Botones medidas
        restricciones.gridx = 0;
        restricciones.gridy = 1;
        restricciones.gridheight = 0;
        restricciones.gridwidth = 3;
        restricciones.weighty = 0;
        restricciones.weightx = 3;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        restricciones.anchor = GridBagConstraints.SOUTHWEST;
        restricciones.insets.set(0,0,0,0);
        contenedorPrincipal.add(BDD, restricciones);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Probar si hay conexón a la base de datos
        if (e.getActionCommand().equals("acProbar")) {
            //Usar la conexión BD
            interfazMysql = new ConexionBD();
             bdCon = interfazMysql.Conectar();

            if (bdCon != null) {
                JOptionPane.showMessageDialog(null,
                        "Conectado");
            }
        }

        //Buscar por Numero llave
        else if (e.getActionCommand().equals("acBuscar")) {
            ResultSet regs = interfazMysql.consultarIndice(indice.getText());

            try {
                while(regs.next()){
                    Registros[0]=regs.getString("Indicador");
                    Registros[1]=regs.getString("fecha");
                    Registros[2]=regs.getString("Medicion");
                    modelo.addRow(Registros);
                    System.out.print("Datos con el indicador 1");
                }

            }
            catch (SQLException errSelect){
                errSelect.printStackTrace();
            }
            tabla.setModel(modelo);
        }

        //Consultar lo que hay en la tabla
        else if (e.getActionCommand().equals("acMostrar")) {
            ResultSet regs = interfazMysql.consultarMedida();

            try {
                while(regs.next()){
                    Registros[0]=regs.getString("Indicador");
                    Registros[1]=regs.getString("fecha");
                    Registros[2]=regs.getString("Medicion");
                    // Obtenemos los campos de la tabla
                    // consultada y lo agregamos a la variable
                    modelo.addRow(Registros);
                    // Agregamos los campos consultados a
                    // las filas de nuestro modelo de la tabla
                }
            }
            catch (SQLException errSelect){
                errSelect.printStackTrace();
            }
            tabla.setModel(modelo);
        }
    }

    public static void main(String[] args) {
        Flexometro flex = new Flexometro();
        Main ventana = new Main();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setResizable(true);
        ventana.setSize(900,600);
        ventana.setLocationRelativeTo(null);
        flex.start();
    }

}
