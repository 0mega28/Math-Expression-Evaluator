package model;

public sealed interface Num {
    record Int(Long value) implements Num {
    }

    record Float(Double value) implements Num {
    }

    default Num add(Num value) {
        return switch (this) {
            case Int(var iV1) -> switch (value) {
                case Int(var iV2) -> new Int(iV1 + iV2);
                case Float(var fV2) -> new Float(iV1 + fV2);
            };
            case Float(var fV1) -> switch (value) {
                case Int(var iV2) -> new Float(fV1 + iV2);
                case Float(var fV2) -> new Float(fV1 + fV2);
            };
        };
    }

    default Num sub(Num value) {
        return switch (this) {
            case Int(var iV1) -> switch (value) {
                case Int(var iV2) -> new Int(iV1 - iV2);
                case Float(var fV2) -> new Float(iV1 - fV2);
            };
            case Float(var fV1) -> switch (value) {
                case Int(var iV2) -> new Float(fV1 - iV2);
                case Float(var fV2) -> new Float(fV1 - fV2);
            };
        };
    }

    default Num mult(Num value) {
        return switch (this) {
            case Int(var iV1) -> switch (value) {
                case Int(var iV2) -> new Int(iV1 * iV2);
                case Float(var fV2) -> new Float(iV1 * fV2);
            };
            case Float(var fV1) -> switch (value) {
                case Int(var iV2) -> new Float(fV1 * iV2);
                case Float(var fV2) -> new Float(fV1 * fV2);
            };
        };
    }

    default Num div(Num value) {
        if ((value instanceof Float(var fValue) && fValue.equals(0D)) ||
                (value instanceof Int(var iValue) && iValue.equals(0L))) {
            throw new ArithmeticException("Division By Zero");
        }

        return switch (this) {
            case Int(var iV1) -> switch (value) {
                case Int(var iV2) ->
                    (iV1 % iV2 == 0) ? new Int(iV1 / iV2) : new Float(iV1.doubleValue() / iV2.doubleValue());
                case Float(var fV2) -> new Float(iV1 / fV2);
            };
            case Float(var fV1) -> switch (value) {
                case Int(var iV2) -> new Float(fV1 / iV2);
                case Float(var fV2) -> new Float(fV1 / fV2);
            };
        };
    }

    default Double floatValue() {
        return switch(this) {
            case Int(var iValue) -> iValue.doubleValue();
            case Float(var fValue) -> fValue;
        };
    }

    default Long longValue() {
        return switch(this) {
            case Int(var iValue) -> iValue;
            case Float(var fValue) -> fValue.longValue();
        };
    }
}
