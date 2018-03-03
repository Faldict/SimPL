package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        // DONE
        // Default Type Environment is a context with no symbol.
        E = empty;

        /*
        E = new TypeEnv () {
        public Type get(Symbol x) {
        return null;
        }

        public String toString() {
        return "";
        }
        };
        */
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
