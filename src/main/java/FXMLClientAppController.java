import Entity.TicketsEntity;
import Entity.TrainStationsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

public class FXMLClientAppController implements Initializable{

    DataGenerator dg = DataGenerator.getInstance();

    @FXML
    private ComboBox<String> departureStation;

    @FXML
    private ComboBox<String> arriveStation;

    @FXML
    private TextField hour;

    @FXML
    private Label routeInformation;

    @FXML
    private TableView table;

    @FXML
    private TextField trainSearcher;

    @FXML
    private TableView ticketTable;

    @FXML
    private Button saveTickets;

    ObservableList<Ticket> routesData = FXCollections.observableArrayList();
    ObservableList<Ticket> ticketsData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dg = DataGenerator.getInstance();
        initStationsComboBox();
        initTrainTable();
        initTicketTable();
        saveTickets.setOnAction(e -> CSVExporter.exportTicketsToCSV());
    }

    public void initStationsComboBox(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        List stations = DBController.getStationsFromDB();
        TrainStationsEntity tse;
        for(Object o: stations){
            tse = (TrainStationsEntity) o;
            obList.add(tse.getName());
        }

        departureStation.setItems(obList);
        arriveStation.setItems(obList);
    }

    public void initTrainTable(){
        TableColumn<Ticket, String> trainColumn = new TableColumn<>("Train");
        trainColumn.setCellValueFactory(new PropertyValueFactory<>("train"));

        TableColumn<Ticket, String> depStationColumn = new TableColumn<>("Dep. station");
        depStationColumn.setCellValueFactory(new PropertyValueFactory<>("departureStation"));

        TableColumn<Ticket, LocalTime> depTimeColumn = new TableColumn<>("Dep. time");
        depTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        TableColumn<Ticket, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Ticket, Integer> overallTimeColumn = new TableColumn<>("Time");
        overallTimeColumn.setCellValueFactory(new PropertyValueFactory<>("overallTime"));

        table.getColumns().addAll(trainColumn, depStationColumn, depTimeColumn, priceColumn, overallTimeColumn);
    }

    public void initTicketTable(){
        TableColumn<Ticket, String> trainColumn = new TableColumn<>("Train");
        trainColumn.setCellValueFactory(new PropertyValueFactory<>("train"));

        TableColumn<Ticket, String> depStationColumn = new TableColumn<>("Dep. station");
        depStationColumn.setCellValueFactory(new PropertyValueFactory<>("departureStation"));

        TableColumn<Ticket, LocalTime> depTimeColumn = new TableColumn<>("Dep. time");
        depTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        TableColumn<Ticket, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Ticket, Integer> overallTimeColumn = new TableColumn<>("Time");
        overallTimeColumn.setCellValueFactory(new PropertyValueFactory<>("overallTime"));
        ticketTable.getColumns().addAll(trainColumn, depStationColumn,depTimeColumn, priceColumn, overallTimeColumn);
        for(Ticket t: dg.mt.tickets){
            ticketsData.add(t);
        }
        ticketTable.setItems(ticketsData);
        initMyTicketsDetail();
    }

    public void findTrain(){
        Ticket rifc;
        table.getItems().clear();
        if(trainSearcher.getText()!=""){
            //select route where trainname contains text
            for (Route r : dg.rc.routes) {
                if(r.train.trainName.contains(trainSearcher.getText())){
                    rifc = new Ticket(r.train, r.route.get(0), r.route.get(r.route.size()-1), r.departures.get(0), r.arrivals.get(r.arrivals.size()-1));
                    routesData.add(rifc);
                }
            }
        }
        else {
            String depStation = departureStation.getValue();
            String arrStation = arriveStation.getValue();
            LocalTime searchedHour = LocalTime.MIDNIGHT;
            if(hour.getText()!="") {
                searchedHour = LocalTime.parse(hour.getText());
            }

            routeInformation.setText("From: " + departureStation.getValue() + " To: " + arriveStation.getValue() + " Time: " + hour.getText());
            //select route by dep and arrStation
            for (Route r : dg.rc.routes) {
                if (r.doesContainDepAndArrStation(departureStation.getValue(), arriveStation.getValue()) && r.isAfter(searchedHour, depStation)) {
                    rifc = new Ticket(r.train, dg.tsc.TrainStations.get(depStation), dg.tsc.TrainStations.get(arrStation), r.getDepartureTime(depStation), r.getArrivalTime(arrStation));
                    routesData.add(rifc);
                }
            }
        }
        initDetails();
        table.setItems(routesData);
    }

    public void initDetails(){
        table.setRowFactory(tableView -> {
            final TableRow<Ticket> row = new TableRow<>();

            row.hoverProperty().addListener((observable) -> {
                final Ticket route = row.getItem();

                if (row.isHover() && route != null) {
                    //get route by train name
                    Route routeFullInfo = dg.rc.getRouteByTrainName(route.train);
                    String info = "Train: "+route.train+" time: "+route.overallTime+" price: "+route.price+"\n";
                    Iterator<TrainStation> itr = routeFullInfo.route.iterator();
                    Iterator<LocalTime> itr2 = routeFullInfo.departures.iterator();
                    Iterator<LocalTime> itr3 = routeFullInfo.arrivals.iterator();
                    while (itr.hasNext()) {
                        TrainStation station = itr.next();
                        LocalTime dep = itr2.next();
                        LocalTime arr = itr3.next();
                        info += "Station: " + station.stationName + " Arr. time: "+arr+" Dep. time: "+dep+"\n";
                    }
                    Tooltip tooltip = new Tooltip();
                    tooltip.setFont(new Font("Verdana", 15));
                    tooltip.setText(info);
                    row.setTooltip(tooltip);
                }
            });

            row.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    final Ticket route = row.getItem();

                    if (row.isSelected() && route != null) {
                        //getroute by train name
                        Route routeFullInfo = dg.rc.getRouteByTrainName(route.train);
                        String info = "Train: " + route.train + " time: " + route.overallTime + " price: " + route.price + " places: "+dg.tc.getTrainByName(route.train).numberOfSeats+"\n";
                        Iterator<TrainStation> itr = routeFullInfo.route.iterator();
                        Iterator<LocalTime> itr2 = routeFullInfo.departures.iterator();
                        Iterator<LocalTime> itr3 = routeFullInfo.arrivals.iterator();
                        while (itr.hasNext()) {
                            TrainStation station = itr.next();
                            LocalTime dep = itr2.next();
                            LocalTime arr = itr3.next();
                            info += "Station: " + station.stationName + " Arr. time: " + arr + " Dep. time: " + dep + "\n";
                        }
                        Dialog<String> dialog = new Dialog<>();
                        dialog.setContentText(info);
                        ButtonType reserve = new ButtonType("Reserve a seat", ButtonBar.ButtonData.OK_DONE);
                        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        dialog.getDialogPane().getButtonTypes().add(reserve);
                        dialog.getDialogPane().getButtonTypes().add(cancel);
                        dialog.setResultConverter((ButtonType type)-> {
                            ButtonBar.ButtonData data = type.getButtonData();
                            if(data == ButtonBar.ButtonData.OK_DONE){
                                return "OK";
                            }
                            else{
                                return null;
                            }
                        });
                        Optional<String> choice = dialog.showAndWait();
                        if(choice.isPresent()){
                            //get train by name
                            Train t = dg.tc.getTrainByName(route.train);
                            t.reserveSeat();
                            ticketsData.add(route);
                            //add ticket to db
                            dg.mt.addTicket(route);
                            ticketTable.setItems(ticketsData);
                            initMyTicketsDetail();
                        }
                    }
                }
            });

            return row;
        });
    }

    public void initMyTicketsDetail(){
        ticketTable.setRowFactory(tableView -> {
            final TableRow<Ticket> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    final Ticket route = row.getItem();

                    if (row.isSelected() && route != null) {
                        //get route by train name
                        Route routeFullInfo = dg.rc.getRouteByTrainName(route.train);
                        String info = "Train: " + route.train + " time: " + route.overallTime + " price: " + route.price + "\n";
                        Iterator<TrainStation> itr = routeFullInfo.route.iterator();
                        Iterator<LocalTime> itr2 = routeFullInfo.departures.iterator();
                        Iterator<LocalTime> itr3 = routeFullInfo.arrivals.iterator();
                        while (itr.hasNext()) {
                            TrainStation station = itr.next();
                            LocalTime dep = itr2.next();
                            LocalTime arr = itr3.next();
                            info += "Station: " + station.stationName + " Arr. time: " + arr + " Dep. time: " + dep + "\n";
                        }
                        Dialog<String> dialog = new Dialog<>();
                        dialog.setContentText(info);
                        ButtonType reserve = new ButtonType("Remove ticket", ButtonBar.ButtonData.OK_DONE);
                        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        dialog.getDialogPane().getButtonTypes().add(reserve);
                        dialog.getDialogPane().getButtonTypes().add(cancel);
                        dialog.setResultConverter((ButtonType type)-> {
                            ButtonBar.ButtonData data = type.getButtonData();
                            if(data == ButtonBar.ButtonData.OK_DONE){
                                return "OK";
                            }
                            else{
                                return null;
                            }
                        });
                        Optional<String> choice = dialog.showAndWait();
                        if(choice.isPresent()){
                            //get train by name, delete ticket
                            Train t = dg.tc.getTrainByName(route.train);
                            t.cancelTicket();
                            ticketsData.remove(route);
                            dg.mt.removeTicket(route);
                            ticketTable.setItems(ticketsData);
                        }
                    }
                }
            });
            return row;
        });
    }

    public static void exitProgram(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setContentText("Save your tickets?");
        ButtonType reserve = new ButtonType("Save tickets", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(reserve);
        dialog.getDialogPane().getButtonTypes().add(cancel);
        dialog.setResultConverter((ButtonType type)-> {
            ButtonBar.ButtonData data = type.getButtonData();
            if(data == ButtonBar.ButtonData.OK_DONE){
                return "OK";
            }
            else{
                return null;
            }
        });
        Optional<String> choice = dialog.showAndWait();
        if(choice.isPresent()){
            CSVExporter.exportTicketsToCSV();
        }
    }
}