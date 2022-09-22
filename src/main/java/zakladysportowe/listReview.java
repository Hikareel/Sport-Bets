package zakladysportowe;

import Models.OddsData;
import Thread.CountOdds;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class listReview extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel sportTitle;
    private JLabel homeTeam;
    private JLabel awayTeam;
    private JLabel oddHomeTeam;
    private JLabel oddAwayTeam;
    private OddsData odd;

    public listReview(OddsData odd) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.odd = odd;
        sportTitle.setText(odd.getSportTitle());
        homeTeam.setText(odd.getHomeTeam());
        awayTeam.setText(odd.getAwayTeam());
        Thread countHomeTeamOdds = new Thread(new Runnable() {
            public void run() {
                CountOdds count = new CountOdds(odd);
                count.countOdds();
                oddHomeTeam.setText("Kurs: " + String.format("%.2g%n", count.homeTeamOdds));
                oddAwayTeam.setText("Kurs: " + String.format("%.2g%n", count.awayTeamOdds));
            }
        });
        countHomeTeamOdds.start();
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
