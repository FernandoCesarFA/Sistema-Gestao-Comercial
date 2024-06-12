package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
    private JTable vendasTable;
    private DefaultTableModel vendasTableModel;
    //private List<Venda> vendaAtualList = new ArrayList<Venda>();

    public IgVenda(Component janelaPai, List<Cliente> clienteList, List<Produto> produtoList, List<Venda> vendaList, DAO<Venda> vendaDao) {
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

        // Criar o modelo de tabela para produtos não editáveis
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Nome do Produto", "Quantidade em Estoque", "Preço"}
        ) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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
        tabelaVendaPanel.setBackground(new Color(255, 255, 255));
        tabelaVendaPanel.setBounds(6, 55, 522, 290);
        vendaPanel.add(tabelaVendaPanel);
        tabelaVendaPanel.setLayout(new BorderLayout());

        // Criação da tabela de vendas
        vendasTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Cliente", "Quantidade", "Produto","Preço Unitário", "Valor Total"}
        ) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3; // Permitir edição de Quantidade e Preço Unitário
            }
        };
        vendasTable = new JTable(vendasTableModel);
        vendasTable.setFillsViewportHeight(true);
        vendasTable.setBackground(Color.WHITE);
        vendasTable.getTableHeader().setBackground(Color.WHITE);
        vendasTable.setShowGrid(true);
        vendasTable.setGridColor(Color.LIGHT_GRAY);
        JScrollPane vendasScrollPane = new JScrollPane(vendasTable);
        tabelaVendaPanel.add(vendasScrollPane, BorderLayout.CENTER);

        vendasTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 2 || column == 3) {
                        atualizarValorTotal(row);
                    }
                }
            }
        });

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
        lblProdutos.setVerticalAlignment(SwingConstants.TOP);
        lblProdutos.setForeground(new Color(0, 128, 128));
        lblProdutos.setFont(new Font("SansSerif", Font.BOLD, 16));

        numeroProdutosLabel = new JLabel();
        numeroProdutosLabel.setText("0");
        numeroProdutosLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        numeroProdutosPanel.add(numeroProdutosLabel);

        clienteLabel = new JLabel("Cliente");
        clienteLabel.setLabelFor(clienteComboBox);
        clienteLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        clienteLabel.setBounds(12, 38, 42, 15);
        panel.add(clienteLabel);

        clienteComboBox = new JComboBox<>();
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

        // Adicionar evento de tecla para a tabela de produtos
        produtosTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int selectedRow = produtosTable.getSelectedRow();
                    if (selectedRow != -1) {
                        adicionarProdutoAVenda(selectedRow);
                    }
                }
            }
        });
        
        // Adicionar evento de tecla para a tabela de vendas
        vendasTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int selectedRow = vendasTable.getSelectedRow();
                    if (selectedRow != -1) {
                        removerProdutoVenda(selectedRow);
                    }
                }
            }
        });

        setModal(true);
        setResizable(false);
        setLocationRelativeTo(janelaPai);
        setVisible(true);
    }
    
    private void removerProdutoVenda(int selectedRow) {
    	((DefaultTableModel) vendasTable.getModel()).removeRow(selectedRow);
    	atualizarTotais();
	}

    private void adicionarProdutoAVenda(int row) {
        String nomeProduto = (String) produtosTable.getValueAt(row, 0);
        String cliente = (String) clienteComboBox.getSelectedItem();
        int quantidade = 1;  // Quantidade padrão, você pode adicionar lógica para personalizar
        String preco = (String) produtosTable.getValueAt(row, 2);
        preco = preco.replace("R$: ", "").replace(",", ".");

        // Verificar se o produto já está na tabela de vendas
        for (int i = 0; i < vendasTableModel.getRowCount(); i++) {
            if (vendasTableModel.getValueAt(i, 1).equals(nomeProduto)) {
                JOptionPane.showMessageDialog(this, "O produto já está no carrinho.", "Produto Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        float precoUnitario = Float.parseFloat(preco);
        vendasTableModel.addRow(new Object[] {cliente, nomeProduto, quantidade, precoUnitario, precoUnitario * quantidade});

        atualizarTotais();
    }

    private void atualizarValorTotal(int row) {
        int quantidade = Integer.parseInt(vendasTableModel.getValueAt(row, 2).toString());
        float precoUnitario = Float.parseFloat( vendasTableModel.getValueAt(row, 3).toString());
        float valorTotal = quantidade * precoUnitario;
        vendasTableModel.setValueAt(valorTotal, row, 4);
        atualizarTotais();
    }

    private void atualizarTotais() {
        int totalProdutos = vendasTableModel.getRowCount();
        double valorTotal = 0.0;
        for (int i = 0; i < totalProdutos; i++) {
            valorTotal += Float.parseFloat(vendasTableModel.getValueAt(i, 4).toString());
        }
        numeroProdutosLabel.setText(String.valueOf(totalProdutos));
        valorTotalLabel.setText(String.format("R$: %.2f", valorTotal));
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
