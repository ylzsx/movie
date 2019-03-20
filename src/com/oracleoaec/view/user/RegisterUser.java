package com.oracleoaec.view.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.oracleoaec.pojo.Users;
import com.oracleoaec.service.UsersService;
import com.oracleoaec.service.imp.UsersServiceImp;

public class RegisterUser extends JDialog {
	private JTextField edtUserName;
	private JTextField edtUserAccount;
	private JPasswordField edtUserPasswordOrigin;
	private JPasswordField edtUserPasswordNew;

	private void closeSelf() {
		this.setVisible(false);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegisterUser dialog = new RegisterUser();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegisterUser() {
		setTitle("注册");
		setBounds(100, 100, 420, 357);
		getContentPane().setLayout(null);
		{
			JLabel label = new JLabel("用户名：");
			label.setBounds(86, 30, 54, 15);
			getContentPane().add(label);
		}
		{
			edtUserName = new JTextField();
			edtUserName.setColumns(10);
			edtUserName.setBounds(150, 27, 150, 21);
			getContentPane().add(edtUserName);
		}
		{
			JLabel label = new JLabel("账号：");
			label.setBounds(86, 85, 54, 15);
			getContentPane().add(label);
		}
		{
			edtUserAccount = new JTextField();
			edtUserAccount.setColumns(10);
			edtUserAccount.setBounds(150, 82, 150, 21);
			getContentPane().add(edtUserAccount);
		}
		{
			JLabel label = new JLabel("密码：");
			label.setBounds(86, 138, 54, 15);
			getContentPane().add(label);
		}
		{
			edtUserPasswordOrigin = new JPasswordField();
			edtUserPasswordOrigin.setBounds(150, 138, 150, 21);
			getContentPane().add(edtUserPasswordOrigin);
		}
		{
			edtUserPasswordNew = new JPasswordField();
			edtUserPasswordNew.setBounds(150, 194, 150, 21);
			getContentPane().add(edtUserPasswordNew);
		}
		{
			JLabel label = new JLabel("再次输入密码：");
			label.setBounds(69, 197, 100, 15);
			getContentPane().add(label);
		}
		{
			JButton button = new JButton("注册");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String userName = edtUserName.getText();
					String userAccount = edtUserAccount.getText();
					String userPasswordOrigin = edtUserPasswordOrigin.getText();
					String userPasswordNew = edtUserPasswordNew.getText();
					
					if(userPasswordOrigin.equals(userPasswordNew)) {
						Users user = new Users();
						user.setUserName(userName);
						user.setUserAccount(userAccount);
						user.setUserPassword(userPasswordOrigin);
						user.setUserBalance(0.0);
						user.setUserState("1");
						
						UsersService us = new UsersServiceImp();
						int i = us.registUser(user);
						if(i > 0) {
							JOptionPane.showMessageDialog(null, "添加成功");
							closeSelf();
						}else if(i == 0){
							JOptionPane.showMessageDialog(null, "添加失败，请联系管理员");
							closeSelf();
						}else if(i == -1) {
							JOptionPane.showMessageDialog(null, "添加失败，该账号被占用");
						}
					}else {
						JOptionPane.showMessageDialog(null, "两次密码输入不一致");
					}
				}
			});
			button.setBounds(134, 261, 97, 23);
			getContentPane().add(button);
		}
	}

}
