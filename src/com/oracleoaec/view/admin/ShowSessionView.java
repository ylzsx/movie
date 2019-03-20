package com.oracleoaec.view.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.oracleoaec.pojo.Session;
import com.oracleoaec.service.SessionService;
import com.oracleoaec.service.imp.SessionServiceImpl;
import com.oracleoaec.util.RegexUtil;
import com.oracleoaec.util.TableUtil;
import com.oracleoaec.util.TimeUtil;

public class ShowSessionView extends JDialog {
	private JTextField textCinemaName;
	private JTextField textHallName;
	private JTable table;
	private JTextField edtSessionTime;
	private JTextField edtSessionPrice;
	
	private Integer cinemaid;
	private String cinemaName;
	private Integer hallid;
	private String hallName;

	public void initHallData(Integer hallid) {
		//将列名放到vector
		Vector<String> columnName = new Vector<String>();
		columnName.add("场次编号");
		columnName.add("电影名称");
		columnName.add("上映时间");
		columnName.add("单价");
		columnName.add("余座");
		columnName.add("电影时长");
		
		SessionService ss = new SessionServiceImpl();
		Vector<Vector<String>> data = ss.findSessionByHallId(hallid);
		//将数据放置到model，后将model放置于table
		DefaultTableModel dm = new DefaultTableModel(data,columnName);
		table.setModel(dm);
		TableUtil.hideTableColumn(table, 5);
		
		resetTextBox();
	}
	public void resetTextBox() {
		edtSessionTime.setText("");
		edtSessionPrice.setText("");
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ShowSessionView dialog = new ShowSessionView(1,"",1,"");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ShowSessionView(Integer cinemaid,String cinemaName,Integer hallid,String hallName) {
		setTitle("场次管理/管理员");
		this.cinemaid = cinemaid;
		this.cinemaName = cinemaName;
		this.hallid = hallid;
		this.hallName = hallName;
		setBounds(100, 100, 631, 360);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 591, 178);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				edtSessionTime.setText(table.getValueAt(row, 2).toString());
				edtSessionPrice.setText(table.getValueAt(row, 3).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel label = new JLabel("影院名称：");
		label.setBounds(10, 16, 70, 15);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("影厅名称：");
		label_1.setBounds(312, 16, 74, 15);
		getContentPane().add(label_1);
		
		textCinemaName = new JTextField();
		textCinemaName.setBounds(75, 13, 133, 21);
		getContentPane().add(textCinemaName);
		textCinemaName.setColumns(10);
		
		textHallName = new JTextField();
		textHallName.setColumns(10);
		textHallName.setBounds(379, 13, 138, 21);
		getContentPane().add(textHallName);
		
		JButton button_1 = new JButton("删除场次");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选择要删除的场次");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					int result = JOptionPane.showConfirmDialog(null, "确定删除该场次？");
					if(result == 0) {
						Integer sessionid = new Integer(table.getValueAt(row, 0).toString());
						SessionService ss = new SessionServiceImpl();
						int i = ss.deleteSessionById(sessionid);
						String message = null;
						if(i == -1) message = "该场次票已卖出，不允许删除";
						else if(i == 0) message = "删除失败，请联系超级管理员";
						else if(i > 0) message = "删除成功";
						JOptionPane.showMessageDialog(null, message);
						initHallData(hallid);
					}
				}
			}
		});
		button_1.setBounds(379, 237, 126, 29);
		getContentPane().add(button_1);
		
		JLabel label_2 = new JLabel("上映时间：");
		label_2.setBounds(25, 244, 70, 15);
		getContentPane().add(label_2);
		
		edtSessionTime = new JTextField();
		edtSessionTime.setColumns(10);
		edtSessionTime.setBounds(90, 241, 150, 21);
		getContentPane().add(edtSessionTime);
		
		JLabel label_3 = new JLabel("单价：");
		label_3.setBounds(25, 278, 70, 15);
		getContentPane().add(label_3);
		
		edtSessionPrice = new JTextField();
		edtSessionPrice.setColumns(10);
		edtSessionPrice.setBounds(90, 275, 150, 21);
		getContentPane().add(edtSessionPrice);
		
		JButton button = new JButton("修改场次");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要更新的场次");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					int result = JOptionPane.showConfirmDialog(null, "确定删除该场次？");
					if(result == 0) {
						Integer sessionid = new Integer(table.getValueAt(row, 0).toString());
						String sessionTime = edtSessionTime.getText();
						String sessionPriceOld = edtSessionPrice.getText();
						Integer movieDuration = new Integer(table.getValueAt(row, 5).toString());
						if(!RegexUtil.regexMatch(RegexUtil.timeRegex,sessionTime)) {
							JOptionPane.showMessageDialog(null, "上映时间输入格式错误，正确格式：1977-03-12 14:04:00");
						}else if(!RegexUtil.regexMatch(RegexUtil.numberRegex, sessionPriceOld)) {
							JOptionPane.showMessageDialog(null, "单价输入格式错误，请输入整数或最多两位小数");
						}else {
							String sessionEndTime = TimeUtil.addTime(sessionTime, movieDuration);
							Double sessionPrice = new Double(sessionPriceOld);
							Session session = new Session();
							session.setSessionid(sessionid);
							session.setSessionTime(sessionTime);
							session.setSessionEndTime(sessionEndTime);
							session.setSessionPrice(sessionPrice);
							
							SessionService ss = new SessionServiceImpl();
							int i = ss.updateSessionById(session);
							String message = (i > 0) ? "修改成功" : "修改失败，请联系超级管理员";
							JOptionPane.showMessageDialog(null, message);
							initHallData(hallid);
						}
					}
				}
			}
		});
		button.setBounds(379, 276, 126, 29);
		getContentPane().add(button);
		
		textCinemaName.setText(this.cinemaName);
		textCinemaName.setEditable(false);
		textHallName.setText(this.hallName);
		textHallName.setEditable(false);
		
		initHallData(this.hallid);
	}
}
