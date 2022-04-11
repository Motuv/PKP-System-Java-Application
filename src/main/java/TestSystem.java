import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestSystem {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("");
    public static void main(String[] args){
        ENTITY_MANAGER_FACTORY.close();
    }

    public void addTicket(){

    }
}
