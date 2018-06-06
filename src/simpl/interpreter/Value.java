package simpl.interpreter;

public abstract class Value {

    public static final Value NIL = new NilValue();
    public static final Value UNIT = new UnitValue();

    public abstract boolean equals(Object other);

    // GC
    public int mark = 0;
}
