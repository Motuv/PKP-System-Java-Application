import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

public class CSVExporter {
    public static String convertTrainToCSV(Train train){
        String result = "";
        for(Field field  : Train.class.getDeclaredFields())
        {
            TableAnnotation f = field.getAnnotation(TableAnnotation.class);
            if(f.export()){
                try {
                    result+=field.get(train)+",";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        result = result.substring(0,result.length()-1);
        result+=System.getProperty("line.separator");
        return result;
    }

    public static String convertTrainStationToCSV(TrainStation trainStation){
        String result = "";
        for(Field field  : TrainStation.class.getDeclaredFields())
        {
            TableAnnotation f = field.getAnnotation(TableAnnotation.class);
            if(f != null) {
                if (f.export()) {
                    try {
                        result += field.get(trainStation) + ",";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        result = result.substring(0,result.length()-1);
        result+=System.getProperty("line.separator");
        return result;
    }

    public static String convertRouteToCSV(Route route){
        String result = "";
        result+=route.train.trainName+",";
        result+=route.overallTime+System.getProperty("line.separator");
        Iterator<TrainStation> itr = route.route.iterator();
        Iterator<LocalTime> itr2 = route.departures.iterator();
        Iterator<LocalTime> itr3 = route.arrivals.iterator();
        while (itr.hasNext()) {
            TrainStation station = itr.next();
            LocalTime dep = itr2.next();
            LocalTime arr = itr3.next();
            result+= station.stationName+","+arr.toString()+","+dep.toString()+System.getProperty("line.separator");
        }
        return result;
    }

    public static String convertTicketToCSV(Ticket ticket){
        String result = "";
        for(Field field  : Ticket.class.getDeclaredFields())
        {
            TableAnnotation f = field.getAnnotation(TableAnnotation.class);
            if(f.export()){
                try {
                    result+=field.get(ticket)+",";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        result = result.substring(0,result.length()-1);
        result+=System.getProperty("line.separator");
        return result;
    }

    public static void exportTrainsToCSV(){
        DataGenerator dg = DataGenerator.getInstance();
        String str = "";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("trains.csv"));
            for(Train t: dg.tc.trains){
                str = convertTrainToCSV(t);
                out.write(str);
            }
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void exportTrainStationsToCSV(){
        DataGenerator dg = DataGenerator.getInstance();
        String str = "";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("train_stations.csv"));
            List<TrainStation> list = dg.tsc.mapToList();
            for(TrainStation t: list){
                str = convertTrainStationToCSV(t);
                out.write(str);
            }
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void exportRoutesToCSV(){
        DataGenerator dg = DataGenerator.getInstance();
        try {
        String str = "";
            BufferedWriter out = new BufferedWriter(new FileWriter("routes.csv"));
            for(Route r: dg.rc.routes){
                str = convertRouteToCSV(r);
                out.write(str);
            }
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void exportTicketsToCSV(){
        DataGenerator dg = DataGenerator.getInstance();
        String str = "";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("tickets.csv"));
            for(Ticket t: dg.mt.tickets){
                str = convertTicketToCSV(t);
                out.write(str);
            }
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
