public class Sorter {

    public static void sortByCapacity(TrainStationTableAbstractModel tstam) {
        tstam.trainStations.sort(new TrainStation("", 0));
        tstam.fireTableDataChanged();
    }
}
