import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainWindow extends JFrame {
    //DataGenerator data = DataGenerator.getInstance();
    TrainTableAbstractModel trainTableAbstractModel;
    TrainStationTableAbstractModel trainStationTableAbstractModel;

    RouteTableAbstractModel routesTableAbstractModel;

    MainWindow(){
        try {
            trainStationTableAbstractModel = new TrainStationTableAbstractModel();
            trainTableAbstractModel = new TrainTableAbstractModel();
        }
        finally {
            routesTableAbstractModel = new RouteTableAbstractModel();
        }

        JTable trainTable = new JTable(trainTableAbstractModel);
        JTable trainStationTable = new JTable(trainStationTableAbstractModel);
        JTable routeTable = new JTable(routesTableAbstractModel);
        setLayout(new GridLayout(4,3));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeTheProgram();
                System.exit(0);
            }
        });
        //data
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String nowStr = String.valueOf(dtf.format(now));
        JLabel date = new JLabel(String.valueOf(nowStr));
        date.setVerticalAlignment(JLabel.BOTTOM);

        JScrollPane scrollPaneTrain = new JScrollPane(trainTable);
        JScrollPane scrollPaneTrainStation = new JScrollPane(trainStationTable);
        JScrollPane scrollPaneRoutes = new JScrollPane(routeTable);

        JButton addTrain = new JButton("Add train");
        JButton addTrainStation = new JButton("Add train station");
        JButton addRoute = new JButton("Add route");
        JButton deleteTrain = new JButton("Delete train");
        JButton deleteTrainStation = new JButton("Delete train station");
        JButton deleteRoute = new JButton("Delete route");
        //JButton saveTrains = new JButton("Save trains");
        //JButton saveTrainStations = new JButton("Save train stations");
        //JButton saveRoutes = new JButton("Save routes");
        JButton infoRoute = new JButton("Show route info");
        JButton sortByCapacity = new JButton("Sort by capacity");

        addTrainStation.addActionListener(e -> Adder.addTrainStation(trainStationTableAbstractModel));
        deleteTrainStation.addActionListener(e -> Deleter.deleteTrainStation(trainStationTableAbstractModel,trainStationTable));

        addTrain.addActionListener(e -> Adder.addTrain(trainTableAbstractModel));
        deleteTrain.addActionListener(e -> Deleter.deleteTrain(trainTableAbstractModel,trainTable));

        addRoute.addActionListener(e -> Adder.addRoute(routesTableAbstractModel,trainTableAbstractModel,trainStationTableAbstractModel));
        deleteRoute.addActionListener(e -> Deleter.deleteRoute(routesTableAbstractModel,routeTable));

        infoRoute.addActionListener(e -> InfoShower.showInfoRoute(routesTableAbstractModel,routeTable));
        sortByCapacity.addActionListener(e -> Sorter.sortByCapacity(trainStationTableAbstractModel));
        /*
        saveTrains.addActionListener(e -> CSVExporter.exportTrainsToCSV());
        saveTrainStations.addActionListener(e -> CSVExporter.exportTrainStationsToCSV());
        saveRoutes.addActionListener(e -> CSVExporter.exportRoutesToCSV());
`       */

        add(scrollPaneTrain);
        add(scrollPaneTrainStation);
        add(scrollPaneRoutes);
        add(addTrain);
        add(addTrainStation);
        add(addRoute);
        add(deleteTrain);
        add(deleteTrainStation);
        add(deleteRoute);
        //add(saveTrains);
        //add(saveTrainStations);
        //add(saveRoutes);
        add(infoRoute);
        add(sortByCapacity);
        add(date);

        setSize(1000,500);

        setVisible(true);
    }

    private void closeTheProgram() {
        JPanel panel = new JPanel();
        JLabel text = new JLabel("Do you want to save the provided data?");
        panel.add(text);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        /*
        int result = JOptionPane.showConfirmDialog(null, panel, "Save", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            CSVExporter.exportTrainsToCSV();
            CSVExporter.exportTrainStationsToCSV();
            CSVExporter.exportRoutesToCSV();
        }
        */
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
