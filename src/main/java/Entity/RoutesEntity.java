package Entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "routes", schema = "pkp"/*, catalog = ""*/)
public class RoutesEntity {
    private int id;
    private TrainsEntity trainId;
    private int overallTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "train_id", nullable = false)
    public TrainsEntity getTrainId() {
        return trainId;
    }

    public void setTrainId(TrainsEntity trainId) {
        this.trainId = trainId;
    }

    @Basic
    @Column(name = "overall_time")
    public int getOverallTime() {
        return overallTime;
    }

    public void setOverallTime(int overallTime) {
        this.overallTime = overallTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutesEntity that = (RoutesEntity) o;
        return id == that.id && trainId == that.trainId && overallTime == that.overallTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainId, overallTime);
    }
}
