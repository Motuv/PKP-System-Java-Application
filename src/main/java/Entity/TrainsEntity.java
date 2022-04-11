package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "trains", schema = "pkp"/*, catalog = ""*/)
public class TrainsEntity {
    private int id;
    private String name;
    private TrainStatesEntity state;
    private int numberOfSeats;
    private int maxSpeed;
    private int bicycleSpaces;
    private byte businessClass;
    private byte firstClass;
    private byte restaurant;
    private byte wifi;

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

    @ManyToOne
    @JoinColumn(name = "state_id", nullable=false)
    public TrainStatesEntity getStateId() {
        return state;
    }

    public void setStateId(TrainStatesEntity state) {
        this.state = state;
    }

    @Basic
    @Column(name = "number_of_seats")
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Basic
    @Column(name = "max_speed")
    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Basic
    @Column(name = "bicycle_spaces")
    public int getBicycleSpaces() {
        return bicycleSpaces;
    }

    public void setBicycleSpaces(int bicycleSpaces) {
        this.bicycleSpaces = bicycleSpaces;
    }

    @Basic
    @Column(name = "business_class")
    public byte getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(byte businessClass) {
        this.businessClass = businessClass;
    }

    @Basic
    @Column(name = "first_class")
    public byte getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(byte firstClass) {
        this.firstClass = firstClass;
    }

    @Basic
    @Column(name = "restaurant")
    public byte getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(byte restaurant) {
        this.restaurant = restaurant;
    }

    @Basic
    @Column(name = "wifi")
    public byte getWifi() {
        return wifi;
    }

    public void setWifi(byte wifi) {
        this.wifi = wifi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainsEntity that = (TrainsEntity) o;
        return id == that.id && state == that.state && numberOfSeats == that.numberOfSeats && maxSpeed == that.maxSpeed && bicycleSpaces == that.bicycleSpaces && businessClass == that.businessClass && firstClass == that.firstClass && restaurant == that.restaurant && wifi == that.wifi && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, state, numberOfSeats, maxSpeed, bicycleSpaces, businessClass, firstClass, restaurant, wifi);
    }
}
