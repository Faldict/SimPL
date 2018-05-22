package simpl.parser.ast;

import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.*;

public class Pair extends BinaryExpr {

    public Pair(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(pair " + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lt = l.typecheck(E);
        Substitution sub = lt.s;

        TypeResult rt = r.typecheck(sub.compose(E));
        sub = rt.s.compose(sub);
        return TypeResult.of(sub, new PairType(sub.apply(lt.t), sub.apply(rt.t)));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value lv = l.eval(s);
        Value rv = r.eval(s);

        return new PairValue(lv, rv);
    }
}
