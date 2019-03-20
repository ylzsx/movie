package com.oracleoaec.view.admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.oracleoaec.pojo.Hall;
import com.oracleoaec.service.CinemaService;
import com.oracleoaec.service.HallService;
import com.oracleoaec.service.SessionService;
import com.oracleoaec.service.imp.CinemaServiceImpl;
import com.oracleoaec.service.imp.HallServiceImpl;
import com.oracleoaec.service.imp.SessionServiceImpl;
import com.oracleoaec.util.RegexUtil;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowHallView extends JDialog {
	private JTable table;
	private JTextField textCinemaName;

	private Integer cinemaid;
	private String cinemaName;
	private JTextField edtHallName;
	private JTextField edtHallCapacity;
	
	public void initHallData(Integer cinemaid) {
		//将列名放到vector
		Vector<String> columnName = new Vector<String>();
		columnName.add("影厅编号");
		columnName.add("影厅名称");
		columnName.add("影厅容量");
		
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		
		HallService hs = new HallServiceImpl();
		List<Map<String, Object>> list = hs.findHallByCinemaId(cinemaid);
		for (Map<String, Object> map : list) {
			Vector<String> row = new Vector<String>();
			row.add(map.get("HALL_ID")+"");
			row.add(map.get("HALL_NAME")+"");
			row.add(map.get("HALL_CAPACITY")+"");
			data.add(row);
		}
		//将数据放置到model，后将model放置于table
		DefaultTableModel dm = new DefaultTableModel(data,columnName);
		table.setModel(dm);
		
		resetTextBox();
	}
	
	public void resetTextBox() {
		edtHallName.setText("");
		edtHallCapacity.setText("");
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ShowHallView dialog = new ShowHallView(1,"");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ShowHallView(Integer cinemaid,String cinemaName) {
		this.cinemaid = cinemaid;
		this.cinemaName = cinemaName;
		setTitle("影厅信息管理/管理员");
		setBounds(100, 100, 462, 324);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 428, 159);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				edtHallName.setText(table.getValueAt(row, 1).toString());
				edtHallCapacity.setText(table.getValueAt(row, 2).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel label = new JLabel("影院名称：");
		label.setBounds(21, 23, 80, 15);
		getContentPane().add(label);
		
		textCinemaName = new JTextField();
		textCinemaName.setBounds(90, 20, 109, 21);
		getContentPane().add(textCinemaName);
		textCinemaName.setColumns(10);
		textCinemaName.setText(this.cinemaName);
		textCinemaName.setEditable(false);
		
		JButton button = new JButton("删除影厅");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要删除的影厅");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					int result = JOptionPane.showConfirmDialog(null, "确认删除该影厅？");
					if(result == 0) {
						Integer hallid = new Integer(table.getValueAt(row, 0).toString());
						
						HallService hs = new HallServiceImpl();
						Set<Integer> hallidSet = new HashSet<Integer>();
						hallidSet.add(hallid);
						
						SessionService ss = new SessionServiceImpl();
						Set<Integer> whetherHasSession = ss.whetherHasSession(hallidSet, cinemaid);
						//System.out.println(whetherHasSession.size());
						if(whetherHasSession.size() == 0) {
							HallService hs1 = new HallServiceImpl();
							int i = hs1.deleteHallById(hallidSet);
							//System.out.println(i);
							String message = (i > 0) ? "删除成功" : "删除失败，请联系超级管理员";
							JOptionPane.showMessageDialog(null, message);
							initHallData(cinemaid);
						}else {
							JOptionPane.showMessageDialog(null, "删除失败，该影厅中有正在上映或未上映的电影");
						}
					}
				}
			}
		});
		button.setBounds(209, 18, 97, 23);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("查看电影场次");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要查询的影厅");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					Integer hallid = new Integer(table.getValueAt(row, 0).toString());
					String hallName = table.getValueAt(row, 1).toString();
					
					ShowSessionView ssv = new ShowSessionView(cinemaid, cinemaName, hallid, hallName);
					ssv.setModal(true);
					ssv.setVisible(true);
				}
			}
		});
		button_1.setBounds(317, 18, 121, 23);
		getContentPane().add(button_1);
		
		JButton button_2 = new JButton("修改影厅");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要为哪家影院添加影厅");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					int result = JOptionPane.showConfirmDialog(null, "是否确定修改影厅？");
					if(result == 0) {
						String hallName = edtHallName.getText();
						String hallCapacityOld = edtHallCapacity.getText();
						Integer hallid = new Integer(table.getValueAt(row, 0).toString());
						if(RegexUtil.regexMatch(RegexUtil.positiveIntegerRegex, hallCapacityOld)) {
							Integer hallCapacity = new Integer(hallCapacityOld);
							Hall hall = new Hall();
							hall.setHallid(hallid);
							hall.setHallName(hallName);
							hall.setHallCapacity(hallCapacity);
							
							HallService hs = new HallServiceImpl();
							int i = hs.updateHallById(hall);
							String message = (i > 0) ? "修改成功" : "修改失败，请联系超级管理员";
							JOptionPane.showMessageDialog(null, message);
							initHallData(cinemaid);
						}else {
							JOptionPane.showMessageDialog(null, "影厅容量输入格式错误，请输入正整数");
						}
					}
				}
			}
		});
		button_2.setBounds(295, 220, 97, 50);
		getContentPane().add(button_2);
		
		JLabel label_1 = new JLabel("影厅名称：");
		label_1.setBounds(20, 220, 81, 15);
		getContentPane().add(label_1);
		
		edtHallName = new JTextField();
		edtHallName.setBounds(90, 220, 109, 21);
		getContentPane().add(edtHallName);
		edtHallName.setColumns(10);
		
		JLabel label_2 = new JLabel("影厅容量：");
		label_2.setBounds(21, 251, 81, 15);
		getContentPane().add(label_2);
		
		edtHallCapacity = new JTextField();
		edtHallCapacity.setColumns(10);
		edtHallCapacity.setBounds(91, 251, 109, 21);
		getContentPane().add(edtHallCapacity);
		
		initHallData(this.cinemaid);
	}
}
