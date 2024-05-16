package gestaocomercial.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class IgCadastroProduto extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField quantidateTextFild;
	private JTextField precoTextField;

	
	public IgCadastroProduto() {
		setBounds(100, 100, 370, 233);
		getContentPane().setLayout(null);
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
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(82, 24, 247, 28);
		panel.add(textField);
		
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
		
		precoTextField = new JTextField();
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
	}
}
