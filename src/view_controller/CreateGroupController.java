package view_controller;

import entities.Grupo;
import entities.UserXGroup;
import entities.UserXGroupPK;
import entities.Usuario;
import entities_controllers.Conexion;
import entities_controllers.GrupoJpaController;
import entities_controllers.UserXGroupJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.CreateGroupView;
import vista.MainView;

public class CreateGroupController implements ActionListener
{
    private CreateGroupView currentView;
    
    public CreateGroupController( CreateGroupView otherView )
    {
        this.currentView = otherView;
    }

    public void deleteMemberBtnAction()
    {
        int row_delete = currentView.getMembersTable().getSelectedRow();
        if( row_delete == -1 )
            JOptionPane.showMessageDialog( currentView, 
            "Seleccione un usuario para eliminarlo del grupo.", 
            "Usuario sin seleccionar", 
            JOptionPane.ERROR_MESSAGE );
        else
        {
            currentView.getUser_list().remove(row_delete);
            currentView.desplegarDatos();
        }
    }
    
    public void addMemberBtnAction()
    {
        MainView.getCreateGroupView().setVisible(false);
        MainView.getAddMemberView().setVisible(true);
    }
    
    private void goBack()
    {
        currentView.setVisible(false);
        MainView.getMainMenuView().setVisible(true);
    }
    
    private BigDecimal findLastGroup() throws SQLException
    {
        Connection con = null;
        PreparedStatement ps = null;
        BigDecimal ans = new BigDecimal(0.0);
        ResultSet rs = null;
        try
        {
            con = Conexion.getConnection();
            ps = con.prepareStatement( "SELECT MAX(GRUPO.ID) AS MAXIMO FROM GRUPO" );
            rs = ps.executeQuery();
            while(rs.next())
            ans = rs.getBigDecimal("MAXIMO");
        }
        catch( SQLException ex )
        {
            Logger.getLogger( Grupo.class.getName() ).log( Level.SEVERE , null , ex );
        }
        finally
        {
            if( con != null )
                con.close();   
        }
        return ans;
    }
    
    public void addGroupBtnAction()
    {
        String nombre_grupo = currentView.getGroupNameField().getText();
        if( nombre_grupo.length() == 0 )
            JOptionPane.showMessageDialog( currentView, 
            "Ingrese un nombre de grupo valido.", 
            "Nombre de grupo invalido", 
            JOptionPane.ERROR_MESSAGE );
        else
        {
            try
            {
                GrupoJpaController controller_grupo = new GrupoJpaController( EntityFactorySingleton.getEMF() );
                Grupo nuevo_grupo = new Grupo();
                nuevo_grupo.setName( nombre_grupo );
                nuevo_grupo.setLeaderEmail( MainView.getActual_user() );
                controller_grupo.create(nuevo_grupo);
                
                 // Hasta aqui se crea el grupo
                 
                BigDecimal group_id = findLastGroup();
                nuevo_grupo.setId(group_id);
                UserXGroupJpaController controller_users = new UserXGroupJpaController( EntityFactorySingleton.getEMF() );
                for( Usuario user : currentView.getUser_list() )
                {
                    UserXGroup nuevo = new UserXGroup();
                    nuevo.setBalance(new BigDecimal(0.0));
                    nuevo.setGrupo(nuevo_grupo);
                    nuevo.setUsuario(user);
                    nuevo.setUserXGroupPK( new UserXGroupPK( user.getEmail() , group_id ) );
                    controller_users.create(nuevo);
                }
                UserXGroup nuevo = new UserXGroup();
                nuevo.setBalance(new BigDecimal(0.0));
                nuevo.setGrupo(nuevo_grupo);
                nuevo.setUsuario(MainView.getActual_user());
                nuevo.setUserXGroupPK( new UserXGroupPK( MainView.getActual_user().getEmail() , group_id ) );
                controller_users.create(nuevo);
                MainView.getMainMenuView().getMenuCtrl().loadTable(MainView.getActual_user().getEmail());
                goBack();
            }catch (Exception ex)
            {
                Logger.getLogger(CreateGroupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource().equals( currentView.getAddMemberBtn()) )
            addMemberBtnAction();
        else if( e.getSource().equals( currentView.getAddGroupBtn() ) )
            addGroupBtnAction();
        else if( e.getSource().equals( currentView.getDeleteMemberBtn() ) )
            deleteMemberBtnAction();
        else if( e.getSource().equals( currentView.getCancelBtn() ) )
            goBack();
    }
    
}
