package gestaocomercial.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class IgCadastroCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField cpfTextField;
	private JTextField nomeTextField;
	private JTextField textField_2;
	private JTextField telefoneTextField;


	/**
	 * Create the dialog.
	 */
	public IgCadastroCliente() {
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
		
		cpfTextField = new JTextField();
		cpfTextField.setColumns(10);
		cpfTextField.setBounds(84, 23, 215, 28);
		mainPanel.add(cpfTextField);
		
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
		emailLabel.setBounds(16, 91, 47, 16);
		mainPanel.add(emailLabel);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(84, 85, 215, 28);
		mainPanel.add(textField_2);
		
		JLabel telefoneLabel = new JLabel("Telefone:");
		telefoneLabel.setDisplayedMnemonic(KeyEvent.VK_T);
		telefoneLabel.setBounds(16, 124, 60, 16);
		mainPanel.add(telefoneLabel);
		
		telefoneTextField = new JTextField();
		telefoneTextField.setColumns(10);
		telefoneTextField.setBounds(84, 118, 215, 28);
		mainPanel.add(telefoneTextField);
		
		JLabel enderecoLabel = new JLabel("Endereço:");
		enderecoLabel.setDisplayedMnemonic(KeyEvent.VK_E);
		enderecoLabel.setBounds(16, 159, 85, 16);
		mainPanel.add(enderecoLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 158, 215, 63);
		mainPanel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		
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
	}
}
