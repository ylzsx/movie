package com.oracleoaec.view.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AdminManageView {

	public JFrame frame;
	JDesktopPane desktopPane;
	
	public void showAnyWindow(JInternalFrame jf) {
		//将窗口类添加到deskTopPane中
		desktopPane.add(jf);
		//设置最小化
		jf.setIconifiable(true);
		//关闭窗口
		jf.setClosable(true);
		//让窗口类显示
		jf.setVisible(true);
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminManageView window = new AdminManageView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminManageView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(450, 120, 926, 652);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu menu = new JMenu("用户管理");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("添加用户");
		menuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AddUserView av = new AddUserView();
				showAnyWindow(av);
			}
		});    
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("查询用户");
		menuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ShowUserInfoView sv = new ShowUserInfoView();
				showAnyWindow(sv);
			}
		});
		menu.add(menuItem_1);
		
		JMenu menu_1 = new JMenu("影院管理");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_2 = new JMenuItem("添加影院");
		menuItem_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AddCinemaView adv = new AddCinemaView();
				showAnyWindow(adv);
			}
		});
		menu_1.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("查询影院");
		menuItem_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ShowCinemaView scv = new ShowCinemaView();
				showAnyWindow(scv);
			}
		});
		menu_1.add(menuItem_3);
		
		JMenu menu_2 = new JMenu("影片管理");
		menuBar.add(menu_2);
		
		JMenuItem menuItem_4 = new JMenuItem("添加影片");
		menuItem_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AddMovieView amv = new AddMovieView();
				showAnyWindow(amv);
			}
		});
		menu_2.add(menuItem_4);
		
		JMenuItem menuItem_5 = new JMenuItem("排片");
		menuItem_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PlatoonView pv = new PlatoonView();
				showAnyWindow(pv);
			}
		});
		
		JMenuItem menuItem_6 = new JMenuItem("查询影片");
		menuItem_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ShowMovieView smv = new ShowMovieView();
				showAnyWindow(smv);
			}
		});
		menu_2.add(menuItem_6);
		menu_2.add(menuItem_5);
		
		desktopPane = new JDesktopPane();
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		//添加窗口名称
		frame.setTitle("影院管理后台/管理员");
	}
}
