package uqac.dim.muscuboost.ui.schedule.fragment;

class Wrapper<T> {

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

}