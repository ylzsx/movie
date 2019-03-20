package com.oracleoaec.view.user;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.oracleoaec.service.SessionService;
import com.oracleoaec.service.imp.SessionServiceImpl;

public class VideoNewsView extends JInternalFrame {
	private JTable table;

	
	public void initTableData() {
		//将列名放到vector
		Vector<String> columnName = new Vector<String>();
		columnName.add("场次编号");
		columnName.add("影院名称");
		columnName.add("影厅名称");
		columnName.add("电影名称");
		columnName.add("上映时间");
		columnName.add("电影类型");
		columnName.add("票价");
		columnName.add("余座");
		
		SessionService ss = new SessionServiceImpl();
		Vector<Vector<String>> data = ss.findAnyMovieInfo();
		
		//将数据放置到model，后将model放置于table
		DefaultTableModel dm = new DefaultTableModel(data,columnName);
		table.setModel(dm);
	}

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VideoNewsView frame = new VideoNewsView();
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
	public VideoNewsView() {
		setTitle("影讯");
		setBounds(20, 100, 921, 385);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 889, 251);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton button = new JButton("购票");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要为哪家影院添加影厅");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					Integer sessionid = new Integer(table.getValueAt(row, 0).toString());
					ShoppingTicketView stv = new ShoppingTicketView(sessionid);
					//设置为模态化窗口
					stv.setModal(true);
					stv.setVisible(true);
				}
			}
		});
		button.setBounds(756, 312, 93, 34);
		getContentPane().add(button);
		
		initTableData();

	}
}
