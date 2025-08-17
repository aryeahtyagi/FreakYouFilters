# FreakYouFilters

FreakYouFilters is a lightweight Java library built for **Spring Data JPA Specifications** that allows developers to easily apply dynamic filters on database queries using JSON input.  
This is especially useful for search and filtering functionalities in web applications.

---

## 🚀 Features

- Convert JSON-based filters into **Spring JPA Specifications**.
- Supports multiple filters with operators (`=`, `>`, `<`, `>=`, `<=`, `LIKE`, etc.).
- Easy integration with existing Spring Data Repositories.
- Minimal setup – just add the dependency and start using!

---

## 📦 Installation ( Not Yet published to maven . Please co-operate 😘)

Add the following dependency to your **Gradle** project:

```gradle
implementation 'org.atria:FreakYouFilters:1.0.0'
```

For **Maven**:

```xml
<dependency>
    <groupId>org.atria</groupId>
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

## 📚 Resources

- [Spring Data JPA Specifications](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#specifications)  
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

---

## 📝 Roadmap (Upcoming Features)

- [ ] Add support for filtering fields from **related entities / joined tables**.  
- [ ] Add support for **pagination & sorting**.  
- [ ] Optimize query performance & memory usage for **large datasets**.  

---

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or create pull requests.

---

## 📜 License

This project is licensed under the MIT License.
