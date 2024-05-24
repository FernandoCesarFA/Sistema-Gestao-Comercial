package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Cliente;

public class IgCadastroCliente extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField cpfTextField;
    private JTextField nomeTextField;
    private JTextField emailTextField;
    private JTextField telefoneTextField;
    private JTextArea enderecoTextArea;
    private DAO<Cliente> clienteDao;

    /**
     * Create the dialog.
     */
    public IgCadastroCliente(Component janelaPai, DAO<Cliente> clienteDao, List<Cliente> clienteList) {
        this.clienteDao = clienteDao;

        setBounds(100, 100, 339, 317);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Cadastro de Cliente", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(6, 6, 310, 237);
        contentPanel.add(mainPanel);

        JLabel cpfLabel = new JLabel("CPF/CNPJ:");
        cpfLabel.setDisplayedMnemonic(KeyEvent.VK_P);
        cpfLabel.setBounds(16, 29, 70, 16);
        mainPanel.add(cpfLabel);

        // Configurar o campo de CPF/CNPJ
        cpfTextField = new JTextField();
        cpfTextField.setColumns(14);
        cpfTextField.setBounds(84, 23, 215, 28);
        mainPanel.add(cpfTextField);

        // Adicionar documento personalizado para CPF/CNPJ
        cpfTextField.setDocument(new CPFOrCNPJDocument());

        nomeTextField = new JTextField();
        nomeTextField.setColumns(10);
        nomeTextField.setBounds(84, 57, 215, 28);
        mainPanel.add(nomeTextField);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        nomeLabel.setBounds(16, 63, 47, 16);
        mainPanel.add(nomeLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setDisplayedMnemonic(KeyEvent.VK_M);
        emailLabel.setBounds(16, 102, 47, 16);
        mainPanel.add(emailLabel);

        emailTextField = new JTextField();
        emailTextField.setColumns(10);
        emailTextField.setBounds(84, 96, 215, 28);
        mainPanel.add(emailTextField);

        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setDisplayedMnemonic(KeyEvent.VK_T);
        telefoneLabel.setBounds(16, 140, 60, 16);
        mainPanel.add(telefoneLabel);

        telefoneTextField = new JTextField();
        telefoneTextField.setColumns(10);
        telefoneTextField.setBounds(84, 134, 215, 28);
        mainPanel.add(telefoneTextField);

        // Adicionar documento personalizado para Telefone
        telefoneTextField.setDocument(new PhoneDocument());

        JLabel enderecoLabel = new JLabel("Endereço:");
        enderecoLabel.setDisplayedMnemonic(KeyEvent.VK_E);
        enderecoLabel.setBounds(16, 173, 85, 16);
        mainPanel.add(enderecoLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(84, 168, 215, 58);
        mainPanel.add(scrollPane);

        enderecoTextArea = new JTextArea();
        enderecoTextArea.setWrapStyleWord(true);
        enderecoTextArea.setLineWrap(true);
        scrollPane.setViewportView(enderecoTextArea);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 243, 323, 34);
        contentPanel.add(panel);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setMnemonic(KeyEvent.VK_D);
        cadastrarButton.setBackground(Color.WHITE);
        cadastrarButton.setBounds(136, 0, 90, 28);
        panel.add(cadastrarButton);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.setMnemonic(KeyEvent.VK_C);
        cancelarButton.setBackground(Color.WHITE);
        cancelarButton.setActionCommand("Cancel");
        cancelarButton.setBounds(227, 0, 90, 28);
        panel.add(cancelarButton);

        // Fecha a janela quando o botão cancelar for precionado.
        cancelarButton.addActionListener((e) -> {
            processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        // Cadastra
        cadastrarButton.addActionListener((e) -> cadastrarCliente(clienteList));

        setModal(true);
        setResizable(false);
        setLocationRelativeTo(janelaPai);
        setVisible(true);
    }

    private void cadastrarCliente(List<Cliente> clienteList) {
        var mensagemDeErro = new StringBuilder();
        Cliente cliente = new Cliente();

        try {
            if (!clienteList.stream().filter((c) -> c.getDocumento().equals(cpfTextField.getText())).toList().isEmpty()) {
                throw new IllegalArgumentException("CPF Já Cadastrado");
            }
            cliente.setDocumento(cpfTextField.getText());
        } catch (Exception e) {
            mensagemDeErro.append(e.getMessage()).append("\n");
        }
        try {
            cliente.setNomeCliente(nomeTextField.getText());
        } catch (Exception e) {
            mensagemDeErro.append(e.getMessage()).append("\n");
        }
        try {
            cliente.setEmail(emailTextField.getText());
        } catch (Exception e) {
            mensagemDeErro.append(e.getMessage()).append("\n");
        }
        try {
            cliente.setTelefone(telefoneTextField.getText());
        } catch (Exception e) {
            mensagemDeErro.append(e.getMessage()).append("\n");
        }
        try {
            cliente.setEndereco(enderecoTextArea.getText());
        } catch (Exception e) {
            mensagemDeErro.append(e.getMessage()).append("\n");
        }

        if (mensagemDeErro.isEmpty()) {
            clienteDao.adiciona(cliente);
            clienteList.add(cliente);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, mensagemDeErro.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Documento personalizado para CPF/CNPJ
    private static class CPFOrCNPJDocument extends PlainDocument {
        private static final long serialVersionUID = 1L;
        private static final String CPF_MASK = "###.###.###-##";
        private static final String CNPJ_MASK = "##.###.###/####-##";

        @Override
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException {
            if (str == null) return;

            StringBuilder currentText = new StringBuilder(getText(0, getLength()));
            currentText.insert(offset, str);
            String text = currentText.toString().replaceAll("\\D", "");

            if (text.length() <= 11) {
                super.remove(0, getLength());
                super.insertString(0, applyMask(text, CPF_MASK), attr);
            } else {
                super.remove(0, getLength());
                super.insertString(0, applyMask(text, CNPJ_MASK), attr);
            }
        }

        @Override
        public void remove(int offset, int length) throws BadLocationException {
            StringBuilder currentText = new StringBuilder(getText(0, getLength()));
            currentText.delete(offset, offset + length);
            String text = currentText.toString().replaceAll("\\D", "");

            if (text.length() <= 11) {
                super.remove(0, getLength());
                super.insertString(0, applyMask(text, CPF_MASK), null);
            } else {
                super.remove(0, getLength());
                super.insertString(0, applyMask(text, CNPJ_MASK), null);
            }
        }

        private String applyMask(String text, String mask) {
            StringBuilder maskedText = new StringBuilder();
            int textIndex = 0;
            for (int i = 0; i < mask.length(); i++) {
                if (mask.charAt(i) == '#') {
                    if (textIndex < text.length()) {
                        maskedText.append(text.charAt(textIndex));
                        textIndex++;
                    } else {
                        break;
                    }
                } else {
                    maskedText.append(mask.charAt(i));
                }
            }
            return maskedText.toString();
        }
    }

    // Documento personalizado para Telefone
    private static class PhoneDocument extends PlainDocument {
        private static final long serialVersionUID = 1L;
        private static final String PHONE_MASK = "(##) #####-####";

        @Override
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException {
            if (str == null) return;

            StringBuilder currentText = new StringBuilder(getText(0, getLength()));
            currentText.insert(offset, str);
            String text = currentText.toString().replaceAll("\\D", "");

            super.remove(0, getLength());
            super.insertString(0, applyMask(text, PHONE_MASK), attr);
        }

        @Override
        public void remove(int offset, int length) throws BadLocationException {
            StringBuilder currentText = new StringBuilder(getText(0, getLength()));
            currentText.delete(offset, offset + length);
            String text = currentText.toString().replaceAll("\\D", "");

            super.remove(0, getLength());
            super.insertString(0, applyMask(text, PHONE_MASK), null);
        }

        private String applyMask(String text, String mask) {
            StringBuilder maskedText = new StringBuilder();
            int textIndex = 0;
            for (int i = 0; i < mask.length(); i++) {
                if (mask.charAt(i) == '#') {
                    if (textIndex < text.length()) {
                        maskedText.append(text.charAt(textIndex));
                        textIndex++;
                    } else {
                        break;
                    }
                } else {
                    maskedText.append(mask.charAt(i));
                }
            }
            return maskedText.toString();
        }
    }
}
