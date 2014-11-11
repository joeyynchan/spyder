package g1436218.com.spyder.object;

/**
 * Created by Joey on 03/11/2014.
 */
public class Connection {

    private String macAddress;
    private int strength;

    public Connection(String macAddress, int strength){
        this.macAddress = macAddress;
        this.strength = strength;
    }

    public String getMacAddress(){
        return macAddress;
    }

    @Override
    public boolean equals(Object obj){
        if(!obj instanceof Connection){
            return false;
        }
        return macAddress.equals(((Connection) obj).getMacAddress());
    }

    @Override
    public int hashCode(){
        return this.macAddress.hashCode();
    }

    @Override
    public String toString(){
        return macAddress + " : " + strength + "dBm";
    }

}
