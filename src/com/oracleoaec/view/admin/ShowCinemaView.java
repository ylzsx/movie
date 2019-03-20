package com.oracleoaec.view.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.oracleoaec.pojo.Cinema;
import com.oracleoaec.service.CinemaService;
import com.oracleoaec.service.HallService;
import com.oracleoaec.service.SessionService;
import com.oracleoaec.service.imp.CinemaServiceImpl;
import com.oracleoaec.service.imp.HallServiceImpl;
import com.oracleoaec.service.imp.SessionServiceImpl;

public class ShowCinemaView extends JInternalFrame {
	private JTextField edtCinemaName;
	private JTable table;
	private JTextField edtCinemaAddress;
	private JTextField textCinemaName;
	private JTextField textCinemaAddress;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowCinemaView frame = new ShowCinemaView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*加载数据*/
	public void initTableData(Cinema cinema) {
		//将列名放到vector
		Vector<String> columnName = new Vector<String>();
		columnName.add("影院编号");
		columnName.add("影院名称");
		columnName.add("影院地址");
		
		CinemaService cs = new CinemaServiceImpl();
		Vector<Vector<String>> data = cs.findCinema(cinema);
		
		//将数据放置到model，后将model放置于table
		DefaultTableModel dm = new DefaultTableModel(data,columnName);
		table.setModel(dm);
		
		resetTextBox();
	}

	public void resetTextBox() {
		textCinemaName.setText("");
		textCinemaAddress.setText("");
	}
	/**
	 * Create the frame.
	 */
	public ShowCinemaView() {
		setTitle("影院信息查询/管理员");
		setBounds(100, 100, 703, 382);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("影院名称：");
		label.setBounds(45, 13, 73, 25);
		getContentPane().add(label);
		
		edtCinemaName = new JTextField();
		edtCinemaName.setBounds(109, 15, 121, 21);
		getContentPane().add(edtCinemaName);
		edtCinemaName.setColumns(10);
		
		//查询按钮
		JButton button = new JButton("添加影厅");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要为哪家影院添加影厅");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					Integer cinemaid = new Integer(table.getValueAt(row, 0).toString());
					AddHallView ahv = new AddHallView(cinemaid);
					ahv.setModal(true);
					ahv.setVisible(true);
				}
			}
		});
		button.setBounds(524, 13, 116, 23);
		getContentPane().add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 81, 610, 165);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowIndex = table.getSelectedRow();
				String cinemaName = (String) table.getValueAt(rowIndex, 1);
				String cinemaAddress = (String) table.getValueAt(rowIndex, 2);
				
				textCinemaName.setText(cinemaName);
				textCinemaAddress.setText(cinemaAddress);
			}
		});
		table.setBounds(0, 0, 610, 165);
		
		JLabel label_2 = new JLabel("影院地点：");
		label_2.setBounds(284, 20, 73, 15);
		getContentPane().add(label_2);
		
		edtCinemaAddress = new JTextField();
		edtCinemaAddress.setColumns(10);
		edtCinemaAddress.setBounds(367, 17, 121, 21);
		getContentPane().add(edtCinemaAddress);
		
		scrollPane.setViewportView(table);
		
		JButton button_1 = new JButton("删除影院");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要删除的影院");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					//是否删除
					int result = JOptionPane.showConfirmDialog(null, "删除影院将删除该影院下的所有影厅，是否继续？");
					if(result == 0) {
						Integer cinemaid = new Integer(table.getValueAt(row, 0).toString());
						String cinemaName = table.getValueAt(row, 1).toString();
						//获得要删除影院的所有影厅id
						HallService hs = new HallServiceImpl();
						List<Map<String, Object>> hallList = hs.findHallByCinemaId(cinemaid);
						Set<Integer> hallidSet = new HashSet<Integer>();
						for (Map<String, Object> map : hallList) {
							hallidSet.add(new Integer(map.get("HALL_ID").toString()));
						}
						//获取这些影厅中排有场次的厂厅
						SessionService ss = new SessionServiceImpl();
						Set<Integer> hasSessionHallidSet = ss.whetherHasSession(hallidSet, cinemaid);
						
						if(hasSessionHallidSet.size() == 0) {
							//删除影院，并删除其所有的影厅
							HallService hs1 = new HallServiceImpl();
							int i = hs1.deleteHallById(hallidSet);
							int tempResult = 0;
							if(i > 0) {
								//若删除影厅成功，则删除影院
								tempResult ++;
								//System.out.println(tempResult);
								CinemaService cs = new CinemaServiceImpl();
								int j = cs.deleteCinemaById(cinemaid);
								if(j > 0) {
									tempResult++;
									//System.out.println(tempResult);
								}
							}
							//System.out.println(tempResult);
							//判断并提示用户删除情况
							if(tempResult == 2) {
								JOptionPane.showMessageDialog(null, "删除成功");
								initTableData(new Cinema());
							}else if(tempResult == 1) {
								JOptionPane.showMessageDialog(null, "影厅删除成功，影院删除失败，请联系超级管理员");
							}else if(tempResult == 0) {
								JOptionPane.showMessageDialog(null, "删除失败，请联系超级管理员");
							}
						}else {
							//获得有场次的影厅的名字
							String str = cinemaName+"中";
							for(Integer hallid : hasSessionHallidSet) {
								for(Map<String, Object> map : hallList) {
									if(map.get("HALL_ID").toString().equals(hallid.toString())) {
										str += " ";
										str += map.get("HALL_NAME").toString();
										break;
									}
								}
							}
							str += " 有正在上映或未上映的场次，请确保无场次后再进行删除";
							//弹出提示框
							JOptionPane.showMessageDialog(null, str);
						}
					}
				}
			}
		});
		button_1.setBounds(75, 48, 93, 23);
		getContentPane().add(button_1);
		
		JButton button_2 = new JButton("更新影院");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要更新的影院");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					//获取更新的数据
					String name = textCinemaName.getText();
					String address = textCinemaAddress.getText();
					Integer id = new Integer(table.getValueAt(row, 0).toString());
					
					int result = JOptionPane.showConfirmDialog(null, "是否更新？");
					if(result == 0) {
						Cinema cinema = new Cinema();
						cinema.setCinemaid(id);
						cinema.setCinemaName(name);
						cinema.setCinemaAddress(address);
						cinema.setCinemaStatus("1");

						CinemaService cs = new CinemaServiceImpl();
						int i = cs.updateCinemaById(cinema);
						String message = i > 0 ? "更新成功" : "更新失败，请联系超级管理员";
						JOptionPane.showMessageDialog(null, message);
						
						//重新加载数据
						String cinemaName = edtCinemaName.getText();
						String cinemaAddress = edtCinemaAddress.getText();
						Cinema cinema1 = new Cinema();
						cinema1.setCinemaName(cinemaName);
						cinema1.setCinemaAddress(cinemaAddress);
						initTableData(cinema1);
					}
				}
			}
		});
		button_2.setBounds(227, 48, 93, 23);
		getContentPane().add(button_2);
		
		JButton button_3 = new JButton("查询影院");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cinemaName = edtCinemaName.getText();
				String cinemaAddress = edtCinemaAddress.getText();
				
				Cinema cinema = new Cinema();
				cinema.setCinemaName(cinemaName);
				cinema.setCinemaAddress(cinemaAddress);
				initTableData(cinema);
			}
		});
		button_3.setBounds(377, 47, 106, 25);
		getContentPane().add(button_3);
		
		textCinemaName = new JTextField();
		textCinemaName.setBounds(109, 279, 140, 25);
		getContentPane().add(textCinemaName);
		textCinemaName.setColumns(10);
		
		JLabel label_1 = new JLabel("影院名称：");
		label_1.setBounds(45, 280, 73, 18);
		getContentPane().add(label_1);
		
		textCinemaAddress = new JTextField();
		textCinemaAddress.setColumns(10);
		textCinemaAddress.setBounds(445, 279, 140, 25);
		getContentPane().add(textCinemaAddress);
		
		JLabel label_3 = new JLabel("影院地点：");
		label_3.setBounds(362, 282, 73, 15);
		getContentPane().add(label_3);
		
		JButton button_4 = new JButton("查询影厅");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选择要查询的影院");
				}else if(count > 1) {
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}else if(count == 1) {
					int row = table.getSelectedRow();
					Integer cinemaid = new Integer(table.getValueAt(row, 0).toString());
					String cinemaName = table.getValueAt(row, 1).toString();
					ShowHallView shv = new ShowHallView(cinemaid, cinemaName);
					shv.setModal(true);
					shv.setVisible(true);
				}
			}
		});
		button_4.setBounds(524, 47, 116, 25);
		getContentPane().add(button_4);
		
		initTableData(new Cinema());

	}
}
