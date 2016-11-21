package view_controller;

import entities.Grupo;
import entities_controllers.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.ChangeGroupNameView;
import vista.MainView;
import vista.ManageGroupView;

public class ChangeGroupNameController implements ActionListener
{
    
    ChangeGroupNameView currentView;
    
    public ChangeGroupNameController( ChangeGroupNameView newOne )
    {
        currentView = newOne;
    }
    
    public void goBack()
    {
        currentView.setVisible(false);
        MainView.getManageGroupView().setVisible(true);
    }
    
    public void init()
    {
        currentView.getNewNameTextField().setText("");
    }

    private void cancelBtnAction()
    {
        goBack();
    }
    
    private void changeNameOnDatabase( String newName , BigDecimal group_id )
    {
        Connection con = null;
        try
        {
            con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement( "UPDATE GRUPO "
            + "SET NAME = ? "
            + "WHERE ID = ?");
            ps.setString( 1 , newName );
            ps.setBigDecimal( 2 , group_id );
            ps.executeUpdate();
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
    
    private void changeNameBtnAction()
    {
        changeNameOnDatabase( currentView.getNewNameTextField().getText() , MainView.getManageGroupView().getGroupID() );
        MainView.getManageGroupView().updateGroupName();
        goBack();
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource().equals( currentView.getCancelBtn() ) )
            cancelBtnAction();
        else if( e.getSource().equals( currentView.getChangeNameBtn() ) )
            changeNameBtnAction();
    }
    
}
