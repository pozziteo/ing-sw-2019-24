package adrenaline.model.deck;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeaponEffectTypeAdapter extends TypeAdapter<WeaponEffect> {

    @Override
    public void write(JsonWriter writer, WeaponEffect effect) {
//      useless
    }

    @Override
    public WeaponEffect read(JsonReader reader) throws IOException {
        WeaponEffect effect = null;
        if (reader.peek() == JsonToken.BEGIN_ARRAY)
            reader.beginArray();
        reader.beginObject();
        if (reader.nextName().equals("type")) {
            switch(reader.nextString()) {
                case "baseEffect":
                    effect = readBaseEffect(reader);
                    break;
                case "optionalEffect":
                case "chainEffect":
                    effect = readOptionalEffect(reader, false);
                    break;
                case "alternativeMode":
                    effect = readOptionalEffect(reader, true);
                    break;
                default: throw new IOException();
            }
        }
        reader.endObject();
        if (reader.peek() == JsonToken.END_ARRAY)
            reader.endArray();
        return effect;
    }

    private BaseEffect readBaseEffect(JsonReader reader) throws IOException {
        reader.nextName();
        reader.beginObject();

        WeaponEffectRequirement requirement = null;
        TargetType targets = null;
        List<AtomicWeaponEffect> effects = null;
        while (reader.peek() != JsonToken.END_OBJECT) {
            switch (reader.nextName()) {
                case "requirement":
                    requirement = createRequirement(reader);
                    break;
                case "target":
                    targets = findTargets (reader);
                    break;
                case "effects":
                    effects = generateEffects(reader);
                    break;
                default: reader.skipValue();
            }
        }
        reader.endObject();
        return new BaseEffect(requirement, targets, effects);
    }

    private OptionalEffect readOptionalEffect(JsonReader reader, boolean alternativeMode) throws IOException {
        boolean usableBeforeBase = false;
        boolean chainEffect = false;
        String chainedTo = null;
        if (!alternativeMode) {
            if (reader.nextName().equals("usableBeforeBase")) {
                usableBeforeBase = reader.nextBoolean();
                chainEffect = false;
            } else {
                chainEffect = true;
                chainedTo = reader.nextString();
                reader.nextName();
                usableBeforeBase = reader.nextBoolean();
            }
        }
        reader.nextName();
        reader.beginObject();
        List<Ammo> additionalCost = new ArrayList<>();
        reader.nextName();
        reader.beginArray();
        while (reader.peek() != JsonToken.END_ARRAY) {
            String ammoColor = reader.nextString();
            for (Ammo ammo : Ammo.values())
                if (ammo.getColor().equals(ammoColor)) {
                    additionalCost.add(ammo);
                    break;
                }
        }
        reader.endArray();
        WeaponEffectRequirement requirement = null;
        TargetType targets = null;
        List<AtomicWeaponEffect> effects = null;
        while (reader.peek() != JsonToken.END_OBJECT) {
            switch (reader.nextName()) {
                case "target":
                    targets = findTargets (reader);
                    break;
                case "requirement":
                    requirement = createRequirement(reader);
                    break;
                case "effects":
                    effects = generateEffects(reader);
                    break;
                default: reader.skipValue();
            }
        }
        reader.endObject();
        if (!chainEffect)
            return new OptionalEffect(requirement, targets, effects, additionalCost, usableBeforeBase, alternativeMode);
        else
            return new ChainEffect(requirement, targets, effects, additionalCost, usableBeforeBase, alternativeMode, chainedTo);
    }


    private WeaponEffectRequirement createRequirement(JsonReader reader) throws IOException {
        WeaponEffectRequirement requirement = null;
        reader.beginObject();
        reader.nextName();
        String reqType = reader.nextString();
        switch (reqType) {

            case "distance":
                reader.nextName();
                int minDistance = reader.nextInt();
                reader.nextName();
                int maxDistance = reader.nextInt();
                requirement = new DistanceRequirement(minDistance, maxDistance);
                break;

            case "visibility":
                reader.nextName();
                boolean visible = reader.nextBoolean();
                requirement = new VisibilityRequirement(visible);
                break;

            case "room":
                reader.nextName();
                String roomIdentifier = reader.nextString();
                requirement = new RoomRequirement(roomIdentifier);
                break;

            case "direction":
                requirement = new DirectionRequirement();
                break;

            case "limitedDirection":
                reader.nextName();
                int maxDirDistance = reader.nextInt();
                requirement = new LimitedDirectionRequirement(maxDirDistance);
                break;

            case "moveToVisible":
                reader.nextName();
                int maxMovements = reader.nextInt();
                requirement = new MoveToVisibleRequirement(maxMovements);
                break;

            default: reader.skipValue();
        }

        reader.endObject();
        return requirement;
    }

    private TargetType findTargets(JsonReader reader) throws IOException {
        TargetType targets = null;
        String targetType;
        String targetValue;
        String areaType;
        ArrayList<String> constraints = new ArrayList<> ();
        reader.beginObject ();
        while (reader.peek() != JsonToken.END_OBJECT) {
            reader.nextName ();
            targetType = reader.nextString ();
            reader.nextName();
            targetValue = reader.nextString ();
            reader.nextName();
            areaType = reader.nextString ();
            reader.nextName ();
            reader.beginArray ();
            while (reader.peek() != JsonToken.END_ARRAY) {
                constraints.add(reader.nextString ());
            }
            reader.endArray ();
            targets = new TargetType (targetType, targetValue, areaType, constraints);
        }
        reader.endObject ();
        if (targets == null) {
            targets = new TargetType ();
        }
        return targets;
    }

    private List<AtomicWeaponEffect> generateEffects(JsonReader reader) throws IOException {
        List<AtomicWeaponEffect> effects = new ArrayList<>();
        AtomicEffectsFactory factory = new AtomicEffectsFactory();
        reader.beginArray();
        while (reader.peek() != JsonToken.END_ARRAY) {
            reader.beginObject();
            reader.nextName();
            AtomicWeaponEffect effect = null;
            switch (reader.nextString()) {
                case "damage":
                    reader.nextName();
                    int damage = reader.nextInt();
                    reader.nextName();
                    int marks = reader.nextInt();
                    effect = factory.createBaseDamageEffect(damage, marks);
                    break;
                case "additionalDamage":
                    reader.nextName();
                    int additionalDamage = reader.nextInt();
                    effect = factory.createAdditionalDamageEffect(additionalDamage);
                    break;
                case "squareBasedDamage":
                    reader.nextName();
                    int squareDamage = reader.nextInt();
                    reader.nextName();
                    int squareMarks = reader.nextInt();
                    effect = factory.createSquareBasedDamage(squareDamage, squareMarks);
                    break;
                case "roomBasedDamage":
                    reader.nextName();
                    int roomDamage = reader.nextInt();
                    reader.nextName();
                    int roomMarks = reader.nextInt();
                    effect = factory.createRoomBasedDamage(roomDamage, roomMarks);
                    break;
                case "movement":
                    reader.nextName();
                    String executor = reader.nextString();
                    reader.nextName();
                    int movements = reader.nextInt();
                    effect = factory.createGenericMovementEffect(executor, movements);
                    break;
                case "moveToAttacker":
                    effect = factory.createMoveToAttackerPosition();
                    break;
                case "moveToTarget":
                    effect = factory.createMoveToTargetPosition();
                    break;
                case "moveTargetToVisibleSquare":
                    reader.nextName();
                    int maxMovements = reader.nextInt();
                    effect = factory.createMoveTargetToVisibleSquare(maxMovements);
                    break;
                case "moveToSquare":
                    reader.nextName();
                    String exec = reader.nextString();
                    effect = factory.createMoveToSquare(exec);
                    break;
                default: reader.skipValue();
            }

            if (effect != null)
                effects.add(effect);
            reader.endObject();
        }
        reader.endArray();
        return effects;
    }
}


