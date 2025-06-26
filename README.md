# Petoria

**Petoria** არის Java-ზე დაფუძნებული Spring Boot ვებაპლიკაცია, რომელიც საშუალებას აძლევს მომხმარებლებს გაყოდონ/იყიდონ ცხოველები, განათავსონ დაკარგულ/ნაპოვნ ცხოველების განცხადებები, დაწერონ ბლოგპოსტები, გააკეთონ კომენტარები და ისარგებლონ ცხოველებისთვის განკუთვნილი სერვისებით — უსაფრთხო, მომხმარებელზე მორგებულ გარემოში.

---

## სარჩევი

- [ფუნქციონალი](#ფუნქციონალი)
- [ტექნოლოგიები](#ტექნოლოგიები)
- [დაყენება და გაშვება](#დაყენება-და-გაშვება)
- [პროექტის სტრუქტურა](#პროექტის-სტრუქტურა)
- [API მიმოხილვა](#api-მიმოხილვა)
- [პრობლემები და გეგმები](#პრობლემები-და-გეგმები)

---

## ფუნქციონალი

- რეგისტრაცია და ავტორიზაცია JWT-ით
- ცხოველების დამატება/დათვალიერება (ყიდვა/გაყიდვის განყოფილება)
- დაკარგული და ნაპოვნი ცხოველების განყოფილება
- ბლოგპოსტების გამოქვეყნება და კომენტარების გაკეთება
- საერთო კომენტარების სისტემა (Lost & Found და ბლოგისთვის)
- სერვისების დირექტორია (ვეტკლინიკები, მოვლის სერვისები, მაღაზიები)
- დაცული სექციები მხოლოდ ავტორიზებული მომხმარებლისთვის
- გვერდების ნავიგაცია (pagination), დალაგება, ფილტრები

---

## ტექნოლოგიები

**Backend:**
- Java 21
- Spring Boot (Web, Security, JPA)
- Spring Security + JWT
- PostgreSQL
- Flyway
- Gradle
- Lombok
- DTO + Service + Repository

**Frontend:**
- HTML
- CSS
- JavaScript

**დამხმარე ხელსაწყოები:**
- IntelliJ IDEA
- Git & GitHub
- pgAdmin (PostgreSQL GUI)

---

## დაყენება და გაშვება

1. **გადმოწერეთ პროექტი:**

   git clone https://github.com/თქვენი-username/petoria.git

2. **დააყენეთ მონაცემთა ბაზა:**

- PostgreSQL-ში შექმენით ბაზა სახელით petoria
- application.properties-ში მიუთითეთ დეტალები:
    spring.datasource.username=postgres
    spring.datasource.password=თქვენი_პაროლი

3. **გაშვება:**

    ./gradlew bootRun
   აპლიკაცია იმუშავებს მისამართზე: http://localhost:8080

---

## პროექტის სტრუქტურა

src
└── main
├── java/com/petoria
│    ├── config
│    ├── controller
│    ├── dto
│    ├── model
│    ├── repository
│    ├── security
│    ├── service
│    ├── util
│    └── DemoApplication
└── resources
├── db.migration
├── static
│    ├── css
│    ├── img
│    ├── js
│    └── favicon.ico
├── templates
└── application.yml

---

## API მიმოხილვა

- POST /api/auth/signup — რეგისტრაცია
- POST /api/auth/login — ავტორიზაცია
- GET/POST /api/pets — ცხოველების დამატება/დათვალიერება
- GET/POST /api/lostandfound — დაკარგული და ნაპოვნი ცხოველები
- GET/POST /api/blog — ბლოგპოსტები
- POST /api/comments — კომენტარების დამატება
- GET/POST /api/services — სერვისების განთავსება

ყველა დაცული endpoint მოითხოვს JWT ტოკენს Header-ში:
Authorization: Bearer <ტოკენი>

---

## პრობლემები და გეგმები

დროის პრობლემის გამო,
- ზოგი სექცია ჯერ კიდევ მშენებლობის პროცესშია (მაგ. lost-and-found, messages, profile)
- საჭიროა unit ტესტების დამატება
- UI-სა და CSS სტილის გაუმჯობესება
- ზოგადი backend-ის დახვეწა