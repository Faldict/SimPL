package simpl.interpreter;

import simpl.parser.Symbol;

public class Env {

    private final Env E;
    private final Symbol x;
    private final Value v;

    private Env() {
        E = null;
        x = null;
        v = null;
    }

    public static Env empty = new Env() {
        public Value get(Symbol y) {
            return null;
        }

        public Env clone() {
            return this;
        }
    };

    public Env(Env E, Symbol x, Value v) {
        this.E = E;
        this.x = x;
        this.v = v;
    }

    public Value get(Symbol y) throws RuntimeError {
        if (x == y) {
            return v;
        } else {
            if (E == null) {
                throw new RuntimeError(x.toString() + " not defined.");
            }
            return E.get(y);
        }
    }

    public Env clone() {
        // TODO
        return null;
    }

    // GC
    public Value getValue() {
        return v;
    }

    public Env getEnv() {
        return E;
    }
}
