package com.benz.service.producer.services;

import com.benz.service.producer.model.User;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

      boolean saveUser(User user , Map<String, String> headers) throws IOException;

      boolean updateUser(User user , Map<String, String> headers) throws IOException, JAXBException;

      List<User> readUser(String name) throws IOException, JAXBException;
}
