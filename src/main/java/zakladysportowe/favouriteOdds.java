package zakladysportowe;

import Database.DatabaseConnection;
import Models.OddsData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class favouriteOdds extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JList favouritesList;
    private ArrayList<String> odds;
    private DefaultListModel listOddsModel;

    public favouriteOdds() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        odds = new ArrayList<String>();
        listOddsModel = new DefaultListModel();
        favouritesList.setModel(listOddsModel);
        fullfillList();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        favouritesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OddsData odd = (OddsData) favouritesList.getSelectedValue();

                listReview listReview = new listReview(odd, odd.getHome_odd(), odd.getAway_odd(), odd.getDb_id());
                listReview.setTitle("Informacje o zakładzie");
                listReview.pack();
                listReview.setVisible(true);
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void fullfillList(){
        listOddsModel.removeAllElements();
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();

        String query = "SELECT id, sport_title, home_team, away_team, home_odd, away_odd FROM favourites";

        ResultSet data = null;

        Statement statement = null;
        try {
            statement = conn.db.createStatement();
            data = statement.executeQuery(query);
            while(data.next()){
                OddsData odd = new OddsData();
                odd.setAwayTeam(data.getString("away_team"));
                odd.setHomeTeam(data.getString("home_team"));
                odd.setSportTitle(data.getString("sport_title"));
                odd.setHome_odd(data.getString("home_odd"));
                odd.setAway_odd(data.getString("away_odd"));
                odd.setDb_id(data.getString("id"));
                listOddsModel.addElement(odd);
            }
            conn.db.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
