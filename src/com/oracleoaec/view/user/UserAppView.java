package com.oracleoaec.view.user;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JDesktopPane;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserAppView {

	public JFrame frame;
	private JDesktopPane desktopPane;
	
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
					UserAppView window = new UserAppView();
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
	public UserAppView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(450, 120, 980, 652);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("购票管理");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("影讯");
		menuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				VideoNewsView vv = new VideoNewsView();
				showAnyWindow(vv);
			}
		});
		menu.add(menuItem);
		
		JMenu menu_1 = new JMenu("个人管理");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_1 = new JMenuItem("个人订单");
		menuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PersonalOrderView pov = new PersonalOrderView();
				showAnyWindow(pov);
			}
		});
		menu_1.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("个人中心");
		menuItem_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PersonalCenterView pcv = new PersonalCenterView();
				showAnyWindow(pcv);
			}
		});
		menu_1.add(menuItem_2);
		
		desktopPane = new JDesktopPane();
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		desktopPane.setLayout(null);
		
		frame.setTitle("用户个人信息/用户");
	}
}
