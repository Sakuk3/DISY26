package at.uastw.disys26bwi.ui;

import at.uastw.disys26bwi.apiSpec.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.apiSpec.dto.HistoricEnergyDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EnergyController {
  private final EnergyService energyService = new EnergyService();
  public Button buttonRefresh;
  public Button buttonShowData;
  @FXML
  private Label communityPoolUsed;
  @FXML
  private Label gridPortion;
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

        LocalDateTime now = LocalDateTime.now();
        LocalDate selectedEndDate = endDate.getValue();

        LocalDateTime end = selectedEndDate.plusDays(1).atStartOfDay().minusSeconds(1);

        // If date is selected for today convert the to UTC
        if (end.isAfter(now)) {
            end = now.minusHours(2).minusSeconds(1);
        }

        //If selected date is in the future
        if (!start.isBefore(end)) {
            communityProduced.setText("Invalid date range");
            communityUsed.setText("Start date must be before end date");
            gridUsed.setText("");
            return;
        }

      HistoricEnergyDto data = energyService.getHistoricEnergy(start, end);
      communityProduced.setText(data.communityProduced() + " kWh");
      communityUsed.setText(data.communityUsed() + " kWh");
      gridUsed.setText(data.gridUsed() + " kWh");
    } catch (Exception e) {
      System.err.println("Fehler beim Laden der historischen Daten: " + e.getMessage());
    }
  }
}
