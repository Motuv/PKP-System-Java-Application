import javax.lang.model.type.ArrayType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TrainStation implements Comparator, Serializable {
    private static final long serialVersionUID = 1L;
    @TableAnnotation(name="Station name", order=0)
    String stationName;
    @TableAnnotation(name="Max capacity", order=1)
    int maxCapacity;
    @TableAnnotation(name="Actual capacity", order=2, export = false)
    int actualCapacity = 0;
    List<Train> trainList = new ArrayList<>();

    public TrainStation(String stationName, int maxCapacity) {
        this.stationName = stationName;
        this.maxCapacity = maxCapacity;
    }

    void addTrain(Train t) {
        if (actualCapacity >= maxCapacity) System.err.println("Max capacity of station exceeded");
        else {
            trainList.add(t);
            actualCapacity++;
        }
    }

    void removeTrain(String trainName) {
        Train toRemove = this.search(trainName);
        trainList.remove(toRemove);
        actualCapacity--;
    }

    Train search(String trainName) {
        for (Train tmp : trainList) {
            if (tmp.compareTo(trainName) == 0) return tmp;
        }
        return null;
    }

    List<Train> searchPart(String trainNamePart) {
        List<Train> list = new ArrayList<>();
        for (Train tmp : trainList) {
            if (tmp.trainName.contains(trainNamePart)) list.add(tmp);
        }
        return list;
    }

    void printInfo() {
        for (Train tmp : trainList) {
            tmp.printInfo();
        }
    }


    @Override
    public int compare(Object o1, Object o2) {
        TrainStation t1 = (TrainStation) o1;
        TrainStation t2 = (TrainStation) o2;
        return t1.maxCapacity-t2.maxCapacity;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
