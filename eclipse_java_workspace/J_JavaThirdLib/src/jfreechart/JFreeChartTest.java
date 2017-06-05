package jfreechart;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeChartTest
{
	public static void main(String[] args)
	{
		DefaultPieDataset dpd = new DefaultPieDataset();

		dpd.setValue("管理人员", 25);
		dpd.setValue("市场人员", 25);
		dpd.setValue("开发人员", 45);
		dpd.setValue("其他人员", 10);

		JFreeChart chart = ChartFactory.createPieChart3D("某公司人员组织结构图", dpd, true,
				true, false);
		//------饼图中文支持
				Font font = new Font("SimSun",Font.BOLD|Font.ITALIC,20); 
				TextTitle tt = chart.getTitle(); 
				tt.setFont(font); 
				chart.getLegend().setItemFont(font); 
				PiePlot pieplot = (PiePlot)chart.getPlot();
			    pieplot.setLabelFont(font);  
				//------
		ChartFrame chartFrame = new ChartFrame("某公司人员组织结构图", chart);//图片显示在Swing窗口中

		chartFrame.pack();

		chartFrame.setVisible(true);

	}
}
