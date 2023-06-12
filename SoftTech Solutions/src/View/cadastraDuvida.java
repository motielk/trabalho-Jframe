/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import java.sql.SQLException;
import conexoes.MySQL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.Duvida;

/**
 *
 * @author jeani
 */
public class cadastraDuvida extends javax.swing.JFrame {

    MySQL conectar = new MySQL();

    Duvida novaDuvida = new Duvida();

    public cadastraDuvida() throws SQLException {
        initComponents();
        cbx();
    }

    private void cadastraDuvida(Duvida novaDuvida) {
        this.conectar.conectaBanco();

        novaDuvida.setCpf(txtCpf.getText());
        novaDuvida.setNivel((String) cbxNivel.getSelectedItem());
        novaDuvida.setMateria((String) cbxMateria.getSelectedItem());
        novaDuvida.setDuvida(txtDuvida.getText());

        try {
            String query = "SELECT idMateria FROM Materias WHERE nome = '" + ((String) cbxMateria.getSelectedItem()) + "'";
            this.conectar.executarSQL(query);

            if (this.conectar.getResultSet().next()) {
                int idMateria = this.conectar.getResultSet().getInt("idMateria");
                novaDuvida.setIdMateria(idMateria);
            } else {
                System.out.println("Matéria não encontrada.");
                return;
            }
            String insert = "INSERT INTO RelacaoMateriaAluno (idMateria, cpfAluno) VALUES (" + novaDuvida.getIdMateria() + ", '" + novaDuvida.getCpf() + "')";
            this.conectar.insertSQL(insert);

            this.conectar.insertSQL("INSERT INTO Duvidas("
                    + "cpf,"
                    + "nivel,"
                    + "materia,"
                    + "duvida)"
                    + "VALUES ("
                    + "'" + novaDuvida.getCpf() + "',"
                    + "'" + novaDuvida.getNivel() + "',"
                    + "'" + novaDuvida.getMateria() + "',"
                    + "'" + novaDuvida.getDuvida() + "'"
                    + ");"
            );
        } catch (Exception e) {
            System.out.println("Erro ao registrar duvida "
                    + e.getMessage());
        } finally {
            this.conectar.fechaBanco();
            JOptionPane.showMessageDialog(rootPane, "Duvida registrada com sucesso!");
        }
    }

    private void buscaDuvida(Duvida novaDuvida) {
        this.conectar.conectaBanco();

        String consultaCpf = this.consultaCpf.getText();

        try {
            this.conectar.executarSQL(
                    "SELECT "
                    + "nivel,"
                    + "materia,"
                    + "duvida "
                    + "FROM "
                    + "Duvidas "
                    + "WHERE "
                    + "cpf = '" + consultaCpf + "';"
            );

            while (this.conectar.getResultSet().next()) {
                novaDuvida.setNivel(this.conectar.getResultSet().getString(1));
                novaDuvida.setMateria(this.conectar.getResultSet().getString(2));
                novaDuvida.setDuvida(this.conectar.getResultSet().getString(3));
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar duvida " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao buscar duvida (CPF inválido).");

        } finally {
            consultaNivel.setText(novaDuvida.getNivel());
            consultaMateria.setText(novaDuvida.getMateria());
            consultaDuvida.setText(novaDuvida.getDuvida());

        }
    }

    private void deletaDuvida(Duvida novaDuvida) {
        this.conectar.conectaBanco();

        String consultaCpf = this.consultaCpf.getText();

        try {
            this.conectar.updateSQL(
                    "DELETE FROM Duvidas "
                    + "WHERE "
                    + "cpf = '" + consultaCpf + "';"
            );
        } catch (Exception e) {
            System.out.println("Erro ao deletar duvida " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar duvida.");
        } finally {
            this.conectar.fechaBanco();
            limparBusca();
            JOptionPane.showMessageDialog(null, "Duvida deletada com sucesso.");
        }
    }

    private void atualizaDuvida(Duvida novaDuvida) {
        this.conectar.conectaBanco();

        String consultaCpf = this.consultaCpf.getText();

        try {
            this.conectar.updateSQL(
                    "UPDATE Duvidas SET "
                    + "nivel = '" + consultaNivel.getText() + "',"
                    + "materia = '" + consultaMateria.getText() + "',"
                    + "duvida = '" + consultaDuvida.getText() + "'"
                    + " WHERE "
                    + "cpf = '" + consultaCpf + "';"
            );
        } catch (Exception e) {
            System.out.println("Erro ao atualizar duvida " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar duvida.");
        } finally {
            this.conectar.fechaBanco();
            limparBusca();
            JOptionPane.showMessageDialog(null, "Duvida atualizada com sucesso.");
        }
    }

    private void limparCadastro() {
        txtCpf.setText("");
        cbxNivel.setSelectedItem(0);
        cbxMateria.setSelectedItem(0);
        txtDuvida.setText("");
    }

    private void limparBusca() {
        consultaCpf.setText("");
        consultaNivel.setText("");
        consultaMateria.setText("");
        consultaDuvida.setText("");
    }

    private void cbx() throws SQLException {

        this.conectar.conectaBanco();
        try {
            this.conectar.executarSQL(
                    "SELECT * FROM Materias");
            while (this.conectar.getResultSet().next()) {
                String item = (this.conectar.getResultSet().getString("nome"));
                cbxMateria.addItem(item);
            }
        } catch (Exception e) {
        }
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
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDuvida = new javax.swing.JTextField();
        cbxNivel = new javax.swing.JComboBox<>();
        cbxMateria = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JFormattedTextField();
        btnCadastrar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        consultaCpf = new javax.swing.JFormattedTextField();
        btnBuscar = new javax.swing.JButton();
        btnLimparBusca = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        consultaNivel = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        consultaMateria = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnDeletar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        consultaDuvida = new javax.swing.JTextArea();
        btnLimparCadastro = new javax.swing.JButton();
        telaFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Digite sua dúvida:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("INFORMAÇÕES SOBRE DÚVIDAS:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 290, 50));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Qual o nível da sua dúvida?");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        txtDuvida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDuvidaActionPerformed(evt);
            }
        });
        jPanel1.add(txtDuvida, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 200, 90));

        cbxNivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Baixo", "Médio", "Alto" }));
        jPanel1.add(cbxNivel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 150, -1));

        cbxMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMateriaActionPerformed(evt);
            }
        });
        jPanel1.add(cbxMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 170, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Dúvida referente a qual matéria?");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Digite seu CPF:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(txtCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 160, -1));

        btnCadastrar.setText("CADASTRAR");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/logo.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 200, 130));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Busca Dúvida"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setText("CPF:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 39, -1, -1));

        try {
            consultaCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        consultaCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaCpfActionPerformed(evt);
            }
        });
        jPanel2.add(consultaCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 36, 186, -1));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 36, -1, -1));

        btnLimparBusca.setText("Limpar");
        btnLimparBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparBuscaActionPerformed(evt);
            }
        });
        jPanel2.add(btnLimparBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 36, -1, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da dúvida"));

        jLabel10.setText("Nível de dificuldade:");

        jLabel11.setText("Matéria:");

        jLabel15.setText("Dúvida:");

        btnDeletar.setText("Deletar");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        consultaDuvida.setColumns(20);
        consultaDuvida.setRows(5);
        jScrollPane2.setViewportView(consultaDuvida);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(consultaNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addComponent(consultaMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnDeletar)
                        .addGap(33, 33, 33)
                        .addComponent(btnAtualizar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(consultaNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(consultaMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 71, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDeletar)
                            .addComponent(btnAtualizar))
                        .addContainerGap())))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 77, 395, 270));
        jPanel3.getAccessibleContext().setAccessibleName("Dados da duvida");

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 420, 360));

        btnLimparCadastro.setText("LIMPAR");
        btnLimparCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCadastroActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimparCadastro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 530, -1, -1));

        telaFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/fundo.png"))); // NOI18N
        telaFundo.setText("jLabel2");
        telaFundo.setMaximumSize(new java.awt.Dimension(700, 500));
        telaFundo.setMinimumSize(new java.awt.Dimension(700, 500));
        telaFundo.setPreferredSize(new java.awt.Dimension(700, 500));
        jPanel1.add(telaFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 590));

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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDuvidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDuvidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDuvidaActionPerformed

    private void cbxMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxMateriaActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastraDuvida(novaDuvida);
        limparCadastro();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void consultaCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_consultaCpfActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscaDuvida(novaDuvida);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimparBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparBuscaActionPerformed
        limparBusca();
    }//GEN-LAST:event_btnLimparBuscaActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        deletaDuvida(novaDuvida);
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        atualizaDuvida(novaDuvida);
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnLimparCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCadastroActionPerformed
        limparCadastro();
    }//GEN-LAST:event_btnLimparCadastroActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(cadastraDuvida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cadastraDuvida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cadastraDuvida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cadastraDuvida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new cadastraDuvida().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(cadastraDuvida.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnLimparBusca;
    private javax.swing.JButton btnLimparCadastro;
    private javax.swing.JComboBox<String> cbxMateria;
    private javax.swing.JComboBox<String> cbxNivel;
    private javax.swing.JFormattedTextField consultaCpf;
    private javax.swing.JTextArea consultaDuvida;
    private javax.swing.JTextField consultaMateria;
    private javax.swing.JTextField consultaNivel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel telaFundo;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextField txtDuvida;
    // End of variables declaration//GEN-END:variables
}
