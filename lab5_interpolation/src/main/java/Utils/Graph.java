package Utils;

import DataStructure.Point;
import Methods.Calculatable;
import lombok.Getter;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Graph {
    private ArrayList<Point> data;
    @Getter
    private final XYChart chartLagrange;
    @Getter
    private final XYChart chartNewton;
    private double leftX = 0;
    private double rightX = 0;

    public Graph(ArrayList<Point> data) {
        this.chartLagrange = new XYChartBuilder().width(750).height(550).title("Интерполяционный многочлен Лангранжа")
                .xAxisTitle("X").yAxisTitle("Y").build();
        this.chartNewton = new XYChartBuilder().width(750).height(550).title("Интерполяционный многочлен Ньютона")
                .xAxisTitle("X").yAxisTitle("Y").build();
        this.data = data;
        leftX = data.get(0).getX();
        rightX = data.get(0).getX();

        for (Point point: data) {
            if (point.getX() < leftX) {
                leftX = point.getX();
            }
            if (point.getX() > rightX) {
                rightX = point.getX();
            }
        }
        double addingArea = abs(rightX - leftX) * 0.05;
        leftX -= addingArea;
        rightX += addingArea;
    }

    private void drawMainData(XYChart chart) {
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        ArrayList<Double> dataX = new ArrayList<>(), dataY = new ArrayList<>();
        data.forEach(p -> {
            dataX.add(p.getX());
            dataY.add(p.getY());
        });
        XYSeries series = chart.addSeries("Исходные данные", dataX, dataY);
        series.setMarker(SeriesMarkers.DIAMOND);
    }

    public void drawMainFrames() {
        drawMainData(chartLagrange);
        drawMainData(chartNewton);
    }

    public void drawDependencies(Calculatable methodL, Calculatable methodN) {
        drawDependency(chartLagrange, methodL, "Многочлен Лангранжа");
        drawDependency(chartNewton, methodN, "Многочлен Ньютона");
    }

    private void drawDependency(XYChart chart, Calculatable method, String name) {
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        ArrayList<Double> dataX = new ArrayList<>(), dataY = new ArrayList<>();
        for (double i = leftX; i < rightX; i += (abs(rightX - leftX))/100) {
            if (!Double.isNaN(method.calculate(i))) {
                dataX.add(i);
                dataY.add(method.calculate(i));
            }
        }
        XYSeries series = chart.addSeries(name, dataX, dataY);
        series.setMarker(SeriesMarkers.NONE);
    }

    public void showGraphs() {
        new SwingWrapper(chartLagrange).displayChart();
        new SwingWrapper(chartNewton).displayChart();
    }
}
