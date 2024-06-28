package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Cliente;

public class IgClientes extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JTable table;
    private DefaultTableModel tableModel;
    private DAO<Cliente> clienteDao;
    private List<Cliente> clienteList;
    private JComboBox<String> filtrarComboBox;

    public IgClientes(Component janelaPai, DAO<Cliente> clienteDao, List<Cliente> clienteList) {
        this.clienteDao = clienteDao;
        this.clienteList = clienteList;
        this.clienteList.sort((c1, c2) -> c1.getNomeCliente().compareTo(c2.getNomeCliente()));
        
        getContentPane().setBackground(new Color(255, 255, 255));
        setBounds(100, 100, 1210, 521);
        setResizable(false);
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(1, 0, 0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Clientes", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(59, 59, 59)));
        contentPanel.add(panel);
        panel.setLayout(null);

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setDisplayedMnemonic(KeyEvent.VK_B);
        lblBuscar.setBounds(21, 59, 49, 16);
        panel.add(lblBuscar);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(72, 53, 176, 28);
        panel.add(textField);
        
        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                buscarCliente();
            }
        });
        
        JLabel lblFiltrar = new JLabel("Filtrar:");
        lblFiltrar.setDisplayedMnemonic(KeyEvent.VK_F);
        lblFiltrar.setBounds(289, 59, 49, 16);
        panel.add(lblFiltrar);
        
        filtrarComboBox = new JComboBox<String>();
        filtrarComboBox.addItem("Ordem Alfabética A-Z");
        filtrarComboBox.addItem("Ordem Alfabética Z-A");
        filtrarComboBox.setSelectedIndex(0);
        filtrarComboBox.setMaximumRowCount(8);
        filtrarComboBox.setBorder(null);
        filtrarComboBox.setBackground(Color.WHITE);
        filtrarComboBox.setBounds(329, 56, 200, 21);
        panel.add(filtrarComboBox);

        filtrarComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(16, 108, 1150, 320);
        panel.add(scrollPane);
        
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] {"Documento", "Nome", "Email", "Telefone", "Endereço"});
        table = new JTable(tableModel);
        table.setForeground(new Color(0, 0, 0));
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(255, 255, 255));
        table.setGridColor(Color.LIGHT_GRAY);
        scrollPane.setViewportView(table);
        
        tableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    atualizarCliente(row);
                }
            }
        });

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });
        btnAtualizar.setBackground(new Color(255, 255, 255));
        btnAtualizar.setBounds(976, 440, 90, 26);
        panel.add(btnAtualizar);
        
        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        voltarButton.setBackground(new Color(255, 255, 255));
        voltarButton.setBounds(1076, 440, 90, 26);
        panel.add(voltarButton);
        
        atualizarTabela();
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(janelaPai);
        setVisible(true);
    }

    private void buscarCliente() {
        String searchText = textField.getText().toLowerCase();
        List<Cliente> filteredList = clienteList.stream()
            .filter(cliente -> cliente.getNomeCliente().toLowerCase().contains(searchText) ||
                               cliente.getDocumento().toLowerCase().contains(searchText))
            .collect(Collectors.toList());
        atualizarTabela(filteredList);
    }

    private void atualizarTabela() {
        atualizarTabela(clienteList);
    }

    private void atualizarTabela(List<Cliente> lista) {
        tableModel.setRowCount(0);
        List<Cliente> sortedList = lista.stream()
            .sorted((c1, c2) -> {
                if (filtrarComboBox.getSelectedItem().equals("Ordem Alfabética A-Z")) {
                    return c1.getNomeCliente().compareToIgnoreCase(c2.getNomeCliente());
                } else {
                    return c2.getNomeCliente().compareToIgnoreCase(c1.getNomeCliente());
                }
            })
            .collect(Collectors.toList());

        for (Cliente cliente : sortedList) {
            tableModel.addRow(new Object[] {
                cliente.getDocumento(),
                cliente.getNomeCliente(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEndereco()
            });
        }
    }

    private void atualizarCliente(int row) {
        String documento = (String) tableModel.getValueAt(row, 0);
        String nome = (String) tableModel.getValueAt(row, 1);
        String email = (String) tableModel.getValueAt(row, 2);
        String telefone = (String) tableModel.getValueAt(row, 3);
        String endereco = (String) tableModel.getValueAt(row, 4);

        Cliente cliente = clienteList.stream()
            .filter(c -> c.getDocumento().equals(documento))
            .findFirst()
            .orElse(null);

        if (cliente != null) {
            cliente.setNomeCliente(nome);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            cliente.setEndereco(endereco);
            clienteDao.altera(cliente);  
        }
    }
}
