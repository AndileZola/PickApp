package backend;

/**
 * Created by Andile on 21/03/2017.
 */
public class DriverModel
{
    String Id;
    String FirstName;
    String LastName;
    String CellNumber;
    String Registration;
    String Rating;
    public DriverModel(String Id, String FirstName, String LastName, String CellNumber, String Registration,String Rating)
    {
        this.Id = Id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.CellNumber = CellNumber;
        this.Registration = Registration;
        this.Rating = Rating;
    }

    public String getCellNumber() {return CellNumber;}

    public String getId() {return Id;}

    public String getLastName() {return LastName;}

    public String getFirstName() {return FirstName;}

    public String getRating() {return Rating;}

    public String getRegistration() {return Registration;}
}
