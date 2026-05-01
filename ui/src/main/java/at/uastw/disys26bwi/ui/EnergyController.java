package at.uastw.disys26bwi.ui;

import at.uastw.disys26bwi.common.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.common.dto.HistoricEnergyDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class EnergyController {
    @FXML
    private Label communityPoolUsed;
    @FXML
    private Label gridPortion;
    public Button buttonRefresh;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    @FXML
    private Label communityProduced;
    @FXML
    private Label communityUsed;
    @FXML
    private Label gridUsed;
    public Button buttonShowData;

    private final EnergyService energyService = new EnergyService();

    @FXML
    protected void onRefreshButtonClick() {
        try {
            CurrentEnergyDto data = energyService.getCurrentEnergy();
            communityPoolUsed.setText(String.format("%.2f %%", data.communityDepleted()));
            gridPortion.setText(String.format("%.2f %%", data.gridPortion()));
        } catch (Exception e) {
            communityPoolUsed.setText("Error");
        }
    }

    @FXML
    protected void onShowButtonClick() {
        if (startDate.getValue() == null || endDate.getValue() == null) return;

        try {
            // Konvertierung von LocalDate zu LocalDateTime für die API
            LocalDateTime start = startDate.getValue().atStartOfDay();
            LocalDateTime end = endDate.getValue().atTime(23, 59);

            HistoricEnergyDto data = energyService.getHistoricEnergy(start, end);
            communityProduced.setText(data.communityProduced() + " kWh");
            communityUsed.setText(data.communityUsed() + " kWh");
            gridUsed.setText(data.gridUsed() + " kWh");
        } catch (Exception e) {
            System.err.println("Fehler beim Laden der historischen Daten: " + e.getMessage());
        }
    }
}
