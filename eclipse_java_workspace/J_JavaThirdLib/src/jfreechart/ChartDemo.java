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
	// 创建柱状图
	public void createBarDemo(String jpgname)
	{
		CategoryDataset dataset = getBarDataset();
		JFreeChart chart = ChartFactory.createBarChart3D("水果产量图", "水果", "产量", dataset, PlotOrientation.VERTICAL, true,
				false, false);
		
		//------柱状图 中文支持
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

	// 获取柱状图数据
	private CategoryDataset getBarDataset()
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, "北京", "苹果");
		dataset.addValue(200, "上海", "梨子");
		dataset.addValue(300, "南昌", "葡萄");
		dataset.addValue(400, "海南", "香蕉");
		dataset.addValue(500, "北京", "荔枝");
		dataset.addValue(-250, "上海", "荔枝");
		return dataset;
	}

	// 创建饼图
	public void createPieDemo(String jpgname)
	{
		DefaultPieDataset dataset = getPieDataset();
		JFreeChart chart = ChartFactory.createPieChart3D("水果产量", dataset, true, true, true);

		//------饼图中文支持
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

	// 获取饼图数据
	private DefaultPieDataset getPieDataset()
	{
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("苹果", 100);
		dataset.setValue("梨子", 200);
		dataset.setValue("葡萄", 300);
		dataset.setValue("荔枝", 400);
		dataset.setValue("香蕉", 500);
		dataset.setValue("枣子", 600);
		return dataset;
	}

}
