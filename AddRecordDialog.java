

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class AddRecordDialog extends JDialog implements ActionListener {
	JTextField idField, ppsField, surnameField, firstNameField, salaryField;
	JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
	JButton save, cancel;
	EmployeeDetails parent;
	private Color incorrectInput= new Color(255, 150, 150);

	public AddRecordDialog(EmployeeDetails parent) {
		setTitle("Add Record");
		setModal(true);
		this.parent = parent;
		this.parent.setEnabled(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane(dialogPane());
		setContentPane(scrollPane);

		getRootPane().setDefaultButton(save);

		setSize(500, 370);
		setLocation(350, 250);
		setVisible(true);
	}

	public Container dialogPane() {
		JPanel employeeDetails, buttonPanel;
		employeeDetails = new JPanel(new MigLayout());
		buttonPanel = new JPanel();
		JTextField field;

		employeeDetails.setBorder(BorderFactory.createTitledBorder("Employee Details"));

		employeeDetails.add(new JLabel("ID:"), "growx, pushx");
		employeeDetails.add(idField = new JTextField(20), "growx, pushx, wrap");
		idField.setEditable(false);

		employeeDetails.add(new JLabel("PPS Number:"), "growx, pushx");
		employeeDetails.add(ppsField = new JTextField(20), "growx, pushx, wrap");

		employeeDetails.add(new JLabel("Surname:"), "growx, pushx");
		employeeDetails.add(surnameField = new JTextField(20), "growx, pushx, wrap");

		employeeDetails.add(new JLabel("First Name:"), "growx, pushx");
		employeeDetails.add(firstNameField = new JTextField(20), "growx, pushx, wrap");

		employeeDetails.add(new JLabel("Gender:"), "growx, pushx");
		employeeDetails.add(genderCombo = new JComboBox<String>(this.parent.gender), "growx, pushx, wrap");

		employeeDetails.add(new JLabel("Department:"), "growx, pushx");
		employeeDetails.add(departmentCombo = new JComboBox<String>(this.parent.department), "growx, pushx, wrap");

		employeeDetails.add(new JLabel("Salary:"), "growx, pushx");
		employeeDetails.add(salaryField = new JTextField(20), "growx, pushx, wrap");

		employeeDetails.add(new JLabel("Full Time:"), "growx, pushx");
		employeeDetails.add(fullTimeCombo = new JComboBox<String>(this.parent.fullTime), "growx, pushx, wrap");

		buttonPanel.add(save = new JButton("Save"));
		save.addActionListener(this);
		save.requestFocus();
		buttonPanel.add(cancel = new JButton("Cancel"));
		cancel.addActionListener(this);

		employeeDetails.add(buttonPanel, "span 2,growx, pushx,wrap");

		for (int i = 0; i < employeeDetails.getComponentCount(); i++) {
			employeeDetails.getComponent(i).setFont(this.parent.font1);
			if (employeeDetails.getComponent(i) instanceof JComboBox) {
				employeeDetails.getComponent(i).setBackground(Color.WHITE);
			} else if (employeeDetails.getComponent(i) instanceof JTextField) {
				field = (JTextField) employeeDetails.getComponent(i);
				if (field == ppsField)
					field.setDocument(new JTextFieldLimit(9));
				else
					field.setDocument(new JTextFieldLimit(20));
			}
		}
		idField.setText(Integer.toString(this.parent.getNextFreeId()));
		return employeeDetails;
	}

	public void addRecord() {
		boolean fullTime = false;
		Employee theEmployee;

		if (((String) fullTimeCombo.getSelectedItem()).equalsIgnoreCase("Yes")) {
			fullTime = true;
		}
		theEmployee = new Employee(Integer.parseInt(idField.getText()), ppsField.getText().toUpperCase(), surnameField.getText().toUpperCase(),
						firstNameField.getText().toUpperCase(), genderCombo.getSelectedItem().toString().charAt(0),
						departmentCombo.getSelectedItem().toString(), Double.parseDouble(salaryField.getText()), fullTime);
		this.parent.currentEmployee = theEmployee;
		this.parent.addRecord(theEmployee);
		this.parent.displayRecords(theEmployee);
	}

	public boolean checkInput() {
		boolean isValid = true;

		if (ppsField.getText().equals("")) {
			ppsField.setBackground(incorrectInput);
			isValid = false;
		}
		if (this.parent.correctPps(this.ppsField.getText().trim(), -1)) {
			ppsField.setBackground(incorrectInput);
			isValid = false;
		}
		if (surnameField.getText().isEmpty()) {
			surnameField.setBackground(incorrectInput);
			isValid = false;
		}
		if (firstNameField.getText().isEmpty()) {
			firstNameField.setBackground(incorrectInput);
			isValid = false;
		}
		if (genderCombo.getSelectedIndex() == 0) {
			genderCombo.setBackground(incorrectInput);
			isValid = false;
		}
		if (departmentCombo.getSelectedIndex() == 0) {
			departmentCombo.setBackground(incorrectInput);
			isValid = false;
		}
		try {
			Double.parseDouble(salaryField.getText());

			if (Double.parseDouble(salaryField.getText()) < 0) {
				salaryField.setBackground(incorrectInput);
				isValid = false;
			}
		} catch (NumberFormatException num) {
			salaryField.setBackground(incorrectInput);
			isValid = false;
		}
		if (fullTimeCombo.getSelectedIndex() == 0) {
			fullTimeCombo.setBackground(incorrectInput);
			isValid = false;
		}
		return isValid;
	}

	public void setToWhite() {
		ppsField.setBackground(Color.WHITE);
		surnameField.setBackground(Color.WHITE);
		firstNameField.setBackground(Color.WHITE);
		salaryField.setBackground(Color.WHITE);
		genderCombo.setBackground(Color.WHITE);
		departmentCombo.setBackground(Color.WHITE);
		fullTimeCombo.setBackground(Color.WHITE);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == save) {
			if (checkInput()) {
				addRecord();
				dispose();
				this.parent.changesMade = true;
			} else {
				JOptionPane.showMessageDialog(null, "Wrong values or format! Please check!");
				setToWhite();
			}
		} else if (event.getSource() == cancel)
			dispose();
	}
}