package view_controller;

import entities.Grupo;
import entities.UserXGroup;
import entities.UserXGroupPK;
import entities.Usuario;
import entities_controllers.GrupoJpaController;
import entities_controllers.UserXGroupJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
        MainView.getAddMemberView().getEmail_textField().setText("");
        MainView.getCreateGroupView().setVisible(false);
        MainView.getAddMemberView().setVisible(true);
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
                 
                //int group_id = controller_grupo.findGrupo();
                UserXGroupJpaController controller_users = new UserXGroupJpaController( EntityFactorySingleton.getEMF() );
                for( Usuario user : currentView.getUser_list() )
                {
                    UserXGroup nuevo = new UserXGroup();
                    UserXGroupPK pk = new UserXGroupPK();
                    nuevo.setBalance(new BigDecimal(0.0));
                    nuevo.setGrupo(nuevo_grupo);
                    nuevo.setUsuario(user);
                    pk.setUserEmail(user.getEmail());
                    pk.setGroupId(nuevo_grupo.getId());
                    nuevo.setUserXGroupPK(pk);
                    //usuarios_insert.add(nuevo);
                }
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
    }
    
}
