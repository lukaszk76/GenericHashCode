import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

public class HashCodeSupplier {

    private HashCodeSupplier() {
    }

    private static int calculateHashForB(B b) {
        Supplier[] hashableValuesArray = new Supplier[]
        {b::getA, b::getB};
        System.out.println("B");
        return genericCalculateHashCode(hashableValuesArray);
    }

    private static int calculateHashForC(C c) {
        Supplier[] hashableValuesArray = new Supplier[]
                {c::getA, c::getC};
        System.out.println("C");
        return genericCalculateHashCode(hashableValuesArray);
    }

    private static <T> int calculateHashCodeForAnItem(int previousHash, @NotNull Supplier<T> hashableValue) {
        HashMap<Class, Function<T, Integer>> aDispatchMap = new HashMap<>();
        aDispatchMap.put(B.class, b -> calculateHashForB((B) b));
        aDispatchMap.put(C.class, c -> calculateHashForC((C) c));

        int hash;
        if (hashableValue.get() instanceof A) {
            hash = aDispatchMap.get(hashableValue.get().getClass()).apply(hashableValue.get());
        } else if (hashableValue.get() == null) {
            hash = 0;
        } else {
            hash = hashableValue.get().hashCode();
        }

        return (int) 31 * previousHash + hash;
    }

    private static int genericCalculateHashCode(Supplier[] hashableValuesArray) {
        List<Supplier> hashableValuesList = new ArrayList<Supplier>(Arrays.asList(hashableValuesArray));
        AtomicInteger hash = new AtomicInteger(7);
        hashableValuesList.forEach((Supplier hashableValue) -> hash.set(calculateHashCodeForAnItem(hash.get(), hashableValue)));

        return hash.get();
    }

    public static int calculateHashCode(@NotNull RequestModel requestModel) {

        Supplier[] hashableValuesArray = new Supplier[]
                {requestModel::getMyName, requestModel::getMyNumber, requestModel::getInnerClass};

        return genericCalculateHashCode(hashableValuesArray);
    }
}
