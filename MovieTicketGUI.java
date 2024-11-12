import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

interface IMovieTickets {
    double CalculateTotalTicketPrice(int numberOfTickets, double ticketPrice);
    boolean ValidateData(MovieTicketData movieTicketData);
}

class MovieTicketData {
    String movieName;
    int numberOfTickets;
    double ticketPrice;

    public MovieTicketData(String movieName, int numberOfTickets, double ticketPrice) {
        this.movieName = movieName;
        this.numberOfTickets = numberOfTickets;
        this.ticketPrice = ticketPrice;
    }
}

class MovieTickets implements IMovieTickets {
    private static final double VAT_RATE = 0.14;

    @Override
    public double CalculateTotalTicketPrice(int numberOfTickets, double ticketPrice) {
        double total = numberOfTickets * ticketPrice;
        return total + (total * VAT_RATE);
    }

    @Override
    public boolean ValidateData(MovieTicketData movieTicketData) {
        if (movieTicketData.movieName == null || movieTicketData.movieName.isEmpty()) {
            return false;
        }
        if (movieTicketData.ticketPrice <= 0 || movieTicketData.numberOfTickets <= 0) {
            return false;
        }
        return true;
    }
}

public class MovieTicketGUI extends JFrame {
    private JComboBox<String> movieComboBox;
    private JTextField ticketPriceField, numberOfTicketsField;
    private JTextArea reportArea;
    private MovieTickets movieTickets = new MovieTickets();

    public MovieTicketGUI() {
        setTitle("Movie Tickets");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for inputs
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Movie:"));
        movieComboBox = new JComboBox<>(new String[]{"Napoleon", "Oppenheimer", "Damsel"});
        inputPanel.add(movieComboBox);

        inputPanel.add(new JLabel("Number of Tickets:"));
        numberOfTicketsField = new JTextField();
        inputPanel.add(numberOfTicketsField);

        inputPanel.add(new JLabel("Ticket Price:"));
        ticketPriceField = new JTextField();
        inputPanel.add(ticketPriceField);

        add(inputPanel, BorderLayout.NORTH);

        // Center area for report display
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processMenuItem = new JMenuItem("Process");
        processMenuItem.addActionListener(e -> processTicket());
        JMenuItem clearMenuItem = new JMenuItem("Clear");
        clearMenuItem.addActionListener(e -> clearFields());

        toolsMenu.add(processMenuItem);
        toolsMenu.add(clearMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);
    }

    private void processTicket() {
        String movieName = (String) movieComboBox.getSelectedItem();
        try {
            int numberOfTickets = Integer.parseInt(numberOfTicketsField.getText());
            double ticketPrice = Double.parseDouble(ticketPriceField.getText());

            MovieTicketData data = new MovieTicketData(movieName, numberOfTickets, ticketPrice);

            if (movieTickets.ValidateData(data)) {
                double totalPrice = movieTickets.CalculateTotalTicketPrice(numberOfTickets, ticketPrice);
                String report = String.format(
                        "MOVIE TICKET REPORT:\n" +
                                "MOVIE NAME: %s\n" +
                                "MOVIE TICKET PRICE: R %.2f\n" +
                                "NUMBER OF TICKETS: %d\n" +
                                "TOTAL TICKET PRICE: R %.2f\n", movieName, ticketPrice, numberOfTickets, totalPrice);

                reportArea.setText(report);
                saveReportToFile(report);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid data. Please check inputs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for ticket price and number of tickets.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveReportToFile(String report) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"))) {
            writer.write(report);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving report to file.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        numberOfTicketsField.setText("");
        ticketPriceField.setText("");
        reportArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MovieTicketGUI().setVisible(true);
        });
    }
}