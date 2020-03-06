package FordhamBank.Factories;

import FordhamBank.Aggregates.User;
import FordhamBank.Main;
import FordhamBank.UI.DonutChart;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.text.DecimalFormat;

public class DonutChartFactory {
    public static void CreateAndDisplay(User user) {
        ObservableList<PieChart.Data> newPieChartData = DonutChart.createData(user);
        DonutChart newDonut = new DonutChart(newPieChartData);
        newDonut.setTitle("Total Balance: $" + new DecimalFormat("#.##").format(user.getTotalBalance()));

        Main.donutChartContainer.getChildren().clear();
        Main.donutChartContainer.getChildren().add(newDonut);
    }
}
