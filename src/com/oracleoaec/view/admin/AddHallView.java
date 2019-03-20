package com.oracleoaec.view.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.oracleoaec.pojo.Hall;
import com.oracleoaec.service.HallService;
import com.oracleoaec.service.imp.HallServiceImpl;
import com.oracleoaec.util.RegexUtil;

public class AddHallView extends JDialog {
	private JTextField edtHallName;
	private JTextField edtHallCapacity;

	private Integer cinemaid;
	
	private void closeSelf() {
		this.setVisible(false);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddHallView dialog = new AddHallView(1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddHallView(Integer cinemaid) {
		setTitle("影厅添加/管理员");
		setBounds(100, 100, 374, 305);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("影厅名称：");
		label.setBounds(42, 61, 81, 21);
		getContentPane().add(label);
		
		edtHallName = new JTextField();
		edtHallName.setBounds(127, 61, 130, 21);
		getContentPane().add(edtHallName);
		edtHallName.setColumns(10);
		
		edtHallCapacity = new JTextField();
		edtHallCapacity.setColumns(10);
		edtHallCapacity.setBounds(127, 124, 130, 21);
		getContentPane().add(edtHallCapacity);
		
		JLabel label_1 = new JLabel("影厅容量：");
		label_1.setBounds(42, 124, 81, 21);
		getContentPane().add(label_1);
		
		JButton button = new JButton("添加影厅");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String hallName = edtHallName.getText();
				String hallCapacityOld = edtHallCapacity.getText();
				
				if("".equals(hallName)) {
					JOptionPane.showMessageDialog(null, "影厅名称不能为空");
				}else if("".equals(hallCapacityOld)) {
					JOptionPane.showMessageDialog(null, "影厅容量不能为空");
				}else {
					if(!RegexUtil.regexMatch(RegexUtil.positiveIntegerRegex, hallCapacityOld)) {
						JOptionPane.showMessageDialog(null, "影厅容量输入格式错误，请输入正整数");
					}else {
						Integer hallCapacity = new Integer(hallCapacityOld);
						
						Hall hall = new Hall();
						hall.setHallName(hallName);
						hall.setHallCapacity(hallCapacity);
						hall.setHallCid(cinemaid);
						hall.setHallStatus("1");
						
						HallService hs = new HallServiceImpl();
						int pk = hs.addHallReturnPrimaryKey(hall);
						String message = null;
						if(pk > 0) {
							message = "添加成功";
							JOptionPane.showMessageDialog(null, message);
							closeSelf();
						}else if(pk == 0) {
							message = "添加失败，请联系超级管理员";
							JOptionPane.showMessageDialog(null, message);
							closeSelf();
						}else if(pk == -1) {
							message = "添加失败，该影院该影厅名称已被占用";
							JOptionPane.showMessageDialog(null, message);
						}
						
					}
				}
				
			}
		});
		button.setBounds(114, 184, 102, 34);
		getContentPane().add(button);
		
		this.cinemaid = cinemaid;
	}
}
