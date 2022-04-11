import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RouteTableAbstractModel extends AbstractTableModel {
    public static List<Route> routes = new ArrayList<>();

    RouteTableAbstractModel(){
        routes = DBController.getRoutesFromDB();
    }

    @Override
    public int getRowCount() {
        return this.routes.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Route r = this.routes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> r.train.trainName;
            case 1 -> r.overallTime;
            case 2 -> r.route.get(0).stationName;
            case 3 -> r.departures.get(0).toString();
            case 4 -> r.route.get(r.route.size()-1).stationName;
            case 5 -> r.arrivals.get(r.route.size()-1).toString();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column){
        return switch (column) {
            case 0 -> "Train";
            case 1 -> "Overall time";
            case 2 -> "Start station";
            case 3 -> "Departure";
            case 4 -> "End station";
            case 5 -> "Arrival";
            default -> null;
        };
    }

    public void remove(Route r){
        routes.remove(r);
        fireTableRowsDeleted(routes.size()-1, routes.size()-1);
    }

    public void add(Route r){
        routes.add(r);
        fireTableRowsInserted(routes.size()-1, routes.size()-1);
    }
}
