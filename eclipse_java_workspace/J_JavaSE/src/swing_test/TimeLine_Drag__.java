package swing_test;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * 程少华的作品
 */
public class TimeLine_Drag__  extends JPanel {
	
	private static final long serialVersionUID = -8227834880356425040L;
	private int topLine = 200;
	private int bottomLine = 250;
	private int leftBar = 350;    //左移动Bar的初始位置
	private int rightBar = 400;   //右移动Bar的初始位置
	private int oldPositionX;    
	private int barLength;      //滚动条的长度， 应该是可变的
	private int barRegion = 5;     //移动Bar的敏感范围
	private int barLocationFromTop = 10;    //left and right bar 离拖动区前沿的距离
	private int scrollHeight = 18;    //滚动轴的高度
	private static int SCROLL_BAG_WIDTH = 18;     //滚动轴两端的前进、后退按钮
	private static int SCROLL_BAG_HEIGHT = 10;
	private static int TIME_LINE_WIDTH = 700;
	private static int TIME_LINE_HEIGHT = 50;
	private Color barBackColor = new Color(163,203,243);
	private static int BAR_ROUND_REGON = 7;    //滚动条的圆角度
	private List barList = new Vector();
	private int currentBar;
	private Rectangle2D toLeftIn;
	private Rectangle2D toRightIn;
	private Color backgroudColor = new Color(180,202,140);
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat monthformat = new SimpleDateFormat("yyyy-MM");
	private Date timeLineBeginDate;
	private Date timeLineEndDate;
	private String[] timeLineMonthsList;
	
	public TimeLine_Drag__() {
		JPanel scrollTimePane = new JPanel();
		JLabel label = new JLabel("Time Line");
		setBackground(Color.WHITE);
		scrollTimePane.add(label);
		add(scrollTimePane);
		
		getTimeLineDate();
		getMonthsList();
		initListener();
	}
	
	/**获取时间轴的开始、结束时间*/
	public void getTimeLineDate(){
		try{
			timeLineBeginDate = dateformat.parse("2008-05-01 12:30:00");
			timeLineEndDate = dateformat.parse("2009-05-01 12:30:00");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] agrs) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(700,300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TimeLine_Drag__ drag = new TimeLine_Drag__();
		frame.getContentPane().add(drag);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		topLine = getHeight()-68;
		bottomLine = getHeight()-scrollHeight;
		g.setColor(Color.GRAY);
		g.drawLine(0, topLine, leftBar, topLine);
		g.drawLine(rightBar, topLine, getWidth(), topLine);
		
		Graphics2D g2 = (Graphics2D) g;
		
		drawTimeLine(g2, timeLineBeginDate, timeLineEndDate, 0, topLine,TIME_LINE_WIDTH,TIME_LINE_HEIGHT);
		drawBar(g2,leftBar,rightBar,topLine);
		drawScroll(g2,leftBar,rightBar,getWidth(),getHeight());
	}
	
	/**时间轴*/
	public void drawTimeLine(Graphics2D g2,Date beginDate,Date endDate,int x,int y,int width,int height) {
		int interval = width/timeLineMonthsList.length;
		
		GradientPaint gp = new GradientPaint(x,y+1,backgroudColor,x,y+height,Color.WHITE,true);
		g2.setPaint(gp);
		g2.fillRect(x, y+1, leftBar, height+1);
		g2.fillRect(rightBar, y+1, width-rightBar, height+1);
		for(int i=0;i<timeLineMonthsList.length;i++){
			g2.setColor(Color.GRAY);
			g2.drawLine(x+i*interval, y+2*height/3, x+i*interval, y+height);
			g2.setColor(Color.BLACK);
//			if(Integer.parseInt(timeLineMonthsList[i])==1){
//				g2.drawString(str, x, y)
//			}
			g2.drawString(timeLineMonthsList[i], x+i*interval+2, y+height-2);
		}
	}
	
	public String[] getMonthsList(){
		GregorianCalendar beginCalendar = new GregorianCalendar(), endCalendar = new GregorianCalendar();
		beginCalendar.setTime(timeLineBeginDate);
		endCalendar.setTime(timeLineEndDate);
		int months = getMonths(beginCalendar,endCalendar);
		int indexMonth = beginCalendar.get(Calendar.MONTH)+1;
		
		timeLineMonthsList = new String[months];
		for(int i=0;i<months;i++){
			indexMonth=indexMonth==13?1:indexMonth;
			timeLineMonthsList[i] = indexMonth+++"月";
		}
		
		return timeLineMonthsList;
	}
	
	public int getMonths(GregorianCalendar g1, GregorianCalendar g2) {
		int elapsed = 0;
		GregorianCalendar gc1, gc2;
		if (g2.after(g1)) {
			gc2 = (GregorianCalendar) g2.clone();
			gc1 = (GregorianCalendar) g1.clone();
		} else {
			gc2 = (GregorianCalendar) g1.clone();
			gc1 = (GregorianCalendar) g2.clone();
		}
		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc1.clear(Calendar.DATE);
		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.DATE);
		while (gc1.before(gc2)) {
			gc1.add(Calendar.MONTH, 1);
			elapsed++;
		}
		return elapsed;
	}
	
	/** leftBarX是第一个拖动Bar的X位置,rightBarX是第二个拖动Bar的X位置,barY是拖动区域的上前沿*/
	public void drawBar(Graphics2D g2,int leftBarX,int rightBarX, int barY){
		
		g2.setColor(Color.GRAY);
		g2.drawLine(leftBar, topLine, leftBar, bottomLine);
		g2.drawLine(rightBar, topLine, rightBar, bottomLine);
		
//		g2.setColor(Color.WHITE);
//		g2.fillRect(leftBar+1,topLine,rightBar-leftBar-1,TIME_LINE_HEIGHT);
		g2.setColor(Color.GRAY);
		g2.drawRoundRect(leftBarX-barRegion, barY+barLocationFromTop, 2*barRegion, 28, BAR_ROUND_REGON, BAR_ROUND_REGON);
		g2.drawRoundRect(rightBarX-barRegion, barY+barLocationFromTop, 2*barRegion, 28, BAR_ROUND_REGON, BAR_ROUND_REGON);
		g2.setColor(Color.WHITE);
		g2.fillRoundRect(leftBarX-barRegion+1, barY+barLocationFromTop+1, 2*barRegion-1, 27, BAR_ROUND_REGON, BAR_ROUND_REGON);
		g2.fillRoundRect(rightBarX-barRegion+1, barY+barLocationFromTop+1, 2*barRegion-1, 27, BAR_ROUND_REGON, BAR_ROUND_REGON);
		
		g2.setColor(Color.GRAY);
		g2.drawLine(leftBarX-2, barY+barLocationFromTop+barRegion, leftBarX-2, barY+32);
		g2.drawLine(leftBarX+1, barY+barLocationFromTop+barRegion, leftBarX+1, barY+32);
		g2.drawLine(rightBarX-2, barY+barLocationFromTop+barRegion, rightBarX-2, barY+32);
		g2.drawLine(rightBarX+1, barY+barLocationFromTop+barRegion, rightBarX+1, barY+32);
		
		barList.add(leftBar);
		barList.add(rightBar);
	}
	
	/** scrollWidth是scrollBar的宽度,leftScrollX是滚动条所在的X位置,bottomY是滚动条的下后沿*/
	private void drawScroll(Graphics2D g2,int leftScrollX,int rightScrollX,int scrollWidth,int bottomY) {
		
		Rectangle2D rectBar=new Rectangle2D.Double(0,bottomLine,scrollWidth,bottomY-bottomLine-1);
		GradientPaint gp = new GradientPaint(0,bottomLine,barBackColor,0,bottomY,Color.WHITE,true);
		g2.setPaint(gp);
		g2.fill(rectBar);
		
		RoundRectangle2D barOut = new RoundRectangle2D.Double(leftBar,bottomLine,rightBar-leftBar,bottomY-bottomLine,BAR_ROUND_REGON,BAR_ROUND_REGON);
		RoundRectangle2D barIn = new RoundRectangle2D.Double(leftBar+1,bottomLine+1,rightBar-leftBar-1,bottomY-bottomLine-1,BAR_ROUND_REGON,BAR_ROUND_REGON);
		Rectangle2D toLeftOut = new Rectangle2D.Double(0,bottomLine,SCROLL_BAG_WIDTH,bottomY-bottomLine+1);
		toLeftIn = new Rectangle2D.Double(1,bottomLine+1,SCROLL_BAG_WIDTH-1,bottomY-bottomLine);
		Rectangle2D toRightOut = new Rectangle2D.Double(getWidth()-SCROLL_BAG_WIDTH,bottomLine,SCROLL_BAG_WIDTH,bottomY-bottomLine+1);
		toRightIn = new Rectangle2D.Double(getWidth()-SCROLL_BAG_WIDTH+1,bottomLine+1,SCROLL_BAG_WIDTH-1,bottomY-bottomLine);
		
		g2.setPaint(Color.WHITE);
		g2.draw(barOut);
		g2.draw(toLeftOut);
		g2.draw(toRightOut);
		g2.setPaint(barBackColor);
		g2.fill(barIn);
		g2.fill(toLeftIn);
		g2.fill(toRightIn);
		
		g2.setPaint(Color.WHITE);
		g2.drawLine((rightBar+leftBar)/2-3, bottomLine+5, (rightBar+leftBar)/2-3, bottomY-5);
		g2.drawLine((rightBar+leftBar)/2, bottomLine+5, (rightBar+leftBar)/2, bottomY-5);
		g2.drawLine((rightBar+leftBar)/2+3, bottomLine+5, (rightBar+leftBar)/2+3, bottomY-5);
		g2.setFont(new Font("Dialog", Font.BOLD, 15));
		g2.drawString("<", 5, bottomY-3);
		g2.drawString(">", getWidth()-SCROLL_BAG_WIDTH+5, bottomY-3);
	}
	
	public void initListener(){
		
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				Point pt = e.getPoint();
				
				if(((pt.x>rightBar-barRegion) && (pt.x<rightBar+barRegion))){
					currentBar = 1;
				}else if((pt.x>leftBar-barRegion) && (pt.x<leftBar+barRegion)){
					currentBar = 0;
				}else if(pt.x>leftBar&&pt.x<rightBar){
					oldPositionX = pt.x;
					barLength = rightBar-leftBar;
					currentBar = -1;
				}
				
				/**滚动按钮的单击触发事件*/
				if((toLeftIn.contains(pt))&&(leftBar>SCROLL_BAG_WIDTH)){
					leftBar=leftBar-10;
					rightBar=rightBar-10;
					repaint();
				}
				if((toRightIn.contains(pt))&&(rightBar<getWidth()-SCROLL_BAG_WIDTH)){
					leftBar=leftBar+10;
					rightBar=rightBar+10;
					repaint();
				}
			}
			
			public void mouseReleased(MouseEvent e){
				if(currentBar!=-1){
					barList.set(currentBar, e.getX());
				}
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e){
				Point pt = e.getPoint();
				drawBarCursor(pt.x,pt.y);
			}
			
			public void mouseDragged(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
				if(!e.isMetaDown()){
					if(((y>topLine+barLocationFromTop) && (y<bottomLine-barLocationFromTop))){
						reDrawBar(x);
					}
					
					if((y>bottomLine&&y<getHeight()-1)&&(x>leftBar&&x<rightBar)){
						int mobilLength = x-oldPositionX;
						int nowPosition = leftBar + mobilLength;
						if(nowPosition<SCROLL_BAG_WIDTH){
							nowPosition=SCROLL_BAG_WIDTH+1;
							rightBar=nowPosition+barLength;
						}
						if(rightBar>getWidth()-SCROLL_BAG_WIDTH){
							rightBar=getWidth()-SCROLL_BAG_WIDTH-1;
							nowPosition=rightBar-barLength;
						}
						leftBar = nowPosition;
						rightBar = rightBar+mobilLength;
						repaint();
						
						oldPositionX=x;
					}
				}
			}
		});
	}
	
	/**控制鼠标所在位置的不同形状*/
	public void drawBarCursor(int x,int y){
		
		if(((y>topLine+barLocationFromTop) && (y<bottomLine-barLocationFromTop))
				&&(((x>rightBar-barRegion) && (x<rightBar+barRegion)) 
					|| ((x>leftBar-barRegion) && (x<leftBar+barRegion)))){
			setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		}else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	/**鼠标拖动左、右Bar的改变位置*/
	public void reDrawBar(int x){
		if((currentBar==0)&&(x>SCROLL_BAG_WIDTH)&&(x<rightBar)){
			leftBar=x;
			repaint();
		}else if((currentBar==1)&&(x>leftBar)&&(x<getWidth()-SCROLL_BAG_WIDTH)){
			rightBar=x;
			repaint();
		}
	}
}