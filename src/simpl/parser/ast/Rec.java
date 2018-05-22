package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Rec extends Expr {

    public Symbol x;
    public Expr e;

    public Rec(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(rec " + x + "." + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeVar a = new TypeVar(true);
        TypeResult e_type = e.typecheck(TypeEnv.of(E, x, a));
        Substitution sub = e_type.s;
        sub = sub.apply(a).unify(sub.apply(e_type.t)).compose(sub);
        return TypeResult.of(sub, sub.apply(a));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return e.eval(State.of(new Env(s.E, x, new RecValue(s.E, x, e)), s.M, s.p));
    }
}
