package view_controller;

import entities.Grupo;
import entities.Usuario;
import entities_controllers.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import vista.ChangeGroupLeaderView;
import vista.MainView;
import vista.ManageGroupView;

public class ChangeGroupLeaderController implements ActionListener
{
    
    ChangeGroupLeaderView currentView;
    
    public ChangeGroupLeaderController( ChangeGroupLeaderView newOne )
    {
        currentView = newOne;
    }
    
    public void init()
    {
        DefaultComboBoxModel < String > modelo = new DefaultComboBoxModel();
        List < Usuario > usuarios = MainView.getManageGroupView().getGroup_list_user();
        for( int i = 0 ; i < usuarios.size() ; ++i )
            modelo.addElement( usuarios.get(i).getEmail() );
        currentView.getComboBoxEmails().setModel(modelo);
    }

    private void goBack()
    {
        currentView.setVisible(false);
        MainView.getManageGroupView().setVisible(true);   
    }
    
    private void cancelBtnAction()
    {
        goBack();
    }
    
    private void changeLeaderOnDatabase( String newLeader , BigDecimal group_id )
    {
        Connection con = null;
        try
        {
            con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement( "UPDATE GRUPO "
            + "SET LEADER_EMAIL = ? "
            + "WHERE ID = ?");
            ps.setString( 1 , newLeader );
            ps.setBigDecimal( 2 , group_id );
            ps.executeUpdate();
            String oracion = "Ahora el lider es " + newLeader;
            JOptionPane.showMessageDialog(currentView,
            oracion,
            "Cambio de lider",
            JOptionPane.PLAIN_MESSAGE );
            
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
    }
    
    private void changeLeaderBtnAction()
    {
        String new_leader_email = currentView.getComboBoxEmails().getItemAt(currentView.getComboBoxEmails().getSelectedIndex());
        changeLeaderOnDatabase( new_leader_email, MainView.getManageGroupView().getGroupID() );
        goBack();
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource().equals( currentView.getCancelBtn() ) )
            cancelBtnAction();
        else if( e.getSource().equals( currentView.getChangeLeaderBtn() ) )
            changeLeaderBtnAction();
    }
    
}
