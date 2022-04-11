import Entity.TrainStatesEntity;
import Entity.TrainsEntity;
import Entity.TrainStationsEntity;
import Entity.RoutesEntity;
import Entity.TrainStopsEntity;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DBController {
    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    public static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static List<Train> getTrainsFromDB(){
        EntityTransaction transaction = entityManager.getTransaction();
        List trainList;
        try{
            transaction.begin();
            Query query = entityManager.createQuery("from TrainsEntity");
            trainList = query.getResultList();
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }

        List<Train> finalTrainList = new ArrayList<>();
        for(Object o: trainList){
            TrainsEntity t = (TrainsEntity) o;
            Train train = new Train(t.getName(), TrainState.READY, t.getNumberOfSeats(), t.getMaxSpeed(), t.getBicycleSpaces(), t.getBusinessClass()==1, t.getFirstClass()==1, t.getRestaurant()==1, t.getWifi()==1);
            finalTrainList.add(train);
        }
        return finalTrainList;
    }

    public static List<Route> getRoutesFromDB(){
        EntityTransaction transaction = entityManager.getTransaction();
        List routesList;
        List trainStopsList;
        try{
            transaction.begin();
            Query query = entityManager.createQuery("from RoutesEntity");
            routesList = query.getResultList();
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
        RoutesEntity re;
        Route r;
        TrainStopsEntity tse;
        List<Route> finalRouteList = new ArrayList<>();
        for(Object o: routesList){
            re = (RoutesEntity) o;
            r = new Route(TrainTableAbstractModel.getTrainByName(re.getTrainId().getName()));
            try {
                transaction.begin();
                Query query = entityManager.createQuery("from TrainStopsEntity t where t.routeId.id=:id").setParameter("id", re.getId());
                trainStopsList = query.getResultList();
                transaction.commit();
            }
            finally{
                if(transaction.isActive()){
                    transaction.rollback();
                }
            }

            for(Object o2: trainStopsList){
                tse = (TrainStopsEntity) o2;
                r.addStation(TrainStationTableAbstractModel.getTrainStationByName(tse.getTrainStationId().getName()), LocalTime.parse(String.valueOf(tse.getDepartureTime())), LocalTime.parse(String.valueOf(tse.getArrivalTime())));
            }
            finalRouteList.add(r);
        }

        return finalRouteList;
    }

    public static List getStationsFromDB(){

        EntityTransaction transaction = entityManager.getTransaction();
        List stationList = null;
        try{
            transaction.begin();
            Query query = entityManager.createQuery("from TrainStationsEntity");
            stationList = query.getResultList();
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }

        return stationList;
    }

    public static List getTicketsFromDB(){
        EntityTransaction transaction = entityManager.getTransaction();
        List ticketList = null;
        try{
            transaction.begin();
            Query query = entityManager.createQuery("from TicketsEntity");
            ticketList = query.getResultList();
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
        return ticketList;
    }

    public static TrainsEntity getTrainByName(String name){
        EntityTransaction transaction = entityManager.getTransaction();
        TrainsEntity te;
        try{
            transaction.begin();
            Query query = entityManager.createQuery("from TrainsEntity t where t.name = :name").setParameter("name", name);
            Object o = query.getSingleResult();
            te = (TrainsEntity) o;
            //te = query.setParameter(id);
            //ticketList = query.getResultList();
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
        return te;
    }

    public static void addTrainStationEntity(TrainStation ts){
        EntityTransaction transaction = entityManager.getTransaction();
        TrainStationsEntity tse = new TrainStationsEntity();
        try{
            transaction.begin();
            tse.setName(ts.stationName);
            tse.setMaxCapacity(ts.maxCapacity);
            entityManager.persist(tse);
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }

    public static void addTrainEntity(Train t){
        EntityTransaction transaction = entityManager.getTransaction();
        TrainsEntity te = new TrainsEntity();
        TrainStatesEntity tse;
        try{
            transaction.begin();
            Query query = entityManager.createQuery("from TrainStatesEntity t where t.description = :desc").setParameter("desc", t.state.toString());
            Object o = query.getSingleResult();
            tse = (TrainStatesEntity) o;
            transaction.commit();
            transaction.begin();
            te.setName(t.trainName);
            te.setStateId(tse);
            te.setMaxSpeed(t.maxSpeed);
            te.setNumberOfSeats(t.numberOfSeats);
            te.setBicycleSpaces(t.bicycleSpaces);
            te.setWifi((byte)(t.doesContainWiFi ? 1:0));
            te.setRestaurant((byte)(t.doesContainRestaurant ? 1:0));
            te.setBusinessClass((byte)(t.doesContainBusinessClass ? 1:0));
            te.setFirstClass((byte)(t.doesContainFirstClass ? 1:0));

            entityManager.persist(te);
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }

    public static void addRouteEntity(Train t){
        EntityTransaction transaction = entityManager.getTransaction();
        RoutesEntity re = new RoutesEntity();
        TrainsEntity te = null;
        try{
            transaction.begin();
            Query query = entityManager.createQuery("from TrainsEntity t where t.name = :name").setParameter("name", t.trainName);
            Object o = query.getSingleResult();
            te = (TrainsEntity) o;
            transaction.commit();
            transaction.begin();
            re.setTrainId(te);
            re.setOverallTime(0);
            entityManager.persist(re);
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }

    public static void deleteRoute(int id){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Query query = entityManager.createQuery(" delete from RoutesEntity t where t.id = :id").setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            transaction.begin();
            Query query2 = entityManager.createQuery("delete from TrainStopsEntity t where t.trainStationId=:id").setParameter("id", id);
            query2.executeUpdate();
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }

    public static void deleteTrainStation(int id){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Query query = entityManager.createQuery("delete from TrainStationsEntity t where t.id = :id").setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }

    public static void deleteTrain(int id){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Query query = entityManager.createQuery(" delete from TrainsEntity t where t.id = :id").setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }

    public static void addTrainStopEntity(Route r, Object[] tuple){
        EntityTransaction transaction = entityManager.getTransaction();
        TrainStopsEntity tse = new TrainStopsEntity();
        TrainStationsEntity tste;
        RoutesEntity re;
        Query query;
        Object o;
        try{
            transaction.begin();
            query = entityManager.createQuery("from RoutesEntity route where route.trainId.name=:name").setParameter("name", r.train.trainName);
            o = query.getSingleResult();
            re = (RoutesEntity) o;
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
        try{
            transaction.begin();
            String stationName = (String) tuple[0];
            query = entityManager.createQuery("from TrainStationsEntity t where t.name = :name").setParameter("name", stationName);
            o = query.getSingleResult();
            tste = (TrainStationsEntity) o;
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
        try{
            transaction.begin();
            tse.setRouteId(re);
            tse.setTrainStationId(tste);
            tse.setArrivalTime(Time.valueOf((LocalTime) tuple[2]));
            tse.setDepartureTime(Time.valueOf((LocalTime) tuple[1]));
            entityManager.persist(tse);
            transaction.commit();
        }
        finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }
}


