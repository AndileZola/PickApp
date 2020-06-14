package backend;

/**
 * Created by Andile on 04/06/2017.
 */
public class GenderModel
{
    public String id;
    public String name;

    public GenderModel(String id,String name)
    {
        this.id = id;
        this.name = name;
    }
    public String getId() {return id;}
    public String getName(){return name;}
}
