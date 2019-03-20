package com.oracleoaec.view.admin;

import java.awt.EventQueue;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.oracleoaec.pojo.Movie;
import com.oracleoaec.service.MovieService;
import com.oracleoaec.service.SessionService;
import com.oracleoaec.service.imp.MovieServiceImpl;
import com.oracleoaec.service.imp.SessionServiceImpl;
import com.oracleoaec.util.RegexUtil;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowMovieView extends JInternalFrame {
	private JTextField edtMovieName;
	private JTextField edtMovieType;
	private JTextField textMovieName;
	private JTextField textMovieDuration;
	private JTextField textMovieDetail;
	private JTextField textMovieType;
	private JTable table;

	/*加载数据*/
	public void initTableData(Movie movie) {
		//将列名放到vector
		Vector<String> columnName = new Vector<String>();
		columnName.add("电影编号");
		columnName.add("电影名称");
		columnName.add("电影时长");
		columnName.add("电影类型");
		columnName.add("电影详情");
		
		MovieService ms = new MovieServiceImpl();
		Vector<Vector<String>> data = ms.findMovieInfo(movie);
		
		//将数据放置到model，后将model放置于table
		DefaultTableModel dm = new DefaultTableModel(data,columnName);
		table.setModel(dm);
		
		resetTextBox();
	}
	private void resetTextBox() {
		textMovieName.setText("");
		textMovieDuration.setText("");
		textMovieType.setText("");
		textMovieDetail.setText("");
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowMovieView frame = new ShowMovieView();
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
	public ShowMovieView() {
		setTitle("影片管理/管理员");
		setBounds(100, 100, 597, 395);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("电影名称：");
		label.setBounds(26, 24, 67, 15);
		getContentPane().add(label);
		
		edtMovieName = new JTextField();
		edtMovieName.setColumns(10);
		edtMovieName.setBounds(96, 21, 108, 21);
		getContentPane().add(edtMovieName);
		
		JLabel label_1 = new JLabel("电影类型：");
		label_1.setBounds(26, 55, 67, 15);
		getContentPane().add(label_1);
		
		edtMovieType = new JTextField();
		edtMovieType.setColumns(10);
		edtMovieType.setBounds(96, 52, 108, 21);
		getContentPane().add(edtMovieType);
		
		JButton button = new JButton("电影查询");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String movieName = edtMovieName.getText();
				String movieType = edtMovieType.getText();
				Movie movie = new Movie();
				movie.setMovieName(movieName);
				movie.setMovieType(movieType);
				
				initTableData(movie);
			}
		});
		button.setBounds(247, 24, 97, 48);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("电影修改");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String movieName = textMovieName.getText();
				String movieDetail = textMovieDetail.getText();
				String movieDuration = textMovieDuration.getText();
				String movieType = textMovieType.getText();
				
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要修改的电影");
				}else if(count == 1){
					int row = table.getSelectedRow();
					Integer movieid = new Integer(table.getValueAt(row, 0).toString());
					if(RegexUtil.regexMatch(RegexUtil.positiveIntegerRegex, movieDuration)) {
						int result = JOptionPane.showConfirmDialog(null, "确认修改？");
						if(result == 0) {
							SessionService ss = new SessionServiceImpl();
							List<Map<String, Object>> findSessionByMovieId = ss.findSessionByMovieId(movieid);
							if(findSessionByMovieId.size() == 0) {
								Movie movie = new Movie();
								movie.setMovieid(movieid);
								movie.setMovieName(movieName);
								movie.setMovieDuration(movieDuration);
								movie.setMovieDetail(movieDetail);
								movie.setMovieType(movieType);
								movie.setMovieStatus("1");
								
								MovieService ms = new MovieServiceImpl();
								int i = ms.updateMovieById(movie);
								String message = (i > 0) ? "修改成功" : "修改失败，请联系管理员";
								JOptionPane.showMessageDialog(null, message);
								
								//重新加载数据
								Movie movie1 = new Movie();
								movie1.setMovieName(edtMovieName.getText());
								movie1.setMovieType(edtMovieType.getText());
								initTableData(movie1);
								
							}else {
								JOptionPane.showMessageDialog(null, "该电影已被加入场次且未上映，不可修改");
							}
						}
					}else {
						JOptionPane.showMessageDialog(null, "电影时长输入不合法，请输入正整数");
					}
				}else if(count > 1){
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}
			}
		});
		button_1.setBounds(354, 24, 97, 48);
		getContentPane().add(button_1);
		
		JButton button_2 = new JButton("电影删除");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = table.getSelectedRowCount();
				if(count == 0) {
					JOptionPane.showMessageDialog(null, "请选中要删除的电影");
				}else if(count == 1){
					int row = table.getSelectedRow();
					Integer movieid = new Integer(table.getValueAt(row, 0).toString());
					
					int result = JOptionPane.showConfirmDialog(null, "确认删除？");
					if(result == 0) {
						SessionService ss = new SessionServiceImpl();
						List<Map<String, Object>> findSessionByMovieId = ss.findSessionByMovieId(movieid);
						if(findSessionByMovieId.size() == 0) {
							MovieService ms = new MovieServiceImpl();
							int i = ms.deleteMovieById(movieid);
							String message = (i > 0) ? "删除成功" : "删除失败，请联系管理员";
							JOptionPane.showMessageDialog(null, message);
							
							//重新加载数据
							Movie movie1 = new Movie();
							movie1.setMovieName(edtMovieName.getText());
							movie1.setMovieType(edtMovieType.getText());
							initTableData(movie1);
						}else {
							JOptionPane.showMessageDialog(null, "该电影已被加入场次且未上映，不可删除");
						}
					}
				}else if(count > 1){
					JOptionPane.showMessageDialog(null, "此功能只支持单行操作");
				}
			}
		});
		button_2.setBounds(461, 24, 97, 48);
		getContentPane().add(button_2);
		
		JLabel label_2 = new JLabel("电 影 名 称：");
		label_2.setBounds(34, 283, 97, 15);
		getContentPane().add(label_2);
		
		textMovieName = new JTextField();
		textMovieName.setText("");
		textMovieName.setColumns(10);
		textMovieName.setBounds(126, 280, 108, 21);
		getContentPane().add(textMovieName);
		
		JLabel label_3 = new JLabel("电影时长(min)：");
		label_3.setBounds(34, 312, 97, 15);
		getContentPane().add(label_3);
		
		textMovieDuration = new JTextField();
		textMovieDuration.setText("");
		textMovieDuration.setColumns(10);
		textMovieDuration.setBounds(126, 309, 108, 21);
		getContentPane().add(textMovieDuration);
		
		textMovieDetail = new JTextField();
		textMovieDetail.setText("");
		textMovieDetail.setColumns(10);
		textMovieDetail.setBounds(418, 280, 108, 21);
		getContentPane().add(textMovieDetail);
		
		JLabel label_4 = new JLabel("电影详情：");
		label_4.setBounds(348, 283, 67, 15);
		getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("电影类型：");
		label_5.setBounds(348, 312, 67, 15);
		getContentPane().add(label_5);
		
		textMovieType = new JTextField();
		textMovieType.setText("");
		textMovieType.setColumns(10);
		textMovieType.setBounds(418, 309, 108, 21);
		getContentPane().add(textMovieType);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 83, 567, 184);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				textMovieName.setText(table.getValueAt(row, 1)+"");
				textMovieDuration.setText(table.getValueAt(row, 2)+"");
				textMovieType.setText(table.getValueAt(row, 3)+"");
				textMovieDetail.setText(table.getValueAt(row, 4)+"");
			}
		});
		scrollPane.setViewportView(table);

		initTableData(new Movie());
	}
}
