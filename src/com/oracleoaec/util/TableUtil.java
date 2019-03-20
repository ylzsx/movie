package com.oracleoaec.util;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableUtil {

	/**
	 * 隐藏表格某列，要隐藏多列时，需先隐藏大的列号，列号从0开始
	 * @param table 	要隐藏列的table
	 * @param column	要隐藏的列的列号
	 */
	public static void hideTableColumn(JTable table, int column){  
	    /*TableColumnModel tcm = table.getColumnModel();  
	    //其实没有移除，仅仅隐藏而已  
	    TableColumn tc = tcm.getColumn(column);  
	    tcm.removeColumn(tc);     */
		TableColumn tc = table.getTableHeader().getColumnModel().getColumn(column);
		tc.setMaxWidth(0);
		tc.setPreferredWidth(0);
		tc.setWidth(0);
		tc.setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(column)
		.setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(column)
		.setMinWidth(0);
	}
}
