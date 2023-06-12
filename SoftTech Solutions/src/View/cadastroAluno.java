/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import javax.swing.JOptionPane;
import objetos.Aluno;
import conexoes.MySQL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeani
 */
public class cadastroAluno extends javax.swing.JFrame {
    

    //Criando um objeto MySQL para conexão com o banco
    MySQL conectar = new MySQL();
    
    Aluno novoAluno = new Aluno();

    public cadastroAluno() throws SQLException {
        initComponents();
    }

    private void cadastraAluno(Aluno novoAluno) {
        this.conectar.conectaBanco();

        
        novoAluno.setNome(txtNome.getText());
        novoAluno.setSenha(txtSenha.getText());
        novoAluno.setTelefone(txtTelefone.getText());
        novoAluno.setCpf(txtCpf.getText());
        novoAluno.setEmail(txtEmail.getText());
        novoAluno.setSexo((String) cbxSexo.getSelectedItem());
        novoAluno.setInstituicao(txtInstituicao.getText());
        novoAluno.setCurso((String) cbxCursos.getSelectedItem());

        try {
            this.conectar.insertSQL("INSERT INTO Alunos("
                    + "nome,"
                    + "senha,"
                    + "telefone,"
                    + "cpf,"
                    + "email,"
                    + "sexo,"
                    + "curso,"
                    + "instituicao)"
                    + "VALUES ("
                    + "'" + novoAluno.getNome() + "',"
                    + "'" + novoAluno.getSenha() + "',"
                    + "'" + novoAluno.getTelefone() + "',"
                    + "'" + novoAluno.getCpf() + "',"
                    + "'" + novoAluno.getEmail() + "',"
                    + "'" + novoAluno.getSexo() + "',"
                    + "'" + novoAluno.getCurso() + "',"
                    + "'" + novoAluno.getInstituicao() + "'"
                    + ");"
            );

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluno "
                    + e.getMessage());
        } finally {
            this.conectar.fechaBanco();
            JOptionPane.showMessageDialog(rootPane, "Aluno Cadastrado com sucesso!");
        }
    }

    private void buscaAluno(Aluno novoAluno) {
        this.conectar.conectaBanco();

        String consultaCpf = this.consultaCpf.getText(); //O CONSULTACPF É O NOME DO TXT DE BUSCA

        if (!consultaCpf.equals("   .   .   -  ")) {
            try {
                this.conectar.executarSQL(
                        "SELECT "
                        + "nome,"
                        + "telefone,"
                        + "email,"
                        + "sexo,"
                        + "instituicao,"
                        + "curso "
                        + "FROM "
                        + "Alunos "
                        + "WHERE "
                        + "cpf = '" + consultaCpf + "';"
                );

                while (this.conectar.getResultSet().next()) {
                    novoAluno.setNome(this.conectar.getResultSet().getString(1));
                    novoAluno.setTelefone(this.conectar.getResultSet().getString(2));
                    novoAluno.setEmail(this.conectar.getResultSet().getString(3));
                    novoAluno.setSexo(this.conectar.getResultSet().getString(4));
                    novoAluno.setInstituicao(this.conectar.getResultSet().getString(5));
                    novoAluno.setCurso(this.conectar.getResultSet().getString(6));
                }
                if (novoAluno.getNome().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
                }

            } catch (Exception e) {
                System.out.println("Erro ao consultar aluno " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao buscar aluno (CPF inválido).");

            } finally {
                consultaNome.setText(novoAluno.getNome());
                consultaTelefone.setText(novoAluno.getTelefone());
                consultaEmail.setText(novoAluno.getEmail());
                consultaSexo.setText(novoAluno.getSexo());
                consultaInstituicao.setText(novoAluno.getInstituicao());
                consultaCurso.setText(novoAluno.getCurso());
                this.conectar.fechaBanco();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Digite um CPF.");
        }

    }

    private void deletaAluno(Aluno novoAluno) {
        this.conectar.conectaBanco();

        String consultaCpf = this.consultaCpf.getText();

        try {
            this.conectar.updateSQL(
                    "DELETE FROM Alunos "
                    + "WHERE "
                    + "cpf = '" + consultaCpf + "';"
            );

        } catch (Exception e) {
            System.out.println("Erro ao deletar aluno " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao deletar aluno.");
        } finally {
            this.conectar.fechaBanco();
            limparBusca(); 
            JOptionPane.showMessageDialog(null, "Aluno deletado com sucesso.");
        }

    }

    private void atualizaAluno(Aluno novoAluno) {
        this.conectar.conectaBanco();

        String consultaCpf = this.consultaCpf.getText();

        try {
            this.conectar.updateSQL(
                    "UPDATE Alunos SET "
                    + "nome = '" + consultaNome.getText() + "',"
                    + "telefone = '" + consultaTelefone.getText() + "',"
                    + "email = '" + consultaEmail.getText() + "',"
                    + "sexo = '" + consultaSexo.getText() + "',"
                    + "curso = '" + consultaCurso.getText() + "',"
                    + "instituicao = '" + consultaInstituicao.getText() + "'"
                    + " WHERE "
                    + "cpf = '" + consultaCpf + "';"
            );
        } catch (Exception e) {
            System.out.println("Erro ao atualizar aluno " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar aluno.");
        } finally {
            this.conectar.fechaBanco();
            limparBusca();
            JOptionPane.showMessageDialog(null, "Aluno atualizado com sucesso.");
        }
    }

    private void limparCadastro() {
        txtNome.setText("");
        txtSenha.setText("");
        txtTelefone.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtInstituicao.setText("");
        cbxCursos.setSelectedItem(0);
        cbxSexo.setSelectedItem(0);
    }

    private void limparBusca() {
        consultaNome.setText("");
        consultaTelefone.setText("");
        consultaCpf.setText("");
        consultaEmail.setText("");
        consultaInstituicao.setText("");
        consultaCurso.setText("");
        consultaSexo.setText("");

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
        imagemEmpresa = new javax.swing.JLabel();
        dadosPessoais = new javax.swing.JLabel();
        nome = new javax.swing.JLabel();
        telefone = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JFormattedTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        cbxSexo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtInstituicao = new javax.swing.JTextField();
        cbxCursos = new javax.swing.JComboBox<>();
        txtNome = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JFormattedTextField();
        btnCadastro = new javax.swing.JButton();
        btnLimparCadastro = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        consultaCpf = new javax.swing.JFormattedTextField();
        consultaBuscar = new javax.swing.JButton();
        consultaLimpar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        consultaNome = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        consultaSexo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        consultaCurso = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        consultaInstituicao = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        consultaEmail = new javax.swing.JTextField();
        consultaDeletar = new javax.swing.JButton();
        consultaAtualizar = new javax.swing.JButton();
        consultaTelefone = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        telaFundo = new javax.swing.JLabel();
        txtSenha1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imagemEmpresa.setBackground(new java.awt.Color(0, 0, 0));
        imagemEmpresa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        imagemEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/logo.png"))); // NOI18N
        jPanel1.add(imagemEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 190, 140));

        dadosPessoais.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dadosPessoais.setForeground(new java.awt.Color(0, 0, 0));
        dadosPessoais.setText("Dados Pessoais");
        jPanel1.add(dadosPessoais, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 140, -1));

        nome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nome.setForeground(new java.awt.Color(0, 0, 0));
        nome.setText("Nome Completo:");
        jPanel1.add(nome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 110, -1));

        telefone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        telefone.setForeground(new java.awt.Color(0, 0, 0));
        telefone.setText("Telefone:");
        jPanel1.add(telefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 170, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Email:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("CPF:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, -1, -1));

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(txtCpf, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, 270, -1));
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 260, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Senha:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Sexo:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 310, -1, -1));
        jPanel1.add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 260, -1));

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino", "Outro" }));
        cbxSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSexoActionPerformed(evt);
            }
        });
        jPanel1.add(cbxSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, 170, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Instituição:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Curso:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 380, -1, -1));
        jPanel1.add(txtInstituicao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 260, -1));

        cbxCursos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Analise e Desenvolvimento de Sistema", "Engenharia de Computação", "Jogos Digitais" }));
        jPanel1.add(cbxCursos, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, -1, -1));

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });
        jPanel1.add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 260, -1));

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefoneActionPerformed(evt);
            }
        });
        jPanel1.add(txtTelefone, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 270, -1));

        btnCadastro.setText("CADASTRAR");
        btnCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastroActionPerformed(evt);
            }
        });
        jPanel1.add(btnCadastro, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 480, 160, 70));

        btnLimparCadastro.setText("LIMPAR");
        btnLimparCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCadastroActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimparCadastro, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 480, 160, 70));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Busca Aluno"));

        jLabel7.setText("CPF:");

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

        consultaBuscar.setText("Buscar");
        consultaBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaBuscarActionPerformed(evt);
            }
        });

        consultaLimpar.setText("Limpar");
        consultaLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaLimparActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Aluno"));

        jLabel8.setText("Nome:");

        jLabel9.setText("Telefone:");

        jLabel10.setText("Sexo:");

        jLabel11.setText("Curso:");

        jLabel12.setText("Instituição:");

        consultaInstituicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaInstituicaoActionPerformed(evt);
            }
        });

        jLabel13.setText("Email:");

        consultaDeletar.setText("Deletar");
        consultaDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaDeletarActionPerformed(evt);
            }
        });

        consultaAtualizar.setText("Atualizar");
        consultaAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaAtualizarActionPerformed(evt);
            }
        });

        try {
            consultaTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(consultaDeletar)
                        .addGap(35, 35, 35)
                        .addComponent(consultaAtualizar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(consultaNome, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9)
                            .addComponent(consultaTelefone, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(consultaEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(consultaCurso))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(consultaInstituicao, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(consultaSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(consultaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(consultaTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(consultaCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(consultaInstituicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(consultaEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(consultaSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(consultaDeletar)
                    .addComponent(consultaAtualizar)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(consultaCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(consultaBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(consultaLimpar)
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(consultaCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(consultaBuscar)
                    .addComponent(consultaLimpar))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 140, 440, 410));
        jPanel2.getAccessibleContext().setAccessibleParent(this);

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 120, 30));

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Ja tem login?");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        telaFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/fundo.png"))); // NOI18N
        telaFundo.setText("jLabel2");
        jPanel1.add(telaFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 600));
        jPanel1.add(txtSenha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 260, -1));

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

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefoneActionPerformed

    private void btnCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastroActionPerformed
        cadastraAluno(novoAluno);
        limparCadastro();
    }//GEN-LAST:event_btnCadastroActionPerformed

    private void consultaAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaAtualizarActionPerformed
        atualizaAluno(novoAluno);
    }//GEN-LAST:event_consultaAtualizarActionPerformed

    private void consultaCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_consultaCpfActionPerformed

    private void consultaInstituicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaInstituicaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_consultaInstituicaoActionPerformed

    private void btnLimparCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCadastroActionPerformed
        limparCadastro();
    }//GEN-LAST:event_btnLimparCadastroActionPerformed

    private void consultaBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaBuscarActionPerformed
        buscaAluno(novoAluno);
    }//GEN-LAST:event_consultaBuscarActionPerformed

    private void consultaLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaLimparActionPerformed
        limparBusca();
    }//GEN-LAST:event_consultaLimparActionPerformed

    private void consultaDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaDeletarActionPerformed
        deletaAluno(novoAluno);
    }//GEN-LAST:event_consultaDeletarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        loginAluno la = null;
        try {
            la = new loginAluno();
        } catch (SQLException ex) {
            Logger.getLogger(cadastroAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
        la.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbxSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxSexoActionPerformed

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
            java.util.logging.Logger.getLogger(cadastroAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cadastroAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cadastroAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cadastroAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new cadastroAluno().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(cadastroAluno.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastro;
    private javax.swing.JButton btnLimparCadastro;
    private javax.swing.JComboBox<String> cbxCursos;
    private javax.swing.JComboBox<String> cbxSexo;
    private javax.swing.JButton consultaAtualizar;
    private javax.swing.JButton consultaBuscar;
    private javax.swing.JFormattedTextField consultaCpf;
    private javax.swing.JTextField consultaCurso;
    private javax.swing.JButton consultaDeletar;
    private javax.swing.JTextField consultaEmail;
    private javax.swing.JTextField consultaInstituicao;
    private javax.swing.JButton consultaLimpar;
    private javax.swing.JTextField consultaNome;
    private javax.swing.JTextField consultaSexo;
    private javax.swing.JFormattedTextField consultaTelefone;
    private javax.swing.JLabel dadosPessoais;
    private javax.swing.JLabel imagemEmpresa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel nome;
    private javax.swing.JLabel telaFundo;
    private javax.swing.JLabel telefone;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtInstituicao;
    private javax.swing.JTextField txtNome;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JPasswordField txtSenha1;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
