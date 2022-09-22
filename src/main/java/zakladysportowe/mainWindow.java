package zakladysportowe;

import Models.OddsData;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class mainWindow extends JFrame {
    private JComboBox regionList;
    private JButton scoresButton;
    private JButton oddsButton;
    private JPanel mainPanel;
    private JList dataList;
    private ArrayList<String> odds;
    private DefaultListModel listOddsModel;

    private final String[] regions = {"eu", "uk", "us", "au"};

    mainWindow(){
        setContentPane(mainPanel);
        pack();
        setSize(700, 600 );
        setTitle("Zakłady Sportowe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        odds = new ArrayList<String>();
        listOddsModel = new DefaultListModel();
        dataList.setModel(listOddsModel);

        for(String region : regions){
            regionList.addItem(region);
        }
        scoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        oddsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getApiDataForOdds();
            }
        });
    }

    public static void main(String[] args) {
        mainWindow mainWindow = new mainWindow();
    }

    public void getApiDataForOdds(){
        listOddsModel.removeAllElements();
        URL url = null;
        InputStreamReader reader = null;
        OddsData[] data = null;
        String html = "";
        try {
            String baseUrl = "https://odds.p.rapidapi.com/v4/sports/upcoming/odds";
            baseUrl += "?regions=" + Objects.requireNonNull(regionList.getSelectedItem());
            baseUrl += "&oddsFormat=decimal&markets=h2h%2Cspreads&dateFormat=iso";
            baseUrl += "&rapidapi-key=2e9ffbfc24mshae7601f99e4bbbep197854jsn060dd4f2c6d5";
            url = new URL(baseUrl);

        } catch(MalformedURLException e){
            e.printStackTrace();
        }

        try{
            reader = new InputStreamReader(url.openStream());
            data = new Gson().fromJson(reader, OddsData[].class);
        }catch(IOException e){
            e.printStackTrace();
        }

        for(OddsData odd: data){
            html = "<html>" +
                    "<h2>" + odd.getSportTitle() + "</h2>" +
                    "</br>" +
                    "<p>" + odd.getHomeTeam() + " vs " + odd.getAwayTeam() + "</p>" +
                    "</html>";
            listOddsModel.addElement(html);
        }
    }


}
