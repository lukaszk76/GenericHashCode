public class GenericHashCode {
    public static void main(String[] args) {
        RequestModel requestModel = new RequestModel();
        System.out.println(HashCodeSupplier.calculateHashCode(requestModel));
    }
}
