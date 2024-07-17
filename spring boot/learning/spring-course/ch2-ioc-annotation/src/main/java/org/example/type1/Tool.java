package org.example.type1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("tool")
public class Tool {
    @Value("tool")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "name='" + name + '\'' +
                '}';
    }
}
