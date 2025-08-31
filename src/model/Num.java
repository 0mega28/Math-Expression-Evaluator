package model;

import java.util.function.BiFunction;
import java.util.function.Function;

public sealed interface Num {
    record Int(Long value) implements Num {
        @Override
        public String toString() {
            return value.toString();
        }
    }

    record Float(Double value) implements Num {
        @Override
        public String toString() {
            return value.toString();
        }
    }

    public interface BinaryOperator extends BiFunction<Num, Num, Num> {
        public static BinaryOperator add = (n1, n2) -> switch (n1) {
            case Int(var iV1) -> switch (n2) {
                case Int(var iV2) -> new Int(iV1 + iV2);
                case Float(var fV2) -> new Float(iV1 + fV2);
            };
            case Float(var fV1) -> switch (n2) {
                case Int(var iV2) -> new Float(fV1 + iV2);
                case Float(var fV2) -> new Float(fV1 + fV2);
            };
        };

        public static BinaryOperator sub = (n1, n2) -> switch (n1) {
            case Int(var iV1) -> switch (n2) {
                case Int(var iV2) -> new Int(iV1 - iV2);
                case Float(var fV2) -> new Float(iV1 - fV2);
            };
            case Float(var fV1) -> switch (n2) {
                case Int(var iV2) -> new Float(fV1 - iV2);
                case Float(var fV2) -> new Float(fV1 - fV2);
            };
        };

        public static BinaryOperator mult = (n1, n2) -> switch (n1) {
            case Int(var iV1) -> switch (n2) {
                case Int(var iV2) -> new Int(iV1 * iV2);
                case Float(var fV2) -> new Float(iV1 * fV2);
            };
            case Float(var fV1) -> switch (n2) {
                case Int(var iV2) -> new Float(fV1 * iV2);
                case Float(var fV2) -> new Float(fV1 * fV2);
            };
        };

        public static BinaryOperator div = (n1, n2) -> {
            if ((n2 instanceof Float(var fValue) && fValue.equals(0D)) ||
                    (n2 instanceof Int(var iValue) && iValue.equals(0L))) {
                throw new ArithmeticException("Division By Zero");
            }

            return switch (n1) {
                case Int(var iV1) -> switch (n2) {
                    case Int(var iV2) ->
                        (iV1 % iV2 == 0) ? new Int(iV1 / iV2) : new Float(iV1.doubleValue() / iV2.doubleValue());
                    case Float(var fV2) -> new Float(iV1 / fV2);
                };
                case Float(var fV1) -> switch (n2) {
                    case Int(var iV2) -> new Float(fV1 / iV2);
                    case Float(var fV2) -> new Float(fV1 / fV2);
                };
            };
        };
    }

    public interface UnaryOperator extends Function<Num, Num> {
        UnaryOperator negate = n -> switch (n) {
            case Int(var iValue) -> new Int(-iValue);
            case Float(var fValue) -> new Float(-fValue);
        };
    }

    default Double floatValue() {
        return switch (this) {
            case Int(var iValue) -> iValue.doubleValue();
            case Float(var fValue) -> fValue;
        };
    }

    default Long longValue() {
        return switch (this) {
            case Int(var iValue) -> iValue;
            case Float(var fValue) -> fValue.longValue();
        };
    }
}
