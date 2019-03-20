package com.oracleoaec.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.oracleoaec.service.UsersService;
import com.oracleoaec.service.imp.UsersServiceImp;
import com.oracleoaec.util.UserInfo;
import com.oracleoaec.view.admin.AdminManageView;
import com.oracleoaec.view.user.RegisterUser;
import com.oracleoaec.view.user.UserAppView;

public class loginView extends JFrame {

	private JPanel contentPane;
	private JTextField edtAccount;
	private JPasswordField edtPwd;
	private JRadioButton radio1;
	private JRadioButton radio2;
	//按钮组
	private ButtonGroup bg = new ButtonGroup();
	private JButton button;
	
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
					loginView frame = new loginView();
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
	public loginView() {
		setTitle("登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 350, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("账号：");
		label.setBounds(86, 72, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密码：");
		label_1.setBounds(86, 109, 54, 15);
		contentPane.add(label_1);
		
		edtAccount = new JTextField();
		edtAccount.setBounds(141, 69, 179, 21);
		contentPane.add(edtAccount);
		edtAccount.setColumns(10);
		
		edtPwd = new JPasswordField();
		edtPwd.setBounds(141, 106, 179, 21);
		contentPane.add(edtPwd);
		
		JButton btnLogin = new JButton("登录");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * 按钮的点击事件，处理登录业务
				 * 1. 获取到登录人的信息
				 * 2. 将信息传递给sevice
				 * 3. 获取到service返回结果，判断是否登录成功
				 */
				//1. 获取到登录人的信息
				String account = edtAccount.getText();
				String pwd = edtPwd.getText();
				String state = radio1.isSelected() ? "1" : "0";
				
				if("".equals(account)) {
					JOptionPane.showMessageDialog(null, "用户名不能为空");
				}else if("".equals(pwd)) {
					JOptionPane.showMessageDialog(null, "密码不能为空");
				}else {
					//2. 将信息传递给sevice
					UsersService us = new UsersServiceImp();
					Map<String,Object> userInfo = us.login(account, pwd, state);
					
					//3. 获取到service返回结果，判断是否登录成功
					if(!userInfo.get("result").equals("登录成功")) {
						JOptionPane.showMessageDialog(null, userInfo.get("result"));
					}else {
						UserInfo.userid = ((BigDecimal)userInfo.get("USER_ID")).intValue();
						System.out.println(UserInfo.userid);
						if("0".equals(state)) {
							AdminManageView window = new AdminManageView();
							//让window类的窗口显示
							window.frame.setVisible(true);
							//保存用户id
							BigDecimal use = (BigDecimal) userInfo.get("USER_ID");
							UserInfo.userid = use.intValue();
							//关闭当前窗口
							closeSelf();
						}else if("1".equals(state)) {
							UserAppView window = new UserAppView();
							window.frame.setVisible(true);
							//关闭当前窗口
							closeSelf();
						}
					}
				}
			}
		});
		btnLogin.setBounds(86, 181, 93, 23);
		contentPane.add(btnLogin);
		
		radio1 = new JRadioButton("用户");
		radio1.setSelected(true);
		radio1.setBounds(114, 152, 77, 23);
		contentPane.add(radio1);
		
		radio2 = new JRadioButton("管理员");
		radio2.setBounds(219, 152, 121, 23);
		contentPane.add(radio2);
		
		bg.add(radio1);bg.add(radio2);
		button = new JButton("注册");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterUser ru = new RegisterUser();
				ru.setModal(true);
				ru.setVisible(true);
			}
		});
		button.setBounds(229, 181, 93, 23);
		contentPane.add(button);
	}
}
