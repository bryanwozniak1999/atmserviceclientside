package FordhamBank.Factories;

import FordhamBank.Aggregates.User;
import FordhamBank.UI.DonutChart;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class DonutChartFactory {
    public static void CreateAndDisplay(User user, VBox donutChartContainer) {
        ObservableList<PieChart.Data> newPieChartData = DonutChart.createData(user);
        DonutChart newDonut = new DonutChart(newPieChartData);
        newDonut.setTitle("Total Balance: $" + new DecimalFormat("#.##").format(user.getTotalBalance()));

        donutChartContainer.getChildren().clear();
        donutChartContainer.getChildren().add(newDonut);
    }
}
