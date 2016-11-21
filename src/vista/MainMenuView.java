package vista;

import javax.swing.JButton;
import javax.swing.JTable;
import view_controller.MainMenuController;

public class MainMenuView extends javax.swing.JFrame {

    public MainMenuView() {
        initComponents();
        menuCtrl = new MainMenuController(this);
        groupsTable.addMouseListener(menuCtrl);
        createGroupBtn.addActionListener(menuCtrl);
        logOutBtn.addActionListener(menuCtrl);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mainTittleLabel = new javax.swing.JLabel();
        groupTittleLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        groupsTable = new javax.swing.JTable();
        createGroupBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        logOutBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainTittleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        mainTittleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainTittleLabel.setText("SplitPay");

        groupTittleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        groupTittleLabel.setText("My Groups");

        groupsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Group Name", "My Balance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        groupsTable.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(groupsTable);
        groupsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (groupsTable.getColumnModel().getColumnCount() > 0) {
            groupsTable.getColumnModel().getColumn(0).setMinWidth(50);
            groupsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            groupsTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        createGroupBtn.setText("Create Group");
        createGroupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createGroupBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Nota: Para acceder a un grupo haz doble click en la fila del grupo que quieras ver.");

        logOutBtn.setText("Log out");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mainTittleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(299, 299, 299)
                                .addComponent(groupTittleLabel))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(293, 293, 293)
                                .addComponent(createGroupBtn))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(147, 147, 147)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(logOutBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainTittleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupTittleLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addComponent(createGroupBtn)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createGroupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createGroupBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createGroupBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createGroupBtn;
    private javax.swing.JLabel groupTittleLabel;
    private javax.swing.JTable groupsTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JLabel mainTittleLabel;
    // End of variables declaration//GEN-END:variables

    private MainMenuController menuCtrl;

    public JButton getCreateGroupBtn() {
        return createGroupBtn;
    }
    
    public JButton getLogOutBtn()
    {
        return logOutBtn;
    }

    public JTable getGroupsTable() {
        return groupsTable;
    }

    public MainMenuController getMenuCtrl() {
        return menuCtrl;
    }

}
