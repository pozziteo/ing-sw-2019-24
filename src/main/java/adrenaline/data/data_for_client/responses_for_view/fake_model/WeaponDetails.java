package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains information for weapons shown to the client
 */

public class WeaponDetails implements Serializable {
    private String name;
    private String description;
    private List<String> ammoCost;
    private List<String> grabCost;
    private List<String> additionalCost1;
    private List<String> additionalCost2;

    public WeaponDetails(String name, String desc, List<String> ammoCost, List<String> grabCost, List<String> additionalCost1, List<String> additionalCost2) {
        this.name = name;
        this.description = desc;
        this.ammoCost = ammoCost;
        this.grabCost = grabCost;
        this.additionalCost1 = additionalCost1;
        this.additionalCost2 = additionalCost2;
    }

    /**
     * Getter method for weapon name
     * @return name
     */

    public String getName() {
        return this.name;
    }

    /**
     * Getter method for description
     * @return description
     */

    public String getDescription() {
        return this.description;
    }

    /**
     * Getter method for ammo cost
     * @return ammo cost
     */

    public List<String> getAmmoCost() {
        return this.ammoCost;
    }

    /**
     * Getter method for grab cost
     * @return grab cost
     */

    public List<String> getGrabCost() {
        return this.grabCost;
    }

    /**
     * Getter method for additional cost
     * @param n is the effect number
     * @return additional cost
     */

    public List<String> getAdditionalCost(int n) {
        if (n == 0)
            return this.additionalCost1;
        else
            return this.additionalCost2;
    }
}
