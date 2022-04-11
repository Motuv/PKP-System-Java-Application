import java.time.LocalTime;
import java.util.*;

public class RoutesContainer {
    List<Route> routes = new ArrayList();

    public void addRoute(Route r) {
        routes.add(r);
    }

    public void printInfo() {
        for (Route r : routes) {
            r.printInfo();
        }
    }

    public void sort() {
        Collections.sort(routes, (o1, o2) -> o1.overallTime - o2.overallTime);
    }

    public Route getRouteByTrainName(String trainName){
        for (Route r : routes) {
            if(r.train.trainName.equals(trainName)){
                return r;
            }
        }
        return null;
    }

}
