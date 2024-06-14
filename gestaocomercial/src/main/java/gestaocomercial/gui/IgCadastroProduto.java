package gestaocomercial.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Produto;

public class IgCadastroProduto extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField nomeTextField;
    private JTextField quantidateTextFild;
    private JFormattedTextField precoTextField;
    private DAO<Produto> produtoDao;

    public IgCadastroProduto(Component janelaPai, DAO<Produto> produtoDao, List<Produto> produtoList) {
        this.produtoDao = produtoDao;

        setBounds(100, 100, 370, 233);
        getContentPane().setLayout(null);
        setResizable(false);
        contentPanel.setBounds(0, 0, 354, 163);
        contentPanel.setBackground(new Color(255, 255, 255));
        getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Cadastro Produto", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(6, 6, 342, 149);
        contentPanel.add(panel);
        panel.setLayout(null);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        nomeLabel.setBounds(6, 28, 50, 21);
        panel.add(nomeLabel);

        nomeTextField = new JTextField();
        nomeTextField.setColumns(10);
        nomeTextField.setBounds(82, 24, 247, 28);
        panel.add(nomeTextField);

        JLabel quantidaLabel = new JLabel("Quantidade:");
        quantidaLabel.setDisplayedMnemonic(KeyEvent.VK_Q);
        quantidaLabel.setBounds(6, 66, 79, 16);
        panel.add(quantidaLabel);

        quantidateTextFild = new JTextField();
        quantidateTextFild.setColumns(10);
        quantidateTextFild.setBounds(82, 64, 247, 28);
        panel.add(quantidateTextFild);

        JLabel precoLabel = new JLabel("Preço:");
        precoLabel.setDisplayedMnemonic(KeyEvent.VK_P);
        precoLabel.setBounds(6, 110, 47, 16);
        panel.add(precoLabel);

        // Configurar o campo de preço com máscara
        NumberFormat format = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0.0);
        precoTextField = new JFormattedTextField(formatter);
        precoTextField.setColumns(10);
        precoTextField.setBounds(82, 104, 247, 28);
        panel.add(precoTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(0, 155, 354, 38);
        getContentPane().add(buttonPanel);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setMnemonic(KeyEvent.VK_D);
        cadastrarButton.setBackground(Color.WHITE);
        cadastrarButton.setBounds(178, 6, 84, 28);
        buttonPanel.add(cadastrarButton);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.setMnemonic(KeyEvent.VK_C);
        cancelarButton.setBackground(Color.WHITE);
        cancelarButton.setActionCommand("Cancel");
        cancelarButton.setBounds(264, 6, 84, 28);
        buttonPanel.add(cancelarButton);

        cancelarButton.addActionListener((e) -> {
            processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        // Cadastra o produto
        cadastrarButton.addActionListener((e) -> cadastrarProduto(produtoList));

        setModal(true);
        setResizable(false);
        setLocationRelativeTo(janelaPai);
        setVisible(true);
    }

    private void cadastrarProduto(List<Produto> produtoList) {
        var mensagemDeErro = new StringBuilder();
        Produto produto = new Produto();

        try {
            if (!produtoList.stream().filter((p) -> p.getNomeProduto().equals(nomeTextField.getText())).toList().isEmpty()) {
                throw new IllegalArgumentException("Produto Já Cadastrado");
            }
            produto.setNomeProduto(nomeTextField.getText());
        }
        catch (Exception e) {
            mensagemDeErro.append(e.getMessage()).append("\n");
        }
        try {
            produto.setQuantidadeEstoque(Integer.parseInt(quantidateTextFild.getText()));
        }
        catch (Exception e) {
            mensagemDeErro.append(e.getMessage()).append("\n");
        }
        try {
            precoTextField.commitEdit();
            produto.setPreco(((Number) precoTextField.getValue()).doubleValue());
        }
        catch (ParseException e) {
            mensagemDeErro.append("Formato de preço inválido").append("\n");
        }

        if (mensagemDeErro.isEmpty()) {
            produtoDao.adiciona(produto);
            produtoList.add(produto);
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this, mensagemDeErro.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
