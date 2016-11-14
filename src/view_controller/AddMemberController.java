package view_controller;

import entities.Usuario;
import entities_controllers.UsuarioJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import vista.AddMemberView;
import vista.MainView;

public class AddMemberController implements ActionListener
{
    private AddMemberView currentView;
    
    public AddMemberController( AddMemberView vista )
    {
        currentView = vista;
    }
    
    boolean inList( List < Usuario > users , Usuario user )
    {
        for( int i = 0 ; i < users.size() ; ++i )
            if( users.get(i).equals( user ) )   return true;
        return false;
    }
    
    private void addBtnAction()
    {
        String email = currentView.getEmail_textField().getText();
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
    
}