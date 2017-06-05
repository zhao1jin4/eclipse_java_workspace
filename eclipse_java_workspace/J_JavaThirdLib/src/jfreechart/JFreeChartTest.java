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

		dpd.setValue("������Ա", 25);
		dpd.setValue("�г���Ա", 25);
		dpd.setValue("������Ա", 45);
		dpd.setValue("������Ա", 10);

		JFreeChart chart = ChartFactory.createPieChart3D("ĳ��˾��Ա��֯�ṹͼ", dpd, true,
				true, false);
		//------��ͼ����֧��
				Font font = new Font("SimSun",Font.BOLD|Font.ITALIC,20); 
				TextTitle tt = chart.getTitle(); 
				tt.setFont(font); 
				chart.getLegend().setItemFont(font); 
				PiePlot pieplot = (PiePlot)chart.getPlot();
			    pieplot.setLabelFont(font);  
				//------
		ChartFrame chartFrame = new ChartFrame("ĳ��˾��Ա��֯�ṹͼ", chart);//ͼƬ��ʾ��Swing������

		chartFrame.pack();

		chartFrame.setVisible(true);

	}
}
