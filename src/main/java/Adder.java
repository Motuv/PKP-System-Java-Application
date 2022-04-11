import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalTime;
import java.util.Locale;

public class Adder {

    public static void addTrainStation(TrainStationTableAbstractModel tstam){
        String name = JOptionPane.showInputDialog("Enter train station name");
        int maxCapacity = Integer.parseInt(JOptionPane.showInputDialog("Enter max capacity of "+name));
        TrainStation ts = new TrainStation(name, maxCapacity);

        DBController.addTrainStationEntity(ts);

        tstam.add(ts);
        tstam.fireTableDataChanged();
    }

    public static void addTrain(TrainTableAbstractModel ttam){
        JTextField name = new JTextField();
        JTextField state = new JTextField();
        JTextField numberOfSeats = new JTextField();
        JTextField maxSpeed = new JTextField();
        JTextField bicycleSpaces = new JTextField();
        JCheckBox business = new JCheckBox();
        JCheckBox first = new JCheckBox();
        JCheckBox restaurant = new JCheckBox();
        JCheckBox wifi = new JCheckBox();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Train name"));
        panel.add(name);
        panel.add(new JLabel("Train state"));
        panel.add(state);
        panel.add(new JLabel("Number of seats"));
        panel.add(numberOfSeats);
        panel.add(new JLabel("Max Speed"));
        panel.add(maxSpeed);
        panel.add(new JLabel("Spaces for bicycles"));
        panel.add(bicycleSpaces);
        panel.add(new JLabel("Business class"));
        panel.add(business);
        panel.add(new JLabel("First class"));
        panel.add(first);
        panel.add(new JLabel("Restaurant"));
        panel.add(restaurant);
        panel.add(new JLabel("WiFi"));
        panel.add(wifi);
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter train data", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            Train train = new Train(name.getText(), TrainState.READY, Integer.parseInt(numberOfSeats.getText()),Integer.parseInt(maxSpeed.getText()),Integer.parseInt(bicycleSpaces.getText()), business.isSelected(), first.isSelected(), restaurant.isSelected(),wifi.isSelected());
            train.setState(TrainState.valueOf(state.getText().toUpperCase(Locale.ROOT)));

            DBController.addTrainEntity(train);

            ttam.add(train);
            ttam.fireTableDataChanged();
        }
    }

    public static void addRoute(RouteTableAbstractModel rtam, TrainTableAbstractModel ttam, TrainStationTableAbstractModel tstam) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        String[] trainNames= new String[ttam.trainList.size()];
        Route r = new Route(ttam.trainList.get(0));
        int i=0;
        for(Train t: ttam.trainList){
            trainNames[i] = t.trainName;
            i+=1;
        }
        JComboBox trains = new JComboBox(trainNames);
        panel.add(trains);
        int result1 = JOptionPane.showConfirmDialog(null, panel, "Select train", JOptionPane.OK_CANCEL_OPTION);
        if(result1 == JOptionPane.YES_OPTION) {
            String selected = String.valueOf(trains.getSelectedItem());
            Train t = ttam.getTrainByName(selected);

            DBController.addRouteEntity(t);

            JPanel panel2 = new JPanel();
            panel2.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            Object[][] route = new Object[1][3];
            Object[] tuple = {"", "", ""};
            route[0] = tuple;
            String[] columnNames = {"Station", "Departure", "Arrival"};
            JLabel trainInfo = new JLabel("Train: " + selected);
            DefaultTableModel model = new DefaultTableModel(route, columnNames);
            JTable table = new JTable(model);
            JButton addStop = new JButton("Add stop");
            addStop.addActionListener(e -> addStop(model, r,0, tstam));
            panel.add(trainInfo);
            panel.add(table);
            panel.add(addStop);
            int result2 = JOptionPane.showConfirmDialog(null, panel, "Route modification", JOptionPane.OK_CANCEL_OPTION);
            if(result2==JOptionPane.OK_OPTION){
                rtam.add(r);
                rtam.fireTableDataChanged();
            }
        }
    }

    public static void addStop(DefaultTableModel model, Route r, int i, TrainStationTableAbstractModel tstam){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        String[] stationNames = new String[tstam.trainStations.size()];
        int j = 0;
        for(TrainStation t: tstam.trainStations){
            stationNames[j] = t.stationName;
            j+=1;
        }
        JComboBox station = new JComboBox(stationNames);
        JTextField departure = new JTextField();
        JTextField arrival = new JTextField();
        panel.add(new JLabel("Station"));
        panel.add(station);
        panel.add(new JLabel("Arrival"));
        panel.add(arrival);
        panel.add(new JLabel("Departure"));
        panel.add(departure);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter stop data", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            String selected = String.valueOf(station.getSelectedItem());
            TrainStation ts = tstam.getTrainStationByName(selected);
            Object[] tuple = {ts.stationName, LocalTime.parse(departure.getText()), LocalTime.parse(arrival.getText())};
            if(i == 0)model.removeRow(0);
            model.addRow(tuple);
            model.fireTableDataChanged();
            r.addStation(ts,(LocalTime) tuple[1],(LocalTime) tuple[2]);
            i+=1;

            DBController.addTrainStopEntity(r, tuple);

            addStop(model,r,i,tstam);
        }
    }
}
