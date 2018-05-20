package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class ArithExpr extends BinaryExpr {

    public ArithExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult l_type = l.typecheck(E);
        Substitution sub = l_type.s;
        sub = sub.apply(l_type.t).unify(Type.INT).compose(sub);

        TypeResult r_type = r.typecheck(sub.compose(E));
        sub = r_type.s.compose(sub);
        sub = sub.apply(r_type.t).unify(Type.INT).compose(sub);

        return TypeResult.of(sub, Type.INT);
    }
}
