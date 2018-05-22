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

public class Loop extends Expr {

    public Expr e1, e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult e1_type = e1.typecheck(E);
        Substitution sub = e1_type.s;
        sub = sub.apply(e1_type.t).unify(Type.BOOL).compose(sub);

        TypeResult e2_type = e2.typecheck(E);
        sub = e2_type.s.compose(sub);

        return TypeResult.of(sub, Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        while (true) {
            Value v1  = e1.eval(s);
            if (!((BoolValue) v1).b) {
                break;
            } else {
                e2.eval(s);
            }
        }
        return Value.UNIT;
    }
}
