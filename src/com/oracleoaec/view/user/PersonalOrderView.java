package com.oracleoaec.view.user;

import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.oracleoaec.service.SessionService;
import com.oracleoaec.service.TicketService;
import com.oracleoaec.service.imp.SessionServiceImpl;
import com.oracleoaec.service.imp.TicketServiceImpl;
import com.oracleoaec.util.TableUtil;
import com.oracleoaec.util.UserInfo;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PersonalOrderView extends JInternalFrame {
	private JTable table;

	
	public void initTableData(Integer userid) {
		//将列名放到vector
		Vector<String> columnName = new Vector<String>();
		columnName.add("订单编号");
		columnName.add("影院名称");
		columnName.add("影厅名称");
		columnName.add("电影名称");
		columnName.add("上映时间");
		columnName.add("票价");
		columnName.add("余座");
		columnName.add("座位号");
		columnName.add("场次编号");
		
		TicketService ts = new TicketServiceImpl();
		Vector<Vector<String>> data = ts.findTicketByUserId(userid);
		
		//将数据放置到model，后将model放置于table
		DefaultTableModel dm = new DefaultTableModel(data,columnName);
		table.setModel(dm);
		
		TableUtil.hideTableColumn(table, 8);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonalOrderView frame = new PersonalOrderView();
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
	public PersonalOrderView() {
		setTitle("个人订单/用户");
		setBounds(30, 100, 923, 301);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 897, 193);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton button = new JButton("我要退票");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int countrow = table.getSelectedRowCount();
				if(countrow == 0) {
					JOptionPane.showMessageDialog(null, "请选中要退的票");
				}else if(countrow == 1){
					int result = JOptionPane.showConfirmDialog(null, "确认退票？");
					int row = table.getSelectedRow();
					if(result == 0) {
						TicketService ts = new TicketServiceImpl();
						Integer ticketid = new Integer(table.getValueAt(row, 0).toString());
						Integer sessionid = new Integer(table.getValueAt(row, 8).toString());
						Double price = new Double(table.getValueAt(row, 5).toString());
						int i = ts.returnTicket(UserInfo.userid, sessionid, ticketid, price);
						String message = null;
						if(i == -1) message = "该电影已过期,不允许退票";
						else if(i == 0) message = "退票失败，请联系超级管理员";
						else if(i > 0) message = "退票成功";
						JOptionPane.showMessageDialog(null, message);
						initTableData(UserInfo.userid);
					}
				}else if(countrow > 1){
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}
			}
		});
		button.setBounds(751, 231, 107, 31);
		getContentPane().add(button);

		initTableData(UserInfo.userid);
	}
}
