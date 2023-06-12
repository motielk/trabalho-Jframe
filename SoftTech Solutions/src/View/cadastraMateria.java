/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import conexoes.MySQL;
import javax.swing.JOptionPane;
import objetos.Materia;

/**
 *
 * @author Hugo
 */
public class cadastraMateria extends javax.swing.JFrame {

    MySQL conectar = new MySQL();

    Materia novaMateria = new Materia();

    private void cadastraMateria(Materia novaMateria) {
        this.conectar.conectaBanco();

        novaMateria.setNome(txtNome.getText());
        novaMateria.setHoras(txtHoras.getText());
        novaMateria.setProfessor(txtProfessor.getText());
        novaMateria.setDescricao(txtDescricao.getText());

        try {
            this.conectar.insertSQL("INSERT INTO Materias("
                    + "nome,"
                    + "horasSemestrais,"
                    + "professor,"
                    + "descricao)"
                    + "VALUES ("
                    + "'" + novaMateria.getNome() + "',"
                    + "'" + novaMateria.getHoras() + "',"
                    + "'" + novaMateria.getProfessor() + "',"
                    + "'" + novaMateria.getDescricao() + "'"
                    + ");"
            );
        } catch (Exception e) {
            System.out.println("Erro ao materia "
                    + e.getMessage());
        } finally {
            this.conectar.fechaBanco();
            JOptionPane.showMessageDialog(rootPane, "Materia cadastrada com sucesso!");
        }
    }

    private void buscaMateria(Materia novaMateria) {
        this.conectar.conectaBanco();

        String consultaMateria = this.consultaMateria.getText();

        try {
            this.conectar.executarSQL(
                    "SELECT "
                    + "horasSemestrais,"
                    + "professor,"
                    + "descricao "
                    + "FROM "
                    + "Materias "
                    + "WHERE "
                    + "nome = '" + consultaMateria + "';"
            );

            while (this.conectar.getResultSet().next()) {
                novaMateria.setHoras(this.conectar.getResultSet().getString(1));
                novaMateria.setProfessor(this.conectar.getResultSet().getString(2));
                novaMateria.setDescricao(this.conectar.getResultSet().getString(3));
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar Materia " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao buscar materia.");
        } finally {
            consultaHoras.setText(String.valueOf(novaMateria.getHoras()));
            consultaProfessor.setText(novaMateria.getProfessor());
            consultaDescricao.setText(novaMateria.getDescricao());
            this.conectar.fechaBanco();
        }
    }

    private void deletaMateria(Materia novaMateria) {
        this.conectar.conectaBanco();

        String consultaMateria = this.consultaMateria.getText();

        try {
            this.conectar.updateSQL(
                    "DELETE FROM Materias "
                    + "WHERE "
                    + "nome = '" + consultaMateria + "';"
            );
        } catch (Exception e) {
            System.out.println("Erro ao deletar materia " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar materia.");
        } finally {
            this.conectar.fechaBanco();
            limparBusca();
            JOptionPane.showMessageDialog(null, "Materia deletada com sucesso.");
        }
    }

    private void atualizaMateria(Materia novaMateria) {
        this.conectar.conectaBanco();

        String consultaMateria = this.consultaMateria.getText();

        try {
            this.conectar.updateSQL(
                    "UPDATE Materias SET "
                    + "horasSemestrais = '" + consultaHoras.getText() + "',"
                    + "professor = '" + consultaProfessor.getText() + "',"
                    + "descricao = '" + consultaDescricao.getText() + "'"
                    + "WHERE "
                    + "nome = '" + consultaMateria + "';"
            );
        } catch (Exception e) {
            System.out.println("Erro ao atualizar materia " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar materia.");
        } finally {
            this.conectar.fechaBanco();
            limparBusca();
            JOptionPane.showMessageDialog(null, "Materia atualizada com sucesso.");
        }
    }

    private void limparCadastro() {
        txtNome.setText("");
        txtHoras.setText("");
        txtProfessor.setText("");
        txtDescricao.setText("");
    }

    private void limparBusca() {
        consultaMateria.setText("");
        consultaHoras.setText("");
        consultaProfessor.setText("");
        consultaDescricao.setText("");
    }

    public cadastraMateria() {
        initComponents();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtHoras = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtProfessor = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        consultaDescricao = new javax.swing.JTextArea();
        btnAtualizar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        consultaProfessor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        consultaMateria = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        consultaHoras = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnLimparCadastro = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        telaFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nome da matéria:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Horas:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Descrição:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, -1, -1));
        jPanel1.add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 210, -1));
        jPanel1.add(txtHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 110, -1));

        txtDescricao.setColumns(20);
        txtDescricao.setRows(5);
        jScrollPane1.setViewportView(txtDescricao);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("CADASTRO MATÉRIA:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Nome do professor:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));
        jPanel1.add(txtProfessor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 200, -1));

        btnCadastrar.setText("CADASTRAR");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Busca Matéria"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, -1, -1));

        btnLimpar.setText("LIMPAR");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });
        jPanel2.add(btnLimpar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        consultaDescricao.setColumns(20);
        consultaDescricao.setRows(5);
        jScrollPane2.setViewportView(consultaDescricao);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, -1, -1));

        btnAtualizar.setText("ATUALIZAR");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAtualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        btnDeletar.setText("DELETAR");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });
        jPanel2.add(btnDeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, -1, -1));
        jPanel2.add(consultaProfessor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 180, -1));

        jLabel5.setText("Nome:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));
        jPanel2.add(consultaMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 180, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da Matéria"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Horas:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));
        jPanel3.add(consultaHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 80, -1));

        jLabel9.setText("Professor");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel7.setText("Descrição:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 390, 270));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 430, 380));

        btnLimparCadastro.setText("LIMPAR");
        btnLimparCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCadastroActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimparCadastro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 470, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/logo.png"))); // NOI18N
        jLabel10.setToolTipText("");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 160, 130));

        btnVoltar.setText("VOLTAR");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });
        jPanel1.add(btnVoltar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        telaFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/fundo.png"))); // NOI18N
        jPanel1.add(telaFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 0, 960, 550));

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

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastraMateria(novaMateria);
        limparCadastro();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        deletaMateria(novaMateria);
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparBusca();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscaMateria(novaMateria);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        atualizaMateria(novaMateria);
        limparBusca();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnLimparCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCadastroActionPerformed
        limparCadastro();
    }//GEN-LAST:event_btnLimparCadastroActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        dispose();
        cadastroProfessor cp = new cadastroProfessor();
        cp.setVisible(true);
    }//GEN-LAST:event_btnVoltarActionPerformed

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
            java.util.logging.Logger.getLogger(cadastraMateria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cadastraMateria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cadastraMateria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cadastraMateria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cadastraMateria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnLimparCadastro;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JTextArea consultaDescricao;
    private javax.swing.JTextField consultaHoras;
    private javax.swing.JTextField consultaMateria;
    private javax.swing.JTextField consultaProfessor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel telaFundo;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtHoras;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtProfessor;
    // End of variables declaration//GEN-END:variables
}
