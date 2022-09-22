package zakladysportowe;

import Database.DatabaseConnection;
import Models.OddsData;
import Thread.CountOdds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class listReview extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel sportTitle;
    private JLabel homeTeam;
    private JLabel awayTeam;
    private JLabel oddHomeTeam;
    private JLabel oddAwayTeam;
    private JLabel favouriteIconLabel;
    private ImageIcon favouriteIcon;
    private OddsData odd;

    public listReview(OddsData odd, String home_odd, String away_odd, String db_id) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.odd = odd;
        sportTitle.setText(odd.getSportTitle());
        homeTeam.setText(odd.getHomeTeam());
        awayTeam.setText(odd.getAwayTeam());
        odd.setHome_odd(home_odd);
        odd.setAway_odd(away_odd);
        odd.setDb_id(db_id);
        isFavourited();

        if(Objects.equals(home_odd, "") && Objects.equals(away_odd, "")){
            Thread countHomeTeamOdds = new Thread(new Runnable() {
                public void run() {
                    CountOdds count = new CountOdds(odd);
                    count.countOdds();
                    oddHomeTeam.setText("Kurs: " + String.format("%.2g%n", count.homeTeamOdds));
                    oddAwayTeam.setText("Kurs: " + String.format("%.2g%n", count.awayTeamOdds));

                }
            });
            countHomeTeamOdds.start();
        } else{
            oddHomeTeam.setText("Kurs: " + home_odd);
            oddAwayTeam.setText("Kurs: " + away_odd);
        }
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        favouriteIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isFavourited()) {
                    DatabaseConnection conn = new DatabaseConnection();
                    conn.connect();
                    String query = "DELETE FROM favourites WHERE id = " + odd.getDb_id();
                    Statement statement = null;
                    try {
                        statement = conn.db.createStatement();
                        statement.execute(query);
                        conn.db.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    DatabaseConnection conn = new DatabaseConnection();
                    conn.connect();

                    String query = "INSERT INTO favourites (sport_title, home_team, away_team, home_odd, away_odd)"
                            + " values (\"" + sportTitle.getText() + "\", "
                            + "\"" + homeTeam.getText() + "\", "
                            + "\"" + awayTeam.getText() + "\", "
                            + "\"" + oddHomeTeam.getText().replace("Kurs: ", "") + "\", "
                            + "\"" + oddAwayTeam.getText().replace("Kurs: ", "") + "\")";

                    Statement statement = null;
                    try {
                        statement = conn.db.createStatement();
                        statement.execute(query);
                        conn.db.close();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private boolean isFavourited(){
        boolean result = false;
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();

        String query = "SELECT id FROM favourites WHERE "
                        + "sport_title = \"" + sportTitle.getText() + "\" AND "
                        + "home_team = \"" + homeTeam.getText() + "\" AND "
                        + "away_team = \"" + awayTeam.getText() + "\"";

        ResultSet data = null;
        Statement statement = null;
        try {
            statement = conn.db.createStatement();
            data = statement.executeQuery(query);
            if(data.next()){
                result = true;
            }
            conn.db.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        if(result)
            favouriteIcon = new ImageIcon("src/main/resources/filledIcon.png");
        else
            favouriteIcon = new ImageIcon("src/main/resources/emptyIcon.png");
        Image fav = favouriteIcon.getImage();
        Image newfav = fav.getScaledInstance(22, 25,  Image.SCALE_SMOOTH);
        favouriteIcon = new ImageIcon(newfav);
        favouriteIconLabel.setIcon(favouriteIcon);

        return result;
    }
}
