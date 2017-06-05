package jfreechart;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ChartDemo
{

	public static void main(String[] args)
	{
		ChartDemo chartD = new ChartDemo();
		chartD.createBarDemo("d:/temp/bar1.jpg");
		chartD.createPieDemo("d:/temp/pie.jpg");
	}
	// ������״ͼ
	public void createBarDemo(String jpgname)
	{
		CategoryDataset dataset = getBarDataset();
		JFreeChart chart = ChartFactory.createBarChart3D("ˮ������ͼ", "ˮ��", "����", dataset, PlotOrientation.VERTICAL, true,
				false, false);
		
		//------��״ͼ ����֧��
		Font font = new Font("SimSun",Font.BOLD|Font.ITALIC,20); 
		TextTitle tt = chart.getTitle(); 
		tt.setFont(font); 
		chart.getLegend().setItemFont(font); 
		
		CategoryPlot plot = (CategoryPlot)chart.getPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickLabelFont(font);
		domainAxis.setLabelFont(font);
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setTickLabelFont(font);
		numberaxis.setLabelFont(font);
		//------
		
		FileOutputStream jpg = null;
		try
		{
			jpg = new FileOutputStream(jpgname);
			ChartUtilities.writeChartAsJPEG(jpg, 0.5f, chart, 400, 300, null);

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				jpg.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// ��ȡ��״ͼ����
	private CategoryDataset getBarDataset()
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, "����", "ƻ��");
		dataset.addValue(200, "�Ϻ�", "����");
		dataset.addValue(300, "�ϲ�", "����");
		dataset.addValue(400, "����", "�㽶");
		dataset.addValue(500, "����", "��֦");
		dataset.addValue(-250, "�Ϻ�", "��֦");
		return dataset;
	}

	// ������ͼ
	public void createPieDemo(String jpgname)
	{
		DefaultPieDataset dataset = getPieDataset();
		JFreeChart chart = ChartFactory.createPieChart3D("ˮ������", dataset, true, true, true);

		//------��ͼ����֧��
		Font font = new Font("SimSun",Font.BOLD|Font.ITALIC,20); 
		TextTitle tt = chart.getTitle(); 
		tt.setFont(font); 
		chart.getLegend().setItemFont(font); 
		PiePlot pieplot = (PiePlot)chart.getPlot();
	    pieplot.setLabelFont(font);  
		//------
		FileOutputStream jpg = null;
		try
		{
			jpg = new FileOutputStream(jpgname);
			ChartUtilities.writeChartAsJPEG(jpg, 0.5f, chart, 400, 300, null);

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				jpg.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// ��ȡ��ͼ����
	private DefaultPieDataset getPieDataset()
	{
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("ƻ��", 100);
		dataset.setValue("����", 200);
		dataset.setValue("����", 300);
		dataset.setValue("��֦", 400);
		dataset.setValue("�㽶", 500);
		dataset.setValue("����", 600);
		return dataset;
	}

}
