package com.benz.service.producer.services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import com.benz.service.producer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ResourceLoader resourceLoader;

    private static final String XML_LOCATION_PATH = "classpath:userRecord.xml";
    private static final String CSV_LOCATION_PATH = "classpath:userData.csv";

    /**
     * This method will write/save user data based on fileType present in Header
     *
     * @param user
     * @param headers
     * @return boolean
     * @throws IOException
     */
    @Override
    public boolean saveUser(User user, Map<String, String> headers) throws IOException {

        boolean userSaveFlag = false;

        String inputFileType = headers.get("filetype");

        log.info("filetype: {} ", inputFileType);

        if ("XML".equalsIgnoreCase(inputFileType)) {

            log.info("inside XML fileType, user data: {}", user);
            User userInput = new User(user.getName(), user.getDob(), user.getSalary(), user.getAge());
            try {
                Resource resource = resourceLoader.getResource(XML_LOCATION_PATH);
                JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(userInput, resource.getFile());

            } catch (JAXBException ex) {
                log.error("error while processing user data in XML fileType", ex);
            }
            userSaveFlag = true;

        } else if ("CSV".equalsIgnoreCase(inputFileType)) {

            try {

                log.info("inside CSV , user data: {}", user);

                Resource resource = resourceLoader.getResource(CSV_LOCATION_PATH);

                User userInput = new User(user.getName(), user.getDob(), user.getSalary(), user.getAge());

                List<User> list = new ArrayList<>(Arrays.asList(userInput));

                CsvMapper mapper = new CsvMapper();
                mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

                CsvSchema schema = CsvSchema.builder().setUseHeader(true)
                        .addColumn("name")
                        .addColumn("dob")
                        .addColumn("salary")
                        .addColumn("age")
                        .build();

                ObjectWriter writer = mapper.writerFor(User.class).with(schema);
                writer.writeValues(resource.getFile()).writeAll(list);
                log.info("Users data saved to csv file");

                userSaveFlag = true;

            } catch (IOException ex) {
                log.error("error while processing user data in CSV fileType", ex);
            }
        } else {
            log.info("Input fileType is incorrect, it should either XML OR CSV");
        }
        return userSaveFlag;
    }

    /**
     * This method will update user data based on fileType present in Header
     *
     * @param user
     * @param headers
     * @return boolean
     * @throws IOException, JAXBException
     */
    @Override
    public boolean updateUser(User user, Map<String, String> headers) throws IOException, JAXBException {

        boolean userUpdateFlag = false;

        String inputFileType = headers.get("filetype");
        log.info("filetype: {} ", inputFileType);

        if ("XML".equalsIgnoreCase(inputFileType)) {

            if (!Objects.isNull(user) && !Objects.isNull(user.getName())
                    && !Objects.isNull(user.getDob())) {

                Resource resource = resourceLoader.getResource(XML_LOCATION_PATH);// XML file path
                JAXBContext jc = JAXBContext.newInstance(User.class);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                User xmlUser = (User) unmarshaller.unmarshal(resource.getFile());

                if (xmlUser.getName().equalsIgnoreCase(user.getName()) &&
                        xmlUser.getDob().equalsIgnoreCase(user.getDob())) {

                    User userInput = new User(user.getName(), user.getDob(), user.getSalary(), user.getAge());
                    try {
                        JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
                        Marshaller marshaller = jaxbContext.createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                        marshaller.marshal(userInput, resource.getFile());
                    } catch (JAXBException ex) {
                        log.error("error while processing user data in XML fileType", ex);
                    }
                    userUpdateFlag = true;
                } else {
                    log.info("user record not found in XML file");
                }
            }
        } else if ("CSV".equalsIgnoreCase(inputFileType)) {

            try {
                if (!Objects.isNull(user) && !Objects.isNull(user.getName())
                        && !Objects.isNull(user.getDob())) {

                    Resource resource = resourceLoader.getResource(CSV_LOCATION_PATH);
                    Reader reader = Files.newBufferedReader(resource.getFile().toPath());
                    CSVReader csvReader =
                            new CSVReaderBuilder(reader).
                                    withSkipLines(1). // Skiping firstline as it is header
                                    build();

                    List<User> csv_objectList = csvReader.readAll().stream().map(data -> {

                        User csvObject = new User();

                        csvObject.setName(data[0]);
                        csvObject.setDob(data[1]);
                        csvObject.setAge(Integer.valueOf(data[2]));
                        csvObject.setSalary(data[3]);

                        return csvObject;
                    }).collect(Collectors.toList());

                    // csv_objectList.forEach(System.out::println);

                    for (User userList : csv_objectList) {

                        if (userList.getName().equalsIgnoreCase(user.getName()) &&
                                userList.getDob().equalsIgnoreCase(user.getDob())) {

                            User userInput = new User(user.getName(), user.getDob(), user.getSalary(), user.getAge());

                            List<User> list = new ArrayList<>(Arrays.asList(userInput));

                            CsvMapper mapper = new CsvMapper();
                            mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

                            CsvSchema schema = CsvSchema.builder().setUseHeader(true)
                                    .addColumn("name")
                                    .addColumn("dob")
                                    .addColumn("salary")
                                    .addColumn("age")
                                    .build();

                            ObjectWriter writer = mapper.writerFor(User.class).with(schema);
                            writer.writeValues(resource.getFile()).writeAll(list);
                            userUpdateFlag = true;
                            break;
                        } else {
                            log.info("user Record not found in CSV file !");
                        }
                    }
                } else {
                    log.info("user 'name' and 'dob' attribute is mandatory for update !");
                }
            } catch (IOException ex) {
                log.error("error while processing user data in CSV fileType", ex);
            }
        } else {

            log.info("Input fileType is incorrect, it should either XML OR CSV");
        }
        return userUpdateFlag;
    }

    /**
     * This method will read all the  user data present in xml and csv file
     *
     * @param name
     * @return List<User>
     * @throws IOException, JAXBException
     */
    @Override
    public List<User> readUser(String name) throws IOException, JAXBException {

        List<User> responseList = new LinkedList<>();

        Resource resource = resourceLoader.getResource(CSV_LOCATION_PATH);
        Reader reader = Files.newBufferedReader(resource.getFile().toPath());

        CSVReader csvReader =
                new CSVReaderBuilder(reader).
                        withSkipLines(1). // Skiping firstline as it is header
                        build();

        List<User> csv_objectList = csvReader.readAll().stream().map(data -> {

            User csvObject = new User();
            csvObject.setName(data[0]);
            csvObject.setDob(data[1]);
            csvObject.setAge(Integer.valueOf(data[2]));
            csvObject.setSalary(data[3]);

            return csvObject;
        }).collect(Collectors.toList());

        for (User user : csv_objectList) {

            if (user.getName().equalsIgnoreCase(name)) {
                responseList.add(user);
            }
        }

        Resource xmlResource = resourceLoader.getResource(XML_LOCATION_PATH);// XML file path
        JAXBContext jc = JAXBContext.newInstance(User.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        User xmlUser = (User) unmarshaller.unmarshal(xmlResource.getFile());

        if (xmlUser.getName().equalsIgnoreCase(name)) {
            User userInput = new User(xmlUser.getName(), xmlUser.getDob(), xmlUser.getSalary(), xmlUser.getAge());
            responseList.add(userInput);
        }

        return responseList;
    }
}
