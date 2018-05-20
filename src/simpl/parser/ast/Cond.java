package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Cond extends Expr {

    public Expr e1, e2, e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult e1_type = e1.typecheck(E);
        Substitution sub = e1_type.s;
        sub = sub.apply(e1_type.t).unify(Type.BOOL).compose(sub);

        TypeResult e2_type = e2.typecheck(sub.compose(E));
        sub = e2_type.s.compose(sub);

        TypeResult e3_type = e3.typecheck(sub.compose(E));
        sub = e3_type.s.compose(sub);
        Type e2t = sub.apply(e2_type.t);
        Type e3t = sub.apply(e3_type.t);
        sub = e3t.unify(e2t).compose(sub);

        return TypeResult.of(e3_type.s, e2t);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        try {
            Value flag = e1.eval(s);
            if (((BoolValue) flag).b) {
                return e2.eval(s);
            } else {
                return e3.eval(s);
            }
        } catch (RuntimeException e) {
            throw new RuntimeError(e.getMessage());
        }
    }
}
