package com.oracleoaec.view.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.oracleoaec.pojo.Users;
import com.oracleoaec.service.UsersService;
import com.oracleoaec.service.imp.UsersServiceImp;
import com.oracleoaec.util.RegexUtil;

public class AddUserView extends JInternalFrame {
	private JTextField edtUserName;
	private JTextField edtUserAccount;
	private JTextField edtUserPwd;
	private JTextField edtUserBalance;
	
	private void closeSelf() {
		this.setVisible(false);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUserView frame = new AddUserView();
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
	public AddUserView() {
		setTitle("用户添加/管理员");
		setBounds(100, 100, 397, 416);
		getContentPane().setLayout(null);
		
		JLabel txtUserName = new JLabel("用户名：");
		txtUserName.setBounds(64, 52, 54, 15);
		getContentPane().add(txtUserName);
		
		edtUserName = new JTextField();
		edtUserName.setBounds(128, 49, 150, 21);
		getContentPane().add(edtUserName);
		edtUserName.setColumns(10);
		
		JLabel label_2 = new JLabel("账号：");
		label_2.setBounds(64, 107, 54, 15);
		getContentPane().add(label_2);
		
		edtUserAccount = new JTextField();
		edtUserAccount.setColumns(10);
		edtUserAccount.setBounds(128, 104, 150, 21);
		getContentPane().add(edtUserAccount);
		
		JLabel label_1 = new JLabel("密码：");
		label_1.setBounds(64, 164, 54, 15);
		getContentPane().add(label_1);
		
		edtUserPwd = new JTextField();
		edtUserPwd.setColumns(10);
		edtUserPwd.setBounds(128, 161, 150, 21);
		getContentPane().add(edtUserPwd);
		
		JLabel label = new JLabel("余额：");
		label.setBounds(64, 224, 54, 15);
		getContentPane().add(label);
		
		edtUserBalance = new JTextField();
		edtUserBalance.setColumns(10);
		edtUserBalance.setBounds(128, 221, 150, 21);
		getContentPane().add(edtUserBalance);
		
		JButton btnRegister = new JButton("添加用户");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = edtUserName.getText();
				String userPassword = edtUserPwd.getText();
				String userAccount = edtUserAccount.getText();
				String userBalanceOld = edtUserBalance.getText();
				
				if("".equals(userName)) {
					JOptionPane.showMessageDialog(null, "用户名不能为空");
				}else if("".equals(userPassword)) {
					JOptionPane.showMessageDialog(null, "用户密码不能为空");
				}else if("".equals(userAccount)) {
					JOptionPane.showMessageDialog(null, "用户账号不能为空");
				}else if("".equals(userBalanceOld)) {
					JOptionPane.showMessageDialog(null, "用户余额不能为空");
				}else {
					if(RegexUtil.regexMatch(RegexUtil.numberRegex, userBalanceOld)) {
						Double userBalance = new Double(userBalanceOld);
						
						Users user = new Users();
						user.setUserName(userName);
						user.setUserPassword(userPassword);
						user.setUserAccount(userAccount);
						user.setUserBalance(userBalance);
						user.setUserState("1");
						
						UsersService us = new UsersServiceImp();
						int i = us.registUser(user);
						if(i > 0) {
							JOptionPane.showMessageDialog(null, "添加成功");
							closeSelf();
						}else if(i == 0){
							JOptionPane.showMessageDialog(null, "添加失败，请联系超级管理员");
						}else if(i == -1) {
							JOptionPane.showMessageDialog(null, "添加失败，该账号被占用");
						}
					}else {
						JOptionPane.showMessageDialog(null, "余额输入类型错误，请输入整数或两位小数");
					}
				}
				
				
			}
		});
		btnRegister.setBounds(139, 283, 93, 23);
		getContentPane().add(btnRegister);

	}

}
