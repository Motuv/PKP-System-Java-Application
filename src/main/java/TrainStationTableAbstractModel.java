import Entity.TrainStationsEntity;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainStationTableAbstractModel extends AbstractTableModel {
    public static List<TrainStation> trainStations = new ArrayList<>();

    TrainStationTableAbstractModel(){
        List tsl = DBController.getStationsFromDB();
        for(Object o: tsl){
            TrainStationsEntity ts = (TrainStationsEntity) o;
            TrainStation trainStation = new TrainStation(ts.getName(), ts.getMaxCapacity());
            trainStations.add(trainStation);
        }
    }

    @Override
    public int getRowCount() {
        return this.trainStations.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TrainStation t = this.trainStations.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> t.stationName;
            case 1 -> t.maxCapacity;
            default -> null;
        };
    }

    public void add(TrainStation t){
        DataGenerator.dg.tsc.addStation(t.stationName, t.maxCapacity);
        trainStations.add(t);
        fireTableRowsInserted(trainStations.size()-1, trainStations.size()-1);
    }

    public void remove(TrainStation t){
        DataGenerator.dg.tsc.TrainStations.remove(t.stationName);
        trainStations.remove(t);
        fireTableRowsDeleted(trainStations.size()-1, trainStations.size()-1);
    }
    @Override
    public String getColumnName(int column){
        return switch (column) {
            case 0 -> "Train station name";
            case 1 -> "Max capacity";
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        TrainStation row = trainStations.get(rowIndex);
        if(0 == columnIndex) {
            row.setStationName((String) aValue);
        }
        else if(1 == columnIndex) {
            row.setMaxCapacity(Integer.parseInt(String.valueOf(aValue)));
        }
    }

    public static TrainStation getTrainStationByName(String n) {
        for (TrainStation t : trainStations) {
            if (t.stationName.equals(n)) {
                return t;
            }
        }
        return null;
    }

}
