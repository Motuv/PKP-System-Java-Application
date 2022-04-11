import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class Ticket implements Serializable {
    @TableAnnotation(name="Overall time", order=6)
    public String overallTime;
    @TableAnnotation(name="Price", order=1)
    public double price;

    @TableAnnotation(name="Dep. station", order=2)
    String departureStation;
    @TableAnnotation(name="Arr. station", order=3)
    String arrivalStation;
    @TableAnnotation(name="Dep. time", order=4)
    LocalTime departureTime;
    @TableAnnotation(name="Arr. time", order=5)
    LocalTime arrivalTime;
    @TableAnnotation(name="Train", order=0)
    String train;

    public Ticket(Train train, TrainStation departureStation, TrainStation arrivalStation, LocalTime departureTime, LocalTime arrivalTime) {
        this.train = train.trainName;
        this.departureStation = departureStation.stationName;
        this.arrivalStation = arrivalStation.stationName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = Route.countOverallTime(departureTime,arrivalTime)/10.0;
        int time = Route.countOverallTime(departureTime,arrivalTime);
        String hours = String.valueOf(time/60);
        String minutes = String.valueOf(time%60);
        this.overallTime = hours+":"+minutes;
    }


    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOverallTime() {
        return overallTime;
    }

    public void setOverallTime(String overallTime) {
        this.overallTime = overallTime;
    }
}
