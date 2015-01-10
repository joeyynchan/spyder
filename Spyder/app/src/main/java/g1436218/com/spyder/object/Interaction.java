package g1436218.com.spyder.object;

public class Interaction {

    private String username;
    private int strength;
    private String profile_pic_url;
    private String gcm_id;

    public Interaction(String username, int strength){
        this.username = username;
        this.strength = strength;
        this.profile_pic_url = "Dummy Profile Picture URL";
        this.gcm_id = "APA91bFhURIRWQd5YLy-fKtdQi0j3qNHIUi372Ej3rxfTUk5bszEeZvMvXfabLg5ViP7rp3dgoEDESNBfE7xZ7aO8626oGFEu1cv5D0n9swpknb5Kqm1iZ80oH6UJ3vArlgHSiLEUBQdM4x2366jwATI2b93N5IMsg";
    }

    public String getUsername(){
        return username;
    }

    public int getStrength(){
       return strength;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public String getGcm_id() {
        return gcm_id;
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
