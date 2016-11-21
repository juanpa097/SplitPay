package view_controller;

import entities.Usuario;
import entities_controllers.UsuarioJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataListener;
import vista.AddMemberView;
import vista.MainView;

public class AddMemberController implements ActionListener
{
    private AddMemberView currentView;
    
    public AddMemberController( AddMemberView vista )
    {
        currentView = vista;
        prepareComboBox();
    }
    
    boolean inList( List < Usuario > users , Usuario user )
    {
        for( int i = 0 ; i < users.size() ; ++i )
            if(users.get(i).equals( user ) )   return true;
        return false;
    }
    
    private void addBtnAction()
    {
        String email = currentView.getEmailComboBox().getItemAt(currentView.getEmailComboBox().getSelectedIndex());
        UsuarioJpaController user = new UsuarioJpaController( EntityFactorySingleton.getEMF() );
        Usuario found = user.findUsuario(email);
        if( found == null )
            JOptionPane.showMessageDialog( currentView, 
            "El usuario no existe.", 
            "Usuario No Existe", 
            JOptionPane.ERROR_MESSAGE );
        else
        {
            if( found.equals( MainView.getActual_user() ) )
                JOptionPane.showMessageDialog( currentView, 
                "Ya haces parte de este grupo.", 
                "Usuario lider", 
                JOptionPane.ERROR_MESSAGE );
            else if( !inList( MainView.getCreateGroupView().getUser_list() , found ) )
            {
                MainView.getCreateGroupView().getUser_list().add(found);
                currentView.setVisible(false);
                MainView.getCreateGroupView().desplegarDatos();
                MainView.getCreateGroupView().setVisible(true);
            }
            else
                JOptionPane.showMessageDialog( currentView, 
                "El usuario ya se encuentra en este grupo.", 
                "Usuario Ya Existente", 
                JOptionPane.ERROR_MESSAGE );
        }
    }
    
    private void cancelBtnAction()
    {
        currentView.setVisible(false);
        MainView.getCreateGroupView().setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource().equals( currentView.getAddBtn() ) )
            addBtnAction();
        else if( e.getSource().equals( currentView.getCancelBtn() ) )
            cancelBtnAction();
    }
    
    private void prepareComboBox()
    {
        UsuarioJpaController usrCrtl = new UsuarioJpaController(EntityFactorySingleton.getEMF());
        List<String> emailList = usrCrtl.getAllUsersEmail();
        Object[] modelEmail = emailList.toArray();
        DefaultComboBoxModel comboModel = new DefaultComboBoxModel(modelEmail);
        currentView.getEmailComboBox().setModel(comboModel);
    }
    
}
