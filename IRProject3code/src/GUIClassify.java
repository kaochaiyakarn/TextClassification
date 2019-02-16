import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JList;

public class GUIClassify {

	private JFrame frame;
	private JTextField textField;
	private JTextField filename;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTextPane textPane;
	private JCheckBox chckbxNewCheckBox;
	private JCheckBox chckbxNegative;
	private JCheckBox chckbxNatural;
	private JButton btnNewButton_2;
	private static GUIProcess GuiP;
	private static String tempPositiveSelect;
	private static String tempNegativeSelect;
	private static String tempNeturalSelect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GuiP = new GUIProcess();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIClassify window = new GUIClassify();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIClassify() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setColumns(1000);
		
		filename = new JTextField();
		filename.setColumns(10);
		
		btnNewButton = new JButton("phase class");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String phase=null;
				try {
					phase = textField.getText();
					textPane.setText(GuiP.classifyPhase(phase));
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showConfirmDialog(null,"Please fill in Phases box");
				}
			}
		});
		
		btnNewButton_1 = new JButton("flie classify");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fName=null;
				try {
					fName = filename.getText();
//					System.out.println(fName);
					textPane.setText(GuiP.classifyFile(fName));
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showConfirmDialog(null,"Please fill in file name box");
				}
			}
		});
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		chckbxNewCheckBox = new JCheckBox("positive");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				String fName=null;
				if(!chckbxNewCheckBox.isSelected()){
//					System.out.println(textPane.getText());
					tempPositiveSelect = textPane.getText();
					textPane.setText(GuiP.removeSentense(textPane.getText(),"+"));
				}
				if(chckbxNewCheckBox.isSelected()){
					textPane.setText(tempPositiveSelect);
				}
//				System.out.println(textPane.getText());
			}
		});
		
		chckbxNegative = new JCheckBox("negative");
		chckbxNegative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!chckbxNegative.isSelected()){
					tempNegativeSelect = textPane.getText();
					textPane.setText(GuiP.removeSentense(textPane.getText(),"-"));
				}
				if(chckbxNegative.isSelected()){
					textPane.setText(tempNegativeSelect);
				}
			}
		});
		chckbxNegative.setSelected(true);
		
		chckbxNatural = new JCheckBox("natural ");
		chckbxNatural.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!chckbxNatural.isSelected()){
					textPane.setText(tempNeturalSelect);
				}
				if(chckbxNatural.isSelected()){
					tempNeturalSelect = textPane.getText();
					textPane.setText(GuiP.neturalClassify(textPane.getText()));
				}
			}
		});
		chckbxNatural.setSelected(false);
		
		btnNewButton_2 = new JButton("evaluate");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fName=null;
				try {
					fName = filename.getText();
//					System.out.println(fName);
					textPane.setText(GuiP.Evaluate(fName).toString());
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showConfirmDialog(null,"Please fill in file name box");
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(textPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
						.addComponent(filename, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
						.addComponent(textField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(chckbxNegative, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(chckbxNewCheckBox, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnNewButton_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(chckbxNatural, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_2))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(filename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(chckbxNewCheckBox)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxNegative)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxNatural)
							.addPreferredGap(ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
							.addComponent(btnNewButton_2))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
							.addGap(7)))
					.addGap(19))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
