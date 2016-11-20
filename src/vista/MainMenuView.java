package vista;

import javax.swing.JButton;
import javax.swing.JTable;
import view_controller.MainMenuController;

public class MainMenuView extends javax.swing.JFrame {

    public MainMenuView() {
        initComponents();
        menuCtrl = new MainMenuController(this);
        viewGroupBtn.addActionListener(menuCtrl);
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
        viewGroupBtn = new javax.swing.JButton();

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

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(groupsTable);
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

        viewGroupBtn.setText("View Group");
        viewGroupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewGroupBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainTittleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(groupTittleLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(createGroupBtn)
                        .addGap(63, 63, 63)
                        .addComponent(viewGroupBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(mainTittleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupTittleLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createGroupBtn)
                    .addComponent(viewGroupBtn))
                .addGap(0, 52, Short.MAX_VALUE))
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

    private void viewGroupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewGroupBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewGroupBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createGroupBtn;
    private javax.swing.JLabel groupTittleLabel;
    private javax.swing.JTable groupsTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mainTittleLabel;
    private javax.swing.JButton viewGroupBtn;
    // End of variables declaration//GEN-END:variables

    private MainMenuController menuCtrl;

    public JButton getCreateGroupBtn() {
        return createGroupBtn;
    }

    public JTable getGroupsTable() {
        return groupsTable;
    }

    public JButton getViewGroupBtn() {
        return viewGroupBtn;
    }

    public MainMenuController getMenuCtrl() {
        return menuCtrl;
    }

}
