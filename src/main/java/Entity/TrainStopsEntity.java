package Entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "train_stops", schema = "pkp"/*catalog = ""*/)
public class TrainStopsEntity {
    private int id;
    private RoutesEntity routeId;
    private TrainStationsEntity trainStationId;
    private Time arrivalTime;
    private Time departureTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "route_id")
    public RoutesEntity getRouteId() {
        return routeId;
    }

    public void setRouteId(RoutesEntity routeId) {
        this.routeId = routeId;
    }

    @ManyToOne
    @JoinColumn(name = "train_station_id")
    public TrainStationsEntity getTrainStationId() {
        return trainStationId;
    }

    public void setTrainStationId(TrainStationsEntity trainStationId) {
        this.trainStationId = trainStationId;
    }

    @Basic
    @Column(name = "arrival_time")
    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Basic
    @Column(name = "departure_time")
    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainStopsEntity that = (TrainStopsEntity) o;
        return routeId == that.routeId && trainStationId == that.trainStationId && Objects.equals(arrivalTime, that.arrivalTime) && Objects.equals(departureTime, that.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, trainStationId, arrivalTime, departureTime);
    }
}
