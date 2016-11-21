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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import vista.MainView;
import vista.ManageGroupView;
import vista.ManagementAddMemberView;

public class ManagementAddMemberController implements ActionListener
{

    ManagementAddMemberView currentView;
    
    public ManagementAddMemberController( ManagementAddMemberView newView )
    {
        currentView = newView;
    }
    
    public void init()
    {
        DefaultComboBoxModel< String > model = new DefaultComboBoxModel<String>();
        ResultSet rs = null;
        Connection con = null;
        try
        {
            con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement( " ( SELECT USUARIO.EMAIL AS EMAIL FROM USUARIO ) "
            + "MINUS ( SELECT USER_X_GROUP.USER_EMAIL AS EMAIL FROM USER_X_GROUP WHERE USER_X_GROUP.GROUP_ID = ? ) " );
            ps.setBigDecimal( 1 , MainView.getManageGroupView().getGroupID() );
            rs = ps.executeQuery();
            while(rs.next())
                model.addElement(rs.getString("EMAIL"));
            currentView.getEmailComboBox().setModel(model);
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
    
    private void goBack()
    {
        currentView.setVisible(false);
        MainView.getManageGroupView().setVisible(true);
    }
    
    private void addMemberOnDatabase( String newEmail , BigDecimal group_id )
    {
        Connection con = null;
        try
        {
            con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement( "INSERT INTO USER_X_GROUP(USER_EMAIL,GROUP_ID,BALANCE) VALUES( ? , ? , ? )" );
            ps.setString( 1 , newEmail );
            ps.setBigDecimal( 2 , group_id );
            ps.setBigDecimal( 3 , BigDecimal.ZERO );
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
    
    private void addMemberBtnAction()
    {
        if( currentView.getEmailComboBox().getSelectedIndex() == -1 )
            new JOptionPane().showMessageDialog(currentView,
            "No hay mas usuarios para agregar a este grupo.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        else
        {
            String new_member_email = currentView.getEmailComboBox().getItemAt(currentView.getEmailComboBox().getSelectedIndex());
            addMemberOnDatabase( new_member_email , MainView.getManageGroupView().getGroupID() );
            MainView.getManageGroupView().init();
            goBack();
        }
    }
    
    private void cancelBtnAction()
    {
        goBack();
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource().equals( currentView.getAddMemberBtn()) )
            addMemberBtnAction();
        else if( e.getSource().equals( currentView.getCancelBtn() ) )
            cancelBtnAction();
    }
    
}
