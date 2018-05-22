package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Neg extends UnaryExpr {

    public Neg(Expr e) {
        super(e);
    }

    public String toString() {
        return "~" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult e_type = e.typecheck(E);
        Substitution sub = e_type.s;
        sub = sub.apply(e_type.t).unify(Type.INT).compose(sub);

        return TypeResult.of(sub, Type.INT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left = e.eval(s);
        int result = -((IntValue) left).n;

        return new IntValue(result);
    }
}
