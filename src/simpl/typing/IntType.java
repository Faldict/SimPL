package simpl.typing;

final class IntType extends Type {

    protected IntType() {
    }

    @Override
    public boolean isEqualityType() {
        // TODO
        return false;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // DONE
        // Copy UnitType.unify()
        if (t instanceof TypeVar) {
            return t.unify(this);
        }
        if (t instanceof IntType) {
            return Substitution.IDENTITY;
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // DONE
        // Copy UnitType.replace()
        return Type.INT;
    }

    public String toString() {
        return "int";
    }
}
