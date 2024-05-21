package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import gestaocomercial.dao.DAO;
import gestaocomercial.dominio.Cliente;
import gestaocomercial.dominio.Produto;
import gestaocomercial.dominio.Venda;
import gestaocomercial.dominio.enuns.Meses;

public class IgJanelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * {@code DecimalFormat} define um formata decimal par os números float. Formato
	 * definido ("#,##0.00");
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
	private JTable vendasTabel;
	private IgCadastroCliente cadastroCliente = null;
	private IgCadastroProduto cadastroProduto = null;
	private JPanel tabelaPanel;
	private JPanel graficoPanel;

	public IgJanelaPrincipal(DAO<Cliente> clienteDAO, DAO<Produto> produtoDAO, DAO<Venda> vendaDAO) {
		clienteList = clienteDAO.listaTodos();
		produtoList = produtoDAO.listaTodos();
		vendaList = vendaDAO.listaTodos();

		String[] meses = Meses.getAbreviacoes();

		// Obtendo as listas de nomes de clientes e produtos
		List<String> nomesClientes = obterNomesClientes(clienteList);
		List<String> nomesProdutos = obterNomesProdutos(produtoList);

		// Convertendo listas para arrays e adicionando "Todos"
		String[] clientes = convertListToArrayWithTodos(nomesClientes);
		String[] produtos = convertListToArrayWithTodos(nomesProdutos);

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

		graficoPanel = new JPanel();
		graficoPanel.setBounds(747, 42, 424, 304);
		centralPanel.add(graficoPanel);
		graficoPanel.setLayout(new BorderLayout(0, 0));

		tabelaPanel = new JPanel();
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
		comboBoxCliente.setModel(new DefaultComboBoxModel<>(clientes));
		comboBoxCliente.setBounds(195, 6, 118, 21);
		comboBoxCliente.setSelectedIndex(clienteList.size());
		panel.add(comboBoxCliente);
		comboBoxCliente.setMaximumRowCount(6);
		labelCliente.setLabelFor(comboBoxCliente);
		comboBoxCliente.setBorder(null);
		comboBoxCliente.setBackground(Color.WHITE);

		JLabel produtoLabel = new JLabel("Produto:");
		produtoLabel.setDisplayedMnemonic(KeyEvent.VK_D);
		produtoLabel.setBounds(325, 8, 60, 16);
		panel.add(produtoLabel);

		comboBoxProduto = new JComboBox<String>();
		comboBoxProduto.setModel(new DefaultComboBoxModel<>(produtos));
		comboBoxProduto.setMaximumRowCount(6);
		comboBoxProduto.setSelectedIndex(produtoList.size());
		produtoLabel.setLabelFor(comboBoxProduto);
		comboBoxProduto.setBorder(null);
		comboBoxProduto.setBackground(Color.WHITE);
		comboBoxProduto.setBounds(377, 6, 118, 21);
		panel.add(comboBoxProduto);

		buttonClientes = new JButton("Clientes..");
		buttonClientes.setMnemonic(KeyEvent.VK_T);
		buttonClientes.setFont(new Font("SansSerif", Font.PLAIN, 11));
		buttonClientes.setBackground(Color.WHITE);
		buttonClientes.setBounds(15, 350, 110, 25);
		centralPanel.add(buttonClientes);

		buttonProdutos = new JButton("Produtos...");
		buttonProdutos.setMnemonic(KeyEvent.VK_O);
		buttonProdutos.setFont(new Font("SansSerif", Font.PLAIN, 11));
		buttonProdutos.setBackground(Color.WHITE);
		buttonProdutos.setBounds(132, 350, 110, 25);
		centralPanel.add(buttonProdutos);
		setVisible(true);

		vendasTabel = criarTabela();
		tabelaPanel.add(vendasTabel);
		tabelaPanel.add(criarTabelaVendas(vendaList));

		// Atualiza a GUI com os dados
		atualizarComponentes();

		// Abre a tela de cadastro de Clientes
		buttonCadastrarCliente.addActionListener((e) -> {
			cadastroCliente = new IgCadastroCliente(this, clienteDAO, clienteList);

			// Adiciona um ouvinte de eventos à janela de cadastro de clientes
			cadastroCliente.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					// Este método será chamado quando a janela for fechada
					atualizarComboBoxClientes();
					atualizarComponentes();
				}
			});
		});

		// Abre a tela de cadastro de Produto
		buttonCadastrarProduto.addActionListener((e) -> {
			cadastroProduto = new IgCadastroProduto(this, produtoDAO, produtoList);

			// Adiciona um ouvinte de eventos à janela de cadastro de produto
			cadastroProduto.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					// Este método será chamado quando a janela for fechada
					atualizarComboBoxProduto();
					atualizarComponentes();
				}
			});
		});

		// Atualiza os componentes de acordo com o mes selecionado no JComboBox.
		comboBoxMes.addItemListener((itemEvent) -> atualizarComponentes(itemEvent));

		// Atualiza a tabela com o cliente selecionado
		comboBoxCliente.addItemListener((itemEvent) -> atualizarComponentes(itemEvent));
		
		// Atualiza a tabelo com o produto selecionado
		comboBoxProduto.addItemListener((itemEvent) -> atualizarComponentes(itemEvent));
		
		// Atualiza os componentes quando a janela volta para o foco.
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				atualizarComponentes();
			}
		});

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

	private static List<String> obterNomesClientes(List<Cliente> clientes) {
		List<String> nomeListOrdenada = clientes.stream().map(Cliente::getNomeCliente).collect(Collectors.toList());
		nomeListOrdenada.sort((x, y) -> x.compareToIgnoreCase(y));
		return nomeListOrdenada;
	}

	private static List<String> obterNomesProdutos(List<Produto> produtos) {
		List<String> produtosOrdenados = produtos.stream().map(Produto::getNomeProduto).collect(Collectors.toList());
		produtosOrdenados.sort((x, y) -> x.compareToIgnoreCase(y));
		return produtosOrdenados;
	}

	private void atualizarComboBoxClientes() {
		comboBoxCliente
				.setModel(new DefaultComboBoxModel<>(convertListToArrayWithTodos(obterNomesClientes(clienteList))));
		comboBoxCliente.setSelectedIndex(clienteList.size());
	}

	protected void atualizarComboBoxProduto() {
		comboBoxProduto
				.setModel(new DefaultComboBoxModel<>(convertListToArrayWithTodos(obterNomesProdutos(produtoList))));
		comboBoxProduto.setSelectedIndex(produtoList.size());
	}
	
	private void graficoEmBarras() {
		if (graficoPanel.getComponentCount() > 0) {
			graficoPanel.remove(0);
		}
		graficoPanel.add(IgGraficoBarras.gerarGraficoBarras(vendaList, ""));
		tabelaPanel.revalidate();
		tabelaPanel.repaint();
	}//graficoEmBarras()
	
	private static String[] convertListToArrayWithTodos(List<String> list) {
		// Criar um array com uma posição extra
		String[] array = list.toArray(new String[0]);
		String[] resultArray = new String[array.length + 1];

		// Copiar os elementos do array original para o novo array
		System.arraycopy(array, 0, resultArray, 0, array.length);

		// Adicionar "Todos" na última posição
		resultArray[array.length] = "Todos";

		return resultArray;
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
	 * Atualiza os componentes do GUI com base nos Eventos do JComboBox selecionados
	 * pelo usuário.
	 *
	 * @param itemEvent O evento de mudança de seleção no mês ou categoria.
	 */
	private void atualizarComponentes(ItemEvent itemEvent) {
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			atualizarComponentes();
		}
	}

	private void atualizarComponentes() {
		atualizarEstadoBotaoClientes();
		atualizarEstadoBotaoProdutos();
		atualizarEstadoBotoesVendaERelatorio();
		atualizarLabels();
		atualizarTabela(vendasTabel, vendaList);
		
		List<Venda> vendaFiltradaList = vendaList;
		
		if (!comboBoxMes.getSelectedItem().toString().equals("Todos")) {
			vendaFiltradaList = vendaFiltradaList.stream().filter((v) -> v.getDataVenda().getMonthValue() == Meses.obterValorPorAbreviacao(comboBoxMes.getSelectedItem().toString())).collect(Collectors.toList());
		}
		
		if (!comboBoxCliente.getSelectedItem().toString().equals("Todos")) {
			vendaFiltradaList = vendaFiltradaList.stream().filter((v) -> v.getCliente().getNomeCliente().equals(comboBoxCliente.getSelectedItem().toString())).collect(Collectors.toList());
		}
		
		if (!comboBoxProduto.getSelectedItem().toString().equals("Todos")) {
		    vendaFiltradaList = vendaFiltradaList.stream()
		        .filter(v -> v.getProdutoList().stream().anyMatch(p -> p.getNomeProduto().equals(comboBoxProduto.getSelectedItem().toString())))
		        .collect(Collectors.toList());
		}
		
		graficoEmBarras();
		
		// Remover o JScrollPane antigo da tabelaPanel
		tabelaPanel.remove(0);
		
		// Adicionar o novo JScrollPane à tabelaPanel
		tabelaPanel.add(atualizarTabela(vendasTabel, vendaFiltradaList));
				
		// Atualizar a interface
		tabelaPanel.revalidate();
		tabelaPanel.repaint();
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
		int clientesTotais = calcularClientesTotais();
		int produtosTotais = calcularProdutosTotais();

		labelValorVendasDiaria.setText(String.format("%s%s", "R$: ", DECIMAL_FORMAT.format(vendasDiarias)));
		labelValorVendasTotal.setText(String.format("%s%s", "R$: ", DECIMAL_FORMAT.format(vendasTotais)));
		labelValorVendasSemanal.setText(String.format("%s%s", "R$: ", DECIMAL_FORMAT.format(vendasSemanal)));
		labelValorVendasMensal.setText(String.format("%s%s", "R$: ", DECIMAL_FORMAT.format(vendasMensais)));

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
				.mapToDouble(Venda::getValorVenda).sum();
	}

	private double calcularVendasMensais() {
		String mesSelecionado = comboBoxMes.getSelectedItem().toString();
		if (mesSelecionado.equals("Todos")) {
			return vendaList.stream().mapToDouble(Venda::getValorVenda).sum();
		}

		int valorMes = Meses.obterValorPorAbreviacao(mesSelecionado);

		return vendaList.stream().filter(venda -> Integer.parseInt(venda.getMesVenda()) == valorMes)
				.mapToDouble(Venda::getValorVenda).sum();
	}

	private int calcularClientesTotais() {
		return clienteList.size();
	}

	private int calcularProdutosTotais() {
		return produtoList.size();
	}

	private JTable criarTabela() {
		// Criar a tabela com o modelo criado
		JTable tabela = new JTable(new DefaultTableModel(new Object[][] {},
				new String[] { "Cliente", "Produto", "Quantidade", "Pagamento", "Data", "Valor" })) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// Todas as células não são editáveis
				return false;
			}
		};

		// Impedir reordenação das colunas
		tabela.getTableHeader().setReorderingAllowed(false);

		// Configurar o estilo da tabela
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setFillsViewportHeight(true);

		// Definir cor de fundo da tabela
		tabela.setBackground(Color.WHITE);

		// Remover sombra da linha quando uma célula é selecionada
		tabela.setRowSelectionAllowed(true);

		// Definir cor de fundo do cabeçalho da tabela
		tabela.getTableHeader().setBackground(Color.WHITE);

		// Adicionar bordas entre as colunas e linhas da tabela
		tabela.setShowGrid(true);
		tabela.setGridColor(Color.LIGHT_GRAY);

		// Ajustar a largura das colunas conforme necessário
		tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(70);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(70);

		return tabela;
	}

	public JScrollPane criarTabelaVendas(List<Venda> vendaList) {
		JTable tabelaVendas = criarTabela();
		JScrollPane scrollPane = atualizarTabela(tabelaVendas, vendaList);
		return scrollPane;
	}

	private JScrollPane atualizarTabela(JTable tabelaVendas, List<Venda> vendaList) {
		DefaultTableModel model = (DefaultTableModel) tabelaVendas.getModel();

		// Limpar dados existentes da tabela
		model.setRowCount(0);

		for (Venda venda : vendaList) {
			for (Produto produto : venda.getProdutoList()) {
				Object[] rowData = { venda.getCliente().getNomeCliente(), produto.getNomeProduto(),
						produto.getQuantidadeVendida(), venda.getFormaPagamento().toString(), venda.getDataVenda(), // format(Utilitario.DIA_MES_ANO_FORMATTER),
						produto.getPreco() };
				model.addRow(rowData);
			}
		}

		// Criar um JScrollPane para permitir a rolagem da tabela
		JScrollPane scrollPane = new JScrollPane(tabelaVendas);

		return scrollPane;
	}

}
