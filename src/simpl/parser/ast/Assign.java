package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult r_type = r.typecheck(E);
        Substitution sub = r_type.s;

        TypeResult l_type = l.typecheck(E);
        sub = l_type.s.compose(sub);
        sub = sub.apply(l_type.t).unify(new RefType(sub.apply(r_type.t))).compose(sub);
        return TypeResult.of(sub, Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        RefValue l_v = (RefValue) l.eval(s);
        Value r_v = r.eval(s);
        s.M.put(l_v.p, r_v);
        return Value.UNIT;
    }
}
