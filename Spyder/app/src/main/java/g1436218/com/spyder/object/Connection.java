package g1436218.com.spyder.object;

/**
 * Created by Joey on 03/11/2014.
 */
public class Connection {

    private String username;
    private int strength;

    public Connection(String username, int strength){
        this.username = username;
        this.strength = strength;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public boolean equals(Object obj){

        if(!(obj instanceof Connection)){
            return false;
        }
        return username.equals(((Connection) obj).getUsername());
    }

    @Override
    public int hashCode(){
        return this.username.hashCode();
    }

    @Override
    public String toString(){
        return username + " : " + strength + "dBm";
    }

}
