
# FreakYouFilters

FreakYouFilters is a lightweight Java library built for **Spring Data JPA Specifications** that allows developers to easily apply dynamic filters on database queries using JSON input.  
This is especially useful for search and filtering functionalities in web applications.

---

## 🚀 Features

- Convert JSON-based filters into **Spring JPA Specifications**.
- Supports multiple filters with operators (`=`, `>`, `<`, `>=`, `<=`, `LIKE`, etc.).
- Supports **nested/complex queries** using dot-notation (e.g., `student.course.author.name`).
- Easy integration with existing Spring Data Repositories.
- Minimal setup – just add the dependency and start using!

---

## 📦 Installation ( Not Yet published to maven . Please co-operate 😘)

Add the following dependency to your **Gradle** project:

```gradle
implementation 'io.github.aryeahtyagi:FreakYouFilters:1.0.0'
```

For **Maven**:

```xml
<dependency>
    <groupId>io.github.aryeahtyagi</groupId>
    <artifactId>FreakYouFilters</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## 🛠 Usage

### JSON input format (from frontend)
```json
{
  "filters": [
    {
      "value": "10",
      "operator": ">",
      "field": "age"
    },
    {
      "value": "60000",
      "operator": ">",
      "field": "familyIncome"
    },
    {
      "value": "Jk rowling",
      "operator": "=",
      "field": "course.author.name"
    }
  ]
}
```

### Example in Spring Boot

#### Step 1: Extend `JpaSpecificationExecutor`
```java
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
}
```

#### Step 2: Use FreakYouFilters in your Controller
```java
@PostMapping("/students/filter")
public List<Student> filterStudents(@RequestBody String json) {
    Specification<Student> specification = EasyFilters.generateQuery(json);
    return studentRepository.findAll(specification);
}
```

---

## ✅ Requirements

1. Add **Spring Data JPA** dependency.  
2. Your repository must extend `JpaSpecificationExecutor<Entity>`.

---

## 📊 How It Works

FreakYouFilters converts JSON into a chain of **JPA Specifications**.  
- Each filter is parsed into a **Predicate** (criteria).  
- Multiple filters are combined using `AND`.  
- Supports **nested entity traversal** via dot-notation.  

---

## 📝 Roadmap (Upcoming Features)

- [ ] Add support for **pagination & sorting**.  
- [ ] Optimize query performance & memory usage for **large datasets**.  
- [ ] Support for aggregate filters (e.g., `students with courses > 10`).  
- [ ] Case-insensitive string comparisons.  

---

## 📚 Resources

- [Spring Data JPA Specifications](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#specifications)  
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

---

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or create pull requests.

---

## 📜 License

This project is licensed under the MIT License.
