package vista;

import entities.Usuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import view_controller.ManageGroupController;

public class ManageGroupView extends javax.swing.JFrame
{
    
    private List < Usuario > usuarios_grupo;
    private ManageGroupController controller;
    private BigDecimal groupID;
    
    public ManageGroupView()
    {
        initComponents();
        usuarios_grupo = new ArrayList < Usuario >();
        controller = new ManageGroupController(this);
        changeGroupLeaderBtn.addActionListener(controller);
        deleteGroupBtn.addActionListener(controller);
        changeGroupNameBtn.addActionListener(controller);
        addMemberBtn.addActionListener(controller);
        goBackBtn.addActionListener(controller);
        desplegarDatos();
    }
    
    // Only call this method if you already set a groupID
    public void init()
    {
        controller.init();
    }
    
    public void updateGroupName()
    {
        controller.refreshName();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        manageGroupTitle = new javax.swing.JLabel();
        users_scrollPane = new javax.swing.JScrollPane();
        users_table = new javax.swing.JTable();
        deleteGroupBtn = new javax.swing.JButton();
        changeGroupLeaderBtn = new javax.swing.JButton();
        groupNameLabel = new javax.swing.JLabel();
        groupNameTextField = new javax.swing.JTextField();
        changeGroupNameBtn = new javax.swing.JButton();
        addMemberBtn = new javax.swing.JButton();
        goBackBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        manageGroupTitle.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        manageGroupTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manageGroupTitle.setText("Manage group");

        users_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Select", "Name", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        users_scrollPane.setViewportView(users_table);

        deleteGroupBtn.setText("Delete selected from group");

        changeGroupLeaderBtn.setText("Change group leader");
        changeGroupLeaderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeGroupLeaderBtnActionPerformed(evt);
            }
        });

        groupNameLabel.setText("Group name");

        groupNameTextField.setEditable(false);
        groupNameTextField.setText("jTextField1");

        changeGroupNameBtn.setText("Change group name");

        addMemberBtn.setText("Add member");

        goBackBtn.setText("Go back");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(manageGroupTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(123, 123, 123)
                                .addComponent(users_scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(148, 148, 148)
                                .addComponent(groupNameLabel)
                                .addGap(18, 18, 18)
                                .addComponent(groupNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(goBackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(deleteGroupBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(changeGroupLeaderBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(changeGroupNameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(addMemberBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 26, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(manageGroupTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(goBackBtn))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupNameLabel)
                    .addComponent(groupNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(users_scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteGroupBtn)
                    .addComponent(changeGroupLeaderBtn)
                    .addComponent(changeGroupNameBtn)
                    .addComponent(addMemberBtn))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeGroupLeaderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeGroupLeaderBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_changeGroupLeaderBtnActionPerformed
    
    public void desplegarDatos()
    {
        DefaultTableModel model = ( DefaultTableModel )users_table.getModel();
        for (int i = model.getRowCount() - 1 ; i >= 0 ; --i )
            model.removeRow(i);
        for( int i = 0 ; i < usuarios_grupo.size() ; ++i )
        {
            //if( usuarios_grupo.get(i).getEmail().equals( MainView.getActual_user().getEmail() ) )   continue;
            model.addRow( new Object[] { false , usuarios_grupo.get(i).getName() , usuarios_grupo.get(i).getEmail() } );
        }
        users_table.setModel( model );
        users_scrollPane.setViewportView( users_table );
    }
    
    public BigDecimal getGroupID()
    {
        return groupID;
    }
    
    public void setGroupID( BigDecimal nuevo )
    {
        groupID = nuevo;
    }
    
    public javax.swing.JButton getDeleteGroupBtn()
    {
        return deleteGroupBtn;
    }
    
    public void setGroup_list_user( List < Usuario > lista )
    {
        usuarios_grupo = lista;
    }
    
    public List < Usuario > getGroup_list_user()
    {
        return usuarios_grupo;
    }
    
    public javax.swing.JButton getGroupLeaderBtn()
    {
        return changeGroupLeaderBtn;
    }
    
    public javax.swing.JTable getUsers_table()
    {
        return users_table;
    }
    
    public javax.swing.JButton getChangeGroupNameBtn()
    {
        return changeGroupNameBtn;
    }
    
    public javax.swing.JTextField getGroupNameTextField()
    {
        return groupNameTextField;
    }
    
    public javax.swing.JButton getAddMemberBtn()
    {
        return addMemberBtn;
    }
    
    public javax.swing.JButton getGoBackBtn()
    {
        return goBackBtn;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMemberBtn;
    private javax.swing.JButton changeGroupLeaderBtn;
    private javax.swing.JButton changeGroupNameBtn;
    private javax.swing.JButton deleteGroupBtn;
    private javax.swing.JButton goBackBtn;
    private javax.swing.JLabel groupNameLabel;
    private javax.swing.JTextField groupNameTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel manageGroupTitle;
    private javax.swing.JScrollPane users_scrollPane;
    private javax.swing.JTable users_table;
    // End of variables declaration//GEN-END:variables
}
