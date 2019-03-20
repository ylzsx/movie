package com.oracleoaec.view.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.oracleoaec.pojo.Movie;
import com.oracleoaec.service.MovieService;
import com.oracleoaec.service.imp.MovieServiceImpl;
import com.oracleoaec.util.RegexUtil;

public class AddMovieView extends JInternalFrame {
	private JTextField edtMovieName;
	private JTextField edtMovieDetail;
	private JTextField edtMovieDuration;
	private JTextField edtMovieType;
	
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
					AddMovieView frame = new AddMovieView();
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
	public AddMovieView() {
		setTitle("影片添加/管理员");
		setBounds(100, 100, 397, 416);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("电影名称：");
		label.setBounds(66, 43, 78, 20);
		getContentPane().add(label);
		
		edtMovieName = new JTextField();
		edtMovieName.setBounds(154, 43, 118, 20);
		getContentPane().add(edtMovieName);
		edtMovieName.setColumns(10);
		
		edtMovieDetail = new JTextField();
		edtMovieDetail.setColumns(10);
		edtMovieDetail.setBounds(154, 101, 118, 20);
		getContentPane().add(edtMovieDetail);
		
		JLabel label_1 = new JLabel("电影详情：");
		label_1.setBounds(66, 101, 78, 20);
		getContentPane().add(label_1);
		
		JLabel lblmin = new JLabel("电影时长(min)：");
		lblmin.setBounds(66, 155, 92, 20);
		getContentPane().add(lblmin);
		
		edtMovieDuration = new JTextField();
		edtMovieDuration.setColumns(10);
		edtMovieDuration.setBounds(154, 155, 118, 20);
		getContentPane().add(edtMovieDuration);
		
		JLabel label_3 = new JLabel("电影类型：");
		label_3.setBounds(66, 214, 78, 20);
		getContentPane().add(label_3);
		
		edtMovieType = new JTextField();
		edtMovieType.setColumns(10);
		edtMovieType.setBounds(154, 214, 118, 20);
		getContentPane().add(edtMovieType);
		
		JButton button = new JButton("添加电影");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String movieName = edtMovieName.getText();
				String movieDetail = edtMovieDetail.getText();
				String movieDuration = edtMovieDuration.getText();
				String movieType = edtMovieType.getText();
				
				if("".equals(movieName)) {
					JOptionPane.showMessageDialog(null, "影片名称不能为空");
				}else if("".equals(movieDetail)) {
					JOptionPane.showMessageDialog(null, "影片详情不能为空");
				}else if("".equals(movieDuration)) {
					JOptionPane.showMessageDialog(null, "影片时长不能为空");
				}else if("".equals(movieType)) {
					JOptionPane.showMessageDialog(null, "影片类型不能为空");
				}else {
					//对时长输入是否合法进行判断
					if("".equals(movieName)) {
						JOptionPane.showMessageDialog(null, "电影名不能为空");
					}else if(!RegexUtil.regexMatch(RegexUtil.positiveIntegerRegex, movieDuration)) {
						JOptionPane.showMessageDialog(null, "电影时长输入格式错误，请输入以分钟为单位的正整数");
					}else {
						Movie movie = new Movie();
						movie.setMovieName(movieName);
						movie.setMovieDetail(movieDetail);
						movie.setMovieDuration(movieDuration);
						movie.setMovieType(movieType);
						movie.setMovieStatus("1");
						
						MovieService ms = new MovieServiceImpl();
						int pk = ms.addMovie(movie);
						String message = null;
						if(pk > 0) {
							message = "添加成功";
							JOptionPane.showMessageDialog(null, message);
							closeSelf();
						}else if(pk == 0){
							message = "添加失败，请联系超级管理员";
							JOptionPane.showMessageDialog(null, message);
							closeSelf();
						}else if(pk == -1) {
							message = "添加失败，该电影名已被占用";
							JOptionPane.showMessageDialog(null, message);
						}
						
					}
				}
				
			}
		});
		button.setBounds(117, 276, 100, 30);
		getContentPane().add(button);

	}

}
