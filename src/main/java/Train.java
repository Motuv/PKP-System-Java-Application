import java.io.Serializable;

public class Train implements Comparable, Serializable {
    @TableAnnotation(name="Train name", order=0)
    String trainName;
    @TableAnnotation(name="State", order=1, export = false)
    TrainState state;
    @TableAnnotation(name="Number of seats", order=2)
    int numberOfSeats;
    @TableAnnotation(name="Max speed", order=3)
    int maxSpeed;
    @TableAnnotation(name="Spaces for bicycle", order=4)
    int bicycleSpaces;
    @TableAnnotation(name="Business class", order=5)
    boolean doesContainBusinessClass;
    @TableAnnotation(name="First class", order=6)
    boolean doesContainFirstClass;
    @TableAnnotation(name="Restaurant", order=7)
    boolean doesContainRestaurant;
    @TableAnnotation(name="Wi-Fi", order=8)
    boolean doesContainWiFi;

    public Train(String trainName, TrainState state, int numberOfSeats, int maxSpeed, int bicycleSpaces, boolean doesContainBusinessClass, boolean doesContainFirstClass, boolean doesContainRestaurant, boolean doesContainWiFi) {
        this.trainName = trainName;
        this.state = state;
        this.numberOfSeats = numberOfSeats;
        this.maxSpeed = maxSpeed;
        this.bicycleSpaces = bicycleSpaces;
        this.doesContainBusinessClass = doesContainBusinessClass;
        this.doesContainFirstClass = doesContainFirstClass;
        this.doesContainRestaurant = doesContainRestaurant;
        this.doesContainWiFi = doesContainWiFi;
    }

    public void printInfo() {
        System.out.println("---Train Info---");
        System.out.println("Train name: " + this.trainName);
        System.out.println("State: " + this.state.toString());
        System.out.println("Number of seats: " + this.numberOfSeats);
        System.out.println("Max speed: " + this.maxSpeed);
        System.out.println("Bicycle spaces: " + this.bicycleSpaces);
        System.out.println("Contains business class: " + this.doesContainBusinessClass);
        System.out.println("Contains first class: " + this.doesContainFirstClass);
        System.out.println("Contains restaurant: " + this.doesContainRestaurant);
        System.out.println("Contains WiFi: " + this.doesContainWiFi);
    }


    @Override
    public int compareTo(Object o) {
        int result = -1;
        if (o.getClass() == this.getClass()) {
            Train t = (Train) o;
            result = String.CASE_INSENSITIVE_ORDER.compare(this.trainName, t.trainName);
        } else if (o.getClass() == this.trainName.getClass()) {
            String s = (String) o;
            result = String.CASE_INSENSITIVE_ORDER.compare(this.trainName, s);
        }
        return result;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public TrainState getState() {
        return state;
    }

    public void setState(TrainState state) {
        this.state = state;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getBicycleSpaces() {
        return bicycleSpaces;
    }

    public void setBicycleSpaces(int bicycleSpaces) {
        this.bicycleSpaces = bicycleSpaces;
    }

    public boolean isDoesContainBusinessClass() {
        return doesContainBusinessClass;
    }

    public void setDoesContainBusinessClass(boolean doesContainBusinessClass) {
        this.doesContainBusinessClass = doesContainBusinessClass;
    }

    public boolean isDoesContainFirstClass() {
        return doesContainFirstClass;
    }

    public void setDoesContainFirstClass(boolean doesContainFirstClass) {
        this.doesContainFirstClass = doesContainFirstClass;
    }

    public boolean isDoesContainRestaurant() {
        return doesContainRestaurant;
    }

    public void setDoesContainRestaurant(boolean doesContainRestaurant) {
        this.doesContainRestaurant = doesContainRestaurant;
    }

    public boolean isDoesContainWiFi() {
        return doesContainWiFi;
    }

    public void setDoesContainWiFi(boolean doesContainWiFi) {
        this.doesContainWiFi = doesContainWiFi;
    }

    public void reserveSeat(){
        if(hasFreeSeat())this.numberOfSeats-=1;
    }

    public void cancelTicket(){
        this.numberOfSeats+=1;
    }

    public boolean hasFreeSeat(){
        if(this.numberOfSeats>0){
            return true;
        }
        else{
            return false;
        }
    }


}
