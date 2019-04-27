package uqac.dim.muscuboost.ui;

import java.util.ArrayList;
import java.util.List;

public class Wrapper<T> {

    private T item;
    private String name;

    public Wrapper(T item, String name) {
        this.item = item;
        this.name = name;
    }

    public T getItem() {
        return item;
    }

    @Override
    public String toString() {
        return name;
    }

    public static <T> List<Wrapper<T>> wrap(List<T> items,
                                            GetItemNameListener<T> getItemNameListener) {
        List<Wrapper<T>> wrappers = new ArrayList<>();
        for(T item : items)
            wrappers.add(new Wrapper<>(item,
                    getItemNameListener.getItemName(item)));
        return wrappers;
    }

    public interface GetItemNameListener<T> {
        String getItemName(T item);
    }

}