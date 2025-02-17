package uploads.models;

import java.util.List;

import lombok.Data;

@Data
public class Author {
 private int id;
 private String name;
 private int age;
 private String gender;
 private List<Book> books;
}
