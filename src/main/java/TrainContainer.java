import java.util.ArrayList;
import java.util.List;

public class TrainContainer {

    public List<Train> trains = new ArrayList<>();

    public Train getTrainByName(String name){
        for(Train t: trains){
            if(t.trainName.equals(name)){
                return t;
            }
        }
        return null;
    }
}
