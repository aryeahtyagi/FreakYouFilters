
# FreakYouFilters

FreakYouFilters is a lightweight Java library built for **Spring Data JPA Specifications** that allows developers to easily apply dynamic filters on database queries using JSON input.  
This is especially useful for search and filtering functionalities in web applications.

---

## 🆕 What’s New (v1.1.0)

- **Case Sensitivity Support** 🎉  
  - Control case sensitivity globally when building queries:  
    ```java
    Specification<Student> specification = EasyFilters.generateQuery(json, false); // false = case-insensitive
    ```
  - Override per filter with `"matchCase": "true/false"` in JSON.  
  - Accepts values: `true`, `false`, `yes`, `no` (default = `false`).  

---

## 🚀 Features

- Convert JSON-based filters into **Spring JPA Specifications**.  
- Supports multiple filters with operators (`=`, `>`, `<`, `LIKE`).  
- Supports **nested/complex queries** using dot-notation (e.g., `student.course.author.name`).  
- Supports **case-sensitive and case-insensitive filtering** (global or per filter).  
- Easy integration with existing Spring Data Repositories.  
- Minimal setup – just add the dependency and start using!  

---

## 📦 Installation

Add the following dependency to your **Gradle** project:

```gradle
implementation 'io.github.aryeahtyagi:FreakYouFilters:1.1.0'
```

For **Maven**:

```xml
<dependency>
    <groupId>io.github.aryeahtyagi</groupId>
    <artifactId>FreakYouFilters</artifactId>
    <version>1.1.0</version>
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
      "value": "10000",
      "operator": ">",
      "field": "familyIncome"
    },
    {
      "value": "trucks",
      "operator": "like",
      "field": "courses.name",
      "matchCase": "false"   // optional; true/false or yes/no. Default: false
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
    // Case sensitivity controlled globally here (default = false)
    Specification<Student> specification = EasyFilters.generateQuery(json, false);
    return studentRepository.findAll(specification);
}
```

---

## 📊 Supported Operators

| Operator | JSON Example | Description | Case Sensitivity |
|----------|-------------|-------------|------------------|
| `=` / `eq` | `"operator": "=", "value": "John"` | Exact match | Case-sensitive if enabled |
| `>` | `"operator": ">", "value": "18"` | Greater than (numbers/dates) | N/A |
| `<` | `"operator": "<", "value": "1000"` | Less than (numbers/dates) | N/A |
| `like` | `"operator": "like", "value": "row"` | Pattern search (`%value%`) | Case-sensitive if enabled |

👉 For string-based operators (`=`, `like`), **case-insensitivity** is achieved by normalizing both DB field and input value.  

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
- Case sensitivity:  
  - **Global** → `EasyFilters.generateQuery(json, true/false)`  
  - **Per-filter** → `"matchCase": "true/false"`  

---

## 📝 Roadmap (Upcoming Features)

- [ ] Add support for **pagination & sorting**.  
- [ ] Optimize query performance & memory usage for **large datasets**.  
- [ ] Support for aggregate filters (e.g., `students with courses > 10`).  
- [ ] Add more operators: `>=`, `<=`, `!=`.  

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
