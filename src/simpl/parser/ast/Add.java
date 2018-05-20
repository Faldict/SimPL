package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Add extends ArithExpr {

    public Add(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " + " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left = l.eval(s);
        Value right = r.eval(s);
        int result = 0;
        try {
            result = ((IntValue) left).n + ((IntValue) right).n;
        } catch (RuntimeException e) {
            throw new RuntimeError(e.getMessage());
        }
        return new IntValue(result);
    }
}
