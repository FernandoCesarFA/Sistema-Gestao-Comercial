package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Cliente;
import gestaocomercial.dominio.Produto;
import gestaocomercial.dominio.Venda;
import gestaocomercial.utilitarios.Utilitario;

public class IgVenda extends JDialog implements Utilitario {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField buscarTextField;
    private JLabel clienteLabel;
    private JComboBox<String> clienteComboBox;
    private JLabel lblBuscar;
    private JPanel tabelaProdutosPanel;
    private JPanel tabelaVendaPanel;
    private JLabel lblProdutos;
    private JLabel numeroProdutosLabel;
    private JLabel valorTotalLabel;
    private JButton vendaButton;
    private JButton cancelarButton;
    private JTable produtosTable;

    public IgVenda(List<Cliente> clienteList, List<Produto> produtoList, List<Venda> vendaList, DAO<Venda> vendaDao) {
        String[] clientes = IgJanelaPrincipal.obterNomesClientes(clienteList).toArray(new String[0]);

        setBounds(100, 100, 1154, 521);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Venda", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(59, 59, 59)));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(0, 6, 1137, 470);
        contentPanel.add(panel);
        panel.setLayout(null);

        JPanel produtosPanel = new JPanel();
        produtosPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Produtos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
        produtosPanel.setBackground(new Color(255, 255, 255));
        produtosPanel.setBounds(18, 83, 534, 351);
        panel.add(produtosPanel);
        produtosPanel.setLayout(null);

        lblBuscar = new JLabel("Buscar:");
        lblBuscar.setDisplayedMnemonic(KeyEvent.VK_B);
        lblBuscar.setBounds(16, 27, 49, 16);
        produtosPanel.add(lblBuscar);

        buscarTextField = new JTextField();
        buscarTextField.setColumns(10);
        buscarTextField.setBounds(63, 21, 176, 24);
        produtosPanel.add(buscarTextField);

        tabelaProdutosPanel = new JPanel();
        tabelaProdutosPanel.setBackground(new Color(255, 255, 255));
        tabelaProdutosPanel.setBounds(6, 55, 522, 290);
        produtosPanel.add(tabelaProdutosPanel);
        tabelaProdutosPanel.setLayout(new BorderLayout());

        // Criar o modelo de tabela
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Nome do Produto", "Quantidade em Estoque", "Preço"}
        );

        // Preencher o modelo de tabela com dados dos produtos
        for (Produto produto : produtoList) {
            tableModel.addRow(new Object[] {
                produto.getNomeProduto(),
                produto.getQuantidadeEstoque(),
                String.format("%s%s", "R$: ", DECIMAL_FORMAT.format(produto.getPreco()))
            });
        }

        // Criar a tabela com o modelo de tabela
        produtosTable = new JTable(tableModel);

        // Configurar propriedades da tabela
        produtosTable.getTableHeader().setReorderingAllowed(false);
        produtosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        produtosTable.setFillsViewportHeight(true);
        produtosTable.setBackground(Color.WHITE);
        produtosTable.setRowSelectionAllowed(true);
        produtosTable.getTableHeader().setBackground(Color.WHITE);
        produtosTable.setShowGrid(true);
        produtosTable.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(produtosTable);
        tabelaProdutosPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel vendaPanel = new JPanel();
        vendaPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Carrinho", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        vendaPanel.setBackground(new Color(255, 255, 255));
        vendaPanel.setBounds(588, 83, 534, 351);
        panel.add(vendaPanel);
        vendaPanel.setLayout(null);

        tabelaVendaPanel = new JPanel();
        tabelaVendaPanel.setBounds(6, 55, 522, 290);
        vendaPanel.add(tabelaVendaPanel);

        JPanel totalPanel = new JPanel();
        totalPanel.setBackground(new Color(255, 255, 255));
        totalPanel.setBounds(371, 27, 157, 23);
        vendaPanel.add(totalPanel);
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.X_AXIS));

        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setVerticalAlignment(SwingConstants.TOP);
        totalPanel.add(lblTotal);
        lblTotal.setForeground(new Color(0, 128, 128));
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));

        valorTotalLabel = new JLabel();
        valorTotalLabel.setText("0");
        valorTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalPanel.add(valorTotalLabel);

        JPanel numeroProdutosPanel = new JPanel();
        numeroProdutosPanel.setBackground(new Color(255, 255, 255));
        numeroProdutosPanel.setBounds(202, 27, 157, 23);
        vendaPanel.add(numeroProdutosPanel);
        numeroProdutosPanel.setLayout(new BoxLayout(numeroProdutosPanel, BoxLayout.X_AXIS));

        lblProdutos = new JLabel("Produtos:");
        numeroProdutosPanel.add(lblProdutos);
        lblProdutos.setForeground(new Color(0, 128, 128));
        lblProdutos.setFont(new Font("SansSerif", Font.BOLD, 16));

        numeroProdutosLabel = new JLabel();
        numeroProdutosLabel.setText("0");
        numeroProdutosLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        numeroProdutosPanel.add(numeroProdutosLabel);

        clienteLabel = new JLabel("Cliente:");
        clienteLabel.setDisplayedMnemonic(KeyEvent.VK_T);
        clienteLabel.setBounds(18, 43, 57, 16);
        panel.add(clienteLabel);

        clienteComboBox = new JComboBox<String>();
        clienteComboBox.setModel(new DefaultComboBoxModel<>(clientes));
        clienteComboBox.setSelectedIndex(0);
        clienteComboBox.setMaximumRowCount(8);
        clienteComboBox.setBorder(null);
        clienteComboBox.setBackground(Color.WHITE);
        clienteComboBox.setBounds(66, 38, 326, 21);
        panel.add(clienteComboBox);

        vendaButton = new JButton("Realizar Venda");
        vendaButton.setMnemonic(KeyEvent.VK_R);
        vendaButton.setBackground(Color.WHITE);
        vendaButton.setActionCommand("");
        vendaButton.setBounds(906, 436, 113, 28);
        panel.add(vendaButton);

        cancelarButton = new JButton("Cancelar");
        cancelarButton.setMnemonic(KeyEvent.VK_C);
        cancelarButton.setBackground(Color.WHITE);
        cancelarButton.setActionCommand("Cancel");
        cancelarButton.setBounds(1031, 436, 90, 28);
        panel.add(cancelarButton);

        buscarTextField.addActionListener((e) -> pesquisarProduto(produtosTable));
        
        setVisible(true);
    }

    /**
     * Realiza a pesquisa produto com base no nome.
     *
     * @param produtosTable JTable com a lista de produtos.
     */
    private void pesquisarProduto(JTable produtosTable) {
        String textoBusca = buscarTextField.getText().trim();
        if (textoBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um nome de produto para buscar.", "Pesquisa Produto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numeroDeLinhas = produtosTable.getRowCount();
        for (int i = 0; i < numeroDeLinhas; i++) {
            Object objeto = produtosTable.getValueAt(i, 0);
            if (objeto != null) {
                if (objeto.toString().equalsIgnoreCase(textoBusca)) {
                    produtosTable.requestFocus();
                    produtosTable.changeSelection(i, 0, false, false);
                    produtosTable.requestFocusInWindow();
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Nenhum Produto foi encontrado", "Pesquisa Produto", JOptionPane.INFORMATION_MESSAGE);
    }
}
