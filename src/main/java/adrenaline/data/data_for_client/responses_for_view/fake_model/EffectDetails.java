package adrenaline.data.data_for_client.responses_for_view.fake_model;

import adrenaline.data.data_for_client.DataForClient;

import java.io.Serializable;

/**
 * This class contains information for weapon effects shown to the client
 */

public class EffectDetails extends DataForClient implements Serializable {
    private String effectType;
    private boolean alternativeMode;
    private boolean usableBeforeBase;

    public EffectDetails(String effect, boolean alternative, boolean usable) {
        this.effectType = effect;
        this.alternativeMode = alternative;
        this.usableBeforeBase = usable;
    }

    /**
     * Getter method for effect type
     * @return effect type
     */

    public String getEffectType() {
        return this.effectType;
    }

    /**
     * Getter method to know whether or not the effect is alternative mode
     * @return true if alternative mode, false otherwise
     */

    public boolean isAlternativeMode() {
        return this.alternativeMode;
    }

    /**
     * Getter method to know whether or not the effect is usable before base
     * @return true if usable, false otherwise
     */

    public boolean isUsableBeforeBase() {
        return this.usableBeforeBase;
    }
}
