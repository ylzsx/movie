package com.oracleoaec.view.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.oracleoaec.pojo.Movie;
import com.oracleoaec.pojo.Session;
import com.oracleoaec.service.HallService;
import com.oracleoaec.service.MovieService;
import com.oracleoaec.service.SessionService;
import com.oracleoaec.service.imp.HallServiceImpl;
import com.oracleoaec.service.imp.MovieServiceImpl;
import com.oracleoaec.service.imp.SessionServiceImpl;
import com.oracleoaec.util.MovieInfoUtil;
import com.oracleoaec.util.RegexUtil;
import com.oracleoaec.util.TableUtil;
import com.oracleoaec.util.TimeUtil;

public class PlatoonView extends JInternalFrame {
	private JTextField edtSessionTime;
	private JTextField edtSessionPrice;
	private JTextField edtSessionRemain;
	private JTable table;
	private JTable table_1;

	private void closeSelf() {
		this.setVisible(false);
	}
	
	public void initMovieTableData() {
		//1. 将列名放入集合
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("电影编号");
		columnNames.add("电影名称");
		columnNames.add("电影时长");
		columnNames.add("电影类型");
		columnNames.add("电影详情");
		
		//2. 将表格数据放入data中
		MovieService ms = new MovieServiceImpl();
		Vector<Vector<String>> data = ms.findMovieInfo(new Movie());
		
		//3. 将数据放入model
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		//4. 将model放入table
		table.setModel(dtm);
		//5.隐藏不需要的列
		TableUtil.hideTableColumn(table, 4);
	}
	public void initHallTableData() {
		//1. 将列名放入集合
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("影院编号");
		columnNames.add("影院名称");
		columnNames.add("影厅编号");
		columnNames.add("影厅名称");
		columnNames.add("影厅容量");
		
		//2. 将表格数据放入data中
		HallService hs = new HallServiceImpl();
		Vector<Vector<String>> data = hs.findHallAndCinema();
		
		//3. 将数据放入model
		DefaultTableModel dtm = new DefaultTableModel(data,columnNames);
		//4. 将model放入table
		table_1.setModel(dtm);
		
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlatoonView frame = new PlatoonView();
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
	public PlatoonView() {
		setTitle("排片/管理员");
		setBounds(100, 100, 789, 450);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 37, 310, 225);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(355, 37, 381, 225);
		getContentPane().add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowHall = table_1.getSelectedRow();
				String hallCapacity = table_1.getValueAt(rowHall, 4).toString();
				edtSessionRemain.setText(hallCapacity);
			}
		});
		scrollPane_1.setViewportView(table_1);
		
		JLabel label = new JLabel("上映时间：");
		label.setBounds(65, 300, 79, 26);
		getContentPane().add(label);
		
		edtSessionTime = new JTextField();
		edtSessionTime.setBounds(143, 303, 175, 23);
		getContentPane().add(edtSessionTime);
		edtSessionTime.setColumns(10);
		
		JLabel label_1 = new JLabel("单   价：");
		label_1.setBounds(397, 300, 79, 26);
		getContentPane().add(label_1);
		
		edtSessionPrice = new JTextField();
		edtSessionPrice.setColumns(10);
		edtSessionPrice.setBounds(475, 303, 175, 23);
		getContentPane().add(edtSessionPrice);
		
		JLabel label_2 = new JLabel("余   座：");
		label_2.setBounds(65, 355, 79, 26);
		getContentPane().add(label_2);
		
		edtSessionRemain = new JTextField();
		edtSessionRemain.setColumns(10);
		edtSessionRemain.setBounds(143, 358, 175, 23);
		getContentPane().add(edtSessionRemain);
		
		JButton button = new JButton("排片");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int countMovie = table.getSelectedRowCount();
				int countHall = table_1.getSelectedRowCount();
				int rowMovie = table.getSelectedRow();
				int rowHall = table_1.getSelectedRow();
				String sessionTime = edtSessionTime.getText();
				String sessionPriceOld = edtSessionPrice.getText();
				String sessionRemainOld = edtSessionRemain.getText();
				String sessionEndTime = null;
				if(countMovie == 0) {
					JOptionPane.showMessageDialog(null, "请选择要排的电影");
				}else  if(countHall == 0) {
					JOptionPane.showMessageDialog(null, "请选择要排入的影院");
				}else if(countMovie > 1 || countHall > 1){
					JOptionPane.showMessageDialog(null, "本功能只支持单行操作");
				}else if(countMovie == 1 && countHall == 1){
					Integer movieTime = new Integer(table.getValueAt(rowMovie, 2).toString());
					Integer sessionHid = new Integer(table_1.getValueAt(rowHall, 2).toString());
					if(!RegexUtil.regexMatch(RegexUtil.timeRegex, sessionTime)){
						JOptionPane.showMessageDialog(null, "上映时间输入格式错误，正确格式：1977-03-12 14:04:00");
					}else if(!RegexUtil.regexMatch(RegexUtil.numberRegex, sessionPriceOld)) {
						JOptionPane.showMessageDialog(null, "单价输入格式错误，请输入整数或最多两位小数");
					}else if(!RegexUtil.regexMatch(RegexUtil.positiveIntegerRegex, sessionRemainOld)) {
						JOptionPane.showMessageDialog(null, "余座输入格式错误，请输入正整数");
					}else {
						sessionEndTime = TimeUtil.addTime(sessionTime, movieTime+MovieInfoUtil.cleanTime);
						SessionService ss = new SessionServiceImpl();
						if(ss.isPlatoo(sessionHid,sessionTime)) {
							Session session = new Session();
							Integer sessionCid = new Integer(table_1.getValueAt(rowHall, 0).toString());
							Integer sessionMid = new Integer(table.getValueAt(rowMovie, 0).toString());
							Double sessionPrice = new Double(sessionPriceOld);
							Integer sessionRemain = new Integer(sessionRemainOld);
							session.setSessionHid(sessionHid);
							session.setSessionCid(sessionCid);
							session.setSessionMid(sessionMid);
							session.setSessionTime(sessionTime);
							session.setSessionPrice(sessionPrice);
							session.setSessionRemain(sessionRemain);
							session.setSessionEndTime(sessionEndTime);
							session.setSessionStatus("1");
							
							int pk = ss.addSession(session);
							String message = (pk > 0) ? "排片成功" : "排片失败，请联系超级管理员";
							JOptionPane.showMessageDialog(null, message);
							closeSelf();						
						}else {
							JOptionPane.showMessageDialog(null, "排片失败，该时间段已有场次");
						}
						
					}
				}
				
				
			}
		});
		button.setBounds(442, 356, 162, 25);
		getContentPane().add(button);

		initMovieTableData();
		initHallTableData();
	}
}
