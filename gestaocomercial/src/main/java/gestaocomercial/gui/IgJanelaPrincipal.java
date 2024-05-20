package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Cliente;
import gestaocomercial.dominio.Produto;
import gestaocomercial.dominio.Venda;
import gestaocomercial.dominio.enuns.Meses;

public class IgJanelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@code DecimalFormat} define um formata decimal par os números float. Formato definido ("#,##0.00");
	 */
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
	
	private JLabel labelValorVendasDiaria;
	private JLabel labelValorVendasSemanal;
	private JLabel labelValorVendasMensal;
	private JLabel labelValorVendasTotal;
	private JLabel labelValorClientes;
	private JLabel labelValorProdutos;
	private JComboBox<String> comboBoxMes;
	private JComboBox<String> comboBoxCliente;
	private JComboBox<String> comboBoxProduto;
	private List<Cliente> clienteList;
	private List<Produto> produtoList;
	private List<Venda> vendaList;
	private JButton buttonClientes;
	private JButton buttonProdutos;
	private JButton buttonRelatorio;
	private JButton buttonVenda;

	public IgJanelaPrincipal(DAO<Cliente> clienteDAO, DAO<Produto> produtoDAO, DAO<Venda> vendaDAO) {
		clienteList = clienteDAO.listaTodos();
		produtoList = produtoDAO.listaTodos();
		vendaList = vendaDAO.listaTodos();
		
		String[] meses = Meses.getAbreviacoes();
		
		setTitle("Gestão Comercial™");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(IgJanelaPrincipal.class.getResource("/gestaocomercial/gui/imagens/comprasIcon.png")));

		getContentPane().setBackground(new Color(255, 255, 255));
		// Define o tamanho da janela
		setBounds(100, 100, 1210, 521);
		setResizable(false);
		getContentPane().setLayout(null);

		JPanel superiorPanel = new JPanel();
		superiorPanel.setLayout(null);
		superiorPanel.setBackground(Color.WHITE);
		superiorPanel.setBounds(0, 0, 1264, 65);
		getContentPane().add(superiorPanel);

		JPanel panelVendasDiaria = new JPanel();
		panelVendasDiaria.setBackground(Color.WHITE);
		panelVendasDiaria.setBounds(10, 0, 121, 59);
		superiorPanel.add(panelVendasDiaria);

		JLabel labelVendasDiarias = new JLabel("Vendas Diaria");
		labelVendasDiarias.setForeground(new Color(0, 128, 128));
		labelVendasDiarias.setFont(new Font("SansSerif", Font.BOLD, 16));
		panelVendasDiaria.add(labelVendasDiarias);

		labelValorVendasDiaria = new JLabel();
		labelValorVendasDiaria.setText("0");
		labelValorVendasDiaria.setFont(new Font("SansSerif", Font.BOLD, 18));
		panelVendasDiaria.add(labelValorVendasDiaria);

		JPanel penelVendasSemanal = new JPanel();
		penelVendasSemanal.setBackground(Color.WHITE);
		penelVendasSemanal.setBounds(167, 0, 144, 59);
		superiorPanel.add(penelVendasSemanal);

		JLabel labelVendasSemanal = new JLabel("Vendas Semanal");
		labelVendasSemanal.setForeground(new Color(0, 128, 128));
		labelVendasSemanal.setFont(new Font("SansSerif", Font.BOLD, 16));
		penelVendasSemanal.add(labelVendasSemanal);

		labelValorVendasSemanal = new JLabel();
		labelValorVendasSemanal.setText("0");
		labelValorVendasSemanal.setFont(new Font("SansSerif", Font.BOLD, 18));
		penelVendasSemanal.add(labelValorVendasSemanal);

		JPanel panelVendasMensal = new JPanel();
		panelVendasMensal.setBackground(Color.WHITE);
		panelVendasMensal.setBounds(345, 0, 128, 59);
		superiorPanel.add(panelVendasMensal);

		JLabel labelVendasMensal = new JLabel("Vendas Mensal");
		labelVendasMensal.setForeground(new Color(0, 128, 128));
		labelVendasMensal.setFont(new Font("SansSerif", Font.BOLD, 16));
		panelVendasMensal.add(labelVendasMensal);

		labelValorVendasMensal = new JLabel();
		labelValorVendasMensal.setText("0");
		labelValorVendasMensal.setFont(new Font("SansSerif", Font.BOLD, 18));
		panelVendasMensal.add(labelValorVendasMensal);

		JPanel panelTotalVendas = new JPanel();
		panelTotalVendas.setBackground(Color.WHITE);
		panelTotalVendas.setBounds(511, 0, 144, 59);
		superiorPanel.add(panelTotalVendas);

		JLabel labelTotalDeVendas = new JLabel("Total De Vendas");
		labelTotalDeVendas.setForeground(new Color(0, 128, 128));
		labelTotalDeVendas.setFont(new Font("SansSerif", Font.BOLD, 16));
		panelTotalVendas.add(labelTotalDeVendas);

		labelValorVendasTotal = new JLabel();
		labelValorVendasTotal.setText("0");
		labelValorVendasTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
		panelTotalVendas.add(labelValorVendasTotal);

		JPanel panelClientes = new JPanel();
		panelClientes.setBackground(Color.WHITE);
		panelClientes.setBounds(799, 0, 178, 59);
		superiorPanel.add(panelClientes);

		JLabel labelClientes = new JLabel("Clientes Cadastrados");
		labelClientes.setForeground(new Color(0, 128, 128));
		labelClientes.setFont(new Font("SansSerif", Font.BOLD, 16));
		panelClientes.add(labelClientes);

		labelValorClientes = new JLabel();
		labelValorClientes.setText("0");
		labelValorClientes.setFont(new Font("SansSerif", Font.BOLD, 18));
		panelClientes.add(labelValorClientes);

		JPanel panelProdutos = new JPanel();
		panelProdutos.setBackground(Color.WHITE);
		panelProdutos.setBounds(993, 0, 187, 59);
		superiorPanel.add(panelProdutos);

		JLabel labelprodutos = new JLabel("Produtos Cadastrados");
		labelprodutos.setForeground(new Color(0, 128, 128));
		labelprodutos.setFont(new Font("SansSerif", Font.BOLD, 16));
		panelProdutos.add(labelprodutos);

		labelValorProdutos = new JLabel();
		labelValorProdutos.setText("0");
		labelValorProdutos.setFont(new Font("SansSerif", Font.BOLD, 18));
		panelProdutos.add(labelValorProdutos);

		JPanel InferiorPanel = new JPanel();
		InferiorPanel.setBackground(Color.WHITE);
		InferiorPanel.setBounds(0, 448, 1194, 34);
		getContentPane().add(InferiorPanel);

		JButton buttonCadastrarCliente = new JButton("Cadastrar Cliente...");
		buttonCadastrarCliente.setMnemonic(KeyEvent.VK_C);
		buttonCadastrarCliente.setFont(new Font("SansSerif", Font.PLAIN, 11));
		buttonCadastrarCliente.setBackground(Color.WHITE);

		JButton buttonCadastrarProduto = new JButton("Cadastrar Produto...");
		buttonCadastrarProduto.setMnemonic(KeyEvent.VK_P);
		buttonCadastrarProduto.setFont(new Font("SansSerif", Font.PLAIN, 11));
		buttonCadastrarProduto.setBackground(Color.WHITE);

		buttonVenda = new JButton("Realizar Venda...");
		buttonVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonVenda.setMnemonic(KeyEvent.VK_V);
		buttonVenda.setFont(new Font("SansSerif", Font.PLAIN, 11));
		buttonVenda.setBackground(Color.WHITE);

		buttonRelatorio = new JButton("Relatorios...");
		buttonRelatorio.setMnemonic(KeyEvent.VK_R);
		buttonRelatorio.setFont(new Font("SansSerif", Font.PLAIN, 11));
		buttonRelatorio.setBackground(Color.WHITE);
		GroupLayout gl_InferiorPanel = new GroupLayout(InferiorPanel);
		gl_InferiorPanel.setHorizontalGroup(gl_InferiorPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_InferiorPanel.createSequentialGroup().addContainerGap(682, Short.MAX_VALUE)
						.addComponent(buttonRelatorio, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(buttonCadastrarCliente)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonCadastrarProduto, GroupLayout.PREFERRED_SIZE, 128,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonVenda, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_InferiorPanel.setVerticalGroup(gl_InferiorPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_InferiorPanel.createSequentialGroup().addGap(3)
						.addGroup(gl_InferiorPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(buttonVenda, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addComponent(buttonCadastrarProduto).addComponent(buttonCadastrarCliente)
								.addComponent(buttonRelatorio))));
		InferiorPanel.setLayout(gl_InferiorPanel);

		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(null);
		centralPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Vendas",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		centralPanel.setBackground(Color.WHITE);
		centralPanel.setBounds(10, 64, 1178, 383);
		getContentPane().add(centralPanel);

		JPanel graficoPanel = new JPanel();
		graficoPanel.setBounds(747, 42, 424, 304);
		centralPanel.add(graficoPanel);
		graficoPanel.setLayout(new BorderLayout(0, 0));

		JPanel tabelaPanel = new JPanel();
		tabelaPanel.setBackground(Color.WHITE);
		tabelaPanel.setBounds(16, 55, 719, 288);
		centralPanel.add(tabelaPanel);
		tabelaPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(6, 16, 729, 30);
		centralPanel.add(panel);

		JLabel labelCliente = new JLabel("Cliente:");
		labelCliente.setDisplayedMnemonic(KeyEvent.VK_E);
		labelCliente.setBounds(146, 8, 60, 16);
		panel.add(labelCliente);

		JLabel mesLabel = new JLabel("Mês:");
		mesLabel.setDisplayedMnemonic(KeyEvent.VK_M);
		mesLabel.setBounds(6, 8, 34, 16);
		panel.add(mesLabel);
		
		comboBoxMes = new JComboBox<String>();
		
		comboBoxMes.setModel(new DefaultComboBoxModel<>(meses));
		comboBoxMes.setMaximumRowCount(6);
		comboBoxMes.setSelectedIndex(mesAtual(meses));
		mesLabel.setLabelFor(comboBoxMes);
		comboBoxMes.setBorder(null);
		comboBoxMes.setBackground(Color.WHITE);
		comboBoxMes.setBounds(39, 6, 95, 21);
		panel.add(comboBoxMes);

		comboBoxCliente = new JComboBox<String>();
		comboBoxCliente.setBounds(195, 6, 118, 21);
		panel.add(comboBoxCliente);
		comboBoxCliente.setMaximumRowCount(3);
		comboBoxCliente.setBorder(null);
		comboBoxCliente.setBackground(Color.WHITE);

		comboBoxProduto = new JComboBox<String>();
		comboBoxProduto.setMaximumRowCount(3);
		comboBoxProduto.setBorder(null);
		comboBoxProduto.setBackground(Color.WHITE);
		comboBoxProduto.setBounds(377, 6, 118, 21);
		panel.add(comboBoxProduto);

		JLabel produtoLabel = new JLabel("Produto:");
		produtoLabel.setDisplayedMnemonic(KeyEvent.VK_D);
		produtoLabel.setBounds(325, 8, 60, 16);
		panel.add(produtoLabel);

		buttonClientes = new JButton("Clientes..");
		buttonClientes.setMnemonic(KeyEvent.VK_T);
		buttonClientes.setFont(new Font("SansSerif", Font.PLAIN, 11));
		buttonClientes.setBackground(Color.WHITE);
		buttonClientes.setBounds(15, 350, 110, 25);
		centralPanel.add(buttonClientes);

		buttonProdutos = new JButton("Produtos...");
		buttonProdutos.setMnemonic(KeyEvent.VK_D);
		buttonProdutos.setFont(new Font("SansSerif", Font.PLAIN, 11));
		buttonProdutos.setBackground(Color.WHITE);
		buttonProdutos.setBounds(132, 350, 110, 25);
		centralPanel.add(buttonProdutos);
		setVisible(true);
		
		
		//Atualiza os componentes de acordo com o mes selecionado no JComboBox.
		comboBoxMes.addItemListener((itemEvent) -> atualizarComponentes(itemEvent));
		
		// Fecha a conexão com banco de dados quando o programa for finalizado
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					clienteDAO.close();
					produtoDAO.close();
					vendaDAO.close();
				} catch (Exception e1) {
					JOptionPane.showInternalConfirmDialog(IgJanelaPrincipal.this,
							"Error ao fechar conexão com banco de dados", "Error", JOptionPane.ERROR_MESSAGE);
				}
				System.exit(0);
			}
		});
	}
	
	/**
	 * Obtém o índice do mês atual em relação a um array de nomes de meses.
	 *
	 * @param meses O array de nomes de meses.
	 * @return O índice do mês atual no array de meses.
	 */
	private int mesAtual(String[] meses) {
		LocalDate dataAtual = LocalDate.now();
		int mesAtual = dataAtual.getMonthValue();

		for (int i = 0; i < meses.length; i++) {
			if (Meses.obterValorPorAbreviacao(meses[i]) == mesAtual) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Atualiza os componentes do GUI com base nos Eventos do JComboBox selecionados pelo usuário.
	 *
	 * @param itemEvent O evento de mudança de seleção no mês ou categoria.
	 */
	private void atualizarComponentes(ItemEvent itemEvent) {
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			}
			atualizarComponentes();
	}
	

	
	private void atualizarComponentes() {
	    atualizarEstadoBotaoClientes();
	    atualizarEstadoBotaoProdutos();
	    atualizarEstadoBotoesVendaERelatorio();
	    atualizarLabels();
	}

	private void atualizarEstadoBotaoClientes() {
	    if (clienteList.isEmpty()) {
	        buttonClientes.setEnabled(false);
	    } else {
	        buttonClientes.setEnabled(true);
	    }
	}

	private void atualizarEstadoBotaoProdutos() {
	    if (produtoList.isEmpty()) {
	        buttonProdutos.setEnabled(false);
	    } else {
	        buttonProdutos.setEnabled(true);
	    }
	}

	private void atualizarEstadoBotoesVendaERelatorio() {
	    if (produtoList.isEmpty()) {
	        buttonVenda.setEnabled(false);
	        buttonRelatorio.setEnabled(false);
	    } else {
	        buttonVenda.setEnabled(true);
	        buttonRelatorio.setEnabled(true);
	    }
	}


	private void atualizarLabels() {
		double vendasDiarias = calcularVendasDiarias();
		double vendasTotais = calcularVendasTotais();
		double vendasSemanal = calcularVendasSemanais(LocalDate.now());
		double vendasMensais = calcularVendasMensais();
		double clientesTotais = calcularClientesTotais();
		double produtosTotais = calcularProdutosTotais();
		
		labelValorVendasDiaria.setText(String.format("%s%s", "R$: ", DECIMAL_FORMAT.format(vendasDiarias)));;
		labelValorVendasTotal.setText(String.format("%s%s", "R$: ", DECIMAL_FORMAT.format(vendasTotais)));;
		labelValorVendasSemanal.setText(String.format("%s%s", "R$: ", DECIMAL_FORMAT.format(vendasSemanal)));;
		labelValorVendasMensal.setText(String.format("%s%s", "R$: ", DECIMAL_FORMAT.format(vendasMensais)));;
		
		labelValorClientes.setText(String.valueOf(clientesTotais));
		labelValorProdutos.setText(String.valueOf(produtosTotais));
	}

	private double calcularVendasDiarias() {
		return vendaList.stream().filter(venda -> venda.getDataVenda().equals(LocalDate.now()))
				.mapToDouble(Venda::getValorVenda).sum();
	}

	private double calcularVendasTotais() {
		return vendaList.stream().mapToDouble(Venda::getValorVenda).sum();
	}
	
	private double calcularVendasSemanais(LocalDate data) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekOfYear = data.get(weekFields.weekOfWeekBasedYear());
        
        return vendaList.stream()
            .filter(venda -> venda.getDataVenda().get(weekFields.weekOfWeekBasedYear()) == weekOfYear)
            .mapToDouble(Venda::getValorVenda)
            .sum();
    }

	private double calcularVendasMensais() {
		String mesSelecionado = comboBoxMes.getSelectedItem().toString();
		int valorMes = Meses.obterValorPorAbreviacao(mesSelecionado);

		return vendaList.stream().filter(venda -> Integer.parseInt(venda.getMesVenda()) == valorMes)
				.mapToDouble(Venda::getValorVenda).sum();
	}

	private double calcularClientesTotais() {
		return clienteList.size();
	}

	private double calcularProdutosTotais() {
		return produtoList.size();
	}

}
