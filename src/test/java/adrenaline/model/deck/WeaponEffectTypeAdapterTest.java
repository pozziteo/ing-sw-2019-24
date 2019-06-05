package adrenaline.model.deck;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WeaponEffectTypeAdapterTest {

    private static Gson parser;
    private static final String WEAPONS_PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "weapons" + File.separatorChar;

    private enum WeaponsFiles {
        CYBERBLADE(WEAPONS_PATH + "cyberblade.json"),
        ELECTROSCYTHE(WEAPONS_PATH + "electroscythe.json"),
        FLAMETHROWER(WEAPONS_PATH + "flamethrower.json"),
        FURNACE(WEAPONS_PATH + "furnace.json"),
        GRENADELAUNCHER(WEAPONS_PATH + "grenadelauncher.json"),
        HEATSEEKER(WEAPONS_PATH + "heatseeker.json"),
        HELLION(WEAPONS_PATH + "hellion.json"),
        LOCKRIFLE(WEAPONS_PATH + "lockrifle.json"),
        MACHINEGUN(WEAPONS_PATH + "machinegun.json"),
        PLASMAGUN(WEAPONS_PATH + "plasmagun.json"),
        POWERGLOVE(WEAPONS_PATH + "powerglove.json"),
        RAILGUN(WEAPONS_PATH + "railgun.json"),
        ROCKETLAUNCHER(WEAPONS_PATH + "rocketlauncher.json"),
        SHOCKWAVE(WEAPONS_PATH + "shockwave.json"),
        SHOTGUN(WEAPONS_PATH + "shotgun.json"),
        SLEDGEHAMMER(WEAPONS_PATH + "sledgehammer.json"),
        THOR(WEAPONS_PATH + "thor.json"),
        TRACTORBEAM(WEAPONS_PATH + "tractorbeam.json"),
        VORTEXCANNON(WEAPONS_PATH + "vortexcannon.json"),
        WHISPER(WEAPONS_PATH + "whisper.json"),
        ZX2(WEAPONS_PATH + "zx2.json");

        private String path;

        WeaponsFiles(String path) {
            this.path = path;
        }

        public String getPath() {
            return this.path;
        }
    }

    @BeforeAll
    static void createParser() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(WeaponEffect.class, new WeaponEffectTypeAdapter());
        parser = builder.create();
    }

    @Test
    void readWeapons() throws FileNotFoundException {
        for (WeaponsFiles file : WeaponsFiles.values()) {
            JsonReader reader = new JsonReader(new FileReader(file.getPath()));
            System.out.println("processing " + file.getPath());
            try {
                while (reader.peek() != JsonToken.END_DOCUMENT) {
                    WeaponEffect effect = parser.fromJson(reader, WeaponEffect.class);
                    assertNotNull(effect);
                    assertNotNull(effect.getEffects());
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    @Test
    void readTargets() throws FileNotFoundException {
        for (WeaponsFiles file : WeaponsFiles.values ()) {
            JsonReader reader = new JsonReader(new FileReader(file.getPath()));
            try {
                while (reader.peek() != JsonToken.END_DOCUMENT) {
                    WeaponEffect effect = parser.fromJson(reader, WeaponEffect.class);
                    System.out.println(file.path + ": " + effect.getTargets ().getType () + ", " + effect.getTargets ().getValue () + ", " + effect.getTargets ().getConstraints ());
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
}