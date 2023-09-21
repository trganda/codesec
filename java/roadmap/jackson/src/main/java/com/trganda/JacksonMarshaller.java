package com.trganda;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trganda.pojo.Person;
import com.trganda.pojo.PhoneNumber;

import java.io.IOException;

public class JacksonMarshaller {

    public static void main(String[] args) throws IOException {
        PhoneNumber phoneNumber = new PhoneNumber(1, 1);
        Person person = new Person("trganda", 18, phoneNumber);

        ObjectMapper objectMapper = new ObjectMapper();
        // String result = objectMapper.writeValueAsString(person);
        // pretty format
        String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
        System.out.println(result);

        Person person1 = objectMapper.readValue(result, Person.class);
        System.out.println(person1);
    }
}
