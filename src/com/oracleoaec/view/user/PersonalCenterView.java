package com.oracleoaec.view.user;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.oracleoaec.pojo.Users;
import com.oracleoaec.service.UsersService;
import com.oracleoaec.service.imp.UsersServiceImp;
import com.oracleoaec.util.UserInfo;

public class PersonalCenterView extends JInternalFrame {
	private JTextField edtUserName;
	private JTextField edtUserBalance;
	private JTextField edtUserLevel;
	private JTextField edtUserAccount;
	JButton button_1 = null;
	Map<String, Object> findUserById = null;
	
	private void initUserData() {
		UsersService us= new UsersServiceImp();
		findUserById = us.findUserById(UserInfo.userid);
		edtUserName.setText(findUserById.get("USER_NAME")+"");
		edtUserAccount.setText(findUserById.get("USER_ACCOUNT")+"");
		edtUserBalance.setText(findUserById.get("USER_BALANCE")+"");
		edtUserLevel.setText(findUserById.get("USER_LEVEL")+"");
		
		edtUserName.setEditable(false);
		edtUserAccount.setEditable(false);
		edtUserBalance.setEditable(false);
		edtUserLevel.setEditable(false);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonalCenterView frame = new PersonalCenterView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PersonalCenterView() {
		setTitle("个人信息/用户");
		setBounds(100, 100, 350, 418);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("用户名：");
		label.setBounds(71, 64, 58, 15);
		getContentPane().add(label);
		
		edtUserName = new JTextField();
		edtUserName.setBounds(149, 61, 105, 21);
		getContentPane().add(edtUserName);
		edtUserName.setColumns(10);
		
		JLabel label_1 = new JLabel("用户余额：");
		label_1.setBounds(71, 186, 68, 15);
		getContentPane().add(label_1);
		
		edtUserBalance = new JTextField();
		edtUserBalance.setColumns(10);
		edtUserBalance.setBounds(149, 183, 105, 21);
		getContentPane().add(edtUserBalance);
		
		JLabel label_2 = new JLabel("用户等级：");
		label_2.setBounds(71, 241, 68, 15);
		getContentPane().add(label_2);
		
		edtUserLevel = new JTextField();
		edtUserLevel.setColumns(10);
		edtUserLevel.setBounds(149, 238, 105, 21);
		getContentPane().add(edtUserLevel);
		
		edtUserAccount = new JTextField();
		edtUserAccount.setColumns(10);
		edtUserAccount.setBounds(149, 121, 105, 21);
		getContentPane().add(edtUserAccount);
		
		JLabel label_3 = new JLabel("用户账号：");
		label_3.setBounds(71, 124, 68, 15);
		getContentPane().add(label_3);
		
		JButton button = new JButton("修改用户名");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edtUserName.setEditable(true);
				button_1.setVisible(true);
			}
		});
		button.setBounds(40, 287, 105, 23);
		getContentPane().add(button);
		
		JButton btnNewButton = new JButton("修改密码");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateUserPasswordView uup = new UpdateUserPasswordView(findUserById);
				uup.setModal(true);
				uup.setVisible(true);
			}
		});
		btnNewButton.setBounds(187, 287, 105, 23);
		getContentPane().add(btnNewButton);
		
		button_1 = new JButton("提交");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Users user = new Users();
				user.setUserId(UserInfo.userid);
				user.setUserName(edtUserName.getText());
				user.setUserPassword(findUserById.get("USER_PASSWORD")+"");
				user.setUserLevel(findUserById.get("USER_LEVEL")+"");
				user.setUserBalance(new Double(findUserById.get("USER_BALANCE").toString()));
				user.setUserAccount(findUserById.get("USER_ACCOUNT")+"");
				user.setUserState("1");
				user.setUserStatus("1");
				
				int result = JOptionPane.showConfirmDialog(null, "确认修改");
				if(result == 0) {
					UsersService us = new UsersServiceImp();
					int i = us.updateUser(user);
					String message = (i > 0) ? "修改成功" : "修改失败，请联系管理员";
					JOptionPane.showMessageDialog(null, message);
				}
				edtUserName.setEditable(false);
				button_1.setVisible(false);
				initUserData();
			}
		});
		button_1.setVisible(false);
		button_1.setBounds(110, 329, 105, 23);
		getContentPane().add(button_1);

		initUserData();
	}
}
