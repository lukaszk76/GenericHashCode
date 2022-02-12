import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class HashCodeSupplier {

    private HashCodeSupplier() {
    }

    private static <T> int calculateHashCodeForAnItem(int hash, @NotNull Supplier<T> hashableValue) {
        return (int) 31 * hash + hashableValue.get().hashCode();
    }

    private static int genericCalculateHashCode(Supplier[] hashableValuesArray) {
        List<Supplier> hashableValuesList = new ArrayList<Supplier>(Arrays.asList(hashableValuesArray));
        AtomicInteger hash = new AtomicInteger(7);
        hashableValuesList.forEach((Supplier hashableValue) -> hash.set(calculateHashCodeForAnItem(hash.get(), hashableValue)));

        return hash.get();
    }

    public static int calculateHashCode(@NotNull RequestModel requestModel) {

        Supplier[] hashableValuesArray = new Supplier[]
                {requestModel::getMyName, requestModel::getMyNumber};

        return genericCalculateHashCode(hashableValuesArray);
    }
}
