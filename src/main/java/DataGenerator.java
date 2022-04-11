import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DataGenerator {
    TrainContainer tc = new TrainContainer();
    TrainStationContainer tsc = new TrainStationContainer();
    RoutesContainer rc = new RoutesContainer();
    MyTickets mt = new MyTickets();

    static DataGenerator dg = new DataGenerator();

    private DataGenerator(){

        this.importTrainsFromCSV();
        this.importTrainStationsFromCSV();
        this.importRoutesFromCSV();
        //this.importTicketsFromCSV();
    }

    public static DataGenerator getInstance(){
        return dg;
    }

    public void importTrainsFromCSV(){
        String filename = "trains.csv";
        Train tmp;
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = in.readLine()) != null) {
                String[] data = line.split(",");
                tmp = new Train(data[0], TrainState.READY, Integer.parseInt(data[3]), Integer.parseInt(data[2]), Integer.parseInt(data[1]), Boolean.parseBoolean(data[4]), Boolean.parseBoolean(data[5]), Boolean.parseBoolean(data[6]), Boolean.parseBoolean(data[7]));
                tc.trains.add(tmp);
            }
            in.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
    }

    public void importTrainStationsFromCSV(){
        DataGenerator dg = DataGenerator.getInstance();
        String filename = "train_stations.csv";
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = in.readLine()) != null) {
                String[] data = line.split(",");
                tsc.addStation(data[0], Integer.parseInt(data[1]));
            }
            in.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
    }

    public void importRoutesFromCSV(){
        String filename = "routes.csv";
        Route tmp = new Route(tc.trains.get(0));
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = in.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length==2){
                    rc.addRoute(tmp);
                    if(rc.routes.get(0).overallTime==0){
                        rc.routes.remove(0);
                    }
                    tmp = new Route(tc.getTrainByName(data[0]));
                    tmp.overallTime = Integer.parseInt(data[1]);
                }
                else{
                    tmp.addStation(tsc.TrainStations.get(data[0]), LocalTime.parse(data[1]), LocalTime.parse(data[2]));
                }
            }
            rc.addRoute(tmp);
            in.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
    }

    public void importTicketsFromCSV(){
        DataGenerator dg = DataGenerator.getInstance();
        String filename = "tickets.csv";
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("k:mm");
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = in.readLine()) != null) {
                String[] data = line.split(",");
                Ticket tmp = new Ticket(tc.getTrainByName(data[0]), tsc.TrainStations.get(data[2]), tsc.TrainStations.get(data[3]), LocalTime.parse(data[4], parser), LocalTime.parse(data[5], parser));
                mt.addTicket(tmp);
            }
            in.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
    }
}
