package g1436218.com.spyder.object;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Joey on 27/11/2014.
 */
public class InteractionPackage extends ArrayList<Interactions> {

    private Interactions interactions;

    public InteractionPackage() {
        this.interactions = new Interactions();
    }

    public Interactions getInteractions() {
        return interactions;
    }

    public void createInteractions() {
        interactions = new Interactions();
    }

    public void addInteraction(Interaction interaction) {
        interactions.add(interaction);
    }

    public void addInteractionsToPackage() {
        add(interactions);
    }

    public boolean isPackageEmpty() {
        if (!isEmpty()) {
            Iterator<Interactions> iterator = this.iterator();
            while(iterator.hasNext()) {
                Interactions interactions = iterator.next();
                if (!interactions.isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

}
