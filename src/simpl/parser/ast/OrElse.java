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

public class OrElse extends BinaryExpr {

    public OrElse(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " orelse " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lt = l.typecheck(E);
        Substitution sub = lt.s;
        sub = sub.apply(lt.t).unify(Type.BOOL).compose(sub);

        TypeResult rt = r.typecheck(sub.compose(E));
        sub = rt.s.compose(sub);
        sub = sub.apply(rt.t).unify(Type.BOOL).compose(sub);

        return TypeResult.of(sub, Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value lv = l.eval(s);
        Value rv = r.eval(s);

        return new BoolValue(((BoolValue) lv).b || ((BoolValue) rv).b);
    }
}
