
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MovieTicketsTest {
    private MovieTickets movieTickets;

    @Before
    public void setUp() {
        movieTickets = new MovieTickets();
    }

    @Test
    public void testCalculateTotalTicketPrice() {
        // Test case 1: Regular values
        int numberOfTickets = 3;
        double ticketPrice = 100.0;
        double expectedTotal = 342.0; // (3 * 100) + 14% VAT
        assertEquals(expectedTotal, movieTickets.CalculateTotalTicketPrice(numberOfTickets, ticketPrice), 0.01);

        // Test case 2: Zero tickets
        numberOfTickets = 0;
        expectedTotal = 0.0;
        assertEquals(expectedTotal, movieTickets.CalculateTotalTicketPrice(numberOfTickets, ticketPrice), 0.01);

        // Test case 3: Zero ticket price
        numberOfTickets = 3;
        ticketPrice = 0.0;
        expectedTotal = 0.0;
        assertEquals(expectedTotal, movieTickets.CalculateTotalTicketPrice(numberOfTickets, ticketPrice), 0.01);
    }

    private void assertEquals(double expectedTotal, double v, double v1) {
    }

    @Test
    public void testValidateData() {
        // Test case 1: Valid data
        MovieTicketData validData = new MovieTicketData("Napoleon", 3, 100.0);
        assertTrue(movieTickets.ValidateData(validData));

        // Test case 2: Empty movie name
        MovieTicketData emptyMovieName = new MovieTicketData("", 3, 100.0);
        assertFalse(movieTickets.ValidateData(emptyMovieName));

        // Test case 3: Negative ticket price
        MovieTicketData negativePrice = new MovieTicketData("Napoleon", 3, -50.0);
        assertFalse(movieTickets.ValidateData(negativePrice));

        // Test case 4: Zero ticket price
        MovieTicketData zeroPrice = new MovieTicketData("Napoleon", 3, 0.0);
        assertFalse(movieTickets.ValidateData(zeroPrice));

        // Test case 5: Negative number of tickets
        MovieTicketData negativeTickets = new MovieTicketData("Napoleon", -3, 100.0);
        assertFalse(movieTickets.ValidateData(negativeTickets));

        // Test case 6: Zero number of tickets
        MovieTicketData zeroTickets = new MovieTicketData("Napoleon", 0, 100.0);
        assertFalse(movieTickets.ValidateData(zeroTickets));
    }
}