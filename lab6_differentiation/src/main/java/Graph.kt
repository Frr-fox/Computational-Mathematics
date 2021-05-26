import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYSeries
import org.knowm.xchart.style.Styler
import org.knowm.xchart.style.markers.SeriesMarkers
import java.util.function.Consumer
import kotlin.math.abs

class Graph(var list: ArrayList<Point>) {
    private val chart: XYChart = org.knowm.xchart.XYChartBuilder().width(750).height(550)
            .title("Дифференцирование функции").xAxisTitle("X").yAxisTitle("Y").build()
    var leftX = list[0].x
    var rightX = list[list.size - 1].x
    init {
        val addingArea: Double = abs(rightX - leftX) * 0.05
        leftX -= addingArea
        rightX += addingArea
    }

    fun drawMainFrame() {
        chart.styler.legendPosition = Styler.LegendPosition.InsideSE
        chart.styler.defaultSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
        val dataX = ArrayList<Double?>()
        val dataY = ArrayList<Double>()
        list.forEach(Consumer { p: Point ->
            dataX.add(p.x)
            dataY.add(p.y)
        })
        val series = chart.addSeries("Интегральная функция", dataX, dataY)
        series.marker = SeriesMarkers.DIAMOND
        series.xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
        series.xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Line
    }

    fun drawDependency(name: String) {
        val dataX = ArrayList<Double>()
        val dataY = ArrayList<Double>()
        list.forEach(Consumer { p: Point ->
            dataX.add(p.x)
            dataY.add(p.y)
        })
        val series = chart.addSeries(name, dataX, dataY)
        series.marker = SeriesMarkers.NONE
        series.xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Line
    }

    fun showGraph() {
        SwingWrapper(chart).displayChart()
    }
}