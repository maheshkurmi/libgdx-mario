package com.shikhar.mario.editor;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 *  Status Bar
 *
 *  @author 	Mahesh kurmi
 */
public class StatusBarPanel extends JPanel
{
	/**
	 *	Standard Status Bar
	 */
	public StatusBarPanel()
	{
		this(false);
	}	//	StatusBar

	/**
	 *	Status Bar with additional info
	 *  @param withInfo with info
	 */
	public StatusBarPanel (boolean withInfo)
	{
		super();
		try
		{
			jbInit();
		}
		catch (Exception e)
		{}
		this.setName("statusBar");
		if (!withInfo)
			infoLine.setVisible(false);
	}	//	StatusBar

	private BorderLayout mainLayout = new BorderLayout();
	private JLabel statusLine = new JLabel();
	private JLabel statusDB = new JLabel();
	private JLabel infoLine = new JLabel();
	//
	private boolean		mt_error;
	private String		mt_text;
	//
	private String      m_text;
	
	/**
	 *	Static Init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		statusLine.setBorder(BorderFactory.createLineBorder(Color.gray));
		statusLine.setText("For any query or suggestion please mail me at: maheshkurmi2010@gmail.com");
		statusLine.setOpaque(false);
		statusDB.setForeground(Color.blue);
		statusDB.setBorder(BorderFactory.createEtchedBorder());
		statusDB.setText("#");
		statusDB.setOpaque(false);
		this.setLayout(mainLayout);
		infoLine.setBorder(BorderFactory.createRaisedBevelBorder());
		infoLine.setHorizontalAlignment(SwingConstants.CENTER);
		infoLine.setHorizontalTextPosition(SwingConstants.CENTER);
		infoLine.setText("info");
		mainLayout.setHgap(2);
		mainLayout.setVgap(2);
		this.add(statusLine, BorderLayout.CENTER);
		this.add(statusDB, BorderLayout.EAST);
		this.add(infoLine, BorderLayout.NORTH);
	}	//	jbInit

	
	/**************************************************************************
	 *	Set Standard Status Line (non error)
	 *  @param text text
	 */
	public void setStatusLine (String text)
	{
		if (text == null)
			setStatusLine("", false);
		else
			setStatusLine(text, false);
	}	//	setStatusLine

	/**
	 *	Set Status Line
	 *  @param text text
	 *  @param error error
	 */
	public void setStatusLine (String text, boolean error)
	{
		mt_error = error;
		mt_text = text;
		if (mt_error)
			statusLine.setForeground(Color.red);
		else
			statusLine.setForeground(Color.black);
		statusLine.setText(" " + mt_text);
		//
		Thread.yield();
	}	//	setStatusLine

	/**
	 *	Get Status Line text
	 *  @return StatusLine text
	 */
	public String getStatusLine ()
	{
		return statusLine.getText().trim();
	}	//	setStatusLine

	/**
	 *  Set ToolTip of StatusLine
	 *  @param tip tip
	 */
	public void setStatusToolTip (String tip)
	{
		statusLine.setToolTipText(tip);
	}   //  setStatusToolTip

	/**
	 *	Set Status DB Info
	 *  @param text text
	 *  @param dse data status event
	 */
	public void setStatusDB (String text)
	{
	//	log.config( "StatusBar.setStatusDB - " + text + " - " + created + "/" + createdBy);
		if (text == null || text.length() == 0)
		{
			statusDB.setText("");
			statusDB.setVisible(false);
		}
		else
		{
			StringBuffer sb = new StringBuffer (" ");
			sb.append(text).append(" ");
			statusDB.setText(sb.toString());
			if (!statusDB.isVisible())
				statusDB.setVisible(true);
		}

		//  Save
		m_text = text;
	}	//	setStatusDB

	
	/**
	 *	Set Info Line
	 *  @param text text
	 */
	public void setInfo (String text)
	{
		if (!infoLine.isVisible())
			infoLine.setVisible(true);
		infoLine.setText(text);
	}	//	setInfo

	/**
	 *	Add Component to East of StatusBar
	 *  @param component component
	 */
	public void addStatusComponent (JComponent component)
	{
		this.add(component, BorderLayout.EAST);
	}   //  addStatusComponent

	

}	//	StatusBar

 