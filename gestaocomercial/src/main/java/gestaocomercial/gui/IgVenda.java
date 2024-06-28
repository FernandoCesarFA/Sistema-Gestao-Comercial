package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
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
import gestaocomercial.dominio.Item;
import gestaocomercial.dominio.Produto;
import gestaocomercial.dominio.Venda;
import gestaocomercial.utilitarios.Utilitario;

public class IgVenda extends JDialog implements Utilitario {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
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
    private List<Produto> produtoList;
    private JLabel lblFormaPagamento;
    private JComboBox<String> formaPagamentoComboBox;
    private Component janelaPai;

 
    public IgVenda(Component janelaPai, List<Cliente> clienteList, List<Produto> produtoList, List<Venda> vendaList, 
    			   DAO<Venda> vendaDao, DAO<Produto> produtoDao, DAO<Item> itemDao) {
        
    	String[] clientes = IgJanelaPrincipal.obterNomesClientes(clienteList).toArray(new String[0]);
        this.produtoList = produtoList;
        this.janelaPai = janelaPai;
        
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

        JTextField buscarTextField = new JTextField();
        buscarTextField.setColumns(10);
        buscarTextField.setBounds(63, 27, 176, 18);
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
            new String[] {"Cliente","Produto", "Quantidade","Preço Unitário", "Valor Total"}
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
        totalPanel.setBounds(376, 27, 152, 23);
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
        numeroProdutosPanel.setBounds(241, 27, 123, 23);
        vendaPanel.add(numeroProdutosPanel);
        numeroProdutosPanel.setLayout(new BoxLayout(numeroProdutosPanel, BoxLayout.X_AXIS));

        lblProdutos = new JLabel("Produtos:");
        numeroProdutosPanel.add(lblProdutos);
        lblProdutos.setVerticalAlignment(SwingConstants.TOP);
        lblProdutos.setForeground(new Color(0, 128, 128));
        lblProdutos.setFont(new Font("SansSerif", Font.BOLD, 16));

        numeroProdutosLabel = new JLabel();
        numeroProdutosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numeroProdutosLabel.setText("0");
        numeroProdutosLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        numeroProdutosPanel.add(numeroProdutosLabel);
        
        lblFormaPagamento = new JLabel("Pagmento:");
        lblFormaPagamento.setVerticalAlignment(SwingConstants.TOP);
        lblFormaPagamento.setForeground(new Color(0, 128, 128));
        lblFormaPagamento.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblFormaPagamento.setBounds(20, 27, 89, 21);
        vendaPanel.add(lblFormaPagamento);
        
        String[] formasDePagamento = {"Dinheiro", "Cartão de Crédito", "Cartão de Débito", "Pix"};
        formaPagamentoComboBox = new JComboBox<>(formasDePagamento);
        formaPagamentoComboBox.setBounds(106, 29, 113, 21);
        vendaPanel.add(formaPagamentoComboBox);
        formaPagamentoComboBox.setSelectedIndex(0);
        formaPagamentoComboBox.setMaximumRowCount(8);
        formaPagamentoComboBox.setBorder(null);
        formaPagamentoComboBox.setBackground(Color.WHITE);

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

        buscarTextField.addActionListener((e) -> pesquisarProduto(this, produtosTable, buscarTextField));

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
        
        // Adicionar evento de mouse para a tabela de produtos (duplo clique para adicionar)
        produtosTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = produtosTable.getSelectedRow();
                    if (selectedRow != -1) {
                        adicionarProdutoAVenda(selectedRow);
                    }
                }
            }
        });

        vendaButton.addActionListener(e -> finalizarVenda(clienteList, vendaList, vendaDao, produtoDao, itemDao));
        cancelarButton.addActionListener(e -> dispose());
        
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(janelaPai);
        setVisible(true);
        
    }
    
    private void finalizarVenda(List<Cliente> clienteList, List<Venda> vendaList, DAO<Venda> vendaDao, DAO<Produto> produtoDAO, DAO<Item> itemDao) {
    	try {
            // Inicializa a venda
            Venda venda = new Venda();
            venda.setCliente(clienteList.stream()
                    .filter(c -> c.getNomeCliente().equals((String) vendasTableModel.getValueAt(0, 0)))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado")));
            venda.setFormaPagamento((String) formaPagamentoComboBox.getSelectedItem());

            // Cria uma lista de itens
            List<Item> itemList = new ArrayList<>();
            double valorTotalVenda = 0.0;

            for (int i = 0; i < vendasTableModel.getRowCount(); i++) {
                // Encontra o produto existente
                Produto produto = encontrarProdutoPorNome((String) vendasTableModel.getValueAt(i, 1));

                int quantidade = Integer.parseInt(vendasTableModel.getValueAt(i, 2).toString());

                // Atualiza a quantidade de estoque do produto
                produto.subtraiQuantidadeEmEstoque(quantidade);
                produtoDAO.altera(produto);

                // Cria o item da venda
                Item item = new Item();
                item.setProduto(produto);
                item.setQuantidade(quantidade);
                item.setValorUnitario(Double.parseDouble(vendasTableModel.getValueAt(i, 3).toString().replace("R$: ", "").replace(",", ".")));
                item.setValorTotal(Double.parseDouble(vendasTableModel.getValueAt(i, 4).toString().replace("R$: ", "").replace(",", ".")));

                // Calcula e soma o valor total do item ao valor total da venda
                valorTotalVenda += item.getValorTotal();

                itemList.add(item);
            }

            // Define os atributos restantes da venda
            venda.setDataVenda(LocalDate.now());
            venda.setValorVenda(valorTotalVenda);
            venda.setItemList(itemList);

            // Persiste a venda
            long idVenda = vendaDao.adicionaRetornaId(venda);
            venda.setId(idVenda);

            // Persiste os itens da venda
            for (Item item : itemList) {
                itemDao.adiciona(item);
            }

            // Adiciona a venda à lista de vendas
            vendaList.add(venda);
            // Fecha a janela de venda
            this.dispose();
            janelaPai.requestFocus();
            
            
            JOptionPane.showMessageDialog(janelaPai, "Venda realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(janelaPai, "Erro ao finalizar venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void pesquisarProduto(JDialog janela, JTable produtosTable, JTextField buscarTextField) {
        String textoBusca = buscarTextField.getText().trim();
        if (textoBusca.isEmpty()) {
            JOptionPane.showMessageDialog(janela, "Por favor, insira um nome de produto para buscar.", "Pesquisa Produto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numeroDeLinhas = produtosTable.getRowCount();
        for (int i = 0; i < numeroDeLinhas; i++) {
            Object objeto = produtosTable.getValueAt(i, 0);
            if (objeto != null && objeto.toString().equalsIgnoreCase(textoBusca)) {
                produtosTable.requestFocus();
                produtosTable.changeSelection(i, 0, false, false);
                produtosTable.requestFocusInWindow();
                return;
            }
        }

        JOptionPane.showMessageDialog(janela, "Nenhum Produto foi encontrado", "Pesquisa Produto", JOptionPane.INFORMATION_MESSAGE);
    }

	private void removerProdutoVenda(int selectedRow) {
        ((DefaultTableModel) vendasTable.getModel()).removeRow(selectedRow);
        atualizar();
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
        int quantidadeEstoque = (int) produtosTable.getValueAt(row, 1);

        if (quantidade > quantidadeEstoque) {
            JOptionPane.showMessageDialog(this, "A quantidade vendida é maior do que a quantidade em estoque.", "Erro de Quantidade", JOptionPane.ERROR_MESSAGE);
            return;
        }

        vendasTableModel.addRow(new Object[] {cliente, nomeProduto, quantidade, String.format("R$: %s", DECIMAL_FORMAT.format(precoUnitario)), String.format("R$: %s", DECIMAL_FORMAT.format(precoUnitario * quantidade))});
        
        atualizar();
    }
    
    private void desabilitarCliente() {
		 if (vendasTableModel.getRowCount() > 0) {
	         clienteComboBox.setEnabled(false);
	     }else {
	    	 clienteComboBox.setEnabled(true);
	     }
    }
    
    public void atualizar() {
    	atualizarTotais();
        atualizarTotalProdutos();
        desabilitarCliente();
    }
    
    private void atualizarValorTotal(int row) {
	     try {
    		int quantidade = Integer.parseInt(vendasTableModel.getValueAt(row, 2).toString());
    		String precoUnitarioStr = vendasTableModel.getValueAt(row, 3).toString();
            precoUnitarioStr = precoUnitarioStr.replace("R$:", "").trim().replace(".", "").replace(",", ".");
            float precoUnitario = Float.parseFloat(precoUnitarioStr);
	
	        // Verificar quantidade em estoque
	        String nomeProduto = (String) vendasTableModel.getValueAt(row, 1);
	        Produto produto = encontrarProdutoPorNome(nomeProduto);
	        if (produto != null && quantidade > produto.getQuantidadeEstoque()) {
	            JOptionPane.showMessageDialog(this, "A quantidade vendida é maior do que a quantidade em estoque.", "Erro de Quantidade", JOptionPane.ERROR_MESSAGE);
	            vendasTableModel.setValueAt(produto.getQuantidadeEstoque(), row, 2); // Resetar para quantidade máxima
	            quantidade = produto.getQuantidadeEstoque();
	        }
	
	        float valorTotal = quantidade * precoUnitario;
	        vendasTableModel.setValueAt(String.format("R$: %s", DECIMAL_FORMAT.format(valorTotal)), row, 4);
	        atualizarTotais();
	     }catch (Exception e) {
	    	 e.printStackTrace();
		}
    }

    private Produto encontrarProdutoPorNome(String nomeProduto) {
    	for (Produto produto : produtoList) {
            if (produto.getNomeProduto().equalsIgnoreCase(nomeProduto)) {
                return produto;
            }
        }
        return null;
    }
    
    private void atualizarTotalProdutos() {
    	numeroProdutosLabel.setText(String.format(" %d",vendasTable.getRowCount()));
    }

    private void atualizarTotais() {
        double valorTotal = 0.0;
        for (int i = 0; i < vendasTableModel.getRowCount(); i++) {
            try {
                String valorTotalStr = (String) vendasTableModel.getValueAt(i, 4);
                valorTotalStr = valorTotalStr.replace("R$:", "").trim().replace(".", "").replace(",", ".").replace(" ", "");
                valorTotal += Double.parseDouble(valorTotalStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        valorTotalLabel.setText(String.format(" R$: %s", DECIMAL_FORMAT.format(valorTotal)));
    }
}
