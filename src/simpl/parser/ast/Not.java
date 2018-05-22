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

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult e_type = e.typecheck(E);
        Substitution sub = e_type.s;
        sub = e_type.t.unify(Type.BOOL).compose(sub);
        return TypeResult.of(sub, Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left = e.eval(s);
        boolean result = !((BoolValue) left).b;
        return new BoolValue(result);
    }
}
