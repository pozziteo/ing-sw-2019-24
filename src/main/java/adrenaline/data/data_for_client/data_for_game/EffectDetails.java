package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;

import java.io.Serializable;

public class EffectDetails extends DataForClient implements Serializable {
    private String effectType;
    private boolean alternativeMode;
    private boolean usableBeforeBase;
    private String targetType;
    private int targetsAmount;
    private String areaType;

    public EffectDetails(String effect, boolean alternative, boolean usable, String target, int amount, String area) {
        this.effectType = effect;
        this.alternativeMode = alternative;
        this.usableBeforeBase = usable;
        this.targetType = target;
        this.targetsAmount = amount;
        this.areaType = area;
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

    public String getTargetType() {
        return this.targetType;
    }

    public int getTargetsAmount() {
        return this.targetsAmount;
    }

    public String getAreaType() {
        return this.areaType;
    }
}
