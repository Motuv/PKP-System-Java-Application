import java.util.*;

public class TrainStationContainer {
    Map<String, TrainStation> TrainStations = new HashMap<>();

    public void addStation(String stationName, int maxCapacity) {
        TrainStation t = new TrainStation(stationName, maxCapacity);
        TrainStations.put(stationName, t);
    }

    public void printInfo() {
        System.out.println("---Train Stations---");
        Iterator<Map.Entry<String, TrainStation>> itr = TrainStations.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, TrainStation> entry = itr.next();
            System.out.println("Station: " + entry.getKey() + " Capacity: " + entry.getValue().actualCapacity + " Max capacity: " + entry.getValue().maxCapacity);
        }
    }

    public void removeStation(String stationName) {
        TrainStations.remove(stationName);
    }

    public List<TrainStation> emptyStations() {
        List<TrainStation> list = new ArrayList<>();
        Iterator<Map.Entry<String, TrainStation>> itr = TrainStations.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, TrainStation> entry = itr.next();
            if (entry.getValue().actualCapacity == 0) list.add(entry.getValue());
        }
        return list;
    }

    public void sort() {
        TrainStations = sortMapByKey(TrainStations);
    }

    private static Map<String, TrainStation> sortMapByKey(Map<String, TrainStation> items) {
        TreeMap<String, TrainStation> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        result.putAll(items);
        return result;
    }

    public List<TrainStation> mapToList(){
        List<TrainStation> list = new ArrayList<>();
        for (Map.Entry<String, TrainStation> entry : TrainStations.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }
}
