package zakladysportowe;

import Models.OddsData;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JComboBox sportList;
    private ArrayList<String> odds;
    private DefaultListModel listOddsModel;

    private final String[] regions = {"eu", "uk", "us", "au"};
    private final String[] sports = {"Brak"};

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
        for(String sport : sports){
            sportList.addItem(sport);
        }
        scoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getApiDataForScores();
            }
        });
        oddsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getApiDataForOdds();
            }
        });
        dataList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listReview listReview = new listReview((OddsData) dataList.getSelectedValue());
                listReview.setTitle("Informacje o zakładzie");
                listReview.pack();
                listReview.setVisible(true);
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
            listOddsModel.addElement(odd);
        }
    }

    public void getApiDataForScores() {
        listOddsModel.removeAllElements();
        URL url = null;
        InputStreamReader reader = null;
        OddsData[] data = null;
        try {
            String baseUrl = "https://odds.p.rapidapi.com/v4/sports/";
            baseUrl += "?regions=" + Objects.requireNonNull(regionList.getSelectedItem());
            baseUrl += "/scores";
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
            listOddsModel.addElement(odd);
        }
    }
}
