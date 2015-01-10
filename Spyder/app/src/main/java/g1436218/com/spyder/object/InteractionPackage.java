package g1436218.com.spyder.object;

import java.util.ArrayList;
import java.util.Iterator;

public class InteractionPackage extends ArrayList<Interactions> {

    private Interactions interactions;
    private Interactions clone;

    public InteractionPackage() {
        this.interactions = new Interactions();
        this.clone = new Interactions();
    }

    public Interactions getInteractions() {
        return interactions;
    }

    public Interactions getClone() {
        return clone;
    }

    public void copyInteractionsToClone() {
        clone = (Interactions) interactions.clone();
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
