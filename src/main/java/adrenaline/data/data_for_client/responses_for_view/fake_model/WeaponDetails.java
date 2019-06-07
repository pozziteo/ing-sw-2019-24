package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

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

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<String> getAmmoCost() {
        return this.ammoCost;
    }

    public List<String> getGrabCost() {
        return this.grabCost;
    }

    public List<String> getAdditionalCost(int n) {
        if (n == 0)
            return this.additionalCost1;
        else
            return this.additionalCost2;
    }
}
