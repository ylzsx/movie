package com.oracleoaec.view.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.oracleoaec.service.SessionService;
import com.oracleoaec.service.TicketService;
import com.oracleoaec.service.imp.SessionServiceImpl;
import com.oracleoaec.service.imp.TicketServiceImpl;
import com.oracleoaec.util.UserInfo;
import javax.swing.JScrollPane;

public class ShoppingTicketView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textCinemaName;
	private JTextField textHallName;
	private JTextField textMovieName;
	private JTextField textSessionTime;
	private JTextField textSessionPrice;
	
	/*客户将要购买的票*/
	private Set ticketSet = new HashSet();
	private Double sessionPrice;
	private Integer sessionid;
	private Integer hallCapacity;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JCheckBox chckbxNewCheckBox;
	
	
	
	//初始化化文本框
	private void initTextInfo() {
		SessionService ss = new SessionServiceImpl();
		//System.out.println(this.sessionid);
		List<Map<String, Object>> list = ss.findAnyMovieInfo(this.sessionid);
		//System.out.println(list.toString());
		Map<String,Object> map = list.get(0);
		
		textCinemaName.setText(map.get("CINEMA_NAME")+"");
		textHallName.setText(map.get("HALL_NAME")+"");
		textMovieName.setText(map.get("MOVIE_NAME")+"");
		textSessionTime.setText(map.get("SESSION_TIME")+"");
		textSessionPrice.setText(map.get("SESSION_PRICE")+"");
		textCinemaName.setEditable(false);
		textHallName.setEditable(false);
		textMovieName.setEditable(false);
		textSessionTime.setEditable(false);
		textSessionPrice.setEditable(false);
		this.hallCapacity = new Integer(map.get("HALL_CAPACITY").toString());
		this.sessionPrice = new Double(map.get("SESSION_PRICE").toString());
	}
	public void initSeat() {
		//以sessionid查询已卖出的票
		TicketService ts = new TicketServiceImpl();
		Set<String> seatSet = ts.findSetBySessionId(this.sessionid);
		//已经卖出的票数
		int count = seatSet.size();
		
		int s = 1;
		for(int y = 0;true;y++) {
			for(int x = 0;x <5;x++) {
				JCheckBox checkBox = new JCheckBox(s+"");
				int ty = 18+y*46;
				checkBox.setBounds(23+x*100,ty,50,23);
				panel.add(checkBox);
				
				if(ty > panel.getPreferredSize().getHeight()) {
					panel.setPreferredSize(new Dimension(500,(int) (panel.getPreferredSize().getHeight()+46)));
				}
				//判断该复选框对应的票是否已卖出,并设置是否可买票
				seatSet.add(s+"");
				if(seatSet.size() == count) {
					checkBox.setSelected(true);
					checkBox.setEnabled(false);
				}else {
					seatSet.remove(s+"");
				}
				//设置点击事件
				checkBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean flag = checkBox.isSelected();
						if(flag) {
							ticketSet.add(checkBox.getText());
						}else {
							ticketSet.remove(checkBox.getText());
						}
					}
				});
				s++;
				if(s > this.hallCapacity) {
					break;
				}
			}
			if(s > this.hallCapacity) {
				break;
			}
		}
	}
	
	public void closeSelf() {
		this.setVisible(false);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ShoppingTicketView dialog = new ShoppingTicketView(13);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ShoppingTicketView(Integer sessionid) {
		this.sessionid = sessionid;
		setTitle("选座/用户");
		setBounds(100, 100, 575, 527);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textCinemaName = new JTextField();
		textCinemaName.setBounds(34, 31, 121, 21);
		contentPanel.add(textCinemaName);
		textCinemaName.setColumns(10);
		
		textHallName = new JTextField();
		textHallName.setColumns(10);
		textHallName.setBounds(199, 31, 121, 21);
		contentPanel.add(textHallName);
		
		textMovieName = new JTextField();
		textMovieName.setColumns(10);
		textMovieName.setBounds(34, 62, 121, 21);
		contentPanel.add(textMovieName);
		
		textSessionTime = new JTextField();
		textSessionTime.setColumns(10);
		textSessionTime.setBounds(199, 62, 121, 21);
		contentPanel.add(textSessionTime);
		
		JButton button = new JButton("购票");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ticketSet.size() == 0) {
					JOptionPane.showMessageDialog(null,"请选中要购买的票");
				}else {
					TicketService ts = new TicketServiceImpl();
					int i = ts.shoppingTicket(UserInfo.userid, sessionid, sessionPrice, ticketSet);
					String message = null;
					if(i == -1) message = "余额不足，请联系管理员充值";
					else if(i == 0) message = "购买失败，请联系管理员";
					else if(i > 0) message = "购买成功";
					JOptionPane.showMessageDialog(null, message);
					closeSelf();
				}
			}
		});
		button.setBounds(365, 62, 121, 21);
		contentPanel.add(button);
		
		textSessionPrice = new JTextField();
		textSessionPrice.setColumns(10);
		textSessionPrice.setBounds(365, 31, 121, 21);
		contentPanel.add(textSessionPrice);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 101, 540, 378);
		contentPanel.add(scrollPane);
		
		panel = new JPanel();
		panel.setLayout(null);
		scrollPane.setViewportView(panel);
		panel.setPreferredSize(new Dimension(500,370));
		
		
		initTextInfo();
		initSeat();
		
		
	}
}
