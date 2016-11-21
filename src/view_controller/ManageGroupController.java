package view_controller;

import entities.Grupo;
import entities.Usuario;
import entities_controllers.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.MainView;
import vista.ManageGroupView;

public class ManageGroupController implements ActionListener
{
    private ManageGroupView currentView;
    
    public ManageGroupController(ManageGroupView view )
    {
        currentView = view;
    }

    public void init()
    {
        List < Usuario > usuarios = new ArrayList < Usuario >();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            con = Conexion.getConnection();
            ps = con.prepareStatement( "SELECT USUARIO.NAME AS NOMBRE , USUARIO.EMAIL AS EMAIL FROM USER_X_GROUP , "
                    + " USUARIO WHERE USER_X_GROUP.GROUP_ID = ? AND"
                    + " USER_X_GROUP.USER_EMAIL = USUARIO.EMAIL" );
            ps.setBigDecimal( 1 , currentView.getGroupID() );
            rs = ps.executeQuery();
            while( rs.next() )
            {
                Usuario user = new Usuario();
                user.setName(rs.getString("NOMBRE"));
                user.setEmail(rs.getString("EMAIL"));
                usuarios.add( user );
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
        currentView.setGroup_list_user(usuarios);
        currentView.getGroupNameTextField().setText(getGroupName());
        currentView.desplegarDatos();
    }
    
    private void delete_members( List < String > delete )
    {
        if( validate() )
        {
            Connection con = null;
            try
            {
                con = Conexion.getConnection();
                for( int i = 0 ; i < delete.size() ; ++i )
                {
                    if( delete.get(i).equals( MainView.getActual_user().getEmail() ) )
                    {
                        JOptionPane.showMessageDialog(currentView,
                        "You can't delete the leader.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    PreparedStatement ps = con.prepareStatement( "DELETE FROM USER_X_GROUP WHERE USER_EMAIL = ? AND GROUP_ID = ? " );
                    ps.setString( 1 , delete.get(i) );
                    ps.setBigDecimal( 2 , currentView.getGroupID() );
                    ps.executeUpdate();
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
            currentView.init();
        }
    }
    
    private String getLeaderEmail()
    {
        ResultSet rs = null;
        Connection con = null;
        try
        {
            con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement( "SELECT GRUPO.LEADER_EMAIL AS LEADER FROM GRUPO WHERE GRUPO.ID = ?" );
            ps.setBigDecimal( 1 , MainView.getManageGroupView().getGroupID() );
            rs = ps.executeQuery();
            while(rs.next())
                return rs.getString("LEADER");
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
    
    private boolean validate()
    {
        if( MainView.getActual_user().getEmail().equals( getLeaderEmail() ) )
            return true;
        JOptionPane.showMessageDialog(currentView,
        "No eres lider , no puedes modificar el grupo.",
        "Error",
        JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    private void deleteGroupBtnAction()
    {
        ArrayList< String > emails_delete = new ArrayList<>();
        for (int i = 0; i < currentView.getUsers_table().getRowCount(); ++i)
        {
            Boolean checked = Boolean.valueOf(currentView.getUsers_table().getValueAt(i, 0).toString());
            if (checked)
            {
                String email = currentView.getUsers_table().getValueAt(i, 2).toString();
                emails_delete.add(email);
            }
        }
        delete_members(emails_delete);
    }
    
    public void refreshName()
    {
        String newOne = getGroupName();
        currentView.getGroupNameTextField().setText(newOne);
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
    
    private void groupLeaderBtnAction()
    {
        if( validate() )
        {
            currentView.setVisible(false);
            MainView.getChangeGroupLeaderView().init();
            MainView.getChangeGroupLeaderView().setVisible(true);
        }
    }
    
    private void changeGroupNameBtnAction()
    {
        if( validate() )
        {
            currentView.setVisible(false);
            MainView.getChangeGroupNameView().setVisible(true);
            MainView.getChangeGroupNameView().init();
        }
    }

    private void addMemberBtnAction()
    {
        if( validate() )
        {
            currentView.setVisible(false);
            MainView.getManagementAddMemberView().init();
            MainView.getManagementAddMemberView().setVisible(true);
        }
    }
    
    private void goBack()
    {
        currentView.setVisible(false);
        MainView.getViewGroupView().init();
        MainView.getViewGroupView().setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource().equals( currentView.getDeleteGroupBtn() ) )
            deleteGroupBtnAction();
        else if( e.getSource().equals( currentView.getGroupLeaderBtn() ) )
            groupLeaderBtnAction();
        else if( e.getSource().equals( currentView.getChangeGroupNameBtn() ) )
            changeGroupNameBtnAction();
        else if( e.getSource().equals( currentView.getAddMemberBtn() ) )
            addMemberBtnAction();
        else if( e.getSource().equals( currentView.getGoBackBtn() ) )
            goBack();
    }
    
}
