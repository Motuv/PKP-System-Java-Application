package Entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "tickets", schema = "pkp"/*, catalog = ""*/)
public class TicketsEntity {
    private int id;
    private TrainsEntity trainId;
    private double price;
    private TrainStopsEntity departureStopId;
    private TrainStopsEntity arrivalStopId;
    private Time overallTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "train_id", nullable=false)
    public TrainsEntity getTrainId() {
        return trainId;
    }

    public void setTrainId(TrainsEntity trainId) {
        this.trainId = trainId;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "departure_stop_id", nullable=false)
    public TrainStopsEntity getDepartureStopId() {
        return departureStopId;
    }

    public void setDepartureStopId(TrainStopsEntity departureStopId) {
        this.departureStopId = departureStopId;
    }

    @ManyToOne
    @JoinColumn(name = "arrival_stop_id", nullable=false)
    public TrainStopsEntity getArrivalStopId() {
        return arrivalStopId;
    }

    public void setArrivalStopId(TrainStopsEntity arrivalStopId) {
        this.arrivalStopId = arrivalStopId;
    }

    @Basic
    @Column(name = "overall_time")
    public Time getOverallTime() {
        return overallTime;
    }

    public void setOverallTime(Time overallTime) {
        this.overallTime = overallTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketsEntity that = (TicketsEntity) o;
        return id == that.id && trainId == that.trainId && Double.compare(that.price, price) == 0 && departureStopId == that.departureStopId && arrivalStopId == that.arrivalStopId && Objects.equals(overallTime, that.overallTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainId, price, departureStopId, arrivalStopId, overallTime);
    }
}
