package adrenaline.model.deck;

import java.util.ArrayList;

public class TargetType {

    private enum Type {
        NONE(""),
        SINGLE("single"),
        MULTIPLE("multiple"),
        AREA("area");

        private String typeIdentifier;

        Type(String typeIdentifier) {
            this.typeIdentifier = typeIdentifier;
        }
    }

    private Type type;
    private boolean all;
    private int value;
    private ArrayList<String> constraints;

    protected TargetType() {
        this.type = Type.NONE;
    }

    protected TargetType(String typeIdentifier, String targetValue, ArrayList<String> constraints) {
        if (typeIdentifier.equals("single")) {
            this.type = Type.SINGLE;
        } else if (typeIdentifier.equals("multiple")) {
            this.type = Type.MULTIPLE;
        } else if (typeIdentifier.equals("area")){
            this.type = Type.AREA;
        }

        if (targetValue.equals("")) {
            this.all = false;
            this.value = -1;
        } else if (targetValue.equals("all")) {
            this.all = true;
            this.value = -1;
        } else {
            this.all = false;
            this.value = Integer.parseInt(targetValue);
        }

        this.constraints = new ArrayList<> ();
        for (String s : constraints) {
            this.constraints.add (s);
        }
    }

    public String getType() {
        return this.type.typeIdentifier;
    }

    public boolean isAll() {
        return this.all;
    }

    public int getValue() {
        return this.value;
    }

    public ArrayList<String> getConstraints() {
        return this.constraints;
    }
}
