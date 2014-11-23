package g1436218.com.spyder.object;

public class Interaction {

    private String username;
    private int strength;

    public Interaction(String username, int strength){
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

        if(!(obj instanceof Interaction)){
            return false;
        }
        return username.equals(((Interaction) obj).getUsername());
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
