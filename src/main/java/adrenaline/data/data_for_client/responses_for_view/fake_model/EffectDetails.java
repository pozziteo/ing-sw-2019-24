package adrenaline.data.data_for_client.responses_for_view.fake_model;

import adrenaline.data.data_for_client.DataForClient;

import java.io.Serializable;

public class EffectDetails extends DataForClient implements Serializable {
    private String effectType;
    private boolean alternativeMode;
    private boolean usableBeforeBase;

    public EffectDetails(String effect, boolean alternative, boolean usable) {
        this.effectType = effect;
        this.alternativeMode = alternative;
        this.usableBeforeBase = usable;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public boolean isAlternativeMode() {
        return this.alternativeMode;
    }

    public boolean isUsableBeforeBase() {
        return this.usableBeforeBase;
    }
}
