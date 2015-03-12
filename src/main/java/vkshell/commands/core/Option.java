package vkshell.commands.core;

import java.lang.reflect.Field;

public class Option {
    private final Field field;

    public Option(Field field) {
        this.field = field;
    }

    public String getName() {
        return field.getName();
    }

    public Field getField() {
        return field;
    }

    public boolean isString() {
        return (this.getField().getType() == String.class);
    }

    public boolean isBoolean() {
        return (this.getField().getType() == Boolean.TYPE) || (this.getField().getType() == Boolean.class);
    }

    public boolean isInteger() {
        return (this.getField().getType() == Integer.TYPE) || (this.getField().getType() == Integer.class);
    }

    public void setFromString(ICommand cmd, String value) throws IllegalArgumentException {
        if (value.startsWith("'") && value.endsWith("'")
                || value.startsWith("\"") && value.endsWith("\"")
                ) {
            value = value.substring(1, value.length() - 1);
        }
        Field field = getField();
        try {
            if (isString()) {
                field.set(cmd, value);
            } else if (isBoolean()) {
                field.set(cmd, true);
            } else if (isInteger()) {
                field.set(cmd, Integer.parseInt(value));
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getField().toGenericString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(this.getClass() == obj.getClass())) return false;
        Option other = (Option) obj;
        return this.field.equals(other.field);
    }

    @Override
    public int hashCode() {
        return field.hashCode();
    }
}
