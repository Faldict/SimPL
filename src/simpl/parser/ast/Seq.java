package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Seq extends BinaryExpr {

    public Seq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ; " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lt = l.typecheck(E);
        Substitution sub = lt.s;

        TypeResult rt = r.typecheck(sub.compose(E));
        sub = rt.s.compose(sub);
        return TypeResult.of(sub, sub.apply(rt.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        l.eval(s);
        return r.eval(s);
    }
}
