# API Testing DummyJSON with Rest Assured

This project demonstrates automated API testing using Java, Rest Assured, Cucumber, and TestNG against the [DummyJSON API](https://dummyjson.com/).

## 🔧 Tech Stack
- **Java 17+**
- **Rest Assured**
- **Cucumber (BDD)**
- **TestNG**
- **Maven**

## 🚀 How to Run
1. Clone the repo:
   git clone https://github.com/yourusername/api-testing-dummyjson-restassured.git
   cd api-testing-dummyjson-restassured
2. Run tests via Maven:
   mvn test -DsuiteXml=restassured-cucumber_suite.xml

## ✅ Features Tested
- User login (POST /auth/login)
- Get products (GET /products)
- Add cart (POST /carts/add)
- Get all carts (GET /carts)
- Update cart (PUT /carts/{id})
- Delete cart (DELETE /carts/{id})
### Each scenario includes:
- Status code assertion
- JSON schema validation
- Response content verification (e.g. product ID, price, discount)