package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class LessEq extends RelExpr {

    public LessEq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " <= " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value lv = l.eval(s);
        Value rv = r.eval(s);

        return new BoolValue(((IntValue) lv).n <= ((IntValue) rv).n);
    }
}
