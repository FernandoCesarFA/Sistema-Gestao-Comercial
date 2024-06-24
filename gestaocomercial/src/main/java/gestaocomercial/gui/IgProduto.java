package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.List;

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
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Produto;
import gestaocomercial.utilitarios.Utilitario;

public class IgProduto extends JDialog implements Utilitario {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField buscarTextField;
    private JTable produtosTable;
    private JComboBox<String> filtrarComboBox;
    private DefaultTableModel tableModel;
    private DAO<Produto> produtoDao;
    private List<Produto> produtoList;
    private JButton voltarButton;

    /**
     * Create the dialog.
     */
    public IgProduto(Component janelaPai, DAO<Produto> produtoDao, List<Produto> produtoList) {
        this.produtoDao = produtoDao;
        this.produtoList = produtoList;
        this.produtoList.sort((p1, p2) -> p1.getNomeProduto().compareTo(p2.getNomeProduto()));
        
        String[] produtosFiltro = {"Ordem Alfabética (A-Z)", "Ordem Alfabética (Z-A)", "Maior Estoque", "Menor Estoque"};
        setBounds(100, 100, 824, 450);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setForeground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
        {
            JPanel panel = new JPanel();
            panel.setBackground(new Color(255, 255, 255));
            panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Produtos", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(59, 59, 59)));
            contentPanel.add(panel);
            panel.setLayout(null);

            buscarTextField = new JTextField();
            buscarTextField.setColumns(10);
            buscarTextField.setBounds(64, 34, 176, 24);
            panel.add(buscarTextField);

            JLabel lblBuscar = new JLabel("Buscar:");
            lblBuscar.setDisplayedMnemonic(KeyEvent.VK_B);
            lblBuscar.setBounds(17, 38, 49, 16);
            panel.add(lblBuscar);

            JPanel tabelaProdutosPanel = new JPanel();
            tabelaProdutosPanel.setBackground(new Color(255, 255, 255));
            tabelaProdutosPanel.setBounds(17, 70, 763, 289);
            panel.add(tabelaProdutosPanel);
            tabelaProdutosPanel.setLayout(new GridLayout(0, 1, 0, 0));

            criarTabela(tabelaProdutosPanel);

            JLabel lblFiltrar = new JLabel("Filtrar:");
            lblFiltrar.setDisplayedMnemonic(KeyEvent.VK_F);
            lblFiltrar.setBounds(270, 38, 49, 16);
            panel.add(lblFiltrar);

            filtrarComboBox = new JComboBox<String>();
            filtrarComboBox.setModel(new DefaultComboBoxModel<>(produtosFiltro));
            filtrarComboBox.setSelectedIndex(0);
            filtrarComboBox.setMaximumRowCount(8);
            filtrarComboBox.setBorder(null);
            filtrarComboBox.setBackground(Color.WHITE);
            filtrarComboBox.setBounds(308, 37, 200, 21);
            panel.add(filtrarComboBox);
            
            voltarButton = new JButton("Voltar");
            voltarButton.setMnemonic(KeyEvent.VK_V);
            voltarButton.setBackground(Color.WHITE);
            voltarButton.setActionCommand("Cancel");
            voltarButton.setBounds(690, 369, 90, 26);
            panel.add(voltarButton);
        }

        buscarTextField.addActionListener((e) -> pesquisarProduto(this, produtosTable, buscarTextField));
        filtrarComboBox.addActionListener((e) -> filtrarTabela());
        voltarButton.addActionListener((e) -> dispose());
        
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(janelaPai);
        setVisible(true);
    }

    private void criarTabela(JPanel tabelaProdutosPanel) {
        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Nome do Produto", "Quantidade em Estoque", "Preço"}
        ) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Permitir edição de todas as células
            }
        };

        produtosTable = new JTable(tableModel);
        produtosTable.getTableHeader().setReorderingAllowed(false);
        produtosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        produtosTable.setFillsViewportHeight(true);
        produtosTable.setBackground(Color.WHITE);
        produtosTable.setRowSelectionAllowed(true);
        produtosTable.getTableHeader().setBackground(Color.WHITE);
        produtosTable.setShowGrid(true);
        produtosTable.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(produtosTable);
        tabelaProdutosPanel.add(scrollPane);

        produtosTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                if (column == TableModelEvent.ALL_COLUMNS) {
                    return; // Caso especial, ignorar
                }

                DefaultTableModel model = (DefaultTableModel) e.getSource();
                String columnName = model.getColumnName(column);
                Object data = model.getValueAt(row, column);

                Produto produto = produtoList.get(row);
                switch (columnName) {
                    case "Nome do Produto":
                    	try {
                    		produto.setNomeProduto((String) data);
                    	} catch (Exception ex) {
                    		 JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    	}
                        break;
                    case "Quantidade em Estoque":
                        try {
                            produto.setQuantidadeEstoque(Integer.parseInt(data.toString()));
                        } catch (Exception ex) {
                        	JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Preço":
                        try {
                            produto.setPreco(Double.parseDouble(data.toString().replace("R$: ", "").replace(",", ".")));
                        } catch (Exception ex) {
                        	JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                }
                try {
                	produtoDao.altera(produto);
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(null, "Erro ao atualizar produto no Banco de Dados ", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        atualizarTabela(produtoList);
    }

    private void atualizarTabela(List<Produto> produtos) {
        tableModel.setRowCount(0); // Limpar tabela

        for (Produto produto : produtos) {
            tableModel.addRow(new Object[] {
                produto.getNomeProduto(),
                produto.getQuantidadeEstoque(),
                String.format("R$: %s", DECIMAL_FORMAT.format(produto.getPreco()))
            });
        }
    }

    private void filtrarTabela() {
        int selectedIndex = filtrarComboBox.getSelectedIndex();
        switch (selectedIndex) {
            case 0:
                produtoList.sort(Comparator.comparing(Produto::getNomeProduto)); // Ordem Alfabética (A-Z)
                break;
            case 1:
                produtoList.sort((p1, p2) -> p2.getNomeProduto().compareTo(p1.getNomeProduto())); // Ordem Alfabética (Z-A)
                break;
            case 2:
                produtoList.sort((p1, p2) -> p2.getQuantidadeEstoque().compareTo(p1.getQuantidadeEstoque())); // Maior Estoque
                break;
            case 3:
                produtoList.sort((p1, p2) -> p1.getQuantidadeEstoque().compareTo(p2.getQuantidadeEstoque())); // Menor Estoque
                break;
        }
        atualizarTabela(produtoList);
    }

    public static void pesquisarProduto(JDialog janela, JTable produtosTable, JTextField buscarTextField) {
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
}
