package Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "rating", schema = "pkp")
public class RatingEntity {
    private int id;
    private TrainsEntity trainId;
    private TrainStationsEntity stationId;
    private int rate;
    private String description;
    private Date date;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "train_id")
    public TrainsEntity getTrainId() {
        return trainId;
    }

    public void setTrainId(TrainsEntity trainId) {
        this.trainId = trainId;
    }

    @ManyToOne
    @JoinColumn(name = "station_id")
    public TrainStationsEntity getStationId() {
        return stationId;
    }

    public void setStationId(TrainStationsEntity stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "rate")
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingEntity that = (RatingEntity) o;
        return id == that.id && Objects.equals(trainId, that.trainId) && Objects.equals(stationId, that.stationId) && Objects.equals(rate, that.rate) && Objects.equals(description, that.description) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainId, stationId, rate, description, date);
    }
}
