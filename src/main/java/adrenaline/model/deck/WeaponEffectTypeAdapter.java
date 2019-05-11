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
                    //TODO
//                    effect = readOptionalEffect(reader);
                    break;
                case "alternativeMode":
                    //TODO
//                    effect = readAlternativeMode(reader);
                    break;
                default: throw new IOException();
            }
        }
        return effect;
    }

    private BaseEffect readBaseEffect(JsonReader reader) throws IOException {
        if (reader.nextName().equals("effect"))
            reader.beginObject();
        else throw new IOException();

        WeaponEffectRequirement requirement;
        List<AtomicWeaponEffect> effects;
        if (reader.nextName().equals("requirement")) {
            requirement = createRequirement(reader);
        }
        else if (reader.nextName().equals("target")) {
            //TODO
        }
        else if (reader.nextName().equals("effects")) {
            effects = generateEffects(reader);
        }
        //TODO
        return null;
    }

    private WeaponEffectRequirement createRequirement(JsonReader reader) throws IOException {
        WeaponEffectRequirement requirement = null;
        reader.beginObject();
        reader.nextName();
        String reqType = reader.nextString();
        if (reqType.equals("distance")) {
            reader.nextName();
            int minDistance = reader.nextInt();
            reader.nextName();
            int maxDistance = reader.nextInt();
            requirement = new DistanceRequirement(minDistance, maxDistance);
        }
        else if (reqType.equals("visibility")) {
            reader.nextName();
            boolean visible = reader.nextBoolean();
            requirement = new VisibilityRequirement(visible);
        }
        else if (reqType.equals("room")) {
            reader.nextName();
            String roomIdentifier = reader.nextString();
            requirement = new RoomRequirement(roomIdentifier);
        }
        else if (reqType.equals("direction")) {
            requirement = new DirectionRequirement();
        }
        else if (reqType.equals("limitedDirection")) {
            reader.nextName();
            int maxDistance = reader.nextInt();
            requirement = new LimitedDirectionRequirement(maxDistance);
        }
        else if (reqType.equals("moveToVisible")) {
            reader.nextName();
            int maxDistance = reader.nextInt();
            requirement = new MoveToVisibleRequirement(maxDistance);
        }

        reader.endObject();
        return requirement;
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
                default:
                    while (reader.peek() != JsonToken.END_OBJECT)
                        reader.skipValue();
            }

            if (effect != null)
                effects.add(effect);
            reader.endObject();
        }
        reader.endArray();
        return effects;
    }
}


