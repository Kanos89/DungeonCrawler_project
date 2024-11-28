import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomeScreen {

    private JFrame fenetre;

    public WelcomeScreen(Runnable startAction) {
        fenetre = new JFrame();
        fenetre.setTitle("Welcome Screen");
        fenetre.setSize(400, 600);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pan = new JPanel();
        Color ForestGreen = new Color(29, 111, 29);
        pan.setBackground(ForestGreen);
        pan.setLayout(null); // Use null layout for absolute positioning
        fenetre.setContentPane(pan);

        // Add the "Welcome" label
        JLabel welcomeLabel = new JLabel("Welcome to Dungeon Crawler");
        welcomeLabel.setForeground(Color.WHITE); // Text color
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Font style and size
        welcomeLabel.setBounds(50, 150, 300, 30); // Position and size
        pan.add(welcomeLabel);

        // Add the "Start Game" button
        JButton startBouton = new JButton("Click to Start");
        Color LightGreen = new Color(103, 189, 103);
        startBouton.setBackground(LightGreen);
        startBouton.setBounds(100, 250, 200, 40); // Set position and size
        startBouton.addActionListener(e -> {
            fenetre.dispose(); // Close the welcome screen
            startAction.run(); // Start the game
        });
        pan.add(startBouton);

        fenetre.setVisible(true); // Make the frame visible
    }
}
