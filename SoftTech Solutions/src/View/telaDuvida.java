/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import java.sql.SQLException;
import conexoes.MySQL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import objetos.Solicitacao;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author aluno
 */
public class telaDuvida extends javax.swing.JFrame {

    MySQL conectar = new MySQL();
    Solicitacao novaSolicitacao = new Solicitacao();

    public telaDuvida() throws SQLException {
        initComponents();
        cbxMateria();
        cbxAluno();
    }

    private void cadastraSolicitacao(Solicitacao novaSolicitacao) {
        this.conectar.conectaBanco();

        novaSolicitacao.setCpf(txtCpf.getText());
        novaSolicitacao.setMateria((String) cbxMateria.getSelectedItem());
        novaSolicitacao.setAluno((String) cbxAluno.getSelectedItem());
        novaSolicitacao.setDuvida(txtDuvida.getText());
        novaSolicitacao.setResposta(txtResposta.getText());

        try {
            this.conectar.insertSQL("INSERT INTO Solicitacao("
                    + "cpfProfessor,"
                    + "materia,"
                    + "aluno,"
                    + "duvida,"
                    + "resposta)"
                    + "VALUES ("
                    + "'" + novaSolicitacao.getCpf() + "',"
                    + "'" + novaSolicitacao.getMateria() + "',"
                    + "'" + novaSolicitacao.getAluno() + "',"
                    + "'" + novaSolicitacao.getDuvida() + "',"
                    + "'" + novaSolicitacao.getResposta() + "'"
                    + ");"
            );
        } catch (Exception e) {
            System.out.println("Erro ao registrar a resposta. "
                    + e.getMessage());
        } finally {
            this.conectar.fechaBanco();
            JOptionPane.showMessageDialog(rootPane, "Sua resposta foi registrada.");
        }
    }

    private void buscaSolicitacao(Solicitacao novaSolicitacao) {
        this.conectar.conectaBanco();

        String consultaCpf = this.consultaCpf.getText();

        try {
            this.conectar.executarSQL(
                    "SELECT "
                    + "materia,"
                    + "aluno,"
                    + "duvida,"
                    + "resposta "
                    + "FROM "
                    + "Solicitacao "
                    + "WHERE "
                    + "cpfProfessor = '" + consultaCpf + "';"
            );

            while (this.conectar.getResultSet().next()) {
                novaSolicitacao.setMateria(this.conectar.getResultSet().getString(1));
                novaSolicitacao.setAluno(this.conectar.getResultSet().getString(2));
                novaSolicitacao.setDuvida(this.conectar.getResultSet().getString(3));
                novaSolicitacao.setResposta(this.conectar.getResultSet().getString(4));
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar solicitacao " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao buscar professor (CPF inválido).");

        } finally {
            consultaMateria.setText(novaSolicitacao.getMateria());
            consultaAluno.setText(novaSolicitacao.getAluno());
            consultaDuvida.setText(novaSolicitacao.getDuvida());
            consultaResposta.setText(novaSolicitacao.getResposta());
        }
    }

    private void deletaSolicitacao(Solicitacao novaSolicitacao) {
        this.conectar.conectaBanco();

        String consultaCpf = this.consultaCpf.getText();

        try {
            this.conectar.updateSQL(
                    "DELETE FROM Solicitacao"
                    + " WHERE "
                    + "cpfProfessor = '" + consultaCpf + "';"
            );
        } catch (Exception e) {
            System.out.println("Erro ao deletar solicitacao " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar solicitacao.");
        } finally {
            this.conectar.fechaBanco();
            limparBusca();
            JOptionPane.showMessageDialog(null, "Solicitacao excluida com sucesso.");
        }
    }

    private void atualizaSolicitacao(Solicitacao novaSolicitacao) {
        this.conectar.conectaBanco();

        String consultaCpf = this.consultaCpf.getText();

        try {
            this.conectar.updateSQL(
                    "UPDATE Solicitacao SET "
                    + "materia = '" + consultaMateria.getText() + "',"
                    + "aluno = '" + consultaAluno.getText() + "',"
                    + "duvida = '" + consultaDuvida.getText() + "',"
                    + "resposta = '" + consultaResposta.getText() + "'"
                    + " WHERE "
                    + "cpfProfessor = '" + consultaCpf + "';"
            );

        } catch (Exception e) {
            System.out.println("Erro ao atualizar solicitacao " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar solicitacao.");
        } finally {
            this.conectar.fechaBanco();
            limparBusca();
            JOptionPane.showMessageDialog(null, "Solicitacao atualizada com sucesso.");
        }
    }

    private void limparBusca() {
        consultaAluno.setText("");
        consultaCpf.setText("");
        consultaDuvida.setText("");
        consultaResposta.setText("");
        consultaMateria.setText("");

    }

    private void limparCadastro() {
        cbxMateria.setSelectedItem(0);
        cbxAluno.setSelectedItem(0);
        txtCpf.setText("");
        txtDuvida.setText("");
        txtResposta.setText("");
    }

    private void cbxMateria() throws SQLException {

        this.conectar.conectaBanco();
        try {
            this.conectar.executarSQL(
                    "SELECT * FROM Materias");
            while (this.conectar.getResultSet().next()) {
                String item = (this.conectar.getResultSet().getString("nome"));
                cbxMateria.addItem(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cbxMateria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    cbxAluno();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void cbxAluno() throws SQLException {
        cbxAluno.removeAllItems();
        String materiaSelecionada = ((String) cbxMateria.getSelectedItem());

        this.conectar.conectaBanco();
        try {
            this.conectar.executarSQL(
                    "SELECT A.nome, A.cpf, D.duvida "
                    + "FROM Alunos A "
                    + "INNER JOIN RelacaoMateriaAluno R ON A.cpf = R.cpfAluno "
                    + "INNER JOIN Materias M ON M.idMateria = R.idMateria "
                    + "LEFT JOIN Duvidas D ON A.cpf = D.cpf "
                    + "WHERE M.nome = '" + materiaSelecionada + "'");

            while (this.conectar.getResultSet().next()) {
                String nomeAluno = this.conectar.getResultSet().getString("nome");
                String cpfAluno = this.conectar.getResultSet().getString("cpf");
                String duvidaAluno = this.conectar.getResultSet().getString("duvida");

                cbxAluno.addItem(nomeAluno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cbxAluno.addActionListener(e -> {
            try {

                String alunoSelecionado = ((String) cbxAluno.getSelectedItem());
                atualizarDuvida(alunoSelecionado);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void atualizarDuvida(String alunoSelecionado) throws SQLException {
        this.conectar.conectaBanco();
        try {
            this.conectar.executarSQL(
                    "SELECT duvida "
                    + "FROM Duvidas "
                    + "WHERE cpf = (SELECT cpf FROM Alunos WHERE nome = '" + alunoSelecionado + "')");

            if (this.conectar.getResultSet().next()) {
                String duvidaAluno = this.conectar.getResultSet().getString("duvida");
                txtDuvida.setText(duvidaAluno);
            } else {
                txtDuvida.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtResposta = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDuvida = new javax.swing.JTextArea();
        cbxMateria = new javax.swing.JComboBox<>();
        cbxAluno = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        consultaMateria = new javax.swing.JTextField();
        consultaAluno = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        consultaDuvida = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        consultaResposta = new javax.swing.JTextArea();
        btnDeletar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        consultaCpf = new javax.swing.JFormattedTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btnLimparBusca = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JFormattedTextField();
        btnLimparCadastro = new javax.swing.JButton();
        telaFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Resposta:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Matéria");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 60, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Aluno");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Dúvida");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        txtResposta.setColumns(20);
        txtResposta.setRows(5);
        jScrollPane2.setViewportView(txtResposta);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, -1, -1));

        txtDuvida.setEditable(false);
        txtDuvida.setColumns(20);
        txtDuvida.setRows(5);
        jScrollPane1.setViewportView(txtDuvida);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

        jPanel1.add(cbxMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 210, -1));

        jPanel1.add(cbxAluno, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 210, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("TELA SOLICITAÇÃO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, -1, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da solicitação"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(consultaMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 140, -1));

        consultaAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaAlunoActionPerformed(evt);
            }
        });
        jPanel3.add(consultaAluno, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 140, -1));

        consultaDuvida.setColumns(20);
        consultaDuvida.setRows(5);
        jScrollPane3.setViewportView(consultaDuvida);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        consultaResposta.setColumns(20);
        consultaResposta.setRows(5);
        jScrollPane4.setViewportView(consultaResposta);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        btnDeletar.setText("Deletar");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });
        jPanel3.add(btnDeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, -1, -1));

        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });
        jPanel3.add(btnAtualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, -1, -1));

        jLabel8.setText("Matéria:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel9.setText("Aluno");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        jLabel10.setText("Pergunta:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel11.setText("Resposta:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 330, 380));

        try {
            consultaCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel2.add(consultaCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 120, -1));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, -1, -1));

        jLabel12.setText("CPF Professor:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        btnLimparBusca.setText("Limpar");
        btnLimparBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparBuscaActionPerformed(evt);
            }
        });
        jPanel2.add(btnLimparBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 170, 430, 460));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/logo.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 160, 130));

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 560, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("CPF do professor responsável:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, -1, -1));

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(txtCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 160, -1));

        btnLimparCadastro.setText("Limpar");
        btnLimparCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCadastroActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimparCadastro, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 560, -1, -1));

        telaFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/fundo.png"))); // NOI18N
        jPanel1.add(telaFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -1, 1050, 660));

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

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        cadastraSolicitacao(novaSolicitacao);
        limparCadastro();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void consultaAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaAlunoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_consultaAlunoActionPerformed

    private void btnLimparCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCadastroActionPerformed
        limparCadastro();
    }//GEN-LAST:event_btnLimparCadastroActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscaSolicitacao(novaSolicitacao);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimparBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparBuscaActionPerformed
        limparBusca();
    }//GEN-LAST:event_btnLimparBuscaActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        deletaSolicitacao(novaSolicitacao);
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        atualizaSolicitacao(novaSolicitacao);
    }//GEN-LAST:event_btnAtualizarActionPerformed

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
            java.util.logging.Logger.getLogger(telaDuvida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(telaDuvida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(telaDuvida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(telaDuvida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new telaDuvida().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(telaDuvida.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnLimparBusca;
    private javax.swing.JButton btnLimparCadastro;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cbxAluno;
    private javax.swing.JComboBox<String> cbxMateria;
    private javax.swing.JTextField consultaAluno;
    private javax.swing.JFormattedTextField consultaCpf;
    private javax.swing.JTextArea consultaDuvida;
    private javax.swing.JTextField consultaMateria;
    private javax.swing.JTextArea consultaResposta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel telaFundo;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextArea txtDuvida;
    private javax.swing.JTextArea txtResposta;
    // End of variables declaration//GEN-END:variables
}
