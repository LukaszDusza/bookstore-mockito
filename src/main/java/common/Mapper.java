package common;

public interface Mapper<F,T> {

    T map(F from);

}
