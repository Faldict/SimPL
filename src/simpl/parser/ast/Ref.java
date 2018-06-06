package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.RefType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Ref extends UnaryExpr {

    private static final int HEAP_SIZE = 20;

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lt = e.typecheck(E);
        return TypeResult.of(lt.s, new RefType(lt.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v = e.eval(s);
        int current_p = s.p.get();

        // Garbage Collection
        if (current_p > HEAP_SIZE) {
            // Mark
            Env env = s.E;
            while (env != Env.empty) {
                Value val = env.getValue();
                while (val instanceof RefValue && val.mark == 0) {
                    val.mark = 1;
                    val = s.M.get(((RefValue) val).p);
                }

                val.mark = 1;
                env = env.getEnv();
            }

            // Sweep
            int i = 0;
            while (i < current_p) {
                if (s.M.get(i) != null && s.M.get(i).mark == 0) {
                    int j = i;
                    while (j < current_p) {
                        s.M.put(i, s.M.get(j));
                        j += 4;
                    }

                    current_p -= 4;
                } else {
                    i += 4;
                }
            }
        }

        s.M.put(current_p, v);
        s.p.set(current_p + 4);
        return new RefValue(current_p);
    }
}
