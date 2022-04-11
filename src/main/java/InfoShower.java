import javax.swing.*;
import java.time.LocalTime;
import java.util.Iterator;

public class InfoShower {
    //change to db
    public static void showInfoRoute(RouteTableAbstractModel rtam, JTable routeTable){
        int rindex = routeTable.getSelectedRow();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Object[][] route = new Object[rtam.routes.get(rindex).route.size()][3];
        Route selected = rtam.routes.get(rindex);
        Iterator<TrainStation> itr = selected.route.iterator();
        Iterator<LocalTime> itr2 = selected.departures.iterator();
        Iterator<LocalTime> itr3 = selected.arrivals.iterator();
        int i = 0;
        while (itr.hasNext()) {
            TrainStation station = itr.next();
            LocalTime dep = itr2.next();
            LocalTime arr = itr3.next();
            Object[] tuple = {station.stationName, dep.toString(), arr.toString()};
            route[i] = tuple;
            i+=1;
        }
        String[] columnNames = {"Station", "Departure", "Arrival"};
        JLabel trainInfo = new JLabel("Train: "+selected.train.trainName);
        JTable schedule = new JTable(route,columnNames);
        panel.add(trainInfo);
        panel.add(schedule);
        int result = JOptionPane.showConfirmDialog(null, panel, "Info about route", JOptionPane.OK_CANCEL_OPTION);
    }
}
