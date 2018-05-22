package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Eq extends EqExpr {

    public Eq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " = " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value l_v = l.eval(s);
        Value r_v = r.eval(s);

        if (l_v.equals(r_v)) {
            return new BoolValue(true);
        } else {
            return new BoolValue(false);
        }
    }
}
