package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

public class WeaponDetails implements Serializable {
    private String name;
    private String description;
    private List<String> ammoCost;
    private List<String> grabCost;

    public WeaponDetails(String name, String desc, List<String> ammoCost, List<String> grabCost) {
        this.name = name;
        this.description = desc;
        this.ammoCost = ammoCost;
        this.grabCost = grabCost;
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
}
