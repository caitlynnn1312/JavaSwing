import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Runner extends JFrame {
    private String petName;
    private int hunger;
    private int happiness;
    private int energy;
    private int age;
    private boolean isSleeping;
    
    private JLabel petImage;
    private JLabel statusLabel;
    private JProgressBar hungerBar;
    private JProgressBar happinessBar;
    private JProgressBar energyBar;
    private JLabel ageLabel;
    private Timer timer;
    private Random random;
    
    public Runner() {
        random = new Random();
        initializeGame();
        setupUI();
        startGameLoop();
    }
    
    private void initializeGame() {
        petName = JOptionPane.showInputDialog(this, "What would you like to name your pet?", "Name Your Pet", JOptionPane.QUESTION_MESSAGE);
        if (petName == null || petName.trim().isEmpty()) {
            petName = "Buddy";
        }
        
        hunger = 100;
        happiness = 100;
        energy = 100;
        age = 0;
        isSleeping = false;
    }
    
    private void setupUI() {
        setTitle("Pet Simulator - " + petName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 500));
        
        // Pet display area
        JPanel petPanel = new JPanel(new BorderLayout());
        petImage = new JLabel("🐱", SwingConstants.CENTER);
        petImage.setFont(new Font("Dialog", Font.PLAIN, 100));
        petPanel.add(petImage, BorderLayout.CENTER);
        
        statusLabel = new JLabel("Your pet is happy!", SwingConstants.CENTER);
        petPanel.add(statusLabel, BorderLayout.SOUTH);
        add(petPanel, BorderLayout.CENTER);
        
        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        hungerBar = createProgressBar("Hunger");
        happinessBar = createProgressBar("Happiness");
        energyBar = createProgressBar("Energy");
        ageLabel = new JLabel("Age: " + age + " days");
        
        statsPanel.add(new JLabel("Hunger:"));
        statsPanel.add(hungerBar);
        statsPanel.add(new JLabel("Happiness:"));
        statsPanel.add(happinessBar);
        statsPanel.add(new JLabel("Energy:"));
        statsPanel.add(energyBar);
        statsPanel.add(new JLabel("Age:"));
        statsPanel.add(ageLabel);
        
        add(statsPanel, BorderLayout.NORTH);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        addButton(buttonPanel, "Feed", e -> feed());
        addButton(buttonPanel, "Play", e -> play());
        addButton(buttonPanel, "Sleep", e -> sleep());
        addButton(buttonPanel, "Clean", e -> clean());
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private JProgressBar createProgressBar(String name) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(100);
        bar.setStringPainted(true);
        return bar;
    }
    
    private void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        panel.add(button);
    }
    
    private void startGameLoop() {
        timer = new Timer(1000, e -> updatePet());
        timer.start();
    }
    
    private void updatePet() {
        if (!isSleeping) {
            // Decrease stats over time
            hunger = Math.max(0, hunger - random.nextInt(2));
            happiness = Math.max(0, happiness - random.nextInt(2));
            energy = Math.max(0, energy - random.nextInt(2));
            
            // Update UI
            hungerBar.setValue(hunger);
            happinessBar.setValue(happiness);
            energyBar.setValue(energy);
            
            // Update pet status
            updateStatus();
            
            // Age the pet (1 day every minute)
            if (age * 1000 % 60 == 0) {
                age++;
                ageLabel.setText("Age: " + age + " days");
            }
        } else {
            // Pet recovers energy while sleeping
            energy = Math.min(100, energy + 1);
            energyBar.setValue(energy);
            
            if (energy >= 100) {
                wakeUp();
            }
        }
    }
    
    private void updateStatus() {
        if (hunger < 30) {
            statusLabel.setText("Your pet is hungry!");
        } else if (happiness < 30) {
            statusLabel.setText("Your pet is sad!");
        } else if (energy < 30) {
            statusLabel.setText("Your pet is tired!");
        } else {
            statusLabel.setText("Your pet is happy!");
        }
    }
    
    private void feed() {
        if (!isSleeping) {
            hunger = Math.min(100, hunger + 30);
            energy = Math.max(0, energy - 10);
            hungerBar.setValue(hunger);
            energyBar.setValue(energy);
            playAnimation("😋");
        } else {
            JOptionPane.showMessageDialog(this, "Can't feed while sleeping!");
        }
    }
    
    private void play() {
        if (!isSleeping && energy > 20) {
            happiness = Math.min(100, happiness + 30);
            energy = Math.max(0, energy - 20);
            hunger = Math.max(0, hunger - 10);
            happinessBar.setValue(happiness);
            energyBar.setValue(energy);
            hungerBar.setValue(hunger);
            playAnimation("😄");
        } else if (isSleeping) {
            JOptionPane.showMessageDialog(this, "Can't play while sleeping!");
        } else {
            JOptionPane.showMessageDialog(this, "Your pet is too tired to play!");
        }
    }
    
    private void sleep() {
        if (!isSleeping) {
            isSleeping = true;
            petImage.setText("😴");
            statusLabel.setText("Your pet is sleeping...");
        } else {
            wakeUp();
        }
    }
    
    private void wakeUp() {
        isSleeping = false;
        petImage.setText("🐱");
        updateStatus();
    }
    
    private void clean() {
        if (!isSleeping) {
            happiness = Math.min(100, happiness + 10);
            happinessBar.setValue(happiness);
            playAnimation("✨");
        } else {
            JOptionPane.showMessageDialog(this, "Can't clean while sleeping!");
        }
    }
    
    private void playAnimation(String emoji) {
        String originalEmoji = petImage.getText();
        petImage.setText(emoji);
        Timer animationTimer = new Timer(1000, e -> petImage.setText(originalEmoji));
        animationTimer.setRepeats(false);
        animationTimer.start();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Runner().setVisible(true);
        });
    }
}