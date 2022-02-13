public class RequestModel {
    private int myNumber = 0;
    private String myName = "Lukasz";
    private A innerClass = new B();

    public int getMyNumber() {
        return myNumber;
    }

    public String getMyName() {
        return myName;
    }

    public A getInnerClass() {
        return innerClass;
    }
}
