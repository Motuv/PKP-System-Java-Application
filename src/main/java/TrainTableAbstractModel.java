import javax.swing.table.AbstractTableModel;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TrainTableAbstractModel extends AbstractTableModel {

    public static List<Train> trainList = new ArrayList<>();

    TrainTableAbstractModel(){
        trainList = DBController.getTrainsFromDB();
    }

    @Override
    public int getRowCount() {
        return trainList.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Train t = trainList.get(rowIndex);
        for(Field field  : Train.class.getDeclaredFields())
        {
            TableAnnotation f = field.getAnnotation(TableAnnotation.class);
            if(columnIndex==f.order()){
                try {
                    return field.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column){
        for(Field field  : Train.class.getDeclaredFields())
        {
            TableAnnotation f = field.getAnnotation(TableAnnotation.class);
            if(column==f.order()){
                return f.name();
            }
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Train row = trainList.get(rowIndex);
        switch (columnIndex) {
            case 0 -> row.setTrainName((String) aValue);
            case 1 -> row.setState(TrainState.valueOf((String) aValue));
            case 2 -> row.setNumberOfSeats(Integer.parseInt((String)aValue));
            case 3 -> row.setMaxSpeed(Integer.parseInt((String)aValue));
            case 4 -> row.setBicycleSpaces(Integer.parseInt((String)aValue));
            case 5 -> row.setDoesContainBusinessClass(Boolean.parseBoolean((String) aValue));
            case 6 -> row.setDoesContainFirstClass(Boolean.parseBoolean((String) aValue));
            case 7 -> row.setDoesContainRestaurant(Boolean.parseBoolean((String) aValue));
            case 8 -> row.setDoesContainWiFi(Boolean.parseBoolean((String) aValue));
        }
    }


    public void add(Train t){
        DataGenerator.dg.tc.trains.add(t);
        trainList.add(t);
        fireTableRowsInserted(trainList.size()-1, trainList.size()-1);
    }

    public void remove(Train t){
        trainList.remove(t);
        fireTableRowsDeleted(trainList.size()-1, trainList.size()-1);
    }

    public static Train getTrainByName(String n){
        for(Train t: trainList){
            if(t.trainName.equals(n)){
                return t;
            }
        }
        return null;
    }
}
