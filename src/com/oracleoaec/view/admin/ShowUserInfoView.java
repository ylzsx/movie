package com.oracleoaec.view.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.oracleoaec.pojo.Users;
import com.oracleoaec.service.UsersService;
import com.oracleoaec.service.imp.UsersServiceImp;
import com.oracleoaec.util.RegexUtil;
import com.oracleoaec.util.TableUtil;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowUserInfoView extends JInternalFrame {
	private JTextField edtQueryName;
	private JTextField edtQueryAccount;
	private JTable table;
	DefaultTableModel dm;
	private JTextField edtQueryLevel;
	private JTextField textName;
	private JTextField textBalance;
	private JTextField textLevel;
	private JTextField edtAddUserBalance;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowUserInfoView frame = new ShowUserInfoView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void initTableData(String userName,String userAccount,String userLevel) {
		//将列名放到vector
		Vector<String> columnName = new Vector<String>();
		columnName.add("用户编号");
		columnName.add("用户名");
		columnName.add("账号");
		columnName.add("用户等级");
		columnName.add("余额");
		columnName.add("密码");
		columnName.add("状态");
		
		UsersService us = new UsersServiceImp();
		Vector<Vector> data = us.findUserInfo(userName, userAccount,userLevel);
		
		//将数据放置到model，后将model放置于table
		dm = new DefaultTableModel(data,columnName);
		table.setModel(dm);
		//隐藏密码和状态列
		TableUtil.hideTableColumn(table, 6);
		TableUtil.hideTableColumn(table, 5);
		
		resetTextBox();
	}

	public void resetTextBox() {
		textName.setText("");
		textBalance.setText("");
		textLevel.setText("");
		edtAddUserBalance.setText("");
	}
	/**
	 * Create the frame.
	 */
	public ShowUserInfoView() {
		setTitle("用户信息管理/管理员");
		setBounds(100, 100, 704, 395);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("用户名：");
		label.setBounds(30, 13, 54, 15);
		getContentPane().add(label);
		
		edtQueryName = new JTextField();
		edtQueryName.setBounds(89, 10, 102, 21);
		getContentPane().add(edtQueryName);
		edtQueryName.setColumns(10);
		
		//查询按钮
		JButton button = new JButton("初始化密码");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要初始化密码的用户");
				}else if(count == 1) {
					int result = JOptionPane.showConfirmDialog(null, "确认为此用户初始化密码？");
					if (result == 0) {
						int row = table.getSelectedRow();
						Integer userid = new Integer(table.getValueAt(row, 0).toString());
						UsersService us = new UsersServiceImp();
						int i = us.initializePassword(userid);
						String message = (i > 0) ? "初始化成功" : "初始化失败，请联系超级管理员" ;
						JOptionPane.showMessageDialog(null, message);
						
						String userName = edtQueryName.getText();
						String userAccount = edtQueryAccount.getText();
						String userLevel = edtQueryLevel.getText();
						initTableData(userName, userAccount, userLevel);
					}
				}else if(count > 1){
					JOptionPane.showMessageDialog(null, "本功能只支持单行操作");
				}
			}
		});
		button.setBounds(420, 48, 116, 23);
		getContentPane().add(button);
		
		edtQueryAccount = new JTextField();
		edtQueryAccount.setColumns(10);
		edtQueryAccount.setBounds(261, 10, 102, 21);
		getContentPane().add(edtQueryAccount);
		
		JLabel label_1 = new JLabel("账号：");
		label_1.setBounds(214, 13, 54, 15);
		getContentPane().add(label_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 81, 610, 165);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowIndex = table.getSelectedRow();
				String userName = (String) table.getValueAt(rowIndex, 1);
				String userBalance = (String) table.getValueAt(rowIndex, 4);
				String userLevel = (String)table.getValueAt(rowIndex, 3);
				
				//将数据显示到下边的框体中
				textName.setText(userName);
				textBalance.setText(userBalance);
				textLevel.setText(userLevel);
			}
		});
		table.setBounds(0, 0, 610, 165);
		
		JLabel label_2 = new JLabel("等级：");
		label_2.setBounds(389, 13, 54, 15);
		getContentPane().add(label_2);
		
		edtQueryLevel = new JTextField();
		edtQueryLevel.setColumns(10);
		edtQueryLevel.setBounds(436, 10, 102, 21);
		getContentPane().add(edtQueryLevel);
		
		scrollPane.setViewportView(table);
		
		JButton button_1 = new JButton("删除");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要删除的用户");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					//是否删除
					int result = JOptionPane.showConfirmDialog(null, "是否删除？");
					if(result == 0) {
						//点击table获取选中记录的id
						//获取选中的行
						int rowIndex = table.getSelectedRow();
						Integer userid = new Integer(table.getValueAt(rowIndex, 0).toString());

						UsersService us = new UsersServiceImp();
						int i = us.deleteUserById(userid);
						String message = i > 0 ? "删除成功" : "删除失败，请联系超级管理员";
						JOptionPane.showMessageDialog(null, message);
						
						String userName = edtQueryName.getText();
						String userAccount = edtQueryAccount.getText();
						String userLevel = edtQueryLevel.getText();
						initTableData(userName, userAccount, userLevel);
					}
				}
			}
		});
		button_1.setBounds(75, 48, 93, 23);
		getContentPane().add(button_1);
		
		JLabel label_3 = new JLabel("用户名：");
		label_3.setBounds(49, 281, 54, 15);
		getContentPane().add(label_3);
		
		textName = new JTextField();
		textName.setBounds(103, 278, 127, 23);
		getContentPane().add(textName);
		textName.setColumns(10);
		
		JLabel label_5 = new JLabel("余额：");
		label_5.setBounds(261, 281, 54, 15);
		getContentPane().add(label_5);
		
		textBalance = new JTextField();
		textBalance.setColumns(10);
		textBalance.setBounds(315, 278, 127, 23);
		getContentPane().add(textBalance);
		
		JLabel label_6 = new JLabel("等级：");
		label_6.setBounds(476, 281, 54, 15);
		getContentPane().add(label_6);
		
		textLevel = new JTextField();
		textLevel.setColumns(10);
		textLevel.setBounds(530, 278, 127, 23);
		getContentPane().add(textLevel);
		
		JButton button_2 = new JButton("更新");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要更新的用户");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					//获取更新的数据
					String name = textName.getText();
					String balanceOld = textBalance.getText();
					String level = textLevel.getText();
					String pwd = (String) table.getValueAt(row, 5);
					String state = (String) table.getValueAt(row, 6);
					String account = (String) table.getValueAt(row, 2);
					Integer id = new Integer(table.getValueAt(row, 0).toString());
					
					if(RegexUtil.regexMatch(RegexUtil.numberRegex, balanceOld)) {
						int result = JOptionPane.showConfirmDialog(null, "是否更新？");
						if(result == 0) {
							Users user = new Users();
							user.setUserName(name);
							user.setUserAccount(account);
							user.setUserLevel(level);
							user.setUserBalance(new Double(balanceOld));
							user.setUserPassword(pwd);
							user.setUserState(state);
							user.setUserId(id);
							user.setUserStatus("1");

							UsersService us = new UsersServiceImp();
							int i = us.updateUser(user);
							//System.out.println(i);
							String message = i > 0 ? "更新成功" : "更新失败，请联系超级管理员";
							JOptionPane.showMessageDialog(null, message);
							
							//重新加载数据
							String userName = edtQueryName.getText();
							String userAccount = edtQueryAccount.getText();
							String userLevel = edtQueryLevel.getText();
							initTableData(userName, userAccount, userLevel);
						}
					}else {
						JOptionPane.showMessageDialog(null, "余额输入类型错误，请输入整数或两位小数");
					}
				}
			}
		});
		button_2.setBounds(261, 48, 93, 23);
		getContentPane().add(button_2);
		
		JButton button_3 = new JButton("查询");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = edtQueryName.getText();
				String userAccount = edtQueryAccount.getText();
				String userLevel = edtQueryLevel.getText();
				initTableData(userName, userAccount, userLevel);
			}
		});
		button_3.setBounds(548, 13, 109, 55);
		getContentPane().add(button_3);
		
		JLabel label_4 = new JLabel("充值金额：");
		label_4.setBounds(250, 329, 71, 15);
		getContentPane().add(label_4);
		
		edtAddUserBalance = new JTextField();
		edtAddUserBalance.setText("");
		edtAddUserBalance.setColumns(10);
		edtAddUserBalance.setBounds(318, 326, 127, 23);
		getContentPane().add(edtAddUserBalance);
		
		JButton btnNewButton = new JButton("充值");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中充值的用户");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "本功能只支持单行操作");
				}if(count == 1) {
					String addUserBalanceOld = edtAddUserBalance.getText();
					if(RegexUtil.regexMatch(RegexUtil.numberRegex, addUserBalanceOld)) {
						Double balance = new Double(addUserBalanceOld);
						int result = JOptionPane.showConfirmDialog(null, "确认为此用户充值？");
						if (result == 0) {
							int row = table.getSelectedRow();
							Integer userid = new Integer(table.getValueAt(row, 0).toString());
							UsersService us = new UsersServiceImp();
							int i = us.addUserBalanceById(userid, balance);
							String message = (i > 0) ? "充值成功" : "充值失败，请联系超级管理员" ;
							JOptionPane.showMessageDialog(null, message);
							
							//重新加载数据
							String userName = edtQueryName.getText();
							String userAccount = edtQueryAccount.getText();
							String userLevel = edtQueryLevel.getText();
							initTableData(userName, userAccount, userLevel);
						}
					}else {
						JOptionPane.showMessageDialog(null, "充值金额输入类型错误，请输入整数或两位小数");
					}
				}
			}
		});
		btnNewButton.setBounds(530, 326, 110, 23);
		getContentPane().add(btnNewButton);
		
		initTableData(null,null,null);

	}
}
