package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "train_stations", schema = "pkp")
public class TrainStationsEntity {
    private int id;
    private String name;
    private int maxCapacity;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "max_capacity")
    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainStationsEntity that = (TrainStationsEntity) o;
        return id == that.id && maxCapacity == that.maxCapacity && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maxCapacity);
    }
}
