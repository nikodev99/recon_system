package who.reconsystem.app.service.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringList {
    private final List<String> elements = new ArrayList<>();

    public StringList(String... elements) {
        this.elements.addAll(Arrays.asList(elements));
    }

    public StringList(List<String> elements) {
        this.elements.addAll(elements);
    }

    public StringList(String element) {
        this.elements.add(element);
    }

    public StringList() {}

    public StringList addElement(String elementToAdd) {
        this.elements.add(elementToAdd);
        return this;
    }

    public StringList addElements(String... elements) {
        this.elements.addAll(Arrays.asList(elements));
        return this;
    }

    public List<String> getElements() {
        return Collections.unmodifiableList(this.elements);
    }

    public String getElement(int index) {
        return getElements().get(index);
    }
}
