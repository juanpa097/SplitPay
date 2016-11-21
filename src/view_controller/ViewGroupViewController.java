package view_controller;

import entities.Grupo;
import entities_controllers.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import vista.MainView;
import vista.ManageGroupView;
import vista.ViewGroupView;

public class ViewGroupViewController implements ActionListener
{

    ViewGroupView currentView;
    
    public ViewGroupViewController( ViewGroupView newOne )
    {
        currentView = newOne;
    }
    
    private String getGroupName()
    {
        ResultSet rs = null;
        Connection con = null;
        try
        {
            con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement( "SELECT GRUPO.NAME AS NOMBRE FROM GRUPO "
            + "WHERE GRUPO.ID = ?");
            ps.setBigDecimal( 1 , currentView.getGroupID() );
            rs = ps.executeQuery();
            while(rs.next())
                return rs.getString("NOMBRE");
        }
        catch( SQLException ex )
        {
            Logger.getLogger( Grupo.class.getName() ).log( Level.SEVERE , null , ex );
        }
        finally
        {
            if( con != null )
                try
                {
                    con.close();
                }catch (SQLException ex)
                {
                    Logger.getLogger(ManageGroupView.class.getName()).log(Level.SEVERE, null, ex);
                }   
        }
        return "";
    }
    
    public ArrayList < ArrayList < Object > > getData()
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList < ArrayList < Object > > data = new ArrayList < >();
        try
        {
            con = Conexion.getConnection();
            ps = con.prepareStatement( "WITH " +
            "USUARIOS AS( " +
            "  SELECT USER_X_GROUP.USER_EMAIL AS EMAIL FROM USER_X_GROUP WHERE USER_X_GROUP.GROUP_ID = ? AND USER_X_GROUP.USER_EMAIL != ?" +
            "), ME_DEBEN AS( " +
            " SELECT USUARIOS.EMAIL AS EMAIL , ( SELECT SUM(DEUDA.AMOUNT) FROM DEUDA , BILL WHERE BILL.ID_RESPONSABLE = ? AND DEUDA.DEUDOR = USUARIOS.EMAIL AND DEUDA.BILL_ID "
            + " = BILL.ID ) AS MONTO FROM USUARIOS ) , LE_DEBO AS("
            + " SELECT USUARIOS.EMAIL AS EMAIL , ( SELECT SUM(DEUDA.AMOUNT) FROM BILL , DEUDA WHERE BILL.ID = DEUDA.BILL_ID AND DEUDA.DEUDOR = ? AND "
            + " BILL.ID_RESPONSABLE = USUARIOS.EMAIL ) AS MONTO FROM USUARIOS ) , RESPUESTA AS("
            + " SELECT USUARIOS.EMAIL AS EMAIL , (SELECT MONTO FROM LE_DEBO WHERE USUARIOS.EMAIL = LE_DEBO.EMAIL) AS DEBO , "
            + " ( SELECT MONTO FROM ME_DEBEN WHERE ME_DEBEN.EMAIL = USUARIOS.EMAIL ) AS MEDEBEN FROM USUARIOS ) SELECT RESPUESTA.EMAIL AS EMAIL,"
            + "( CASE WHEN ( RESPUESTA.DEBO IS NULL ) THEN 0 ELSE RESPUESTA.DEBO END) AS DEBO , "
            + "( CASE WHEN ( RESPUESTA.MEDEBEN IS NULL ) THEN 0 ELSE RESPUESTA.MEDEBEN END) AS ME_DEBEN , "
            + "( SELECT USER_X_GROUP.BALANCE FROM USER_X_GROUP WHERE USER_X_GROUP.USER_EMAIL = RESPUESTA.EMAIL AND USER_X_GROUP.GROUP_ID = ? ) AS BALANCE "
            + "FROM RESPUESTA" );
            ps.setBigDecimal( 1 , currentView.getGroupID() );
            ps.setString( 2 , MainView.getActual_user().getEmail() );
            ps.setString( 3 , MainView.getActual_user().getEmail() );
            ps.setString( 4 , MainView.getActual_user().getEmail() );
            ps.setBigDecimal( 5 , currentView.getGroupID() );
            rs = ps.executeQuery();
            while( rs.next() )
            {
                ArrayList < Object > row = new ArrayList <  >();
                String email = rs.getString("EMAIL");
                row.add( email );
                row.add( rs.getBigDecimal("DEBO") );
                row.add( rs.getBigDecimal("ME_DEBEN") );
                row.add( rs.getBigDecimal("BALANCE"));
                data.add( row );
            }
        }
        catch( SQLException ex )
        {
            Logger.getLogger( Grupo.class.getName() ).log( Level.SEVERE , null , ex );
        }
        finally
        {
            if( con != null )
                try
                {
                    con.close();
                }catch (SQLException ex)
                {
                    Logger.getLogger(ManageGroupView.class.getName()).log(Level.SEVERE, null, ex);
                }   
        }
        return data;
    }
    
    public void init()
    {
        String columnas[] = { "Members" , "Debo" , "Me debe" , "Balance in group" };
        ArrayList < ArrayList < Object > > data = getData();
        Object [][]datos = new Object[data.size()][columnas.length];
        for( int i = 0 ; i < data.size() ; ++i )
            for( int j = 0 ; j < 4 ; ++j )
                datos[i][j] = data.get(i).get(j);
        DefaultTableModel model = new DefaultTableModel( datos , columnas );
        currentView.getMembersTable().setModel(model);
        currentView.getMembersScrollPane().setViewportView(currentView.getMembersTable());
        currentView.getGroupNameTextField().setText( getGroupName() );
    }

    public void goBack()
    {
        currentView.setVisible(false);
        MainView.getMainMenuView().setVisible(true);
    }
    
    public void manageGroupBtnAction()
    {
        currentView.setVisible(false);
        MainView.getManageGroupView().setGroupID( currentView.getGroupID() );
        MainView.getManageGroupView().init();
        MainView.getManageGroupView().setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource().equals( currentView.getBackBtn() ) )
            goBack();
        else if( e.getSource().equals( currentView.getManageGroupBtn() ) )
            manageGroupBtnAction();
    }
    
}
