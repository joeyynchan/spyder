package g1436218.com.spyder.object;

public class Interaction {

    private Attendee attendee;
    private int strength;

    public Interaction(Attendee attendee, int strength){
        this.attendee = attendee;
        this.strength = strength;
    }

    public String getUsername(){
        return attendee.getUsername();
    }

    public String getName() {
        return attendee.getName();
    }

    public int getStrength(){
       return strength;
    }

    public String getPhoto_url() {
        return attendee.getPhoto_url();
    }

    public String getGcm_id() {
        return attendee.getGcm_id();
    }
    @Override
    public boolean equals(Object obj){

        if(!(obj instanceof Interaction)){
            return false;
        }
        return getUsername().equals(((Interaction) obj).getUsername());
    }

    @Override
    public int hashCode(){
        return this.getUsername().hashCode();
    }

    @Override
    public String toString(){
        return getUsername() + " : " + strength + "dBm";
    }

}
