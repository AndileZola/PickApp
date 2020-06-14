package backend;

/**
 * Created by Andile on 04/06/2017.
 */
public class TownshipModel
{
    public String id;
    public String name;

    public TownshipModel(String id,String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId() {return id;}
    public String getName(){return name;}
}
