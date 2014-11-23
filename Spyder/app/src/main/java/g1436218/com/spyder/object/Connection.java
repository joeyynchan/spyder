package g1436218.com.spyder.object;

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

    public int getStrength(){
        return strength;
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
