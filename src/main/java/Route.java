import javax.swing.text.html.HTMLDocument;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

public class Route implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableAnnotation(name="Train", order=0)
    Train train;
    @TableAnnotation(name="Overall time", order=1)
    int overallTime;
    @TableAnnotation(name="Train station", order=2)
    List<TrainStation> route = new ArrayList<>();
    @TableAnnotation(name="Dep. time", order=3)
    List<LocalTime> departures = new ArrayList<>();
    @TableAnnotation(name="Arr. time", order=4)
    List<LocalTime> arrivals = new ArrayList<>();

    public Route(Train train) {
        this.train = train;
    }

    public void addStation(TrainStation TS, LocalTime departure, LocalTime arrival) {
        route.add(TS);
        departures.add(departure);
        arrivals.add(arrival);
        Iterator<LocalTime> itr = departures.iterator();
        LocalTime begin = itr.next();
        overallTime = arrival.getHour() * 60 + arrival.getMinute() - begin.getHour() * 60 + begin.getMinute();
    }

    public void printInfo() {
        Iterator<TrainStation> itr = route.iterator();
        Iterator<LocalTime> itr2 = departures.iterator();
        Iterator<LocalTime> itr3 = arrivals.iterator();
        System.out.println("---Route---" + overallTime + " minutes");
        while (itr.hasNext()) {
            TrainStation station = itr.next();
            LocalTime dep = itr2.next();
            LocalTime arr = itr3.next();
            System.out.println("Station: " + station.stationName + " Arrival: " + arr.toString() + " Departure: " + dep.toString());
        }
    }

    public boolean doesContainDepAndArrStation(String depStationName, String arrStationName){
        Iterator<TrainStation> itr = route.iterator();
        TrainStation ts;
        while (itr.hasNext()) {
            ts = itr.next();
            if(ts.stationName.equals(depStationName)){
                while (itr.hasNext()) {
                    ts = itr.next();
                    if(ts.stationName.equals(arrStationName)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public LocalTime getDepartureTime(String stationName){
        Iterator<TrainStation> itr = route.iterator();
        Iterator<LocalTime> itr2 = departures.iterator();
        TrainStation ts;
        LocalTime depTime;
        while (itr.hasNext()){
            ts = itr.next();
            depTime = itr2.next();
            if(ts.stationName.equals(stationName)){
                return depTime;
            }
        }
        return null;
    }

    public LocalTime getArrivalTime(String stationName){
        Iterator<TrainStation> itr = route.iterator();
        Iterator<LocalTime> itr2 = arrivals.iterator();
        TrainStation ts;
        LocalTime arrTime;
        while (itr.hasNext()){
            ts = itr.next();
            arrTime = itr2.next();
            if(ts.stationName.equals(stationName)){
                return arrTime;
            }
        }
        return null;
    }

    public static Integer countOverallTime(LocalTime dep, LocalTime arr){
        return arr.getHour() * 60 + arr.getMinute() - dep.getHour() * 60 + dep.getMinute();
    }

    public boolean isAfter(LocalTime hour, String station){
        int i = getStationPosition(station);
        return departures.get(i).isAfter(hour);
    }

    private int getStationPosition(String s) {
        int i=0;
        for(TrainStation ts: route){
            if(ts.stationName.equals(s)){
                return i;
            }
            i += 1;
        }
        return -1;
    }

    public void writeObject(ObjectOutputStream stream) throws IOException {
        CSVExporter.convertRouteToCSV(this);
    }
    private void readObject(ObjectOutputStream stream) throws IOException, ClassNotFoundException {
        DataGenerator.getInstance().importTicketsFromCSV();
    }
}
