package backend;

/**
 * Created by Andile on 09/08/2016.
 */
public class TripModel
{
    String destination;
    String origin;
    String seats;
    String fare;
    String date;

    public TripModel(String destination, String origin, String seats, String fare, String date)
    {
        this.destination = destination;
        this.origin = origin;
        this.seats = seats;
        this.fare = fare;
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }

    public String getSeats() {
        return seats;
    }

    public String getFare() {return fare;}

    public String getDate() {return date;}
}
