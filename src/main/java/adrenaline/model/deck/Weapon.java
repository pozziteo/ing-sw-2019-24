package adrenaline.model.deck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Weapon is a Card which contains the description about a single
 * weapon available in the game and its effects
 */
public class Weapon extends Card {

    /**
     * Type is the kind of weapon, which defines its effects
     */
    private WeaponType type;

    private transient BaseEffect baseEffect;

    private transient List<OptionalEffect> optionalEffects;

    protected Weapon() {
        super();
    }

    /**
     * Constructor which initialize the type of weapon
     * @param type is the type of a weapon
     */
    public Weapon(WeaponType type) {
        this.type = type;
        try {
            buildEffects(type);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Getter to obtain the kind of weapon to know its effects
     * @return the type of a weapon
     */
    public String getWeaponsName() {
        return this.type.getName ();
    }

    public String getWeaponsDescription() {
        return this.type.getDescription ();
    }

    public BaseEffect getBaseEffect() {
        return this.baseEffect;
    }

    public List<OptionalEffect> getOptionalEffects() {
        return this.optionalEffects;
    }

    private void buildEffects(WeaponType type) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(WeaponEffect.class, new WeaponEffectTypeAdapter());
        Gson parser = builder.create();
        JsonReader reader = new JsonReader(new FileReader(type.getPath()));
        this.baseEffect = parser.fromJson(reader, WeaponEffect.class);
        this.optionalEffects = new ArrayList<>();
        try {
            while (reader.peek() != JsonToken.END_DOCUMENT) {
                OptionalEffect effect = parser.fromJson(reader, WeaponEffect.class);
                optionalEffects.add(effect);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
