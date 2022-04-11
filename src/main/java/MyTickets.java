import java.util.ArrayList;
import java.util.List;

public class MyTickets {
    List<Ticket> tickets  = new ArrayList<>();

    public void addTicket(Ticket t) {
        tickets.add(t);
    }

    public void removeTicket(Ticket t){
        tickets.remove(t);
    }
}
