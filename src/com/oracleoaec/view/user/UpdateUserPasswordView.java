package com.oracleoaec.view.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.oracleoaec.pojo.Users;
import com.oracleoaec.service.UsersService;
import com.oracleoaec.service.imp.UsersServiceImp;
import com.oracleoaec.util.UserInfo;
import com.oracleoaec.view.user.PersonalCenterView;

public class UpdateUserPasswordView extends JDialog {
	private JPasswordField edtOriginPassword;
	private JPasswordField edtNewPassword;
	private JPasswordField edtPasswordAgain;

	private void closeSelf() {
		this.setVisible(false);
	}
	/**
	 * Create the dialog.
	 */
	public UpdateUserPasswordView(Map<String, Object> findUserById) {
		setTitle("修改密码/用户");
		setBounds(100, 100, 401, 300);
		getContentPane().setLayout(null);
		{
			JLabel label = new JLabel("请输入原密码：");
			label.setBounds(67, 38, 98, 15);
			getContentPane().add(label);
		}
		{
			JLabel label = new JLabel("请输入新密码：");
			label.setBounds(67, 84, 98, 15);
			getContentPane().add(label);
		}
		{
			JLabel label = new JLabel("再次输入新密码：");
			label.setBounds(59, 135, 106, 15);
			getContentPane().add(label);
		}
		{
			JButton button = new JButton("提交");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String originPassword = edtOriginPassword.getText();
					String newPassword = edtNewPassword.getText();
					String newPasswordAgain = edtPasswordAgain.getText();
					if(!originPassword.equals(findUserById.get("USER_PASSWORD"))) {
						JOptionPane.showMessageDialog(null, "原密码错误");
					}else if(!newPassword.equals(newPasswordAgain)) {
						JOptionPane.showMessageDialog(null, "两次密码输入不一致");
					}else {
						Users user = new Users();
						user.setUserId(UserInfo.userid);
						user.setUserName(findUserById.get("USER_NAME")+"");
						user.setUserPassword(newPassword);
						user.setUserLevel(findUserById.get("USER_LEVEL")+"");
						user.setUserBalance(new Double(findUserById.get("USER_BALANCE").toString()));
						user.setUserAccount(findUserById.get("USER_ACCOUNT")+"");
						user.setUserState("1");
						user.setUserStatus("1");
						
						UsersService us = new UsersServiceImp();
						int i = us.updateUser(user);
						String message = (i > 0) ? "修改成功" : "修改失败，请联系管理员" ;
						JOptionPane.showMessageDialog(null, message);
						
						closeSelf();
					}
				}
			});
			button.setBounds(115, 188, 115, 23);
			getContentPane().add(button);
		}
		
		edtOriginPassword = new JPasswordField();
		edtOriginPassword.setBounds(179, 35, 122, 21);
		getContentPane().add(edtOriginPassword);
		
		edtNewPassword = new JPasswordField();
		edtNewPassword.setBounds(179, 81, 122, 21);
		getContentPane().add(edtNewPassword);
		
		edtPasswordAgain = new JPasswordField();
		edtPasswordAgain.setBounds(179, 132, 122, 21);
		getContentPane().add(edtPasswordAgain);
	}
}
