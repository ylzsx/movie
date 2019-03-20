package com.oracleoaec.view.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.oracleoaec.pojo.Cinema;
import com.oracleoaec.service.CinemaService;
import com.oracleoaec.service.UsersService;
import com.oracleoaec.service.imp.CinemaServiceImpl;
import com.oracleoaec.service.imp.UsersServiceImp;

public class AddCinemaView extends JInternalFrame {
	private JTextField edtCinemaName;
	private JTextField edtCinemaAddress;
	
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
					AddCinemaView frame = new AddCinemaView();
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
	public AddCinemaView() {
		setTitle("影院添加/管理员");
		setBounds(100, 100, 397, 416);
		getContentPane().setLayout(null);
		
		JLabel txtUserName = new JLabel("影院名称：");
		txtUserName.setBounds(64, 92, 86, 15);
		getContentPane().add(txtUserName);
		
		edtCinemaName = new JTextField();
		edtCinemaName.setBounds(128, 89, 150, 21);
		getContentPane().add(edtCinemaName);
		edtCinemaName.setColumns(10);
		
		JLabel label_2 = new JLabel("影院地址：");
		label_2.setBounds(64, 169, 86, 18);
		getContentPane().add(label_2);
		
		edtCinemaAddress = new JTextField();
		edtCinemaAddress.setColumns(10);
		edtCinemaAddress.setBounds(128, 166, 150, 21);
		getContentPane().add(edtCinemaAddress);
		
		JButton btnRegister = new JButton("添加影院");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cinemaName = edtCinemaName.getText();
				String cinemaAddress = edtCinemaAddress.getText();
				
				if("".equals(cinemaName)) {
					JOptionPane.showMessageDialog(null, "影院名称不能为空");
				}else if("".equals(cinemaAddress)) {
					JOptionPane.showMessageDialog(null, "影院地址不能为空");
				}else {
					Cinema cinema = new Cinema();
					cinema.setCinemaName(cinemaName);
					cinema.setCinemaAddress(cinemaAddress);
					cinema.setCinemaStatus("1");
					
					CinemaService cs = new CinemaServiceImpl();
					int i = cs.addCinemaReturnPrimaryKey(cinema);
					if(i > 0) {
						JOptionPane.showMessageDialog(null, "影院添加成功");
						closeSelf();
					}else if(i == 0){
						JOptionPane.showMessageDialog(null, "添加失败，请联系超级管理员");
					}else if(i == -1) {
						JOptionPane.showMessageDialog(null, "添加失败，该影院名称已被占用");
					}
				}
			}
		});
		btnRegister.setBounds(139, 268, 93, 23);
		getContentPane().add(btnRegister);

	}

}
