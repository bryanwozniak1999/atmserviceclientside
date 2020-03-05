package FordhamBank.Events;

import FordhamBank.Aggregates.BankAccount;
import FordhamBank.Aggregates.User;
import FordhamBank.UI.DonutChart;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public interface IBankAccountChangeEvent {
    void fireEvent(User user, BankAccount bankAccount, Label balanceLabel, VBox donutChartContainer, String amount);

    static void updateBalanceLabelAndChart(User user, BankAccount bankAccount, Label balanceLabel, VBox donutChartContainer) {
        balanceLabel.setText("$" + bankAccount.GetBalance());

        ObservableList<PieChart.Data> newPieChartData = DonutChart.createData(user);
        DonutChart newDonut = new DonutChart(newPieChartData);
        newDonut.setTitle("Total Balance: $" + user.getTotalBalance());

        donutChartContainer.getChildren().clear();
        donutChartContainer.getChildren().add(newDonut);
    }
}
