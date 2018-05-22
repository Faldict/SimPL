package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class RelExpr extends BinaryExpr {

    public RelExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult lt = l.typecheck(E);
        Substitution sub = lt.s;
        sub = sub.apply(lt.t).unify(Type.INT).compose(sub);

        TypeResult rt = r.typecheck(sub.compose(E));
        sub = rt.s.compose(sub);
        sub = sub.apply(rt.t).unify(Type.INT).compose(sub);

        return TypeResult.of(sub, Type.BOOL);
    }
}
