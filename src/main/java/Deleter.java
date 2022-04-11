import Entity.TrainsEntity;

import javax.swing.*;

public class Deleter {
    //delete from db

    public static void deleteTrainStation(TrainStationTableAbstractModel tstam, JTable trainStationTable){
        int choice = JOptionPane.showConfirmDialog(null, "Delete this train station?");
        if(choice == JOptionPane.YES_OPTION) {
            int tsindex = trainStationTable.getSelectedRow();
            DBController.deleteTrainStation(tsindex);
            tstam.remove(tstam.trainStations.get(tsindex));
            tstam.fireTableDataChanged();
        }
    }

    public static void deleteRoute(RouteTableAbstractModel rtam, JTable routeTable) {
        int choice = JOptionPane.showConfirmDialog(null, "Delete this route?");
        if(choice == JOptionPane.YES_OPTION) {
            int rindex = routeTable.getSelectedRow();
            DBController.deleteRoute(rindex);
            rtam.remove(rtam.routes.get(rindex));
            rtam.fireTableDataChanged();
        }
    }

    public static void deleteTrain(TrainTableAbstractModel ttam, JTable trainTable){
        int choice = JOptionPane.showConfirmDialog(null, "Delete this train?");
        if(choice == JOptionPane.YES_OPTION) {
            int tindex = trainTable.getSelectedRow();
            String name = (String) trainTable.getValueAt(tindex, 0);
            DBController.deleteTrain(DBController.getTrainByName(name).getId());
            ttam.remove(ttam.trainList.get(tindex));
            ttam.fireTableDataChanged();
        }
    }
}
