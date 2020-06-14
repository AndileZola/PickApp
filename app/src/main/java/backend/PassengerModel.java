package backend;

/**
 * Created by Andile on 25/03/2017.
 */
public class PassengerModel
{
    String Id;
    String FirstName;
    String LastName;
    String CellNumber;
    String Gender;
    String Regular;    //Shows other passengers that the this passenger is trust worthy incase they are with them in a taxi,the value accumulates the more you use the app
    public PassengerModel(String Id, String FirstName, String LastName,String Gender, String CellNumber, String Regular)
    {
        this.Id         =   Id;
        this.FirstName  =   FirstName;
        this.LastName   =   LastName;
        this.CellNumber =   CellNumber;
        this.Regular    =   Regular;
        this.Gender     =   Gender;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getId() {
        return Id;
    }

    public String getCellNumber() {
        return CellNumber;
    }

    public String getRegular() {
        return Regular;
    }
    public String getGender() {
        return Gender;
    }
}
